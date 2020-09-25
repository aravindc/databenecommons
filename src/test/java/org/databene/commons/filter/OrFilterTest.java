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
package org.databene.commons.filter;

import static org.junit.Assert.*;

import org.junit.Test;

/**
 * Tests the {@link OrFilter}.
 * Created: 08.06.2012 20:37:39
 * @since 0.5.16
 * @author Volker Bergmann
 */
@SuppressWarnings("unchecked")
public class OrFilterTest {
	
	private static final ConstantFilter<Integer> TRUE = new ConstantFilter<Integer>(true);
	private static final ConstantFilter<Integer> FALSE = new ConstantFilter<Integer>(false);
	
	@Test
	public void testTrueOrTrue() {
		assertTrue(new OrFilter<Integer>(TRUE, TRUE).accept(0));
	}
	
	@Test
	public void testTrueOrFalse() {
		assertTrue(new OrFilter<Integer>(TRUE, FALSE).accept(0));
	}
	
	@Test
	public void testFalseOrTrue() {
		assertTrue(new OrFilter<Integer>(FALSE, TRUE).accept(0));
	}
	
	@Test
	public void testFalseOrFalse() {
		assertFalse(new OrFilter<Integer>(FALSE, FALSE).accept(0));
	}
	
}
