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

import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.databene.commons.ConversionException;
import org.databene.commons.TimeUtil;
import org.databene.commons.converter.ParseFormatConverter;

/**
 * Tests the {@link ParseFormatConverter}.<br/><br/>
 * Created: 29.09.2006 15:55:35
 * @since 0.1
 * @author Volker Bergmann
 */
public class ParseFormatConverterTest extends AbstractConverterTest {

	public ParseFormatConverterTest() {
	    super(ParseFormatConverter.class);
    }

	@Test
    public void testIntegerConversion() throws ConversionException {
        ParseFormatConverter<Long> converter = new ParseFormatConverter<Long>(Long.class, NumberFormat.getInstance(), false);
        assertNull(converter.convert(null));
        assertEquals( 1L, (long)converter.convert( "1"));
        assertEquals( 0L, (long)converter.convert( "0"));
        assertEquals(-1L, (long)converter.convert("-1"));
    }

	@Test
    public void testDateConversion() throws ConversionException {
        ParseFormatConverter<Date> converter = new ParseFormatConverter<Date>(Date.class, new SimpleDateFormat("yyyy-MM-dd"), false);
        assertNull(converter.convert(null));
        assertEquals(TimeUtil.date(1969,  5, 24), converter.convert("1969-06-24"));
        assertEquals(TimeUtil.date(1970,  0,  1), converter.convert("1970-01-01"));
        assertEquals(TimeUtil.date(2000, 11, 31), converter.convert("2000-12-31"));
        assertEquals(TimeUtil.date(2006,  8, 29), converter.convert("2006-09-29"));
    }
	
}
