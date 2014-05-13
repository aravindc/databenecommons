/*
 * (c) Copyright 2011 by Volker Bergmann. All rights reserved.
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

package org.databene.commons.version;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import javax.validation.constraints.NotNull;

import org.databene.commons.Assert;
import org.databene.commons.ConfigurationError;
import org.databene.commons.DeploymentError;
import org.databene.commons.IOUtil;
import org.databene.commons.ProgrammerError;
import org.databene.commons.xml.XMLUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

/**
 * Provides a mechanism to access an application's version number and 
 * check its dependencies programmatically.<br/><br/>
 * Created: 23.03.2011 10:38:31
 * @since 0.5.8
 * @author Volker Bergmann
 */
public class VersionInfo {

	private static final Logger LOGGER = LoggerFactory.getLogger(VersionInfo.class);
	
	private static final String VERSION_SUFFIX = "_version";

	private static final Map<String, VersionInfo> INSTANCES = new HashMap<String, VersionInfo>();
	
	private static final String VERSION_FILE_PATTERN = "org/databene/{0}/version.properties";

	private static boolean development;
	
	private final String name;
	private final String filePath;
	private String version;
	private Map<String, String> dependencies;

	private String buildNumber;

	public static VersionInfo getInfo(@NotNull String name) {
		return getInfo(name, true);
	}

	public static VersionInfo getInfo(@NotNull String name, boolean parsingDependencies) {
		VersionInfo result = INSTANCES.get(name);
		if (result == null) {
			result = new VersionInfo(name, parsingDependencies);
			INSTANCES.put(name, result);
		}
		return result;
	}

	private VersionInfo(String name, boolean parsingDependencies) {
		Assert.notNull(name, "name");
		this.name = name;
		this.filePath = normalizedPath(name);
		this.dependencies = new HashMap<String, String>();
		readVersionInfo(this, parsingDependencies);
	}

	public String getName() {
		return name;
	}

	public String getVersion() {
		return version;
	}
	
	public String getBuildNumber() {
		return buildNumber;
	}
	
	public Map<String, String> getDependencies() {
		return dependencies;
	}
	
	public void verifyDependencies() {
		if (VersionInfo.development)
			return;
		for (Map.Entry<String, String> dependency : dependencies.entrySet()) {
			String library = dependency.getKey();
			if (library.equals("build_number"))
				continue;
			VersionNumber expectedVersion = VersionNumber.valueOf(dependency.getValue());
			VersionNumber actualVersion = VersionNumber.valueOf(getInfo(library).getVersion());
			if (!VersionInfo.development && actualVersion.compareTo(expectedVersion) < 0)
				throw new DeploymentError(this + " requires at least " + library + ' ' + expectedVersion + ", " +
						"but found " + library + ' ' + actualVersion);
		}
	}
	
	
	
	// private helper methods ------------------------------------------------------------------------------------------

	private static String normalizedPath(String name) {
		if (name.contains("."))
			name = name.replace('.', '/');
		return name;
	}

	private static void readVersionInfo(VersionInfo versionInfo, boolean parsingDependencies) {
		versionInfo.version = "<unknown version>";
	    try {
			String versionFileName;
	    	if (versionInfo.filePath.contains("/"))
	    		versionFileName = versionInfo.filePath + "/version.properties";
	    	else
	    		versionFileName = VERSION_FILE_PATTERN.replace("{0}", versionInfo.name);
	    	boolean ok = readVersionInfo(versionInfo, versionFileName);
	        if (!ok)
	        	LOGGER.warn("Version number file '" + versionFileName + "' not found, falling back to POM");
	        if (versionInfo.version.startsWith("${") || versionInfo.version.startsWith("<unknown")) { // ...in Eclipse no filtering is applied,...
	        	VersionInfo.development = true;
	        	if (versionInfo.version.startsWith("${"))
	        		LOGGER.warn("Version number has not been resolved, falling back to POM info"); // ...so I fetch it directly from the POM!
	    		Document doc = XMLUtil.parse("pom.xml");
	    		Element versionElement = XMLUtil.getChildElement(doc.getDocumentElement(), false, true, "version");
	    		versionInfo.version = versionElement.getTextContent();
	    		if (parsingDependencies)
	    			parseDependencies(versionInfo, doc);
	        }
        } catch (IOException e) {
	        LOGGER.error("Error reading version info file", e);
        }
    }

	private static void parseDependencies(VersionInfo versionInfo, Document doc) {
		Element propsElement = XMLUtil.getChildElement(doc.getDocumentElement(), false, false, "properties");
		if (propsElement != null)
    		for (Element childElement : XMLUtil.getChildElements(propsElement)) {
    			String dependencyName = childElement.getNodeName();
    			String dependencyVersion = childElement.getTextContent();
    			if ("build_number".equals(dependencyName))
    				versionInfo.buildNumber = dependencyVersion;
    			else
    				addDependency(dependencyName, dependencyVersion, versionInfo);
    		}
	}

	private static boolean readVersionInfo(VersionInfo versionInfo, String versionFileName) throws IOException {
        if (IOUtil.isURIAvailable(versionFileName)) {
    		Map<String, String> props = IOUtil.readProperties(versionFileName);
    		for (Entry<String, String> dependency : props.entrySet()) {
    			String dependencyName = dependency.getKey();
				String dependencyVersion = dependency.getValue();
				if ("build_number".equals(dependencyName))
    				versionInfo.buildNumber = dependencyVersion;
    			else
    				addDependency(dependencyName, dependencyVersion, versionInfo);
    		}
    		String versionKey = versionInfo.name.replace('.', '_') + VERSION_SUFFIX;
			versionInfo.version = props.get(versionKey);
	        if (versionInfo.version == null) // TODO remove this convenience fallback after transition period
	        	versionInfo.version = props.get(versionInfo.name.replace('.', '/') + VERSION_SUFFIX);
	        if (versionInfo.version == null)
	        	throw new ConfigurationError("No version number (" + versionKey + ") defined in file " + versionFileName);
    		return true;
        } else
        	return false;
	}
	
	private static void addDependency(String dependencyName,
			String dependencyVersion, VersionInfo versionInfo) {
		if (!dependencyName.endsWith(VERSION_SUFFIX))
			throw new ProgrammerError("Dependency configuration '" + dependencyName + 
					" does not end with '" + VERSION_SUFFIX + "'.");
		dependencyName = dependencyName.substring(0, dependencyName.length() - VERSION_SUFFIX.length());
		if (!dependencyName.equals(versionInfo.name))
			versionInfo.dependencies.put(dependencyName, dependencyVersion);
	}
	
	@Override
	public String toString() {
		return name + ' ' + version + (buildNumber == null || ("${buildNumber}".equals(buildNumber)) ? "" : " build " + buildNumber);
	}
	
}
