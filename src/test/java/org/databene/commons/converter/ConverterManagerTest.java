/*
 * (c) Copyright 2007-2010 by Volker Bergmann. All rights reserved.
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
import static org.junit.Assert.assertEquals;

import java.io.File;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.net.URI;
import java.net.URL;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Locale;
import java.util.TimeZone;
import java.util.regex.Pattern;

import org.databene.SomeEnum;
import org.databene.commons.ArrayUtil;
import org.databene.commons.ConversionException;
import org.databene.commons.Converter;
import org.databene.commons.TimeUtil;
import org.databene.commons.converter.String2LocaleConverter;

/**
 * Tests the ConverterManager.<br/>
 * <br/>
 * Created: 05.08.2007 07:07:26
 * @author Volker Bergmann
 */
public class ConverterManagerTest {

    ConverterManager mgr = ConverterManager.getInstance();

    // Boolean to x tests ----------------------------------------------------------------------------------------------
    
	@Test
	public void testBooleanToBoolean() {
		Converter<Boolean, Boolean> converter = mgr.createConverter(Boolean.class, Boolean.class);
		assertEquals(true, converter.convert(true).booleanValue());
		assertEquals(false, converter.convert(false).booleanValue());
	}

	@Test
	public void testBooleanToLong() {
		Converter<Boolean, Long> converter = mgr.createConverter(Boolean.class, Long.class);
		assertEquals(1, converter.convert(true).intValue());
		assertEquals(0, converter.convert(false).intValue());
	}

	@Test
	public void testBooleanToString() {
		Converter<Boolean, String> converter = mgr.createConverter(Boolean.class, String.class);
		assertEquals("true",  converter.convert(true));
		assertEquals("false", converter.convert(false));
	}

	// x to date tests -----------------------------------------------------------------------------------------------
	
	@Test
	public void testLongToDate() {
		Converter<Long, Date> converter = mgr.createConverter(Long.class, Date.class);
		Date actual = converter.convert(0L);
		assertEquals(new Date(0 - TimeZone.getDefault().getRawOffset()),  actual);
	}

	@Test
	public void testIntToDate() {
		Converter<Integer, Date> converter = mgr.createConverter(Integer.class, Date.class);
		Date actual = converter.convert(0);
		assertEquals(new Date(0 - TimeZone.getDefault().getRawOffset()),  actual);
	}

	// String to x tests -----------------------------------------------------------------------------------------------
	
    @SuppressWarnings("rawtypes")
	@Test
    public void test() {
        Converter converter = mgr.createConverter(String.class, Locale.class);
		assertEquals(String2LocaleConverter.class, converter.getClass());
    }
	
	@Test
	public void testConvertStringToString() {
        check("ABC", "ABC", String.class);
	}
	
	@Test
	public void testConvertEmptyStringToNumber() {
        check(null, "", Integer.class);
        check(null, "", Byte.class);
        check(null, "", BigDecimal.class);
	}
	
	@Test
	public void testConvertEmptyStringToPrimitive() {
        check(null, "", int.class);
        check(null, "", byte.class);
        check(null, "", char.class);
        check(null, "", boolean.class);
	}
	
	@Test
	public void testConvertValidStringToCharacter() {
        check('A', "A", Character.class);
        check(null, "", Character.class);
	}
	
	@Test(expected = ConversionException.class)
	public void testConvertInvalidStringToCharacter() {
		check(null, "ABC", Character.class);
	}
	
	@Test
    public void testString2Enum() {
        check(SomeEnum.ONE, "ONE", SomeEnum.class);
    }

	@Test
    public void testString2Number() {
        check((byte) 1, "1", byte.class);
        check(Byte.valueOf((byte) 1), "1", Byte.class);
        
        check((short) 1, "1", short.class);
        check(Short.valueOf((short) 1), "1", Short.class);
        
        check(1, "1", int.class);
        check(Integer.valueOf(1), "1", Integer.class);
        
        check(1L, "1", long.class);
        check(Long.valueOf((byte) 1), "1", Long.class);
        
        check((float) 1, "1", float.class);
        check(Float.valueOf((byte) 1), "1", Float.class);
        
        check(1., "1", double.class);
        check(Double.valueOf((byte) 1), "1", Double.class);
        
        check(new BigInteger("1"), "1", BigInteger.class);
        check(new BigDecimal("1"), "1", BigDecimal.class);
        
        check(1., "1", Number.class);
    }
    
	@Test
    public void testString2Date() {
		Converter<String, Date> converter = mgr.createConverter(String.class, Date.class);
        long expectedMillis = new GregorianCalendar(1970, 0, 3).getTimeInMillis();
		assertEquals(expectedMillis, converter.convert("1970-01-03").getTime());
    }

	@Test
    public void testString2GregorianCalendar() {
        check(new GregorianCalendar(1970, 0, 1), "1970-01-01", GregorianCalendar.class);
        check(new GregorianCalendar(2001, 1, 2, 3, 4, 5), "2001-02-02T03:04:05", GregorianCalendar.class);
    }
    
	@Test
    public void testString2Calendar() {
        check(new GregorianCalendar(1970, 0, 1), "1970-01-01", Calendar.class);
    }
    
	@Test
    public void testString2StringArray() {
		Converter<String, String[]> converter = mgr.createConverter(String.class, String[].class);
        assertTrue(Arrays.equals(ArrayUtil.toArray("A", "B", "C"), converter.convert("A,B,C")));
    }
    
	@Test
    public void testString2CharArray() {
		Converter<String, char[]> converter = mgr.createConverter(String.class, char[].class);
        assertTrue(Arrays.equals(ArrayUtil.toCharArray('A', 'B', 'C'), converter.convert("A,B,C")));
    }

	@Test
    public void testString2IntArray() {
		Converter<String, int[]> converter = mgr.createConverter(String.class, int[].class);
        assertTrue(Arrays.equals(ArrayUtil.toIntArray(1, 2, 3), converter.convert("1,2,3")));
    }
    
	@Test
    public void testString2IntegerArray() {
		Converter<String, Integer[]> converter = mgr.createConverter(String.class, Integer[].class);
        assertTrue(Arrays.equals(ArrayUtil.toArray(1, 2, 3), converter.convert("1,2,3")));
    }
    
	@Test
    public void testString2PrimitiveNumber() {
        check(1, "1", int.class);
        check((byte)1, "1", byte.class);
    }
    
	@Test
    public void testString2Locale() {
        check(Locale.GERMAN, "de", Locale.class);
    }
    
	@Test
    public void testString2Class() {
		check(URL.class, "java.net.URL", Class.class);
    }
    
	@Test(expected = ConversionException.class)
    public void testString2StringConverterTest() {
		check(null, "Bla", ConverterManagerTest.class);
    }
    
	@Test
    public void testString2File() {
    	check(new File("license.txt"), "license.txt", File.class);
    }
    
	@Test
    public void testString2URL() throws Exception {
    	check(new URL("http://databene.org/databene.benerator:80"), 
    			"http://databene.org/databene.benerator:80", URL.class);
    }
    
	@Test
    public void testString2URI() throws Exception {
    	check(new URI("http://databene.org/databene.benerator:80"), 
    			"http://databene.org/databene.benerator:80", URI.class);
    }
    
	@Test
    public void testString2SimpleDateFormat() {
    	check(new SimpleDateFormat("dd.MM.yy"), "dd.MM.yy", SimpleDateFormat.class);
    }
    
	@Test
    public void testString2DateFormat() {
    	check(new SimpleDateFormat("dd.MM.yy"), "dd.MM.yy", DateFormat.class);
    }
    
	@Test
    public void testString2DecimalFormat() {
    	check(new DecimalFormat("00.00"), "00.00", DecimalFormat.class);
    }
    
	@Test
    public void testString2NumberFormat() {
    	check(new DecimalFormat("00.00"), "00.00", NumberFormat.class);
    }
    
	@Test
    public void testString2SqlDate() {
		Converter<String, java.sql.Date> converter = mgr.createConverter(String.class, java.sql.Date.class);
        assertEquals(
        		new GregorianCalendar(1970, 0, 1).getTimeInMillis(), 
        		converter.convert("1970-01-01").getTime());
    }

	@Test
    public void testString2SqlTime() {
        check(TimeUtil.time(12, 34, 56, 789), "12:34:56.789", java.sql.Time.class);
    }
    
	@Test
    public void testString2SqlTimestamp() {
        check(
        	TimeUtil.timestamp(2008, 1, 1, 12, 34, 56, 789000000), 
        	"2008-02-01T12:34:56.789", java.sql.Timestamp.class);
    }

	@Test
    public void testString2Pattern() {
		Converter<String, Pattern> converter = mgr.createConverter(String.class, Pattern.class);
    	// the Pattern's equal() method does not seem to be implemented correctly, 
    	// so we only compare the String representations
        assertEquals(
        		Pattern.compile("[1-3]{2,4}").toString(), 
        		converter.convert("[1-3]{2,4}").toString());
    }
	
	// private helpers -------------------------------------------------------------------------------------------------
	
	@SuppressWarnings("unchecked")
    private <S, T> void check(T expected, S source, Class<T> targetType) {
		Converter<S, T> converter = mgr.createConverter((Class<S>) source.getClass(), targetType);
        assertEquals(expected, converter.convert(source));
    }

}
