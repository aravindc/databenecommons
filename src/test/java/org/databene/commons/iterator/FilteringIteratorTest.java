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

package org.databene.commons.iterator;

import org.junit.Test;
import static junit.framework.Assert.*;

import java.util.List;
import java.util.Arrays;

import org.databene.commons.Filter;
import org.databene.commons.iterator.BidirectionalIterator;
import org.databene.commons.iterator.BidirectionalListIterator;
import org.databene.commons.iterator.FilteringIterator;

/**
 * Tests the {@link FilteringIterator}.<br/><br/>
 * Created: 08.05.2007 19:03:28
 * @author Volker Bergmann
 */
public class FilteringIteratorTest {

	@Test
    public void testNext() {
        List<Character> list = Arrays.asList('1', 'a', '2', 'b', '3');
        BidirectionalIterator<Character> realIterator
                = new BidirectionalListIterator<Character>(list);
        Filter<Character> filter = new Filter<Character>() {

            public boolean accept(Character c) {
                return Character.isDigit(c);
            }
        };
        BidirectionalIterator<Character> iterator = new FilteringIterator<Character>(realIterator, filter);
        assertTrue(iterator.hasNext());
        assertTrue(iterator.hasNext());
        assertEquals('1', iterator.next().charValue());
        assertTrue(iterator.hasNext());
        assertTrue(iterator.hasNext());
        assertEquals('2', iterator.next().charValue());
        assertTrue(iterator.hasNext());
        assertTrue(iterator.hasNext());
        assertEquals('3', iterator.next().charValue());
        assertFalse(iterator.hasNext());
        assertFalse(iterator.hasNext());
    }

}
