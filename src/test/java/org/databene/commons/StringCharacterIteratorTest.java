/*
 * (c) Copyright 2007-2014 by Volker Bergmann. All rights reserved.
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

/**
 * Tests the {@link StringCharacterIterator}.
 * Created: 21.06.2007 08:34:31
 * @author Volker Bergmann
 */
public class StringCharacterIteratorTest {

	@Test
    public void testIteration() {
    	StringCharacterIterator iterator = new StringCharacterIterator("abc");
        assertTrue(iterator.hasNext());
        assertEquals('a', iterator.next());
        assertTrue(iterator.hasNext());
        assertEquals('b', iterator.next());
        assertTrue(iterator.hasNext());
        assertEquals('c', iterator.next());
        assertFalse(iterator.hasNext());
    }

	@Test
    public void testLifeCycle() {
    	StringCharacterIterator iterator = new StringCharacterIterator("ab");
        assertTrue(iterator.hasNext());
        assertEquals('a', iterator.next());
        iterator.assertNext('b');
        assertFalse(iterator.hasNext());
        iterator.pushBack();
        assertTrue(iterator.hasNext());
        iterator.assertNext('b');
        assertFalse(iterator.hasNext());
    }

	@Test
    public void testLineColumn() {
    	StringCharacterIterator iterator = new StringCharacterIterator("a\nb");
    	assertPosition(1, 1, iterator);
    	iterator.next();
    	assertPosition(1, 2, iterator);
    	iterator.next();
    	assertPosition(2, 1, iterator);
    	iterator.pushBack();
    	assertPosition(1, 2, iterator);
    	iterator.pushBack();
    	assertPosition(1, 1, iterator);
    }

	private static void assertPosition(int expectedLine, int expectedColumn, StringCharacterIterator iterator) {
		assertEquals("Unexpected line,", expectedLine, iterator.line());
		assertEquals("Unexpected column,", expectedColumn, iterator.column());
	}
}
