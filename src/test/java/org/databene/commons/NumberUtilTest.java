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

import java.math.BigDecimal;
import java.math.BigInteger;

import org.junit.Test;
import static junit.framework.Assert.*;
import static org.databene.commons.NumberUtil.*;

/**
 * Created: 21.06.2007 08:32:34
 * @author Volker Bergmann
 */
public class NumberUtilTest {

	@Test
    public void test() {
        assertEquals("1.23", NumberUtil.format(1.23, 2));
        assertEquals("1", NumberUtil.format(1.23, 0));
    }

	@Test
    public void testBitsUsed() {
        assertEquals(1, bitsUsed(0));
        assertEquals(1, bitsUsed(1));
        assertEquals(2, bitsUsed(2));
        assertEquals(8, bitsUsed(0xff));
        assertEquals(9, bitsUsed(0x100));
        assertEquals(16, bitsUsed(0xffff));
        assertEquals(17, bitsUsed(0x10000));
        assertEquals(32, bitsUsed(0xffffffffL));
        assertEquals(63, bitsUsed(0x7fffffffffffffffL));
        assertEquals(64, bitsUsed(0xffffffffffffffffL));
        assertEquals(64, bitsUsed(-1L));
    }

	@Test
    public void testFormatHex() {
        assertEquals("0001", NumberUtil.formatHex( 1, 4));
        assertEquals("ffff", NumberUtil.formatHex(-1, 4));
    }
    
	@Test
	public void testTotalDigits() {
		assertEquals(3, NumberUtil.totalDigits(byte.class));
		assertEquals(3, NumberUtil.totalDigits(Byte.class));
		assertEquals(5, NumberUtil.totalDigits(short.class));
		assertEquals(5, NumberUtil.totalDigits(Short.class));
		assertEquals(10, NumberUtil.totalDigits(int.class));
		assertEquals(10, NumberUtil.totalDigits(Integer.class));
		assertEquals(19, NumberUtil.totalDigits(long.class));
		assertEquals(19, NumberUtil.totalDigits(Long.class));
		assertEquals(39, NumberUtil.totalDigits(float.class));
		assertEquals(39, NumberUtil.totalDigits(Float.class));
		assertEquals(309, NumberUtil.totalDigits(double.class));
		assertEquals(309, NumberUtil.totalDigits(Double.class));
		assertEquals(309, NumberUtil.totalDigits(BigInteger.class));
		assertEquals(309, NumberUtil.totalDigits(BigDecimal.class));
	}
	
}
