/*
 * (c) Copyright 2008-2009 by Volker Bergmann. All rights reserved.
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

package org.databene.commons.comparator;

import org.junit.Test;
import static junit.framework.Assert.*;

/**
 * Tests the {@link ArrayComparator}.<br/><br/>
 * Created at 04.05.2008 09:04:36
 * @since 0.4.3
 * @author Volker Bergmann
 */
public class ArrayComparatorTest {
	
	private static final Integer[] EMPTY = new Integer[0];
	private static final Integer[] I123 = new Integer[] {1, 2, 3};
	private static final Integer[] I321 = new Integer[] {3, 2, 1};
	private static final Integer[] I12 = new Integer[] {1, 2};
	
	ArrayComparator<Integer> c = new ArrayComparator<Integer>();

	@Test
	public void testNull() {
		assertEquals( 0, c.compare(null, null));
		assertEquals(-1, c.compare(null, EMPTY));
		assertEquals( 1, c.compare(EMPTY, null));
	}
	
	@Test
	public void testEqual() {
		assertEquals(0, c.compare(EMPTY, EMPTY));
		assertEquals(0, c.compare(I123, I123));
	}
	
	@Test
	public void testEqualLength() {
		assertEquals(-1, c.compare(I123, I321));
		assertEquals( 1, c.compare(I321, I123));
	}
	
	@Test
	public void testDifferentLength() {
		assertEquals(-1, c.compare( I12, I123));
		assertEquals( 1, c.compare(I123,  I12));

		assertEquals(-1, c.compare( I12, I321));
		assertEquals( 1, c.compare(I321,  I12));
	}

}
