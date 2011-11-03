/*
 * (c) Copyright 2007-2011 by Volker Bergmann. All rights reserved.
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

package org.databene.commons.converter;

import org.junit.Test;
import static junit.framework.Assert.*;

import java.util.*;

import org.databene.commons.ArrayUtil;
import org.databene.commons.CollectionUtil;
import org.databene.commons.ConversionException;
import org.databene.commons.Converter;
import org.databene.commons.converter.ToCollectionConverter;

/**
 * Tests the ToCollectionConverter.<br/>
 * <br/>
 * Created: 28.08.2007 17:35:50
 * @author Volker Bergmann
 */
public class ToCollectionConverterTest extends AbstractConverterTest {

    // tests -----------------------------------------------------------------------------------------------------------

	public ToCollectionConverterTest() {
	    super(ToCollectionConverter.class);
    }

	@Test
    public void testNull() {
        assertNull(ToCollectionConverter.convert(null, List.class));
    }

    @Test
	@SuppressWarnings({ "rawtypes", "unchecked" })
    public void testToList() throws ConversionException {
        Converter<Object, ArrayList> toArrayListConverter = new ToCollectionConverter<ArrayList>(ArrayList.class);
        Converter<Object, List> toListConverter = new ToCollectionConverter<List>(List.class);
        Integer[] array = createArray();
        List<Integer> list = createList();
        Set<Integer> set = createSet();
        Set<Integer> sortedSet = createSortedSet();
        assertEquals(list, toArrayListConverter.convert(array));
        assertEquals(list, toListConverter.convert(array));
        assertEquals(list, toArrayListConverter.convert(list));
        assertEquals(list, toListConverter.convert(list));
        assertTrue(CollectionUtil.equalsIgnoreOrder(list, toArrayListConverter.convert(set)));
        assertTrue(CollectionUtil.equalsIgnoreOrder(list, toListConverter.convert(set)));
        assertEquals(list, toArrayListConverter.convert(sortedSet));
        assertEquals(list, toListConverter.convert(sortedSet));
    }


    @Test
	@SuppressWarnings("rawtypes")
    public void testToSet() throws ConversionException {
        Converter<Object, HashSet> toHashSetConverter = new ToCollectionConverter<HashSet>(HashSet.class);
        Converter<Object, Set> toSetConverter = new ToCollectionConverter<Set>(Set.class);
        Integer[] array = createArray();
        Set<Integer> set = createSet();
        Set<Integer> sortedSet = createSortedSet();
        List<Integer> list = createList();
        assertEquals(set, toHashSetConverter.convert(array));
        assertEquals(set, toSetConverter.convert(array));
        assertEquals(set, toHashSetConverter.convert(list));
        assertEquals(set, toSetConverter.convert(list));
        assertEquals(set, toHashSetConverter.convert(set));
        assertEquals(set, toSetConverter.convert(set));
        assertEquals(set, toHashSetConverter.convert(sortedSet));
        assertEquals(set, toSetConverter.convert(sortedSet));
    }

	@Test
	@SuppressWarnings("rawtypes")
    public void testToSortedSet() throws ConversionException {
        Converter<Object, TreeSet> toTreeSetConverter = new ToCollectionConverter<TreeSet>(TreeSet.class);
        Converter<Object, SortedSet> toSortedSetConverter = new ToCollectionConverter<SortedSet>(SortedSet.class);
        List<Integer> list = createList();
        Set<Integer> set = createSet();
        Set<Integer> sortedSet = createSortedSet();
        Integer[] array = createArray();
        assertEquals(sortedSet, toTreeSetConverter.convert(array));
        assertEquals(sortedSet, toSortedSetConverter.convert(array));
        assertEquals(sortedSet, toTreeSetConverter.convert(list));
        assertEquals(sortedSet, toSortedSetConverter.convert(list));
        assertEquals(sortedSet, toTreeSetConverter.convert(set));
        assertEquals(sortedSet, toSortedSetConverter.convert(set));
        assertEquals(sortedSet, toTreeSetConverter.convert(sortedSet));
        assertEquals(sortedSet, toSortedSetConverter.convert(sortedSet));
    }

    // private helpers -------------------------------------------------------------------------------------------------

    private Integer[] createArray() {
        return ArrayUtil.toArray(1, 2, 3);
    }

    private List<Integer> createList() {
        return CollectionUtil.toList(1, 2, 3);
    }

    private Set<Integer> createSet() {
        return CollectionUtil.toSet(1, 2, 3);
    }

    private Set<Integer> createSortedSet() {
        SortedSet<Integer> set = new TreeSet<Integer>();
        set.add(1);
        set.add(2);
        set.add(3);
        return set;
    }
    
}
