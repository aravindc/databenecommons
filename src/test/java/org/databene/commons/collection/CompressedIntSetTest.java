/*
 * (c) Copyright 2010 by Volker Bergmann. All rights reserved.
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

package org.databene.commons.collection;

import static org.junit.Assert.*;

import org.databene.commons.iterator.IteratorTestCase;
import org.junit.Test;

/**
 * Tests the {@link CompressedIntSet}.<br/><br/>
 * Created: 05.10.2010 19:53:23
 * @since 0.5.4
 * @author Volker Bergmann
 */
public class CompressedIntSetTest extends IteratorTestCase {

	@Test
	public void testAdd_11() {
		CompressedIntSet set = new CompressedIntSet();
		assertTrue(set.isEmpty());
		set.add(1);
		assertFalse(set.isEmpty());
		assertTrue(set.contains(1));
		set.add(1);
		assertEquals(new IntRange(1, 1), set.numbers.get(1));
		assertFalse(set.isEmpty());
		assertTrue(set.contains(1));
		assertEquals(1, set.size());
		expectNextElements(set.iterator(), 1).withNoNext();
	}
	
	@Test
	public void testAdd_12() {
		CompressedIntSet set = new CompressedIntSet();
		assertTrue(set.isEmpty());
		
		set.add(1);
		assertFalse(set.isEmpty());
		assertTrue(set.contains(1));
		assertFalse(set.contains(2));
		assertEquals(1, set.size());
		
		set.add(2);
		assertEquals(new IntRange(1, 2), set.numbers.get(1));
		assertFalse(set.isEmpty());
		assertTrue(set.contains(1));
		assertTrue(set.contains(2));
		
		assertEquals(2, set.size());
		expectNextElements(set.iterator(), 1, 2).withNoNext();
	}

	@Test
	public void testAdd_21() {
		CompressedIntSet set = new CompressedIntSet();

		set.add(2);
		assertFalse(set.isEmpty());
		assertFalse(set.contains(1));
		assertTrue(set.contains(2));
		assertEquals(1, set.size());
		
		set.add(1);
		assertEquals(new IntRange(1, 2), set.numbers.get(1));
		assertFalse(set.isEmpty());
		assertTrue(set.contains(1));
		assertTrue(set.contains(2));
		
		assertEquals(2, set.size());
		expectNextElements(set.iterator(), 1, 2).withNoNext();
	}

	@Test
	public void testAdd_13() {
		CompressedIntSet set = new CompressedIntSet();

		set.add(1);
		assertFalse(set.isEmpty());
		assertTrue(set.contains(1));
		assertFalse(set.contains(3));
		
		set.add(3);
		assertEquals(new IntRange(1, 1), set.numbers.get(1));
		assertEquals(new IntRange(3, 3), set.numbers.get(3));
		assertFalse(set.isEmpty());
		assertTrue(set.contains(1));
		assertTrue(set.contains(3));
		
		assertEquals(2, set.size());
		expectNextElements(set.iterator(), 1, 3).withNoNext();
	}

	@Test
	public void testAdd_31() {
		CompressedIntSet set = new CompressedIntSet();

		set.add(3);
		assertFalse(set.isEmpty());
		assertFalse(set.contains(1));
		assertTrue(set.contains(3));
		
		set.add(1);
		assertEquals(new IntRange(1, 1), set.numbers.get(1));
		assertEquals(new IntRange(3, 3), set.numbers.get(3));
		assertFalse(set.isEmpty());
		assertTrue(set.contains(1));
		assertTrue(set.contains(3));
		
		assertEquals(2, set.size());
		expectNextElements(set.iterator(), 1, 3).withNoNext();
	}

	@Test
	public void testAdd_132() {
		CompressedIntSet set = new CompressedIntSet();
		set.add(1);
		set.add(3);
		set.add(2);
		assertEquals(new IntRange(1, 3), set.numbers.get(1));
		assertEquals(3, set.size());
		expectNextElements(set.iterator(), 1, 2, 3).withNoNext();
	}
	
	@Test
	public void testAdd_1to10() {
		CompressedIntSet set = new CompressedIntSet();
		set.add(1);
		set.add(3);
		set.add(6);
		set.add(9);
		set.add(2);
		set.add(7);
		set.add(10);
		set.add(4);
		set.add(8);
		set.add(5);
		assertEquals(new IntRange(1, 10), set.numbers.get(1));
		assertEquals(10, set.size());
		expectNextElements(set.iterator(), 1, 2, 3, 4, 5, 6, 7, 8, 9, 10).withNoNext();
	}

	@Test
	public void testRemove_not() {
		CompressedIntSet set = new CompressedIntSet();
		assertFalse(set.remove(3));
		set.add(2);
		assertFalse(set.remove(3));
		set.add(4);
		assertFalse(set.remove(3));
	}
	
	@Test
	public void testRemove_exactly() {
		CompressedIntSet set = new CompressedIntSet();
		set.add(2);
		assertTrue(set.remove(2));
		assertEquals(0, set.numbers.size());
		assertTrue(set.isEmpty());
	}
	
	@Test
	public void testRemove_min() {
		CompressedIntSet set = new CompressedIntSet();
		set.addAll(2, 3);
		assertTrue(set.remove(2));
		assertEquals(1, set.numbers.size());
		assertEquals(new IntRange(3, 3), set.numbers.get(3));
	}
	
	@Test
	public void testRemove_max() {
		CompressedIntSet set = new CompressedIntSet();
		set.addAll(4, 5);
		assertTrue(set.remove(5));
		assertEquals(1, set.numbers.size());
		assertEquals(new IntRange(4, 4), set.numbers.get(4));
	}
	
	@Test
	public void testRemove_mid() {
		CompressedIntSet set = new CompressedIntSet();
		set.addAll(6, 7, 8);
		assertTrue(set.remove(7));
		assertEquals(2, set.numbers.size());
		assertEquals(new IntRange(6, 6), set.numbers.get(6));
		assertEquals(new IntRange(8, 8), set.numbers.get(8));
	}
	
}
