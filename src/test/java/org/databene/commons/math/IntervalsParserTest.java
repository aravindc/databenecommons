/*
 * (c) Copyright 2011-2014 by Volker Bergmann. All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, is permitted under the terms of the
 * GNU General Public License (GPL).
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS"
 * WITHOUT A WARRANTY OF ANY KIND. ALL EXPRESS OR IMPLIED CONDITIONS,
 * REPRESENTATIONS AND WARRANTIES, INCLUDING ANY IMPLIED WARRANTY OF
 * MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE OR NON-INFRINGEMENT, ARE
 * HEREBY EXCLUDED. IN NO EVENT SHALL THE COPYRIGHT OWNER OR CONTRIBUTORS BE
 * LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR
 * CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF
 * SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS
 * INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN
 * CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE)
 * ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE
 * POSSIBILITY OF SUCH DAMAGE.
 */

package org.databene.commons.math;

import static org.junit.Assert.*;

import org.databene.commons.comparator.IntComparator;
import org.junit.Test;

/**
 * Tests the {@link IntervalsParser}.<br/><br/>
 * Created: 10.03.2011 17:36:33
 * @since 0.5.8
 * @author Volker Bergmann
 */
public class IntervalsParserTest {

	@Test
	public void testWildcard() {
		Intervals<Integer> collection = parse("*");
		assertEquals(1, collection.intervalCount());
		assertEquals(createIntInterval(null, false, null, false), collection.getInterval(0));
	}

	@Test
	public void testEndpoint() {
		Intervals<Integer> collection = parse("1");
		assertEquals(1, collection.intervalCount());
		assertEquals(createIntInterval(1, true, 1, true), collection.getInterval(0));
	}

	@Test
	public void testSingleInterval() {
		Intervals<Integer> collection = parse("[1,2]");
		assertEquals(1, collection.intervalCount());
		assertEquals(createIntInterval(1, true, 2, true), collection.getInterval(0));
	}

	@Test
	public void testMinInclusiveEndpoint() {
		Intervals<Integer> collection = parse(">=2");
		assertEquals(1, collection.intervalCount());
		assertEquals(createIntInterval(2, true, null, false), collection.getInterval(0));
	}

	@Test
	public void testMinExclusiveEndpoint() {
		Intervals<Integer> collection = parse(">2");
		assertEquals(1, collection.intervalCount());
		assertEquals(createIntInterval(2, false, null, false), collection.getInterval(0));
	}

	@Test
	public void testMaxInclusiveEndpoint() {
		Intervals<Integer> collection = parse("<=2");
		assertEquals(1, collection.intervalCount());
		assertEquals(createIntInterval(null, false, 2, true), collection.getInterval(0));
	}

	@Test
	public void testMaxExclusiveEndpoint() {
		Intervals<Integer> collection = parse("<2");
		assertEquals(1, collection.intervalCount());
		assertEquals(createIntInterval(null, false, 2, false), collection.getInterval(0));
	}

	@Test
	public void testTwoEndpoints() {
		Intervals<Integer> collection = parse("1,3");
		assertEquals(2, collection.intervalCount());
		assertEquals(createIntInterval(1, true, 1, true), collection.getInterval(0));
		assertEquals(createIntInterval(3, true, 3, true), collection.getInterval(1));
	}

	@Test
	public void testTwoIntervals() {
		Intervals<Integer> collection = parse("[1,2],]3,5[");
		assertEquals(2, collection.intervalCount());
		assertEquals(createIntInterval(1, true, 2, true), collection.getInterval(0));
		assertEquals(createIntInterval(3, false, 5, false), collection.getInterval(1));
	}

	@Test
	public void testTwoIntervalsWithWhitespace() {
		Intervals<Integer> collection = parse("[ 1 , 2 ] , ] 3 , 5 [");
		assertEquals(2, collection.intervalCount());
		assertEquals(createIntInterval(1, true, 2, true), collection.getInterval(0));
		assertEquals(createIntInterval(3, false, 5, false), collection.getInterval(1));
	}

	@Test
	public void testIntervalAndBound() {
		Intervals<Integer> collection = parse("[1,2] , >= 3");
		assertEquals(2, collection.intervalCount());
		assertEquals(createIntInterval(1, true, 2, true), collection.getInterval(0));
		assertEquals(createIntInterval(3, true, null, false), collection.getInterval(1));
	}

	// helpers ---------------------------------------------------------------------------------------------------------
	
	private static Interval<Integer> createIntInterval(Integer min, boolean minInclusive, Integer max, boolean maxInclusive) {
		return new Interval<Integer>(min, minInclusive, max, maxInclusive, new IntComparator());
	}

	private static Intervals<Integer> parse(String spec) {
		return IntervalsParser.parse(spec, new IntParser(), new IntComparator());
	}
	
}
