/*
 * (c) Copyright 2009 by Volker Bergmann. All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, is permitted under the terms of the
 * GNU General Public License.
 *
 * For redistributing this software or a derivative work under a license other
 * than the GPL-compatible Free Software License as defined by the Free
 * Software Foundation or approved by OSI, you must first obtain a commercial
 * license to this software product from Volker Bergmann.
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

package org.databene.commons;

import java.util.Arrays;
import java.util.Iterator;

import org.junit.Test;
import static junit.framework.Assert.*;

/**
 * Test the {@link OrderedSet}.<br/>
 * <br/>
 * Created at 28.02.2009 13:52:28
 * @since 0.4.8
 * @author Volker Bergmann
 */

public class OrderedSetTest {

	@Test
	public void testAdd() {
		OrderedSet<Integer> set = create123();
		assertFalse(set.add(1));
		assertEquals(3, set.size());
		assertTrue(set.add(4));
		assertEquals(4, set.size());
	}

	@Test
	public void testAddAll() {
		OrderedSet<Integer> set = create123();
		assertFalse(set.addAll(CollectionUtil.toList(1, 2, 3)));
		assertEquals(3, set.size());
		assertTrue(set.addAll(CollectionUtil.toList(3, 4, 5)));
		assertEquals(5, set.size());
	}

	@Test
	public void testContains() {
		OrderedSet<Integer> set = create123();
		assertFalse(set.contains(0));
		assertTrue(set.contains(1));
		assertTrue(set.contains(2));
		assertTrue(set.contains(3));
		assertFalse(set.contains(4));
		set.clear();
		assertTrue(set.isEmpty());
		assertEquals(0, set.size());
	}

	@Test
	public void testContainsAll() {
		OrderedSet<Integer> set = create123();
		assertFalse(set.containsAll(CollectionUtil.toList(0, 4)));
		assertTrue(set.containsAll(CollectionUtil.toList(1, 2)));
		assertFalse(set.containsAll(CollectionUtil.toList(0, 1)));
	}

	@Test
	public void testClearAndIsEmpty() {
		OrderedSet<Integer> set = create123();
		assertFalse(set.isEmpty());
		set.clear();
		assertTrue(set.isEmpty());
		assertEquals(0, set.size());
	}
	
	@Test
	public void testIterator() {
		OrderedSet<Integer> set = create123();
		Iterator<Integer> iterator = set.iterator();
		assertTrue(iterator.hasNext());
		assertEquals(1, iterator.next().intValue());
		assertTrue(iterator.hasNext());
		assertEquals(2, iterator.next().intValue());
		assertTrue(iterator.hasNext());
		assertEquals(3, iterator.next().intValue());
		assertFalse(iterator.hasNext());
	}
	
	@Test
	public void testRemove() {
		OrderedSet<Integer> set = create123();
		assertTrue(set.remove(1));
		assertFalse(set.remove(0));
		assertEquals(2, set.size());
	}
	
	@Test
	public void testRemoveAll() {
		OrderedSet<Integer> set = create123();
		assertFalse(set.removeAll(CollectionUtil.toList(0, 4)));
		assertTrue(set.removeAll(CollectionUtil.toList(0, 1)));
		assertEquals(2, set.size());
		assertTrue(set.removeAll(CollectionUtil.toList(2, 3)));
		assertEquals(0, set.size());
	}
	
	@Test
	public void testRetainAll() {
		OrderedSet<Integer> set = create123();
		assertFalse(set.retainAll(CollectionUtil.toList(0, 1, 2, 3, 4)));
		assertEquals(3, set.size());
		assertFalse(set.retainAll(CollectionUtil.toList(1, 2, 3)));
		assertEquals(3, set.size());
		assertTrue(set.retainAll(CollectionUtil.toList(2, 3)));
		assertEquals(2, set.size());
		assertFalse(set.contains(1));
	}
	
	@Test
	public void testToArrayDefault() {
		OrderedSet<Integer> set = create123();
		assertTrue(Arrays.equals(new Integer[] { 1, 2, 3}, set.toArray()));
	}
	
	@Test
	public void testToArrayParametrized() {
		OrderedSet<Integer> set = create123();
		assertTrue(Arrays.equals(new Integer[] { 1, 2, 3}, set.toArray(new Integer[3])));
	}
	
	// helper methods --------------------------------------------------------------------------------------------------

	private OrderedSet<Integer> create123() {
	    return new OrderedSet<Integer>(CollectionUtil.toList(1, 2, 3));
    }
	
}
