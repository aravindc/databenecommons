/*
 * Copyright (C) 2004-2014 Volker Bergmann (volker.bergmann@bergmann-it.de).
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

import org.junit.Test;
import static junit.framework.Assert.*;

import org.databene.commons.ConversionException;
import org.databene.commons.Converter;
import org.databene.commons.converter.String2LocaleConverter;

import java.util.Locale;

/**
 * Tests the {@link String2LocaleConverter}.
 * Created: 05.08.2007 06:32:38
 * @author Volker Bergmann
 */
public class String2LocaleConverterTest extends AbstractConverterTest {

	public String2LocaleConverterTest() {
	    super(String2LocaleConverter.class);
    }

	@Test
    public void testConvert() throws ConversionException {
        Converter<String, Locale> converter = new String2LocaleConverter();
        assertEquals(Locale.GERMAN, converter.convert("de"));
        assertEquals(Locale.GERMANY, converter.convert("de_DE"));
        assertEquals(new Locale("de", "DE", "BY"), converter.convert("de_DE_BY"));
    }

}
