/*
 * (c) Copyright 2009-2012 by Volker Bergmann. All rights reserved.
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

import java.util.Formatter;
import java.util.Locale;

import org.databene.commons.ConversionException;
import org.databene.commons.Converter;

/**
 * {@link Converter} implementation that uses a {@link Formatter} 
 * to render argument objects in C-like printf format.<br/>
 * <br/>
 * Created at 20.07.2009 07:18:43
 * @since 0.5.0
 * @author Volker Bergmann
 */

public class PrintfConverter extends ThreadSafeConverter<Object, String> {
	
	private Locale locale;
	private String pattern;
	
	// constructors ----------------------------------------------------------------------------------------------------

    public PrintfConverter() {
	    this("");
    }
    
    public PrintfConverter(String pattern) {
	    this(pattern, Locale.getDefault());
    }
    
    public PrintfConverter(String pattern, Locale locale) {
	    super(Object.class, String.class);
	    this.pattern = pattern;
	    this.locale = locale;
    }
    
    // properties ------------------------------------------------------------------------------------------------------
    
    public Locale getLocale() {
    	return locale;
    }

	public void setLocale(Locale locale) {
    	this.locale = locale;
    }

	public String getPattern() {
    	return pattern;
    }

	public void setPattern(String pattern) {
    	this.pattern = pattern;
    }

	// converter interface ---------------------------------------------------------------------------------------------

    @Override
	public String convert(Object sourceValue) throws ConversionException {
	    if (sourceValue == null)
	    	return null;
	    Formatter formatter = new Formatter(locale);
	    return formatter.format(pattern, sourceValue).out().toString();
    }

}
