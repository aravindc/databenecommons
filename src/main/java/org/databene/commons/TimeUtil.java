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

package org.databene.commons;

import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Calendar;
import java.util.TimeZone;
import java.util.concurrent.Callable;
import java.sql.Time;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.SimpleDateFormat;

/**
 * Provides utility methods for creating and manipulating Dates and Calendars.<br/>
 * <br/>
 * Created: 06.06.2004 18:16:26
 * @since 0.1
 * @author Volker Bergmann
 */
public final class TimeUtil {
	
	public static TimeZone GMT = TimeZone.getTimeZone("GMT");
	public static TimeZone CENTRAL_EUROPEAN_TIME = TimeZone.getTimeZone("CET");
	public static TimeZone PACIFIC_STANDARD_TIME = TimeZone.getTimeZone("PST");
	public static TimeZone SNGAPORE_TIME = TimeZone.getTimeZone("SGT");

	public static int currentYear() {
        return new GregorianCalendar().get(Calendar.YEAR);
    }

    public static boolean isWeekend(Calendar day) {
        int dayOfWeek = day.get(Calendar.DAY_OF_WEEK);
        return (dayOfWeek == Calendar.SATURDAY || dayOfWeek == Calendar.SUNDAY);
    }

    public static Date today() {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.MILLISECOND, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        return calendar.getTime();
    }

    public static Calendar todayCalendar() {
        Calendar today = Calendar.getInstance();
        today.set(Calendar.MILLISECOND, 0);
        today.set(Calendar.SECOND, 0);
        today.set(Calendar.MINUTE, 0);
        today.set(Calendar.HOUR_OF_DAY, 0);
        return today;
    }

    public static Date yesterday() {
        Calendar calendar = todayCalendar();
        calendar.add(Calendar.DAY_OF_MONTH, -1);
        return calendar.getTime();
    }

    public static Date tomorrow() {
        Calendar calendar = todayCalendar();
        calendar.add(Calendar.DAY_OF_MONTH, 1);
        return calendar.getTime();
    }

    public static Date date(int year, int month, int day) {
        return calendar(year, month, day).getTime();
    }

    public static Date gmtDate(int year, int month, int day) {
        return calendar(year, month, day, TimeZone.getTimeZone("GMT")).getTime();
    }

    public static Date date(int year, int month, int day, int hours, int minutes, int seconds, int milliseconds) {
        return calendar(year, month, day, hours, minutes, seconds, milliseconds).getTime();
    }

    public static Date date(long millis) {
        Date date = new Date();
        date.setTime(millis);
        return date;
    }

    public static Calendar calendar(int year, int month, int day) {
        return new GregorianCalendar(year, month, day);
    }

    public static Calendar calendar(int year, int month, int day, TimeZone timeZone) {
        GregorianCalendar calendar = new GregorianCalendar(timeZone);
        calendar.set(Calendar.YEAR, year);
        calendar.set(Calendar.MONTH, month);
        calendar.set(Calendar.DAY_OF_MONTH, day);
		return calendar;
    }

    public static Calendar calendar(int year, int month, int day,
                                    int hours, int minutes, int seconds, int milliseconds) {
        GregorianCalendar calendar = new GregorianCalendar(year, month, day, hours, minutes, seconds);
        calendar.set(Calendar.MILLISECOND, milliseconds);
        return calendar;
    }

    public static Calendar calendar(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        return calendar;
    }

    public static GregorianCalendar gregorianCalendar(Date date) {
        GregorianCalendar calendar = new GregorianCalendar();
        calendar.setTime(date);
        return calendar;
    }

    private static DateFormat df = DateFormat.getDateInstance();

    public static String formatDate(Date date) {
        return df.format(date);
    }

    public static Date max(Date date1, Date date2) {
        return (date1.before(date2) ? date2 : date1);
    }

    public static Date min(Date date1, Date date2) {
        return (date1.before(date2) ? date1 : date2);
    }

    public static boolean between(long test, long min, long max) {
        return (test >= min && test <= max);
    }

    public static boolean isNow(long time, long tolerance) {
    	long now = System.currentTimeMillis();
        return between(time, now - tolerance, now + tolerance);
    }

    public static String formatMillis(long t) {
        Date date = new Date(t);
        return formatDate(date);
    }

    private static DateFormat mdf = new SimpleDateFormat("MM/yy");

    public static String formatMonth(Calendar calendar) {
//        return formatDate(calendar.getTime());
        return mdf.format(calendar.getTime());
    }

    public static int year(Date date) {
        Calendar calendar = new GregorianCalendar();
        calendar.setTime(date);
        return calendar.get(Calendar.YEAR);
    }

    public static int month(Date date) {
        Calendar calendar = new GregorianCalendar();
        calendar.setTime(date);
        return calendar.get(Calendar.MONTH);
    }

    public static int dayOfMonth(Date date) {
        Calendar calendar = new GregorianCalendar();
        calendar.setTime(date);
        return calendar.get(Calendar.DAY_OF_MONTH) + 1;
    }

    public static Date lastDayOfMonth(Date date) {
    	Calendar cal = calendar(date);
    	cal.add(Calendar.MONTH, 1);
    	cal.set(Calendar.DAY_OF_MONTH, 1);
    	cal.add(Calendar.DATE, -1);
    	cal.set(Calendar.HOUR, 0);
    	cal.set(Calendar.MINUTE, 0);
    	cal.set(Calendar.SECOND, 0);
    	cal.set(Calendar.MILLISECOND, 0);
    	return cal.getTime();
    }
    
    public static Date firstDayOfMonth(Date date) {
    	Calendar cal = calendar(date);
    	cal.set(Calendar.DAY_OF_MONTH, 1);
    	cal.set(Calendar.HOUR, 0);
    	cal.set(Calendar.MINUTE, 0);
    	cal.set(Calendar.SECOND, 0);
    	cal.set(Calendar.MILLISECOND, 0);
    	return cal.getTime();
    }
    
    public static Date addDays(Date date, int delta) {
        Calendar calendar = calendar(date);
        calendar.add(Calendar.DAY_OF_YEAR, delta);
        return calendar.getTime();
    }
    
    public static Date addMonths(Date date, int delta) {
        Calendar calendar = calendar(date);
        calendar.add(Calendar.MONTH, delta);
        return calendar.getTime();
    }
    
    public static Date addYears(Date date, int delta) {
        Calendar calendar = calendar(date);
        calendar.add(Calendar.YEAR, delta);
        return calendar.getTime();
    }
    
    public static Date add(Date date, int field, int i) {
        Calendar calendar = calendar(date);
        calendar.add(field, i);
        return calendar.getTime();
    }
    
    public static Date add(Date date, Date offset) {
    	long millis = millisSinceOwnEpoch(offset);
    	if (millis < 0)
    		millis -= millisSinceOwnEpoch(TimeUtil.date(0, 0, 0));
		return new Date(date.getTime() + millis);
    }

	public static Object subtract(Date minuend, Date subtrahend) {
    	return new Date(minuend.getTime() - millisSinceOwnEpoch(subtrahend));
    }
    
    public static int yearsBetween(Date from, Date until) {
        Calendar fromCalendar = calendar(from);
        Calendar untilCalendar = calendar(until);
        int years = untilCalendar.get(Calendar.YEAR) - fromCalendar.get(Calendar.YEAR);
        int month1 = untilCalendar.get(Calendar.MONTH);
		int month2 = fromCalendar.get(Calendar.MONTH);
		if (month1 < month2) // DAY_OF_YEAR comparison would fail in leap years 
            years--;
        else if (month1 == month2 && untilCalendar.get(Calendar.DAY_OF_MONTH) < fromCalendar.get(Calendar.DAY_OF_MONTH))
            years--;
        return years;
    }

    public static long millis(int year, int month, int day, int hour, int minute, int second) {
    	GregorianCalendar calendar = new GregorianCalendar(year, month, day, hour, minute, second);
    	return calendar.getTimeInMillis();
    }
    
    public static Time time(int hour, int minute) {
        return time(hour, minute, 0, 0);
    }
    
    public static Time time(int hour, int minute, int second) {
        return time(hour, minute, second, 0);
    }
    
    public static Time time(int hour, int minute, int second, int millisecond) {
    	GregorianCalendar calendar = new GregorianCalendar(1970, 0, 1, hour, minute, second);
    	calendar.set(Calendar.MILLISECOND, millisecond);
        return new Time(calendar.getTimeInMillis());
    }
    
    public static Timestamp timestamp(int year, int month, int day, int hour, int minute, int second, int nanosecond) {
    	Timestamp result = new Timestamp(millis(year, month, day, hour, minute, second));
    	result.setNanos(nanosecond);
		return result;
    }

	public static DateFormat createDefaultDateFormat() {
		return new SimpleDateFormat(Patterns.DEFAULT_DATE_PATTERN);
	}

	public static boolean isMidnight(Date date) {
		// note that a calculation based on the raw millis value is not viable 
		// since time zones like Singapore had 7:30 hours delay in 1970-01-01
		// and 8:00 hours now!
        Calendar cal = new GregorianCalendar();
        cal.setTime(date);
        return (cal.get(Calendar.MILLISECOND) == 0 && cal.get(Calendar.SECOND) == 0 
        		&& cal.get(Calendar.MINUTE) == 0 && cal.get(Calendar.HOUR_OF_DAY) == 0);
	}

	public static Date midnightOf(Date date) {
        Calendar cal = calendar(date);
		cal.set(Calendar.HOUR_OF_DAY, 0);
		cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.SECOND, 0); 
        cal.set(Calendar.MILLISECOND, 0);
        return cal.getTime();
	}

    public static Time currentTime() {
	    Calendar now = new GregorianCalendar();
	    return time(now.get(Calendar.HOUR), now.get(Calendar.MINUTE), now.get(Calendar.SECOND), now.get(Calendar.MILLISECOND));
    }

    public static void runInTimeZone(TimeZone timeZone, Runnable action) {
    	TimeZone originalZone = TimeZone.getDefault();
    	try {
    		TimeZone.setDefault(timeZone);
    		action.run();
    	} finally {
    		TimeZone.setDefault(originalZone);
    	}
    }

    public static <T> T callInTimeZone(TimeZone timeZone, Callable<T> action) throws Exception {
    	TimeZone originalZone = TimeZone.getDefault();
    	try {
    		TimeZone.setDefault(timeZone);
    		return action.call();
    	} finally {
    		TimeZone.setDefault(originalZone);
    	}
    }

    public static long millisSinceOwnEpoch(Date date) {
    	return millisSinceOwnEpoch(date.getTime());
    }

    public static long millisSinceOwnEpoch(long millis) {
    	return millis + TimeZone.getDefault().getOffset(0);
    }

}
