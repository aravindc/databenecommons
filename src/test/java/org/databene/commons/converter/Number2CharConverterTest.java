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

import static org.junit.Assert.*;

import org.databene.commons.Converter;
import org.junit.Test;

/**
 * Tests the {@link Number2CharConverter}.
 * Created: 19.01.2011 15:35:12
 * @since 0.5.5
 * @author Volker Bergmann
 */
public class Number2CharConverterTest extends AbstractConverterTest {
	
	public Number2CharConverterTest() {
		super(Number2CharConverter.class);
	}

	@Test
	public void testNull() {
		Number2CharConverter converter = new Number2CharConverter();
		assertEquals(null, converter.convert(null));
	}

	@Test
	public void testInstance() {
		checkNumberTypes(new Number2CharConverter());
	}

	@Test
	public void testConverterManagerIntegration() {
		Converter<Number, Character> converter = ConverterManager.getInstance().createConverter(Number.class, Character.class);
		checkNumberTypes(converter);
	}

	private static void checkNumberTypes(Converter<Number, Character> converter) {
		assertEquals('A', (char) converter.convert((byte) 65));
		assertEquals('A', (char) converter.convert(65));
		assertEquals('A', (char) converter.convert((short) 65));
		assertEquals('A', (char) converter.convert((long) 65));
	}

}
