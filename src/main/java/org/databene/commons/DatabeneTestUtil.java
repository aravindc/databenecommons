/*
 * (c) Copyright 2009-2011 by Volker Bergmann. All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, is permitted under the terms of the
 * GNU General Public License.
 *
 * For redistributing this software or a derivative work under a license other
 * than the GPL-compatible Free Software License as defined by the Free
 * Software Foundation or approved by OSI, you must first obtain a commercial
 * license to this software product from Volker Bergmann.
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

package org.databene.commons;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Provides test settings from a file <code>${user.home}/databene.test.properties</code>.<br/>
 * <br/>
 * Created at 01.10.2009 15:30:51
 * @since 0.5.0
 * @author Volker Bergmann
 */

public class DatabeneTestUtil {
	
    private static final String DATABENE_TEST_PROPERTIES = "test.properties";

	private static final Logger LOGGER = LoggerFactory.getLogger(DatabeneTestUtil.class);
	
	private static Map<String, String> properties;

	static {
		init();
	}

    private static void init() {
	    File file = new File(SystemInfo.getUserHome() + File.separator + "databene", DATABENE_TEST_PROPERTIES);
	    if (file.exists()) {
	    	try {
	            properties = IOUtil.readProperties(file.getAbsolutePath());
            } catch (IOException e) {
	    		LOGGER.error("Error reading " + file.getAbsolutePath(), e);
	    		createDefaultProperties();
            }
	    } else {
	    	createDefaultProperties();
	    	try {
	    		IOUtil.writeProperties(properties, file.getAbsolutePath());
	    	} catch (Exception e) {
	    		LOGGER.error("Error writing " + file.getAbsolutePath(), e);
	    	}
	    }
    }
	
    private static void createDefaultProperties() {
	    properties = new HashMap<String, String>();
	    properties.put("online", "false");
    }

	public static boolean isOnline() {
		String setting = properties.get("online");
		if (StringUtil.isEmpty(setting))
			return false;
		else
			return setting.toLowerCase().equals("true");
	}
	
	public static String ftpDownloadUrl() {
		return properties.get("ftp.download.url");
	}
	
	public static String ftpUploadUrl() {
		return properties.get("ftp.upload.url");
	}

	public static Map<String, String> getProperties() {
		return properties;
	}
	
}
