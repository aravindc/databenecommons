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

import org.databene.commons.ConversionException;
import org.databene.commons.StringUtil;

/**
 * Parses a {@link String} as a {@link Boolean}.
 * Created: 27.02.2010 11:44:57
 * @since 0.5.0
 * @author Volker Bergmann
 */
public class String2BooleanConverter extends ThreadSafeConverter<String, Boolean> {

	public String2BooleanConverter() {
	    super(String.class, Boolean.class);
    }

	@Override
	public Boolean convert(String sourceValue) throws ConversionException {
	    if (StringUtil.isEmpty(sourceValue))
	    	return null;
	    sourceValue = sourceValue.trim();
	    if ("true".equalsIgnoreCase(sourceValue))
	    	return true;
	    else if ("false".equalsIgnoreCase(sourceValue))
	    	return false;
	    else
	    	throw new IllegalArgumentException("Not a boolean value: " + sourceValue);
	}
	
}
