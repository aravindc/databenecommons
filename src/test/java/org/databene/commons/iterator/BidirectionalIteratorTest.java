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

import static org.junit.Assert.*;

/**
 * Provides functionality for testing classes that implement the {@link BidirectionalIterator} interface.
 * Created at 04.05.2008 09:37:07
 * @since 0.4.3
 * @author Volker Bergmann
 */
public abstract class BidirectionalIteratorTest extends IteratorTestCase {

	public static <T> PreviousHelper<T> expectPreviousElements(BidirectionalIterator<T> iterator, T... elements) {
		for (T element : elements) {
			assertTrue(iterator.hasPrevious());
			assertEquals(element, iterator.previous());
		}
		return new PreviousHelper<T>(iterator);
	}
	
	public static class PreviousHelper<T> {
		
		BidirectionalIterator<T> iterator;

		public PreviousHelper(BidirectionalIterator<T> iterator) {
			this.iterator = iterator;
		}
		
		public void withPrevious() {
			assertTrue(iterator.hasNext());
		}
		
		public void withNoPrevious() {
			assertFalse(iterator.hasPrevious());
		}
	}
	
}
