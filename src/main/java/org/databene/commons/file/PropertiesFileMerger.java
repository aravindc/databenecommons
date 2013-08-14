/*
 * (c) Copyright 2013 by Volker Bergmann. All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, is permitted under the terms of the
 * GNU General Public License (GPL).
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS"
 * WITHOUT A WARRANTY OF ANY KIND. ALL EXPRESS OR IMPLIED CONDITIONS,
 * REPRESENTATIONS AND WARRANTIES, INCLUDING ANY IMPLIED WARRANTY OF
 * MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE OR NON-INFRINGEMENT, ARE
 * HEREBY EXCLUDED. IN NO EVENT SHALL THE COPYRIGHT OWNER OR CONTRIBUTORS BE
 * LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR
 * CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF
 * SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS
 * INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN
 * CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE)
 * ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE
 * POSSIBILITY OF SUCH DAMAGE.
 */

package org.databene.commons.file;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;
import java.util.InvalidPropertiesFormatException;
import java.util.Map;
import java.util.Properties;

import org.databene.commons.Encodings;
import org.databene.commons.IOUtil;
import org.databene.commons.xml.XMLUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Merges properties files with priority and allows for override by VM parameters.<br/><br/>
 * Created: 01.08.2013 10:37:30
 * @since 0.5.24
 * @author Volker Bergmann
 */

public class PropertiesFileMerger {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(PropertiesFileMerger.class);

	public static void merge(String targetPath, String... sourceFiles) throws FileNotFoundException, IOException {
		merge(targetPath, true, sourceFiles);
	}

	public static void merge(String targetPath, boolean vmOverride, String... sourceFiles) throws FileNotFoundException, IOException {
		LOGGER.debug("Merging the files {} into target file: {}", Arrays.toString(sourceFiles), targetPath);
		Properties properties = new Properties();
		for (String sourceFile : sourceFiles) {
			loadClasspathResourceIfPresent(sourceFile, properties); // find resource on class path
			loadFileIfPresent(sourceFile, properties); // find resource on file system
		}
		if (vmOverride)
			overwritePropertiesWithVMParams(properties);
		// write properties in UTF-8
		if (targetPath.toLowerCase().endsWith(".xml"))
			XMLUtil.saveAsProperties(properties, new File(targetPath), Encodings.UTF_8);
		else {
			FileOutputStream in = new FileOutputStream(targetPath);
			try {
				properties.store(in, "Merged properties file");
			} finally {
				IOUtil.close(in);
			}
		}
	}

	static void loadClasspathResourceIfPresent(String resourceName, Properties properties) throws IOException {
		InputStream in = IOUtil.getResourceAsStream(resourceName, false);
		if (in != null) {
			LOGGER.debug("Merging properties of classpath resource '{}' ", resourceName);
			try {
				Properties cpProperties = loadFromStream(in, resourceName);
				overwriteProperties(properties, cpProperties);
			} finally {
				IOUtil.close(in);
			}
		}
	}

	static void loadFileIfPresent(String sourceFile, Properties properties) throws FileNotFoundException, IOException {
		File file = new File(sourceFile);
		if (file.exists()) {
			LOGGER.debug("Merging properties of file '{}' ", sourceFile);
			FileInputStream in = new FileInputStream(file);
			try {
				Properties fileProperties = loadFromStream(in, sourceFile);
				overwriteProperties(properties, fileProperties);
			} finally {
				IOUtil.close(in);
			}
		}
	}

	public static Properties loadFromStream(InputStream in, String sourceFile) throws IOException,
			InvalidPropertiesFormatException {
		Properties fileProperties = new Properties();
		if (sourceFile.toLowerCase().endsWith(".properties")) {
			try {
				fileProperties.load(in);
			} finally {
				IOUtil.close(in);
			}
		} else
			fileProperties = XMLUtil.parseAsProperties(in);
		return fileProperties;
	}
	
	static void overwriteProperties(Properties properties, Properties overwrites) {
		for (Map.Entry<Object, Object> entry : overwrites.entrySet()) {
			String key = (String) entry.getKey();
			String oldValue = properties.getProperty(key);
			String newValue = (String) entry.getValue();
			if (oldValue != null && !oldValue.equals(newValue))
				LOGGER.debug("Overwriting '{}' property to '{}'", key, newValue);
			else
				LOGGER.debug("Adding '{}' property of value '{}'", key, newValue);
			properties.setProperty(key, newValue);
		}
	}

	static void overwritePropertiesWithVMParams(Properties properties) {
		LOGGER.debug("Checking properties against VM settings override");
		for (Map.Entry<Object, Object> entry : properties.entrySet()) {
			String key = (String) entry.getKey();
			String vmSetting = System.getProperty(key);
			if (vmSetting != null && vmSetting.length() > 0) {
				LOGGER.debug("Overwriting '{}' property to '{}'", key, vmSetting);
				entry.setValue(vmSetting);
			}
		}
	}
	
}
