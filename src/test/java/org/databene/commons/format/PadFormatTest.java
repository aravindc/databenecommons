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

package org.databene.commons.format;

import org.junit.Test;
import static junit.framework.Assert.*;

import java.text.NumberFormat;
import java.text.ParseException;
import java.util.Locale;

import org.databene.commons.NumberUtil;
import org.databene.commons.format.Alignment;
import org.databene.commons.format.PadFormat;

/**
 * Tests the PadFormat.<br/><br/>
 * Created: 29.06.2007 19:44:11
 * @since 0.1
 * @author Volker Bergmann
 */
public class PadFormatTest {

	@Test
    public void testFormatLeftAligned() {
        PadFormat format = new PadFormat(3, Alignment.LEFT, '0');
        assertEquals("100", format.format("1"));
        assertEquals("000", format.format("0"));
        assertEquals("000", format.format(null));
    }

	@Test
    public void testFormatRightAligned() {
        PadFormat format = new PadFormat(3, Alignment.RIGHT, '0');
        assertEquals("001", format.format("1"));
        assertEquals("000", format.format("0"));
        assertEquals("000", format.format(null));
        assertEquals("-01", format.format("-1"));
    }

	@Test
    public void testFormatCentered() {
        PadFormat format = new PadFormat(3, Alignment.CENTER, '0');
        assertEquals("010", format.format("1"));
        assertEquals("000", format.format("0"));
        assertEquals("000", format.format(null));
    }

	@Test
    public void testFormatNumber() {
		
        PadFormat format = new PadFormat(NumberUtil.numberFormat(2, Locale.US), 5, Alignment.RIGHT, '0');
        assertEquals("00.01", format.format(0.01));
        assertEquals("00.10", format.format(0.1));
        assertEquals("01.00", format.format(1));
        assertEquals("01.00", format.format(1.));
        assertEquals("01.00", format.format(1));
        assertEquals("01.00", format.format(1L));
    }

	@Test
    public void testParseLeftAligned() throws ParseException {
        PadFormat format = new PadFormat(3, Alignment.LEFT, '0');
        assertEquals(null, format.parseObject(null));
        assertEquals("", format.parseObject(""));
        assertEquals("1", format.parseObject("1"));
        assertEquals("1", format.parseObject("100"));
        assertEquals("01", format.parseObject("0100"));
    }

	@Test
    public void testParseRightAligned() throws ParseException {
        PadFormat format = new PadFormat(3, Alignment.RIGHT, '0');
        assertEquals(null, format.parseObject(null));
        assertEquals("", format.parseObject(""));
        assertEquals("1", format.parseObject("1"));
        assertEquals("1", format.parseObject("001"));
        assertEquals("10", format.parseObject("0010"));
        assertEquals("-1", format.parseObject("-01"));
    }

    public void testParseCentered() throws ParseException {
        PadFormat format = new PadFormat(3, Alignment.CENTER, '0');
        assertEquals(null, format.parseObject(null));
        assertEquals("", format.parseObject(""));
        assertEquals("1", format.parseObject("1"));
        assertEquals("1", format.parseObject("00100"));
    }

	@Test
    public void testEquals() {
		NumberFormat intFormat = NumberUtil.numberFormat(0, Locale.US);
		NumberFormat decFormat = NumberUtil.numberFormat(1, Locale.US);
    	PadFormat p10ls = new PadFormat(intFormat, 1, Alignment.LEFT, ' ');
       	PadFormat p20ls = new PadFormat(intFormat, 2, Alignment.LEFT, ' ');
    	PadFormat p11ls = new PadFormat(decFormat, 1, Alignment.LEFT, ' ');
    	PadFormat p10rs = new PadFormat(intFormat, 1, Alignment.RIGHT, ' ');
        PadFormat p10l0 = new PadFormat(intFormat, 1, Alignment.LEFT, '0');
        assertFalse(p10ls.equals(null));
        assertFalse(p10ls.equals("Test"));
        assertTrue(p10ls.equals(p10ls));
        assertTrue(p10ls.equals(new PadFormat(intFormat, 1, Alignment.LEFT, ' ')));
        assertFalse(p10ls.equals(p20ls));
        assertFalse(p10ls.equals(p11ls));
        assertFalse(p10ls.equals(p10rs));
        assertFalse(p10ls.equals(p10l0));
    }
	
}
