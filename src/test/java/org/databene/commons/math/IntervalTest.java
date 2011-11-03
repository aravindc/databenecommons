/*
 * (c) Copyright 2011 by Volker Bergmann. All rights reserved.
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
 * Tests the {@link Interval} class.<br/><br/>
 * Created: 10.03.2011 15:28:39
 * @since 0.5.8
 * @author Volker Bergmann
 */
public class IntervalTest {

	@Test
	public void testClosedInterval() {
		Interval<Integer> interval = new Interval<Integer>(1, true, 2, true, new IntComparator());
		assertFalse(interval.contains(0));
		assertTrue(interval.contains(1));
		assertTrue(interval.contains(2));
		assertFalse(interval.contains(3));
		assertEquals("[1,2]", interval.toString());
	}

	@Test
	public void testRightUnboundedInterval() {
		Interval<Integer> interval = new Interval<Integer>(1, true, null, true, new IntComparator());
		assertFalse(interval.contains(0));
		assertTrue(interval.contains(1));
		assertTrue(interval.contains(2));
		assertTrue(interval.contains(3));
		assertEquals("[1,null]", interval.toString());
	}

	@Test
	public void testLeftUnboundedInterval() {
		Interval<Integer> interval = new Interval<Integer>(null, true, 2, true, new IntComparator());
		assertTrue(interval.contains(0));
		assertTrue(interval.contains(1));
		assertTrue(interval.contains(2));
		assertFalse(interval.contains(3));
		assertEquals("[null,2]", interval.toString());
	}

	@Test
	public void testRightOpenInterval() {
		Interval<Integer> interval = new Interval<Integer>(1, true, 2, false, new IntComparator());
		assertFalse(interval.contains(0));
		assertTrue(interval.contains(1));
		assertFalse(interval.contains(2));
		assertFalse(interval.contains(3));
		assertEquals("[1,2[", interval.toString());
	}

	@Test
	public void testLeftOpenInterval() {
		Interval<Integer> interval = new Interval<Integer>(1, false, 2, true, new IntComparator());
		assertFalse(interval.contains(0));
		assertFalse(interval.contains(1));
		assertTrue(interval.contains(2));
		assertFalse(interval.contains(3));
		assertEquals("]1,2]", interval.toString());
	}

	@Test
	public void testOpenInterval() {
		Interval<Integer> interval = new Interval<Integer>(1, false, 2, false, new IntComparator());
		assertFalse(interval.contains(0));
		assertFalse(interval.contains(1));
		assertFalse(interval.contains(2));
		assertFalse(interval.contains(3));
		assertEquals("]1,2[", interval.toString());
	}

	@Test
	public void testInfiniteInterval() {
		Interval<Integer> interval = new Interval<Integer>(null, false, null, false, new IntComparator());
		assertTrue(interval.contains(0));
		assertTrue(interval.contains(1));
		assertTrue(interval.contains(2));
		assertTrue(interval.contains(3));
		assertEquals("]null,null[", interval.toString());
	}

}
