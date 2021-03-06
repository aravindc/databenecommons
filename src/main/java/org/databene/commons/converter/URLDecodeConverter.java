/*
 * Copyright (C) 2004-2015 Volker Bergmann (volker.bergmann@bergmann-it.de).
 * All rights reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * http://www.apache.org/licenses/LICENSE-2.0
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.databene.commons.converter;

import java.net.URLEncoder;

import org.databene.commons.ConversionException;
import org.databene.commons.Encodings;

/**
 * Decodes a String from the <code>application/x-www-form-urlencoded</code>  MIME format.
 * @see java.net.URLDecoder
 * Created at 04.07.2009 07:21:15
 * @since 0.5.0
 * @author Volker Bergmann
 */

public class URLDecodeConverter extends ThreadSafeConverter<String, String> {
	
	private String encoding;
	
    public URLDecodeConverter() {
	    this(Encodings.UTF_8);
    }

    public URLDecodeConverter(String encoding) {
	    super(String.class, String.class);
    }

    @Override
	public String convert(String sourceValue) throws ConversionException {
	    try {
	        return URLEncoder.encode(sourceValue, encoding);
        } catch (Exception e) {
        	throw new ConversionException("URLDecoding of '" + sourceValue 
        			+ "' failed for encoding '" + encoding + "'", e);
        }
    }

}
