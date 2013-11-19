/*
 * (c) Copyright 2007-2009 by Volker Bergmann. All rights reserved.
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

import org.junit.Test;
import static junit.framework.Assert.*;

import java.util.*;

/**
 * Tests the {@link OrderedMap}.<br/><br/>
 * Created: 21.06.2007 08:32:53
 * @author Volker Bergmann
 */
public class OrderedMapTest {

	@Test
    public void testDefaultConstructor() {
        OrderedMap<Integer, Integer> map = createMap123();
        check(map, 1, 11, 2, 22, 3, 33);
    }

	@Test
    public void testCopyConstructor() {
        OrderedMap<Integer, Integer> map = createMap123();
        OrderedMap<Integer, Integer> copy = new OrderedMap<Integer, Integer>(map);
        check(copy, 1, 11, 2, 22, 3, 33);
    }

	@Test
    public void testEmptyMap() {
        OrderedMap<Integer, Integer> map = new OrderedMap<Integer, Integer>();
        check(map);
    }

	@Test
    public void testClear() {
        OrderedMap<Integer, Integer> map = createMap123();
        map.clear();
        check(map);
    }

	@Test
    public void testAppend() {
        OrderedMap<Integer, Integer> map = createMap123();
        map.put(4, 44);
        check(map, 1, 11, 2, 22, 3, 33, 4, 44);
    }

	@Test
    public void testOverwrite() {
        OrderedMap<Integer, Integer> map = createMap123();
        map.put(2, 222);
        check(map, 1, 11, 2, 222, 3, 33);
    }

	@Test
    public void testRemoveAtStart() {
        OrderedMap<Integer, Integer> map = createMap123();
        map.remove(1);
        check(map, 2, 22, 3, 33);
    }

	@Test
    public void testRemoveInMiddle() {
        OrderedMap<Integer, Integer> map = createMap123();
        map.remove(2);
        check(map, 1, 11, 3, 33);
    }

	@Test
    public void testRemoveAtEnd() {
        OrderedMap<Integer, Integer> map = createMap123();
        map.remove(3);
        check(map, 1, 11, 2, 22);
    }

	@Test
    public void testPutAll() {
        OrderedMap<Integer, Integer> map = createMap123();
        OrderedMap<Integer, Integer> map2 = new OrderedMap<Integer, Integer>();
        map2.put(0, 0);
        map2.put(4, 44);
        map.putAll(map2);
        check(map, 1, 11, 2, 22, 3, 33, 0, 0, 4, 44);
    }

	@Test
    public void testEquals() {
        assertTrue(new OrderedMap<Integer, Integer>().equals(new OrderedMap<Integer, Integer>()));
        OrderedMap<Integer, Integer> map1 = createMap123();
        OrderedMap<Integer, Integer> map2 = createMap123();
        assertTrue(map1.equals(map2));
        map2.put(4, 44);
        assertFalse(map1.equals(map2));
        assertFalse(map1.equals(new OrderedMap<Integer, Integer>()));
    }

	@Test
    public void testHashCode() {
        assertEquals(new OrderedMap<Integer, Integer>().hashCode(), new OrderedMap<Integer, Integer>().hashCode());
        OrderedMap<Integer, Integer> map1 = createMap123();
        OrderedMap<Integer, Integer> map2 = createMap123();
        assertEquals(map1.hashCode(), map2.hashCode());
        map2.put(4, 44);
        assertTrue(map1.hashCode() != map2.hashCode());
        assertTrue(map1.hashCode() != new OrderedMap<Integer, Integer>().hashCode());
    }

	@Test
    public void testToString() {
        assertEquals("{1=11, 2=22, 3=33}", createMap123().toString());
    }
    
	@Test
    public void testEqualsIgnoreOrder() {
    	OrderedMap<Integer, Integer> map123 = createMap123();
    	OrderedMap<Integer, Integer> map321 = new OrderedMap<Integer, Integer>();
    	map321.put(3, 33);
    	map321.put(2, 22);
    	assertFalse(map123.equalsIgnoreOrder(map321));
    	assertFalse(map321.equalsIgnoreOrder(map123));
    	map321.put(1, 11);
    	assertTrue(map123.equalsIgnoreOrder(map321));
    	assertTrue(map321.equalsIgnoreOrder(map123));
    }
    
	@Test
    public void testUpdateEntry() {
    	OrderedMap<Integer, Integer> map = createMap123();
    	for (Map.Entry<Integer, Integer> entry : map.entrySet())
    		entry.setValue(4);
    	for (Integer value : map.values)
    		assertEquals(4, (int)value);
    }

    // private helpers -------------------------------------------------------------------------------------------------

    private static <T> void check(OrderedMap<T, T> map, T ... expectedKeyValuePairs) {
        if (expectedKeyValuePairs.length == 0)
            assertTrue("Map is expected to be empty", map.isEmpty());
        assertEquals("Unexpected size", expectedKeyValuePairs.length / 2, map.size());
        Object[] expectedValues = new Object[expectedKeyValuePairs.length / 2];
        Object[] expectedKeys = new Object[expectedKeyValuePairs.length / 2];
        for (int i = 0; i < expectedKeyValuePairs.length; i += 2) {
            expectedKeys[i / 2] = expectedKeyValuePairs[i];
            expectedValues[i / 2] = expectedKeyValuePairs[i + 1];
        }
        assertTrue(Arrays.equals(expectedValues, map.toArray()));
        Set<T> keys = map.keySet();
        Iterator<T> keyIterator = keys.iterator();
        List<T> values = map.values();
        Iterator<T> valueIterator = values.iterator();
        Set<Map.Entry<T, T>> entries = map.entrySet();
        Iterator<Map.Entry<T, T>> entryIterator = entries.iterator();
        for (int i = 0; i < expectedKeys.length; i++) {
            assertTrue(map.containsKey(expectedKeys[i]));
            assertTrue(map.containsValue(expectedValues[i]));
            assertEquals(expectedValues[i], map.get(expectedKeys[i]));
            assertTrue(keyIterator.hasNext());
            assertEquals(expectedKeys[i], keyIterator.next());
            assertTrue(valueIterator.hasNext());
            assertEquals(expectedValues[i], valueIterator.next());
            assertEquals(expectedValues[i], values.get(i));
            assertEquals(expectedValues[i], map.valueAt(i));
            assertTrue(entryIterator.hasNext());
            Map.Entry<T, T> entry = entryIterator.next();
            assertEquals(expectedKeys[i], entry.getKey());
            assertEquals(expectedValues[i], entry.getValue());
        }
    }

    private static OrderedMap<Integer, Integer> createMap123() {
        OrderedMap<Integer, Integer> map = new OrderedMap<Integer, Integer>();
        map.put(1, 11);
        map.put(2, 22);
        map.put(3, 33);
        return map;
    }
}
