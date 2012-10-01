/*
 * (c) Copyright 2010-2012 by Volker Bergmann. All rights reserved.
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

package org.databene.commons.converter;

import java.io.UnsupportedEncodingException;

import org.databene.commons.ConfigurationError;
import org.databene.commons.ConversionException;
import org.databene.commons.SystemInfo;

/**
 * Converts byte arrays to Strings based on a character encoding, e.g. UTF-8.<br/><br/>
 * Created: 26.02.2010 08:26:55
 * @since 0.5.0
 * @author Volker Bergmann
 */
public class ByteArray2StringConverter extends ThreadSafeConverter<byte[], String> {

    private String encoding;
    
    public ByteArray2StringConverter() {
        this(SystemInfo.getFileEncoding());
    }

    public ByteArray2StringConverter(String encoding) {
        super(byte[].class, String.class);
        this.encoding = encoding;
    }

    @Override
	public String convert(byte[] target) throws ConversionException {
        try {
            return new String(target, encoding);
        } catch (UnsupportedEncodingException e) {
            throw new ConfigurationError(e);
        }
    }

}
