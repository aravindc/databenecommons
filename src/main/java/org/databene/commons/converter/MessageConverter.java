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

import org.databene.commons.ConversionException;
import org.databene.commons.LocaleUtil;

import java.text.MessageFormat;
import java.util.Locale;

/**
 * Renders a single object or an array of objects into a String, similar to the java.text.MessageFormat.<br/>
 * <br/>
 * Created: 12.11.2007 20:46:31
 * @author Volker Bergmann
 */
public class MessageConverter extends ThreadSafeConverter<Object, String> {

    private String pattern;
    private Locale locale;

    public MessageConverter() {
        this("{0}");
    }

    public MessageConverter(String pattern) {
    	this(pattern, LocaleUtil.getFallbackLocale());
    }

    public MessageConverter(String pattern, Locale locale) {
    	super(Object.class, String.class);
        this.pattern = pattern;
        this.locale = locale;
    }

    public void setPattern(String pattern) {
        this.pattern = pattern;
    }

    public void setLocale(Locale locale) {
        this.locale = locale;
    }

    @Override
	public String convert(Object sourceValue) throws ConversionException {
        Object tmp = sourceValue;
        if (tmp != null && !tmp.getClass().isArray())
            tmp = new Object[] { tmp };
        return new MessageFormat(pattern, locale).format(tmp);
    }

}
