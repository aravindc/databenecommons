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

import static org.junit.Assert.assertEquals;

import org.databene.commons.Converter;
import org.junit.Test;

/**
 * Tests the {@link Char2StringConverter}.
 * Created: 19.01.2011 22:08:07
 * @since 0.5.5
 * @author Volker Bergmann
 */
public class Char2StringConverterTest extends AbstractConverterTest {
	
	public Char2StringConverterTest() {
		super(Char2StringConverter.class);
	}

	@Test
	public void testNull() {
		Char2StringConverter converter = new Char2StringConverter();
		assertEquals(null, converter.convert(null));
	}

	@Test
	public void testInstance() {
		Converter<Character, String> converter = new Char2StringConverter();
		assertEquals("A", converter.convert('A'));
	}

	@Test
	public void testConverterManagerIntegration() {
		Converter<Character, String> converter = ConverterManager.getInstance().createConverter(Character.class, String.class);
		assertEquals("A", converter.convert('A'));
	}

}
