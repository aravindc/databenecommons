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

	private static final String VERSION_SUFFIX = "_version";

	private static final Logger LOGGER = LoggerFactory.getLogger(VersionInfo.class);
	
	private static final Map<String, VersionInfo> INSTANCES = new HashMap<String, VersionInfo>();
	
	private static final String VERSION_FILE_PATTERN = "org/databene/{0}/version.properties";

	private static boolean development;
	
	private final String name;
	private String version;
	private Map<String, String> dependencies;

	private String buildNumber;

	public static VersionInfo getInfo(String name) {
		VersionInfo result = INSTANCES.get(name);
		if (result == null) {
			result = new VersionInfo(name);
			INSTANCES.put(name, result);
		}
		return result;
	}

	private VersionInfo(String name) {
		this.name = name;
		this.dependencies = new HashMap<String, String>();
		readVersionInfo(this);
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

	private static void readVersionInfo(VersionInfo versionInfo) {
		versionInfo.version = "<unknown version>";
	    try {
	    	String versionFileName = VERSION_FILE_PATTERN.replace("{0}", versionInfo.name);
	        if (IOUtil.isURIAvailable(versionFileName)) {			// This works in Maven, but...
	    		Map<String, String> props = IOUtil.readProperties(versionFileName);
	    		for (Entry<String, String> dependency : props.entrySet()) {
	    			String dependencyName = dependency.getKey();
					String dependencyVersion = dependency.getValue();
					if ("build_number".equals(dependencyName))
	    				versionInfo.buildNumber = dependencyVersion;
	    			else
	    				addDependency(dependencyName, dependencyVersion, versionInfo);
	    		}
	    		versionInfo.version = props.get(versionInfo.name + VERSION_SUFFIX);
	        } else {
	        	LOGGER.warn("Version number file '" + versionFileName + "' not found, falling back to POM");
	        }
	        if (versionInfo.version.startsWith("${") || versionInfo.version.startsWith("<unknown")) { // ...in Eclipse no filtering is applied,...
	        	VersionInfo.development = true;
	        	if (versionInfo.version.startsWith("${"))
	        		LOGGER.warn("Version number has not been resolved, falling back to POM info"); // ...so I fetch it directly from the POM!
	    		Document doc = XMLUtil.parse("pom.xml");
	    		Element versionElement = XMLUtil.getChildElement(doc.getDocumentElement(), false, true, "version");
	    		versionInfo.version = versionElement.getTextContent();
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
        } catch (IOException e) {
	        LOGGER.error("Error reading version info file", e);
        }
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
