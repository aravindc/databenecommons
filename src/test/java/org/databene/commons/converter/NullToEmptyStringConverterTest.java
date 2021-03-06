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

import org.junit.Test;

/**
 * Tests the {@link NullToEmptyStringConverter}.
 * Created: 08.03.2011 15:01:54
 * @since 0.5.8
 * @author Volker Bergmann
 */
public class NullToEmptyStringConverterTest extends AbstractConverterTest {

	public NullToEmptyStringConverterTest() {
		super(NullToEmptyStringConverter.class);
	}

	@Test
	public void test() {
		NullToEmptyStringConverter converter = new NullToEmptyStringConverter();
		assertEquals("", converter.convert(null));
		assertEquals("", converter.convert(""));
		assertEquals("x", converter.convert("x"));
	}
	
}
