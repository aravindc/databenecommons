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

package org.databene.commons.converter;

import java.util.Date;
import java.util.Locale;

import org.junit.Test;
import static junit.framework.Assert.*;

import org.databene.commons.TimeUtil;
import org.databene.commons.converter.String2DateConverter;

/**
 * Tests the {@link String2DateConverter}.<br/>
 * <br/>
 * Created: 07.09.2007 18:00:32
 * @author Volker Bergmann
 */
public class String2DateConverterTest extends AbstractConverterTest {

	public String2DateConverterTest() {
	    super(String2DateConverter.class);
    }

	@Test
    public void testExplicitConfig() {
		String2DateConverter<Date> usConverter = new String2DateConverter<Date>("dd-MMM-yyyy", Locale.US);
		assertEquals(TimeUtil.date(2010, 11, 31), usConverter.convert("31-DEC-2010"));
		String2DateConverter<Date> deConverter = new String2DateConverter<Date>("dd-MMM-yyyy", Locale.GERMANY);
		assertEquals(TimeUtil.date(2010, 11, 31), deConverter.convert("31-DEZ-2010"));
    }

	@Test
    public void testStandardDates() {
        String stringValue = "2007-09-06";
		assertEquals(TimeUtil.date(2007, 8, 6), convert(stringValue));
        assertEquals(TimeUtil.date(2007, 8, 6, 13, 28, 0, 0), convert("2007-09-06T13:28"));
        assertEquals(TimeUtil.date(2007, 8, 6, 13, 28, 56, 0), convert("2007-09-06T13:28:56"));
        assertEquals(TimeUtil.date(2007, 8, 6, 13, 28, 56, 123), convert("2007-09-06T13:28:56.123"));
    }

	@Test
    public void testStrangeDates() {
        assertEquals(null, convert(null));
        assertEquals(null, convert(""));
        assertEquals(TimeUtil.date(1234, 2, 5), convert("1234-3-5"));
        assertEquals(TimeUtil.date(12345, 11, 1), convert("12345-12-1"));
        assertEquals(TimeUtil.date(-10000, 3, 1), convert("-10000-4-1"));
    }

	private static Date convert(String stringValue) {
		return new String2DateConverter<Date>().convert(stringValue);
	}

}
