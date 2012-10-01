/*
 * (c) Copyright 2012 by Volker Bergmann. All rights reserved.
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

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

import org.databene.commons.CollectionUtil;
import org.junit.Test;

/**
 * Combines several {@link List} into one, providing a common access interface.<br/><br/>
 * Created: 10.09.2012 20:09:13
 * @since 0.5.18
 * @author Volker Bergmann
 */
public class CompositeListTest {
	
	@Test
	public void testDefaultConstruction() {
		@SuppressWarnings("unchecked")
		CompositeList<Integer> list = new CompositeList<Integer>();
		assertEquals(0, list.size());
		assertEquals("[]", list.toString());
		assertTrue(list.isEmpty());
	}
	
	@Test
	public void testVarArgsConstruction() {
		@SuppressWarnings("unchecked")
		CompositeList<Integer> list = new CompositeList<Integer>(CollectionUtil.toList(1, 2), CollectionUtil.toList(3), new ArrayList<Integer>());
		assertEquals(3, list.size());
		assertEquals("[[1, 2], [3], []]", list.toString());
		assertFalse(list.isEmpty());
	}
	
	@Test
	public void testAppendingAdd() {
		@SuppressWarnings("unchecked")
		CompositeList<Integer> list = new CompositeList<Integer>(CollectionUtil.toList(1, 2), CollectionUtil.toList(3), new ArrayList<Integer>());
		list.add(4);
		assertEquals(4, list.size());
		assertEquals("[[1, 2], [3], [4]]", list.toString());
	}
	
	@Test
	public void testInsertingAdd() {
		@SuppressWarnings("unchecked")
		CompositeList<Integer> list = new CompositeList<Integer>(CollectionUtil.toList(1, 2), CollectionUtil.toList(3), new ArrayList<Integer>());
		list.add(1, 4);
		assertEquals(4, list.size());
		assertEquals("[[1, 4, 2], [3], []]", list.toString());
		list.add(4, 5);
		assertEquals(5, list.size());
		assertEquals("[[1, 4, 2], [3], [5]]", list.toString());
	}
	
	@Test
	public void testAppendingAddAll() {
		@SuppressWarnings("unchecked")
		CompositeList<Integer> list = new CompositeList<Integer>(CollectionUtil.toList(1, 2), new ArrayList<Integer>());
		list.addAll(CollectionUtil.toList(3, 4));
		assertEquals(4, list.size());
		assertEquals("[[1, 2], [3, 4]]", list.toString());
	}
	
	@Test
	public void testInsertingAddAll() {
		@SuppressWarnings("unchecked")
		CompositeList<Integer> list = new CompositeList<Integer>(CollectionUtil.toList(1, 2), new ArrayList<Integer>());
		list.addAll(1, CollectionUtil.toList(3, 4));
		assertEquals(4, list.size());
		assertEquals("[[1, 3, 4, 2], []]", list.toString());
	}
	
	@Test
	public void testClear() {
		@SuppressWarnings("unchecked")
		CompositeList<Integer> list = new CompositeList<Integer>(CollectionUtil.toList(1, 2));
		assertEquals(2, list.size());
		assertEquals("[[1, 2]]", list.toString());
		list.clear();
		assertEquals(0, list.size());
		assertEquals("[]", list.toString());
	}
	
	@Test
	public void testContains() {
		@SuppressWarnings("unchecked")
		CompositeList<Integer> list = new CompositeList<Integer>(CollectionUtil.toList(1, 2), CollectionUtil.toList(3, 4));
		assertFalse(list.contains(0));
		assertTrue(list.contains(1));
		assertTrue(list.contains(2));
		assertTrue(list.contains(3));
		assertTrue(list.contains(4));
		assertFalse(list.contains(5));
	}
	
	@Test
	public void testContainsAll() {
		@SuppressWarnings("unchecked")
		CompositeList<Integer> list = new CompositeList<Integer>(CollectionUtil.toList(1, 2), CollectionUtil.toList(3, 4));
		assertFalse(list.containsAll(CollectionUtil.toList(0)));
		assertTrue(list.containsAll(CollectionUtil.toList(1)));
		assertTrue(list.containsAll(CollectionUtil.toList(2, 3, 4)));
		assertFalse(list.containsAll(CollectionUtil.toList(1, 2, 3, 4, 5)));
	}
	
	@Test
	public void testGet_legalIndex() {
		@SuppressWarnings("unchecked")
		CompositeList<Integer> list = new CompositeList<Integer>(CollectionUtil.toList(1, 2), CollectionUtil.toList(3, 4));
		assertEquals(1, list.get(0).intValue());
		assertEquals(2, list.get(1).intValue());
		assertEquals(3, list.get(2).intValue());
		assertEquals(4, list.get(3).intValue());
	}
	
	@Test(expected = IndexOutOfBoundsException.class)
	public void testGet_index_minus_1() {
		@SuppressWarnings("unchecked")
		CompositeList<Integer> list = new CompositeList<Integer>(CollectionUtil.toList(1, 2), CollectionUtil.toList(3, 4));
		list.get(-1);
	}
	
	@Test(expected = IndexOutOfBoundsException.class)
	public void testGet_indexN() {
		@SuppressWarnings("unchecked")
		CompositeList<Integer> list = new CompositeList<Integer>(CollectionUtil.toList(1, 2), CollectionUtil.toList(3, 4));
		list.get(4);
	}
	
	@Test
	public void testIndexOf() {
		@SuppressWarnings("unchecked")
		CompositeList<Integer> list = new CompositeList<Integer>(CollectionUtil.toList(1, 2), CollectionUtil.toList(3, 1));
		assertEquals(-1, list.indexOf(0));
		assertEquals(0, list.indexOf(1));
		assertEquals(1, list.indexOf(2));
		assertEquals(2, list.indexOf(3));
		assertEquals(-1, list.indexOf(4));
	}
	
	@Test
	public void testIterator() {
		@SuppressWarnings("unchecked")
		CompositeList<Integer> list = new CompositeList<Integer>(CollectionUtil.toList(1, 2), CollectionUtil.toList(3), new ArrayList<Integer>());
		Iterator<Integer> iterator = list.iterator();
		assertTrue(iterator.hasNext());
		assertEquals(1, iterator.next().intValue());
		assertTrue(iterator.hasNext());
		assertEquals(2, iterator.next().intValue());
		assertTrue(iterator.hasNext());
		assertEquals(3, iterator.next().intValue());
		assertFalse(iterator.hasNext());
	}
	
	@Test
	public void testListIterator() {
		@SuppressWarnings("unchecked")
		CompositeList<Integer> list = new CompositeList<Integer>(CollectionUtil.toList(1, 2), CollectionUtil.toList(3), new ArrayList<Integer>());
		ListIterator<Integer> iterator = list.listIterator();
		assertFalse(iterator.hasPrevious());
		assertTrue(iterator.hasNext());
		assertEquals(1, iterator.next().intValue());
		assertTrue(iterator.hasPrevious());
		assertTrue(iterator.hasNext());
		assertEquals(2, iterator.next().intValue());
		assertTrue(iterator.hasPrevious());
		assertTrue(iterator.hasNext());
		assertEquals(3, iterator.next().intValue());
		assertTrue(iterator.hasPrevious());
		assertFalse(iterator.hasNext());
		assertEquals(3, iterator.previous().intValue());
		assertTrue(iterator.hasPrevious());
		assertTrue(iterator.hasNext());
		assertEquals(2, iterator.previous().intValue());
		assertTrue(iterator.hasPrevious());
		assertTrue(iterator.hasNext());
		assertEquals(1, iterator.previous().intValue());
		assertFalse(iterator.hasPrevious());
		assertTrue(iterator.hasNext());
	}
	
	@Test
	public void testSubListIterator() {
		@SuppressWarnings("unchecked")
		CompositeList<Integer> list = new CompositeList<Integer>(CollectionUtil.toList(1, 2), CollectionUtil.toList(3), new ArrayList<Integer>());
		ListIterator<Integer> iterator = list.listIterator(1);
		assertTrue(iterator.hasPrevious());
		assertTrue(iterator.hasNext());
		assertEquals(2, iterator.next().intValue());
		assertTrue(iterator.hasPrevious());
		assertTrue(iterator.hasNext());
		assertEquals(3, iterator.next().intValue());
		assertTrue(iterator.hasPrevious());
		assertFalse(iterator.hasNext());
	}
	
	@Test
	public void testLastIndexOf() {
		@SuppressWarnings("unchecked")
		CompositeList<Integer> list = new CompositeList<Integer>(CollectionUtil.toList(1, 2), CollectionUtil.toList(3, 1));
		assertEquals(-1, list.lastIndexOf(0));
		assertEquals(3, list.lastIndexOf(1));
		assertEquals(2, list.lastIndexOf(3));
		assertEquals(-1, list.lastIndexOf(4));
	}
	
	@Test
	public void testRemove_object() {
		@SuppressWarnings("unchecked")
		CompositeList<String> list = new CompositeList<String>(CollectionUtil.toList("1", "2"), CollectionUtil.toList("3", "4"));
		list.remove("3");
		assertEquals("[[1, 2], [4]]", list.toString());
		list.remove("2");
		assertEquals("[[1], [4]]", list.toString());
		list.remove("4");
		assertEquals("[[1], []]", list.toString());
		list.remove("1");
		assertEquals("[[], []]", list.toString());
	}
	
	@Test
	public void testRemove_index() {
		@SuppressWarnings("unchecked")
		CompositeList<String> list = new CompositeList<String>(CollectionUtil.toList("1", "2"), CollectionUtil.toList("3", "4"));
		list.remove(2);
		assertEquals("[[1, 2], [4]]", list.toString());
		list.remove(1);
		assertEquals("[[1], [4]]", list.toString());
		list.remove(1);
		assertEquals("[[1], []]", list.toString());
		list.remove(0);
		assertEquals("[[], []]", list.toString());
	}
	
	@Test
	public void testRemoveAll() {
		@SuppressWarnings("unchecked")
		CompositeList<Integer> list = new CompositeList<Integer>(CollectionUtil.toList(1, 2), CollectionUtil.toList(3, 1));
		list.removeAll(CollectionUtil.toList(1, 4));
		assertEquals("[[2], [3]]", list.toString());
		list.removeAll(CollectionUtil.toList(2, 3));
		assertEquals("[[], []]", list.toString());
	}
	
	@Test
	public void testRetainAll() {
		@SuppressWarnings("unchecked")
		CompositeList<Integer> list = new CompositeList<Integer>(CollectionUtil.toList(1, 2), CollectionUtil.toList(3, 1));
		list.retainAll(CollectionUtil.toList(1, 2));
		assertEquals("[[1, 2], [1]]", list.toString());
		list.retainAll(CollectionUtil.toList(4));
		assertEquals("[[], []]", list.toString());
	}
	
	@Test
	public void testSet_legal() {
		@SuppressWarnings("unchecked")
		CompositeList<Integer> list = new CompositeList<Integer>(CollectionUtil.toList(1, 2), CollectionUtil.toList(3, 1));
		list.set(1, 5);
		assertEquals("[[1, 5], [3, 1]]", list.toString());
		list.set(3, 6);
		assertEquals("[[1, 5], [3, 6]]", list.toString());
	}
	
	@Test(expected = IndexOutOfBoundsException.class)
	public void testSet_minus1() {
		@SuppressWarnings("unchecked")
		CompositeList<Integer> list = new CompositeList<Integer>(CollectionUtil.toList(1, 2), CollectionUtil.toList(3, 1));
		list.set(-1, 5);
	}
	
	@Test(expected = IndexOutOfBoundsException.class)
	public void testSet_N() {
		@SuppressWarnings("unchecked")
		CompositeList<Integer> list = new CompositeList<Integer>(CollectionUtil.toList(1, 2), CollectionUtil.toList(3, 1));
		list.set(4, 5);
	}
	
	@Test
	public void testSubList() {
		@SuppressWarnings("unchecked")
		CompositeList<Integer> list = new CompositeList<Integer>(CollectionUtil.toList(1, 2), CollectionUtil.toList(3, 4));
		assertEquals("[2, 3]", list.subList(1, 3).toString());
	}
	
	@Test
	public void testToArray_noarg() {
		@SuppressWarnings("unchecked")
		CompositeList<Integer> list = new CompositeList<Integer>(CollectionUtil.toList(1, 2), CollectionUtil.toList(3, 4));
		assertArrayEquals(new Object[] { 1, 2, 3, 4 }, list.toArray());
	}
	
	@Test
	public void testToArray_arrayArg() {
		@SuppressWarnings("unchecked")
		CompositeList<Integer> list = new CompositeList<Integer>(CollectionUtil.toList(1, 2), CollectionUtil.toList(3, 4));
		assertArrayEquals(new Integer[] { 1, 2, 3, 4 }, list.toArray(new Integer[4]));
	}
	
	
}
