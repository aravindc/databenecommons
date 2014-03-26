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

import java.text.Format;

import org.databene.commons.ConversionException;

/**
 * Converts an object to a String by using a java.lang.Format object's format() method.<br/>
 * <br/>
 * Created: 30.08.2006 19:43:09
 * @since 0.1
 * @author Volker Bergmann
 */
public class FormatFormatConverter<S> extends FormatBasedConverter<S, String> {

    /**
     * Constructor that initializes the format object.
     * @param format the format object to use.
     */
    public FormatFormatConverter(Class<S> sourceType, Format format, boolean threadSafe) {
        super(sourceType, String.class, format, threadSafe);
    }

    /**
     * Converts an object to a String by using the format's format() method.
     * @see org.databene.commons.Converter
     */
	@Override
	public synchronized String convert(S source) {
        try {
        	return format.format(source);
        } catch (Exception e) {
        	throw new ConversionException("Conversion failed for value: " + source, e);
        }
    }

}
