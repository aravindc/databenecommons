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
package org.databene.commons.iterator;

import org.junit.Test;
import static junit.framework.Assert.*;

import java.util.List;
import java.util.Arrays;

import org.databene.commons.Filter;
import org.databene.commons.iterator.BidirectionalIterator;
import org.databene.commons.iterator.BidirectionalListIterator;
import org.databene.commons.iterator.FilteringIterator;

/**
 * Tests the {@link FilteringIterator}.<br/><br/>
 * Created: 08.05.2007 19:03:28
 * @author Volker Bergmann
 */
public class FilteringIteratorTest {

	@Test
    public void testNext() {
        List<Character> list = Arrays.asList('1', 'a', '2', 'b', '3');
        BidirectionalIterator<Character> realIterator
                = new BidirectionalListIterator<Character>(list);
        Filter<Character> filter = new Filter<Character>() {

            @Override
			public boolean accept(Character c) {
                return Character.isDigit(c);
            }
        };
        BidirectionalIterator<Character> iterator = new FilteringIterator<Character>(realIterator, filter);
        assertTrue(iterator.hasNext());
        assertTrue(iterator.hasNext());
        assertEquals('1', iterator.next().charValue());
        assertTrue(iterator.hasNext());
        assertTrue(iterator.hasNext());
        assertEquals('2', iterator.next().charValue());
        assertTrue(iterator.hasNext());
        assertTrue(iterator.hasNext());
        assertEquals('3', iterator.next().charValue());
        assertFalse(iterator.hasNext());
        assertFalse(iterator.hasNext());
    }

}
