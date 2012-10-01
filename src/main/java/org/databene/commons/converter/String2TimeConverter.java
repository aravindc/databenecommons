/*
 * (c) Copyright 2008-2012 by Volker Bergmann. All rights reserved.
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

import java.sql.Time;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.databene.commons.ConversionException;
import org.databene.commons.Patterns;
import org.databene.commons.StringUtil;

/**
 * Parses a String as a time value.<br/>
 * <br/>
 * Created: 14.03.2008 22:15:58
 * @author Volker Bergmann
 */
public class String2TimeConverter extends ThreadSafeConverter<String, Time> {

	private String pattern;
	
    public String2TimeConverter() {
        this(null);
    }

    public String2TimeConverter(String pattern) {
        super(String.class, Time.class);
        this.pattern = pattern;
    }

    @Override
	public Time convert(String sourceValue) throws ConversionException {
        return parse(sourceValue, pattern);
    }

    public static Time parse(String value) throws ConversionException {
        return parse(value, null);
    }

    public static Time parse(String value, String pattern) throws ConversionException {
        if (StringUtil.isEmpty(value))
            return null;
        pattern = choosePattern(value, pattern);
	    try {
	        Date simpleDate = new SimpleDateFormat(pattern).parse(value);
	        long millis = simpleDate.getTime();
	        return new Time(millis);
	    } catch (ParseException e) {
	        throw new ConversionException(e);
	    }
    }

	private static String choosePattern(String sourceValue, String pattern) {
	    if (pattern == null)
            switch (sourceValue.length()) {
                case 12 : pattern = Patterns.DEFAULT_TIME_MILLIS_PATTERN;  break;
                case  8 : pattern = Patterns.DEFAULT_TIME_SECONDS_PATTERN; break;
                case  5 : pattern = Patterns.DEFAULT_TIME_MINUTES_PATTERN; break;
                default : throw new IllegalArgumentException("Not a supported time format: " + sourceValue);
            }
	    return pattern;
    }

}
