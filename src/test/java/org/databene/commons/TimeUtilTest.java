/*
 * (c) Copyright 2005-2013 by Volker Bergmann. All rights reserved.
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

import java.sql.Timestamp;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Locale;
import java.util.TimeZone;

/**
 * Tests the {@link TimeUtil} class.
 * Created: 17.02.2005 21:19:01
 * @since 0.1
 * @author Volker Bergmann
 */
public class TimeUtilTest {
	
	private static final Calendar NEW_YEARS_DAY_2013 = calendar(2013, Calendar.JANUARY, 1);
	private static final Calendar MEMORIAL_DAY_2013 = calendar(2013, Calendar.MAY, 27);
	private static final Calendar INDEPENDANCE_DAY_2013 = calendar(2013, Calendar.JULY, 4);
	
	
	
	// tests -----------------------------------------------------------------------------------------------------------
	
	@Test
	public void testIsBusinessDay() {
		assertFalse(TimeUtil.isBusinessDay(NEW_YEARS_DAY_2013, Locale.US));
		assertFalse(TimeUtil.isBusinessDay(NEW_YEARS_DAY_2013, Locale.GERMANY));
		assertFalse(TimeUtil.isBusinessDay(MEMORIAL_DAY_2013, Locale.US));
		assertTrue(TimeUtil.isBusinessDay(MEMORIAL_DAY_2013, Locale.GERMANY));
		assertFalse(TimeUtil.isBusinessDay(INDEPENDANCE_DAY_2013, Locale.US));
		assertTrue(TimeUtil.isBusinessDay(INDEPENDANCE_DAY_2013, Locale.GERMANY));
	}
	
	@Test
	public void testIsLastWeekdayOfTypeInMonth() {
		assertFalse(TimeUtil.isLastWeekdayOfTypeInMonth(Calendar.MONDAY, calendar(2013, 4, 19))); // Sunday
		assertFalse(TimeUtil.isLastWeekdayOfTypeInMonth(Calendar.MONDAY, calendar(2013, 4, 20))); // 2nd last Monday
		assertTrue(TimeUtil.isLastWeekdayOfTypeInMonth(Calendar.MONDAY, calendar(2013, 4, 27)));  // last Monday
	}
	
	@Test
	public void testMonthLength() {
		assertEquals(31, TimeUtil.monthLength(Calendar.JANUARY, 2013));
		assertEquals(28, TimeUtil.monthLength(Calendar.FEBRUARY, 2013));
		assertEquals(29, TimeUtil.monthLength(Calendar.FEBRUARY, 2012));
		assertEquals(31, TimeUtil.monthLength(Calendar.MARCH, 2013));
		assertEquals(30, TimeUtil.monthLength(Calendar.APRIL, 2013));
		assertEquals(31, TimeUtil.monthLength(Calendar.MAY, 2013));
		assertEquals(30, TimeUtil.monthLength(Calendar.JUNE, 2013));
		assertEquals(31, TimeUtil.monthLength(Calendar.JULY, 2013));
		assertEquals(31, TimeUtil.monthLength(Calendar.AUGUST, 2013));
		assertEquals(30, TimeUtil.monthLength(Calendar.SEPTEMBER, 2013));
		assertEquals(31, TimeUtil.monthLength(Calendar.OCTOBER, 2013));
		assertEquals(30, TimeUtil.monthLength(Calendar.NOVEMBER, 2013));
		assertEquals(31, TimeUtil.monthLength(Calendar.DECEMBER, 2013));
	}
	
	@Test
	public void testFirstDayOfMonth() {
		assertEquals(TimeUtil.date(1970, 0, 1), TimeUtil.firstDayOfMonth(TimeUtil.date(0)));
		assertEquals(TimeUtil.date(2008, 1, 1), TimeUtil.firstDayOfMonth(TimeUtil.date(2008, 1, 15)));
		assertEquals(TimeUtil.date(2009, 1, 1), TimeUtil.firstDayOfMonth(TimeUtil.date(2009, 1, 28)));
	}
	
	@Test
	public void testLastDayOfMonth() {
		assertEquals(TimeUtil.date(1970, 0, 31), TimeUtil.lastDayOfMonth(TimeUtil.date(0)));
		assertEquals(TimeUtil.date(2008, 1, 29), TimeUtil.lastDayOfMonth(TimeUtil.date(2008, 1, 15)));
		assertEquals(TimeUtil.date(2009, 1, 28), TimeUtil.lastDayOfMonth(TimeUtil.date(2009, 1, 28)));
	}
	
	@Test
	public void testAddDays() {
		// adding 0
		assertEquals(TimeUtil.date(1970, 0, 1), TimeUtil.addDays(TimeUtil.date(1970, 0, 1), 0));
		// simple case
		assertEquals(TimeUtil.date(1970, 0, 3), TimeUtil.addDays(TimeUtil.date(1970, 0, 1), 2));
		// over month's end 
		assertEquals(TimeUtil.date(2008, 3, 15), TimeUtil.addDays(TimeUtil.date(2008, 2, 15), 31));
		// over year's end
		assertEquals(TimeUtil.date(2010, 1, 28), TimeUtil.addDays(TimeUtil.date(2009, 1, 28), 365));
		// over year's end + leap year
		assertEquals(TimeUtil.date(2008, 2, 1), TimeUtil.addDays(TimeUtil.date(2007, 1, 28), 367));
	}
	
	@Test
	public void testAddMonths() {
		// adding 0
		assertEquals(TimeUtil.date(1970, 0,  1), TimeUtil.addMonths(TimeUtil.date(1970, 0,  1),  0));
		// simple addition
		assertEquals(TimeUtil.date(1970, 3,  1), TimeUtil.addMonths(TimeUtil.date(1970, 1,  1),  2));
		// 31. of the moth + n months -> month's end (31/30/29/28)
		assertEquals(TimeUtil.date(2008, 3, 30), TimeUtil.addMonths(TimeUtil.date(2008, 0, 31),  3));
		// over year's end
		assertEquals(TimeUtil.date(2011, 1, 28), TimeUtil.addMonths(TimeUtil.date(2009, 1, 28), 24));
		// over year's end + leap year
		assertEquals(TimeUtil.date(2009, 1, 28), TimeUtil.addMonths(TimeUtil.date(2008, 1, 29), 12));
	}
	
	@Test
	public void testAddYears() {
		// adding 0
		assertEquals(TimeUtil.date(1970, 0,  1), TimeUtil.addYears(TimeUtil.date(1970, 0,  1),  0));
		// simple addition
		assertEquals(TimeUtil.date(1972, 1,  1), TimeUtil.addYears(TimeUtil.date(1970, 1,  1),  2));
		// Add ing to Feb, 29 in leap year -> Feb. 28/29
		assertEquals(TimeUtil.date(2011, 1, 28), TimeUtil.addYears(TimeUtil.date(2008, 1, 29), 3));
	}
	
	@Test
    public void testMax() {
        Date now = new Date();
        Date later = new Date(now.getTime() + Period.DAY.getMillis());
        assertEquals(later, TimeUtil.max(now, later));
        assertEquals(later, TimeUtil.max(later, now));
    }

	@Test
    public void testMin() {
        Date now = new Date();
        Date later = new Date(now.getTime() + Period.DAY.getMillis());
        assertEquals(now, TimeUtil.min(now, later));
        assertEquals(now, TimeUtil.min(later, now));
    }
    
	@Test
    public void testIsMidnight() {
    	TimeZone zone = TimeZone.getDefault();
    	try {
    		TimeZone.setDefault(TimeZone.getTimeZone("GMT"));
    		assertTrue(TimeUtil.isMidnight(new Date(0)));
    	} finally {
    		TimeZone.setDefault(zone);
    	}
    }
    
	@Test
    public void testDate() {
    	Date date = TimeUtil.date(2009, 6, 19);
    	Calendar calendar = new GregorianCalendar(2009, 6, 19);
    	assertEquals(calendar.getTime(), date);
    }
    
	@Test
    public void testTimestamp() {
    	Timestamp timestamp = TimeUtil.timestamp(2009, 6, 19, 1, 34, 45, 123456789);
    	Calendar cal = new GregorianCalendar();
    	cal.setTime(new Date(timestamp.getTime()));
    	assertEquals(2009, cal.get(Calendar.YEAR));
    	assertEquals(6, cal.get(Calendar.MONTH));
    	assertEquals(19, cal.get(Calendar.DAY_OF_MONTH));
    	assertEquals(1, cal.get(Calendar.HOUR_OF_DAY));
    	assertEquals(34, cal.get(Calendar.MINUTE));
    	assertEquals(45, cal.get(Calendar.SECOND));
    	assertEquals(123, cal.get(Calendar.MILLISECOND));
    }
    
	@Test
	public void testYearsBetween() {
		Date base = TimeUtil.date(2008, 2, 24);
		Date threeLater = TimeUtil.date(2011, 2, 24);
		assertEquals(3, TimeUtil.yearsBetween(base, threeLater));
		Date lessThanThreeLater = TimeUtil.date(2011, 2, 23);
		assertEquals(2, TimeUtil.yearsBetween(base, lessThanThreeLater));
	}
	
	@Test
	public void testAdd_epoche() {
		assertEquals(TimeUtil.date(2010, 7, 31), TimeUtil.add(TimeUtil.date(2010, 7, 30), TimeUtil.date(1970, 0, 2)));
	}

	@Test
	public void testAdd_AC() {
		assertEquals(TimeUtil.date(2010, 7, 31), TimeUtil.add(TimeUtil.date(2010, 7, 30), TimeUtil.date(0, 0, 1)));
	}

	@Test
	public void testFormatDurationFixed() {
		// 1 ms
		assertEquals("0:00:00 h", TimeUtil.formatDuration(1, false, false));
		assertEquals("0:00:00.001 h", TimeUtil.formatDuration(1, false, true));
		// 1 s
		assertEquals("0:00:01 h", TimeUtil.formatDuration(1000, false, false));
		assertEquals("0:00:01.000 h", TimeUtil.formatDuration(1000, false, true));
		// 1 min
		assertEquals("0:01:00 h", TimeUtil.formatDuration(60000, false, false));
		assertEquals("0:01:00.000 h", TimeUtil.formatDuration(60000, false, true));
		// 1 h
		assertEquals("1:00:00 h", TimeUtil.formatDuration(3600000, false, false));
		assertEquals("1:00:00.000 h", TimeUtil.formatDuration(3600000, false, true));
		// 25 h
		assertEquals("25:00:00 h", TimeUtil.formatDuration(25*3600000, false, false));
		assertEquals("25:00:00.000 h", TimeUtil.formatDuration(25*3600000, false, true));
		
		assertEquals("0:00:00.009 h", TimeUtil.formatDuration(9, false, true));
		assertEquals("0:00:00.012 h", TimeUtil.formatDuration(12, false, true));
		assertEquals("0:00:00.123 h", TimeUtil.formatDuration(123, false, true));
		assertEquals("0:00:02.345 h", TimeUtil.formatDuration(2345, false, true));
		assertEquals("0:01:12.345 h", TimeUtil.formatDuration(72345, false, true));
		assertEquals("0:11:01.000 h", TimeUtil.formatDuration(661000, false, true));
		assertEquals("2:59:00.001 h", TimeUtil.formatDuration(10740001, false, true));
		assertEquals("27:59:59.999 h", TimeUtil.formatDuration(100799999, false, true));
	}
	
	@Test
	public void testFormatDurationSimplified() {
		// 1 ms
		assertEquals("0 s", TimeUtil.formatDuration(1, true, false));
		assertEquals("0.001 s", TimeUtil.formatDuration(1, true, true));
		// 1 s
		assertEquals("1 s", TimeUtil.formatDuration(1000, true, false));
		assertEquals("1.000 s", TimeUtil.formatDuration(1000, true, true));
		// 1 min
		assertEquals("1 min", TimeUtil.formatDuration(60999, true, false));
		assertEquals("1:01 min", TimeUtil.formatDuration(61000, true, false));
		assertEquals("1:59 min", TimeUtil.formatDuration(119000, true, false));
		assertEquals("1 min", TimeUtil.formatDuration(60000, true, true));
		// 1 h
		assertEquals("1 h", TimeUtil.formatDuration(3600000, true, false));
		assertEquals("1 h", TimeUtil.formatDuration(3600000, true, true));
		assertEquals("1:59 h", TimeUtil.formatDuration(7140000, true, true));
		// 25 h
		assertEquals("25 h", TimeUtil.formatDuration(90000000, true, false));
		assertEquals("25 h", TimeUtil.formatDuration(90000000, true, true));
		
		assertEquals("1:00.001 min", TimeUtil.formatDuration(60001, true, true));
		assertEquals("1:02:03 h", TimeUtil.formatDuration(3723000, true, false));
		assertEquals("1:02:03.009 h", TimeUtil.formatDuration(3723009, true, true));
	}
	
	@Test
	public void testParse() {
		assertEquals(TimeUtil.date(2012, 3, 26), TimeUtil.parse("2012-04-26"));
		assertEquals(TimeUtil.date(2012, 3, 26, 8, 30, 23, 0), TimeUtil.parse("2012-04-26 08:30:23"));
		assertEquals(TimeUtil.time(8, 30, 23, 0), TimeUtil.parse("08:30:23"));
		assertEquals(TimeUtil.timestamp(2012, 3, 26, 8, 30, 23, 123000000), TimeUtil.parse("2012-04-26 08:30:23.123"));
	}
	
	@Test
	public void testDaysBetween() {
		assertEquals(1, TimeUtil.daysBetween(TimeUtil.date(1975, 6, 31), TimeUtil.date(1975, 7, 1)));
	}
	
	// helpers ---------------------------------------------------------------------------------------------------------
	
	private static Calendar calendar(int year, int month, int day) {
		return TimeUtil.calendar(year, month, day);
	}
	
}
