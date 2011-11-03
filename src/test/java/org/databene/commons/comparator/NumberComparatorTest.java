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

import java.math.BigDecimal;
import java.math.BigInteger;

import org.junit.Test;
import static junit.framework.Assert.*;

/**
 * Tests the {@link NumberComparator}.<br/><br/>
 * Created at 29.04.2008 18:30:06
 * @since 0.4.2
 * @author Volker Bergmann
 */
public class NumberComparatorTest {
	
	NumberComparator<Number> comparator = new NumberComparator<Number>();

	@Test
	public void testNull() {
		expectExceptionFor(null, 0);
		expectExceptionFor(0, null);
		expectExceptionFor(null, null);
	}
	
	@Test
	public void testByte() {
		expectEquality((byte)1, (byte)1);
		expectLess((byte)1, (byte)2);
		expectGreater((byte)2, (byte)1);
	}

	@Test
	public void testShort() {
		expectEquality((short)1, (short)1);
		expectLess((short)1, (short)2);
		expectGreater((short)2, (short)1);
	}

	@Test
	public void testInteger() {
		expectEquality(1, 1);
		expectLess(1, 2);
		expectGreater(2, 1);
	}

	@Test
	public void testLong() {
		expectEquality(1L, 1L);
		expectLess(1L, 2L);
		expectGreater(2L, 1L);
	}
	
	@Test
	public void testFloat() {
		expectEquality((float)1, (float)1);
		expectLess((float)1, (float)2);
		expectGreater((float)2, (float)1);
	}
	
	@Test
	public void testDouble() {
		expectEquality(1., 1.);
		expectLess(1., 2.);
		expectGreater(2., 1.);
	}
	
	@Test
	public void testBigInteger() {
		expectEquality(new BigInteger("1"), new BigInteger("1"));
		expectLess(new BigInteger("1"), new BigInteger("2"));
		expectGreater(new BigInteger("2"), new BigInteger("1"));
	}
	
	@Test
	public void testBigDecimal() {
		BigDecimal one = new BigDecimal(1);
		BigDecimal two = new BigDecimal(2);
		expectEquality(one, one);
		expectLess(one, two);
		expectGreater(two, one);
	}
	
	@Test
	public void testMixed() {
		expectEquality(1, 1.);
		expectLess(1., 2);
		expectGreater(2, 1.);
	}
	
	// private helpers -------------------------------------------------------------------------------------------------

	private void expectEquality(Number n1, Number n2) {
		assertEquals(0, comparator.compare(n1, n2));
	}

	private void expectLess(Number n1, Number n2) {
		assertEquals(-1, comparator.compare(n1, n2));
	}

	private void expectGreater(Number n1, Number n2) {
		assertEquals(1, comparator.compare(n1, n2));
	}

	private void expectExceptionFor(Number n1, Number n2) {
		try {
			comparator.compare(n1, n2);
			fail("IllegalArgumentException expected");
		} catch (IllegalArgumentException e) {
			// this is the expected behavior
		}
	}
}
