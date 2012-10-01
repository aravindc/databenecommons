/*
 * (c) Copyright 2007-2012 by Volker Bergmann. All rights reserved.
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

package org.databene.commons.converter;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.util.PropertyResourceBundle;
import java.util.Locale;
import java.util.ResourceBundle;

import org.databene.commons.Encodings;
import org.databene.commons.IOUtil;
import org.databene.commons.LocaleUtil;

/**
 * Converts key strings to localized texts by using a ResourceBundle.<br/>
 * <br/>
 * Created: 07.06.2007 07:48:35
 * @author Volker Bergmann
 */
public class PropertyResourceBundleConverter extends ThreadSafeConverter<String, String> {

    private final ResourceBundle bundle;
    private final ResourceBundle.Control control = new UTF8Control();

    public PropertyResourceBundleConverter(String baseName, Locale locale) {
    	super(String.class, String.class);
        bundle = PropertyResourceBundle.getBundle(baseName, locale, control);
    }

    @Override
	public String convert(String sourceValue) {
        return bundle.getString(sourceValue);
    }
    
    static class UTF8Control extends PropertyResourceBundle.Control {
    	
    	@Override
    	public ResourceBundle newBundle(String baseName, Locale locale, String format, ClassLoader loader, boolean reload)
    	        throws IOException {
    		String bundleName = toBundleName(baseName, locale);
    		String resourceName = toResourceName(bundleName, "properties");
    		InputStream stream = IOUtil.getInputStreamForURI(resourceName, true);
			Charset utf8 = Charset.forName(Encodings.UTF_8);
			return new PropertyResourceBundle(new InputStreamReader(stream, utf8)); 
    	}
    	
    	@Override
    	public Locale getFallbackLocale(String baseName, Locale locale) {
    		Locale fallback = LocaleUtil.getFallbackLocale();
    	    return (fallback.equals(locale) ? null : fallback);
    	}
    }
    
}
