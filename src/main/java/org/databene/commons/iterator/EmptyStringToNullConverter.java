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
package org.databene.commons.iterator;

import org.databene.commons.ConversionException;
import org.databene.commons.Converter;
import org.databene.commons.converter.ThreadSafeConverter;

/**
 * {@link Converter} implementation which converts empty {@link String}s to null values.
 * Created: 08.03.2011 14:50:50
 * @since 0.5.8
 * @author Volker Bergmann
 */
public class EmptyStringToNullConverter extends ThreadSafeConverter<String, String> {

	public EmptyStringToNullConverter() {
		super(String.class, String.class);
	}

	@Override
	public String convert(String sourceValue) throws ConversionException {
		return (sourceValue == null || sourceValue.isEmpty() ? null : sourceValue);
	}

}
