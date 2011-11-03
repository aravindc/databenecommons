/*
 * (c) Copyright 2008 by Volker Bergmann. All rights reserved.
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

package org.databene.commons.ui;

import java.text.MessageFormat;
import java.util.Locale;
import java.util.MissingResourceException;
import java.util.PropertyResourceBundle;
import java.util.ResourceBundle;

import org.databene.commons.BeanUtil;
import org.databene.commons.Escalator;
import org.databene.commons.LoggerEscalator;

/**
 * Internationalization utilities.<br/>
 * <br/>
 * Created at 21.07.2008 07:32:56
 * @since 0.4.5
 * @author Volker Bergmann
 */
public class I18NSupport {
	
	private static final Escalator escalator = new LoggerEscalator();
	
	private String name;
	private ResourceBundle bundle;

	public I18NSupport(String name, Locale locale) {
		this.name = name;
		this.bundle = PropertyResourceBundle.getBundle(name, locale);
	}

	public String getName() {
		return name;
	}
	
	public Locale getLocale() {
		return bundle.getLocale();
	}

	public String getString(String resourceName) {
		String string;
		try {
			string = bundle.getString(resourceName);
		} catch (MissingResourceException e) {
			escalator.escalate("Resource not defined: " + resourceName, this, null);
			string = resourceName;
		}
		return string;
	}
	
	public String format(String resourceName, Object... args) {
		return MessageFormat.format(getString(resourceName), args);
	}
	
	@Override
	public String toString() {
		return BeanUtil.toString(this);
	}
}
