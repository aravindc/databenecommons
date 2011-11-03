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

import org.databene.commons.ComparableComparator;
import org.junit.Test;

/**
 * Tests the {@link Intervals} class.<br/><br/>
 * Created: 10.03.2011 17:36:21
 * @since 0.5.8
 * @author Volker Bergmann
 */
public class IntervalsTest {

	@Test
	public void test() {
		Intervals<Integer> collection = new Intervals<Integer>();
		ComparableComparator<Integer> comparator = new ComparableComparator<Integer>();
		collection.add(new Interval<Integer>(1, true, 2, true, comparator));
		collection.add(new Interval<Integer>(3, false, 5, false, comparator));
		assertFalse(collection.contains(0));
		assertTrue( collection.contains(1));
		assertTrue( collection.contains(2));
		assertFalse(collection.contains(3));
		assertTrue( collection.contains(4));
		assertFalse(collection.contains(5));
		assertEquals("[1,2], ]3,5[", collection.toString());
	}
	
}
