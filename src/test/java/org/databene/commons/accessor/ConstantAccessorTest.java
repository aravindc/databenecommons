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
package org.databene.commons.accessor;

import org.junit.Test;

import static junit.framework.Assert.*;

/**
 * Tests the ConstantAccessor.
 * Created at 02.05.2008 14:13:01
 * @since 0.4.3
 * @author Volker Bergmann
 */
public class ConstantAccessorTest {

	@Test
	public void testGet() {
		ConstantAccessor<Integer> accessor = new ConstantAccessor<Integer>(1);
		assertEquals(1, (int) accessor.getValue(null));
	}

	@Test
	public void testEquals() {
		ConstantAccessor<Integer> a1 = new ConstantAccessor<Integer>(1);
		// simple test
		assertFalse(a1.equals(null));
		assertFalse(a1.equals(""));
		assertTrue(a1.equals(a1));
		// real comparisons
		assertTrue(a1.equals(new ConstantAccessor<Integer>(1)));
		ConstantAccessor<Integer> a0 = new ConstantAccessor<Integer>(null);
		ConstantAccessor<Integer> a2 = new ConstantAccessor<Integer>(2);
		assertFalse(a0.equals(a1));
		assertFalse(a1.equals(a0));
		assertFalse(a1.equals(a2));
		assertFalse(a2.equals(a1));
	}
	
}
