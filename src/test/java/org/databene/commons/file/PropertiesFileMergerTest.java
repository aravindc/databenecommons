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

import static org.junit.Assert.*;

import java.io.FileInputStream;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.Properties;

import org.databene.commons.xml.XMLUtil;
import org.junit.Test;

/**
 * Tests the {@link PropertiesFileMerger}.<br/><br/>
 * Created: 01.08.2013 10:38:32
 * @since 0.5.24
 * @author Volker Bergmann
 */

public class PropertiesFileMergerTest {

	private static final String NON_EXISTING_PROPERTIES_FILENAME = "non_existing.properties";
	private static final String JAR_PROPERTIES_FILENAME = "propsInJar.properties";
	private static final String FILE_PROPERTIES_FILENAME = "src/test/resources/org/databene/commons/file/propsInFile.properties";
	private static final String MERGED_PROPERTIES_FILENAME = "target/merged.properties";
	private static final String FILE_XML_FILENAME = "src/test/resources/org/databene/commons/xml/properties.xml";
	private static final String MERGED_XML_FILENAME = "target/merged.xml";
	private static final URL JAR_URL;
	private static final String COMMON_PROPERTY_NAME = "common.property";
	private static final String FILE_PROPERTY_NAME = "file.property";
	private static final Object JAR_PROPERTY_NAME = "jar.property";
	
	static {
		try {
			JAR_URL = new URL("file:src/test/resources/org/databene/commons/file/JarWithProperties.jar");
		} catch (MalformedURLException e) {
			throw new RuntimeException(e);
		}
	}

	@Test
	public void testOverwritePropertiesWithVMParams() {
		// GIVEN a property xyz=1
		Properties props = new Properties();
		props.setProperty("xyz", "1");
		
		// WHEN no VM setting is defined
		System.setProperty("xyz", "");
		// THEN the property shall not be overwritten
		PropertiesFileMerger.overwritePropertiesWithVMParams(props);
		assertEquals("1", props.get("xyz"));
		
		// AND WHEN a VM setting is defined 
		System.setProperty("xyz", "2");
		// THEN it shall overwrite the property
		PropertiesFileMerger.overwritePropertiesWithVMParams(props);
		assertEquals("2", props.get("xyz"));
	}

	@Test
	public void testLoadFileIfPresent() throws IOException {
		// GIVEN a property 'common.property' of value 'none'
		Properties props = new Properties();
		props.put(COMMON_PROPERTY_NAME, "none");
		
		// WHEN trying to load a non-existing file
		PropertiesFileMerger.loadFileIfPresent(NON_EXISTING_PROPERTIES_FILENAME, props);
		// THEN the property shall not be changed
		assertEquals("none", props.get(COMMON_PROPERTY_NAME));
		
		// AND WHEN loading an existing file with that property
		PropertiesFileMerger.loadFileIfPresent(FILE_PROPERTIES_FILENAME, props);
		// THEN the setting is supposed to be changed
		assertEquals("loaded_from_file", props.get(COMMON_PROPERTY_NAME));
	}

	@Test
	public void testLoadClasspathResourceIfPresent() throws IOException {
		// GIVEN a property 'common.property' of value 'none'
		Properties props = new Properties();
		props.put(COMMON_PROPERTY_NAME, "none");
		
		// WHEN trying to load a non-existing classpath resource
		PropertiesFileMerger.loadClasspathResourceIfPresent(NON_EXISTING_PROPERTIES_FILENAME, props);
		// THEN the property shall not be changed
		assertEquals("none", props.get(COMMON_PROPERTY_NAME));
		
		// AND WHEN loading an existing classpath resource with that property
		Thread.currentThread().setContextClassLoader(new URLClassLoader(new URL[] { JAR_URL }));
		PropertiesFileMerger.loadClasspathResourceIfPresent(JAR_PROPERTIES_FILENAME, props);
		// THEN the setting is supposed to be changed
		assertEquals("loaded_from_jar", props.get(COMMON_PROPERTY_NAME));
	}

	@Test
	public void testClasspathAccess() throws IOException {
		// GIVEN a property 'common.property' of value 'none'
		Properties props = new Properties();
		props.put(COMMON_PROPERTY_NAME, "none");
		
		// WHEN trying to load a non-existing classpath resource
		PropertiesFileMerger.loadClasspathResourceIfPresent(NON_EXISTING_PROPERTIES_FILENAME, props);
		// THEN the property shall not be changed
		assertEquals("none", props.get(COMMON_PROPERTY_NAME));
		
		// AND WHEN loading an existing classpath resource with that property
		Thread.currentThread().setContextClassLoader(new URLClassLoader(new URL[] { JAR_URL }));
		PropertiesFileMerger.loadClasspathResourceIfPresent(JAR_PROPERTIES_FILENAME, props);
		// THEN the setting is supposed to be changed
		assertEquals("loaded_from_jar", props.get(COMMON_PROPERTY_NAME));
	}
	
	@Test
	public void testMergeWithVMOverride() throws IOException {
		System.setProperty(COMMON_PROPERTY_NAME, "loaded_from_vm");
		PropertiesFileMerger.merge(MERGED_PROPERTIES_FILENAME, true, FILE_PROPERTIES_FILENAME, JAR_PROPERTIES_FILENAME);
		Properties props = new Properties();
		props.load(new FileInputStream(MERGED_PROPERTIES_FILENAME));
		assertEquals("loaded_from_vm", props.get(COMMON_PROPERTY_NAME));
		assertEquals("loaded_from_file", props.get(FILE_PROPERTY_NAME));
		assertEquals("loaded_from_jar", props.get(JAR_PROPERTY_NAME));
	}
	
	@Test
	public void testMergeWithoutVMOverride() throws IOException {
		System.setProperty(COMMON_PROPERTY_NAME, "loaded_from_vm");
		PropertiesFileMerger.merge(MERGED_PROPERTIES_FILENAME, false, FILE_PROPERTIES_FILENAME, JAR_PROPERTIES_FILENAME);
		Properties props = new Properties();
		props.load(new FileInputStream(MERGED_PROPERTIES_FILENAME));
		assertEquals("loaded_from_jar", props.get(COMMON_PROPERTY_NAME));
		assertEquals("loaded_from_file", props.get(FILE_PROPERTY_NAME));
		assertEquals("loaded_from_jar", props.get(JAR_PROPERTY_NAME));
	}
	
	@Test
	public void testXMLFile() throws IOException {
		PropertiesFileMerger.merge(MERGED_XML_FILENAME, true, FILE_XML_FILENAME);
		Properties props = XMLUtil.parseAsProperties(new FileInputStream(MERGED_XML_FILENAME));
		assertEquals("groupValue", props.get("root.group.groupProp"));
		assertEquals("", props.get("root.emptyProp"));
	}
	
}
