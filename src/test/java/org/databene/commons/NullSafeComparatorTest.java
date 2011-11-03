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

import java.util.Comparator;
import java.util.Locale;
import java.text.Collator;

import org.databene.commons.NullSafeComparator;

/**
 * Tests the NullSafeComparator.<br/>
 * <br/>
 * Created: 20.04.2006 18:01:28
 * @author Volker Bergmann
 */
public class NullSafeComparatorTest {

	@Test
    public void testInstantiation() {
        new NullSafeComparator<Integer>();
    }

	@Test
    public void testComparableComparation() {
        Comparator<Integer> comparator = new NullSafeComparator<Integer>();
        Integer i1 = new Integer(1);
        Integer i2 = new Integer(2);
        Integer i2d = new Integer(2);
        assertEquals(-1, comparator.compare(i1, i2));
        assertEquals(-1, comparator.compare(null, i2));
        assertEquals(1, comparator.compare(i2, i1));
        assertEquals(1, comparator.compare(i2, null));
        assertEquals(0, comparator.compare(i2, i2));
        assertEquals(0, comparator.compare(i2, i2d));
        assertEquals(0, comparator.compare(null, null));
    }

	@Test
    public void testStaticComparableComparation() {
        Integer i1 = new Integer(1);
        Integer i2 = new Integer(2);
        Integer i2d = new Integer(2);
        assertEquals(-1, NullSafeComparator.compare(i1, i2, -1));
        assertEquals(1, NullSafeComparator.compare(i2, null, -1));
        assertEquals(-1, NullSafeComparator.compare(null, i2, -1));
        assertEquals(1, NullSafeComparator.compare(i2, i1, -1));
        assertEquals(0, NullSafeComparator.compare(i2, i2, -1));
        assertEquals(0, NullSafeComparator.compare(i2, i2d, -1));
        assertEquals(0, NullSafeComparator.compare((Integer) null, (Integer) null, Integer.valueOf(-1)));
    }

	@Test
    public void testDownwardComparation() {
        Comparator<Integer> comparator = new NullSafeComparator<Integer>(NullSafeComparator.NULL_IS_GREATER);
        Integer i1 = new Integer(1);
        Integer i2 = new Integer(2);
        Integer i2d = new Integer(2);
        assertEquals(-1, comparator.compare(i1, i2));
        assertEquals(1, comparator.compare(null, i2));
        assertEquals(1, comparator.compare(i2, i1));
        assertEquals(-1, comparator.compare(i2, null));
        assertEquals(0, comparator.compare(i2, i2));
        assertEquals(0, comparator.compare(i2, i2d));
        assertEquals(0, comparator.compare(null, null));
    }

	@Test
    public void testCollatorComparation() {
        Collator collator = Collator.getInstance(Locale.GERMANY);
        Comparator<String> comparator = new NullSafeComparator<String>(collator);
        String s1 = "Alpha";
        String s2 = "Beta";
        String s2d = "Beta";
        assertEquals(-1, comparator.compare(s1, s2));
        assertEquals(-1, comparator.compare(null, s1));
        assertEquals(1, comparator.compare(s2, s1));
        assertEquals(1, comparator.compare(s2, null));
        assertEquals(0, comparator.compare(s2, s2));
        assertEquals(0, comparator.compare(s2, s2d));
        assertEquals(0, comparator.compare(null, null));
        String s0 = "";
        assertEquals(-1, comparator.compare(null, s0));
    }

}
