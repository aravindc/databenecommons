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
package org.databene.commons.math;

import static org.junit.Assert.*;

import java.text.ParsePosition;

import org.databene.commons.ComparableComparator;
import org.databene.commons.comparator.IntComparator;
import org.junit.Test;

/**
 * Tests the {@link IntervalParser}.
 * Created: 10.03.2011 16:04:54
 * @since 0.5.8
 * @author Volker Bergmann
 */
public class IntervalParserTest {
	
	@Test
	public void testClosedInterval() {
		Interval<Integer> parsedInterval = parseInterval("[1,2]");
		assertEquals(new Interval<Integer>(1, true, 2, true, new ComparableComparator<Integer>()), parsedInterval);
	}

	@Test
	public void testOpenInterval() {
		Interval<Integer> parsedInterval = parseInterval("]1,2[");
		assertEquals(new Interval<Integer>(1, false, 2, false, new ComparableComparator<Integer>()), parsedInterval);
	}

	@Test
	public void testWhitespace() {
		Interval<Integer> parsedInterval = parseInterval(" [ 1 ,	2 ] ");
		assertEquals(new Interval<Integer>(1, true, 2, true, new ComparableComparator<Integer>()), parsedInterval);
	}

	// helpers ---------------------------------------------------------------------------------------------------------
	
	private static Interval<Integer> parseInterval(String text) {
		IntervalParser<Integer> parser = new IntervalParser<Integer>(new IntParser(), new IntComparator());
		return parser.parseObject(text, new ParsePosition(0));
	}

}
