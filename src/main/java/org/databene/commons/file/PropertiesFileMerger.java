/*
 * (c) Copyright 2013-2014 by Volker Bergmann. All rights reserved.
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
import java.util.Map;

import org.databene.commons.Encodings;
import org.databene.commons.IOUtil;
import org.databene.commons.StringUtil;
import org.databene.commons.collection.TreeBuilder;
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
		TreeBuilder tree = null;
		for (String sourceFile : sourceFiles) {
			tree = loadClasspathResourceIfPresent(sourceFile, tree); // find resource on class path
			tree = loadFileIfPresent(sourceFile, tree); // find resource on file system
		}
		if (tree != null) {
			if (vmOverride)
				overwritePropertiesWithVMParams(tree);
			// write properties in UTF-8
			if (targetPath.toLowerCase().endsWith(".xml"))
				tree.saveAsXML(new FileOutputStream(targetPath), Encodings.UTF_8);
			else
				tree.saveAsProperties(new FileOutputStream(targetPath));
		}
	}

	static TreeBuilder loadClasspathResourceIfPresent(String resourceName, TreeBuilder base) throws IOException {
		InputStream in = IOUtil.getResourceAsStream(resourceName, false);
		if (in != null) {
			LOGGER.debug("Loading and merging structure of classpath resource '{}' ", resourceName);
			try {
				TreeBuilder newTree = TreeBuilder.loadFromStream(in, resourceName);
				return overwriteProperties(base, newTree);
			} finally {
				IOUtil.close(in);
			}
		} else
			return base;
	}

	static TreeBuilder loadFileIfPresent(String sourceFile, TreeBuilder base) throws FileNotFoundException, IOException {
		File file = new File(sourceFile);
		if (file.exists()) {
			LOGGER.debug("Loading and merging properties of file '{}' ", sourceFile);
			FileInputStream in = new FileInputStream(file);
			try {
				TreeBuilder newTree = TreeBuilder.loadFromStream(in, sourceFile);
				return overwriteProperties(base, newTree);
			} finally {
				IOUtil.close(in);
			}
		} else 
			return base;
	}

	static TreeBuilder overwriteProperties(TreeBuilder base, TreeBuilder overwrites) {
		if (base == null)
			return overwrites;
		if (overwrites == null)
			return base;
		overwrite(base.getRootNode(), overwrites.getRootNode());
		return base;
	}

	@SuppressWarnings("unchecked")
	private static void overwrite(Map<String, Object> baseNode, Map<String, Object> overwriteNode) {
		for (Map.Entry<String, Object> overEntry : overwriteNode.entrySet()) {
			String key = overEntry.getKey();
			Object oldValue = baseNode.get(key);
			if (oldValue == null) {
				baseNode.put(key, overEntry.getValue());
			} else if (oldValue instanceof Map) {
				overwrite((Map<String, Object>) oldValue, (Map<String, Object>) overEntry.getValue());
			} else {
				baseNode.put(key, overEntry.getValue());
			}
		}
	}

	static void overwritePropertiesWithVMParams(TreeBuilder tree) {
		LOGGER.debug("Checking properties against VM settings override");
		overwritePropertiesWithVMParams(tree.getRootNode(), tree.getRootName());
	}
	
	@SuppressWarnings("unchecked")
	private static void overwritePropertiesWithVMParams(Map<String, Object> node, String path) {
		for (Map.Entry<String, Object> entry : node.entrySet()) {
			String subPath = subPath(path, entry.getKey(), '.');
			Object child = entry.getValue();
			if (child instanceof Map) {
				overwritePropertiesWithVMParams((Map<String, Object>) child, subPath);
			} else if (child instanceof String) {
				String vmSetting = System.getProperty(subPath);
				if (vmSetting != null && vmSetting.length() > 0) {
					LOGGER.debug("Overwriting '{}' property with '{}'", subPath, vmSetting);
					entry.setValue(vmSetting);
				}
			}
		}
	}
	
	private static String subPath(String parentPath, String childName, char separator) {
		return (StringUtil.isEmpty(parentPath) ? "" : parentPath + separator)  + childName;
	}
	
}
