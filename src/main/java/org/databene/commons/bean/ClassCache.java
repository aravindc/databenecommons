/*
 * (c) Copyright 2008-2009 by Volker Bergmann. All rights reserved.
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

package org.databene.commons.bean;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.databene.commons.BeanUtil;
import org.databene.commons.ConfigurationError;
import org.databene.commons.StringUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Provides classes by name, supporting package import and local class names.<br/>
 * <br/>
 * Created at 15.11.2008 17:03:04
 * @since 0.4.6
 * @author Volker Bergmann
 */
public class ClassCache {
	
	private static final Logger logger = LoggerFactory.getLogger(ClassCache.class); 
	
    private Map<String, Class<?>> classes;
	private Set<String> packages;
	private Set<String> nonClassNames = new HashSet<String>(1000);
	
    public ClassCache() {
		classes = new HashMap<String, Class<?>>();
		packages = new HashSet<String>();
		importPackage("java.lang");
	}

	public void importClass(String className) {
		className = className.trim();
		if (className.endsWith(".*"))
			importPackage(className.substring(0, className.length() - 2));
		else 
			classes.put(StringUtil.lastToken(className, '.'), BeanUtil.forName(className));
	}

	public void importPackage(String packageName) {
		packages.add(packageName);
	}

    public Class<?> forName(String name) {
		Class<?> result = classes.get(name);
		if (result != null)
			return result;
		if (!nonClassNames.contains(name)) {
			try {
				result = BeanUtil.forName(name);
				classes.put(result.getSimpleName(), result);
				return result;
			} catch (ConfigurationError e) {
				nonClassNames.add(name);
				if (logger.isDebugEnabled())
					logger.debug("class not found: " + name);
			}
		}
		for (String pkg : packages) {
			String fqnTrial = pkg + '.' + name;
			if (!nonClassNames.contains(fqnTrial)) {
				try {
					result = BeanUtil.forName(fqnTrial);
					classes.put(result.getSimpleName(), result);
					return result;
				} catch (ConfigurationError e) {
					nonClassNames.add(fqnTrial);
					if (logger.isDebugEnabled())
						logger.debug("class not found: " + name);
				}
			}
		}
		throw new ConfigurationError("Class not found: " + name);
	}
}
