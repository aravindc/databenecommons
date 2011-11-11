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

import java.util.List;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import java.util.TreeSet;

/**
 * Tests the {@link CollectionUtil} class.<br/><br/>
 * Created: 21.06.2007 08:29:32
 * @author Volker Bergmann
 */
public class CollectionUtilTest {

	@Test
    public void testToList() {
        List<Number> expectedList = new ArrayList<Number>();
        expectedList.add(1);
        expectedList.add(2);
        expectedList.add(3);
        assertEquals(expectedList, CollectionUtil.toList(1, 2, 3));
    }

	@Test
    public void testToSet() {
        Set<Integer> expectedSet = new HashSet<Integer>();
        expectedSet.add(1);
        expectedSet.add(2);
        expectedSet.add(3);
        assertEquals(expectedSet, CollectionUtil.toSet(1, 2, 3));
    }

	@Test
    public void testToSortedSet() {
        Set<Integer> expectedSet = new TreeSet<Integer>();
        expectedSet.add(1);
        expectedSet.add(2);
        expectedSet.add(3);
        assertEquals(expectedSet, CollectionUtil.toSortedSet(3, 2, 1));
    }

	@Test
    public void testAdd() {
        List<Integer> list = new ArrayList<Integer>();
        CollectionUtil.add(list, 1);
        assertEquals(Arrays.asList(1), list);
        CollectionUtil.add(list, 2);
        assertEquals(Arrays.asList(1, 2), list);
    }

	@Test
    public void testCopy() {
        List<Integer> list = Arrays.asList(1, 2, 3);
        assertEquals(Arrays.asList(), CollectionUtil.copy(list, 0, 0));
        assertEquals(Arrays.asList(1), CollectionUtil.copy(list, 0, 1));
        assertEquals(Arrays.asList(1, 2), CollectionUtil.copy(list, 0, 2));
        assertEquals(Arrays.asList(2, 3), CollectionUtil.copy(list, 1, 2));
    }

	@Test
    public void testEmpty() {
        assertTrue(CollectionUtil.isEmpty(null));
        assertTrue(CollectionUtil.isEmpty(new HashSet<String>()));
        assertTrue(CollectionUtil.isEmpty(new ArrayList<String>()));
        assertFalse(CollectionUtil.isEmpty(Arrays.asList(1)));
    }

	@Test
    public void testToArray() {
        assertTrue(Arrays.equals(new Integer[] { 1 }, CollectionUtil.toArray(Arrays.asList(1), Integer.class)));
        assertTrue(Arrays.equals(new Integer[] { 1, 2, 3 }, CollectionUtil.toArray(Arrays.asList(1, 2, 3), Integer.class)));
    }
	
	@Test
	public void testFormatCommaSeparatedList() {
		List<String> list = new ArrayList<String>();
		list.add("A");
		list.add("B");
		assertEquals("A, B", CollectionUtil.formatCommaSeparatedList(list, null));
		assertEquals("'A', 'B'", CollectionUtil.formatCommaSeparatedList(list, '\''));
	}
	
}
