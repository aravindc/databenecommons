/*
 * (c) Copyright 2010-2012 by Volker Bergmann. All rights reserved.
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

package org.databene.commons.time;

import java.io.File;
import java.text.DecimalFormatSymbols;
import java.util.Locale;

import org.databene.commons.ConversionException;
import org.databene.commons.converter.PropertyResourceBundleConverter;
import org.databene.commons.converter.ThreadSafeConverter;

/**
 * Formats millisecond values in a rounded and for humans convenient form.<br/><br/>
 * Created: 14.12.2010 13:39:18
 * @since 0.5.5
 * @author Volker Bergmann
 */
public class ElapsedTimeFormatter extends ThreadSafeConverter<Long, String> {
	
	private static final long SECOND_MILLIS = 1000;
	private static final long MINUTE_MILLIS = 60 * SECOND_MILLIS;
	private static final long HOUR_MILLIS   = 60 * MINUTE_MILLIS;
	private static final long DAY_MILLIS    = 24 * HOUR_MILLIS;
	
	private static final String UNIT_BUNDLE_NAME = ElapsedTimeFormatter.class.getPackage().getName().replace(".", File.separator) + File.separator + "timeUnits";
	private final PropertyResourceBundleConverter unitConverter;
	
	private final char decimalSeparator;
	private final boolean localUnits;
	private final String space;
	
	private static final ElapsedTimeFormatter DEFAULT_INSTANCE = new ElapsedTimeFormatter();

	public ElapsedTimeFormatter() {
		this(Locale.getDefault(), " ", true);
	}
	
	public ElapsedTimeFormatter(Locale locale, String space, boolean localUnits) {
		super(Long.class, String.class);
		DecimalFormatSymbols symbols = DecimalFormatSymbols.getInstance(locale);
		this.decimalSeparator = symbols.getDecimalSeparator();
		this.space = space;
		this.localUnits = localUnits;
		this.unitConverter = new PropertyResourceBundleConverter(UNIT_BUNDLE_NAME, locale);
	}

	@Override
	public String convert(Long millis) throws ConversionException {
		if (millis < SECOND_MILLIS)
			return render(millis, 1, "ms");
		else if (millis < MINUTE_MILLIS)
			return render(millis, SECOND_MILLIS, "s");
		else if (millis < HOUR_MILLIS)
			return render(millis, MINUTE_MILLIS, "min");
		else if (millis < DAY_MILLIS)
			return render(millis, HOUR_MILLIS, "h");
		else
			return render(millis, DAY_MILLIS, "d");
	}
	
	public static String format(long millis) {
		return DEFAULT_INSTANCE.convert(millis);
	}
	
	// private helper --------------------------------------------------------------------------------------------------

	private String render(long millis, long base, String unitCode) {
		long prefix = millis / base;
		long postfix = (millis - prefix * base + base / 20) * 10 / base;
		if (postfix >= 10) {
			prefix++;
			postfix -= 10;
		}
		StringBuilder builder = new StringBuilder();
		builder.append(prefix);
		if (postfix != 0 && prefix / 10 == 0)
			builder.append(decimalSeparator).append(postfix);
		builder.append(space);
		String unit = (localUnits ? unitConverter.convert(unitCode) : unitCode);
		builder.append(unit);
		return builder.toString();
	}

}
