/*
 * (c) Copyright 2013 by Volker Bergmann. All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, is permitted under the terms of the
 * GNU General Public License (GPL).
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

import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.MessageFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.PropertyResourceBundle;
import java.util.ResourceBundle;

import org.databene.commons.converter.PercentageFormatter;

/**
 * Provides text formatting features for different data types.<br/><br/>
 * Created: 06.07.2013 10:21:19
 * @since 0.5.24
 * @author Volker Bergmann
 */

public class Formatter {
	
	public static String formatPercentage(double fraction) {
		return PercentageFormatter.format(fraction, 1, false);
	}
	
	public static String formatPercentalChange(double fraction) {
		return formatPercentalChange(fraction, 1);
	}

	public static String formatPercentalChange(double fraction, int fractionDigits) {
		return PercentageFormatter.format(fraction, fractionDigits, true);
	}

	public static String format(Calendar calendar) {
		return (calendar != null ? format(calendar.getTime()) : null);
	}

	public static String format(Date date) {
		return format(date, "yyyy-MM-dd");
	}

	public static String format(Date date, String pattern) {
		return (date != null ? new SimpleDateFormat(pattern).format(date) : "null");
	}
	
	public static String formatLocal(Date date) {
		return (date != null ? DateFormat.getDateInstance().format(date) : "null");
	}

	public static String format(double value) {
		return new DecimalFormat("#,##0.00", DecimalFormatSymbols.getInstance()).format(value);
	}

	public static String formatDaysFromNow(Date date) {
		int days = TimeUtil.daysBetween(TimeUtil.today(), date);
		switch (days) {
			case -2: return getBundle().getString("days_from_now.two_ago");
			case -1: return getBundle().getString("days_from_now.yesterday");
			case  0: return getBundle().getString("days_from_now.today");
			case  1: return getBundle().getString("days_from_now.tomorrow");
			case  2: return getBundle().getString("days_from_now.two_later");
			default: String key = (days < 0 ? "days_from_now.n_ago" : "days_from_now.n_later");
					 String format = getBundle().getString(key);
					 return MessageFormat.format(format, Math.abs(days));
		}
	}

	private static ResourceBundle getBundle() {
		return PropertyResourceBundle.getBundle("org/databene/commons/formatter", Locale.getDefault());
	}

}
