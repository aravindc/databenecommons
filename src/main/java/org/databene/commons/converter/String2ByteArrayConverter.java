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

import java.io.UnsupportedEncodingException;

import org.databene.commons.ConfigurationError;
import org.databene.commons.ConversionException;
import org.databene.commons.SystemInfo;

/**
 * Converts strings to byte arrays based on a character encoding, e.g. UTF-8.
 * @author Volker Bergmann
 * @since 0.2.04
 */
public class String2ByteArrayConverter extends ThreadSafeConverter<String, byte[]> {

    private String encoding;
    
    public String2ByteArrayConverter() {
        this(SystemInfo.getFileEncoding());
    }

    public String2ByteArrayConverter(String encoding) {
        super(String.class, byte[].class);
        this.encoding = encoding;
    }

    @Override
	public byte[] convert(String sourceValue) throws ConversionException {
        try {
            return sourceValue.getBytes(encoding);
        } catch (UnsupportedEncodingException e) {
            throw new ConfigurationError(e);
        }
    }

}
