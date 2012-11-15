/*
 * (c) Copyright 2007-2012 by Volker Bergmann. All rights reserved.
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

import java.math.BigDecimal;
import java.sql.Time;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.databene.commons.Capitalization;
import org.databene.commons.ConversionException;
import org.databene.commons.Converter;

/**
 * Converts an object to a String by using its toString() method.
 * Null values can be mapped to an individual String.<br/>
 * <br/>
 * Created: 31.08.2006 18:44:59
 * @since 0.1
 * @author Volker Bergmann
 */
public class ToStringConverter extends FormatHolder implements Converter<Object, String>, Cloneable {
	
	private static ToStringConverter singletonInstance = new ToStringConverter();

    // constructors ----------------------------------------------------------------------------------------------------

    /** Default constructor that uses an isEmpty String as null representation */
    public ToStringConverter() {
        this(DEFAULT_NULL_STRING);
    }

    /**
     * Constructor that initializes the null replacement to the specified parameter.
     * @param nullString the String to use for replacing null values.
     */
    public ToStringConverter(String nullString) {
        this(nullString, DEFAULT_DATE_PATTERN, DEFAULT_DATETIME_SECONDS_PATTERN + '.');
    }

    public ToStringConverter(String nullString, String datePattern, String timestampPattern) {
    	super(nullString, datePattern, timestampPattern);
    }
    
    // Converter interface implementation ------------------------------------------------------------------------------

    public boolean canConvert(Object sourceValue) {
	    return true;
    }

    @Override
	public Class<Object> getSourceType() {
        return Object.class;
    }
    
    @Override
	public Class<String> getTargetType() {
	    return String.class;
    }

	@Override
	@SuppressWarnings({ "unchecked", "rawtypes" })
    public String convert(Object source) throws ConversionException {
        if (source == null)
            return nullString;
        else if (source instanceof String) {
        	if (stringQuote == null)
        		return (String) source;
        	else
        		return stringQuote + source + stringQuote;
        } else if (source instanceof Character) {
        	if (charQuote == null)
        		return String.valueOf(source);
        	else
        		return charQuote + source + charQuote;
        }
        
        Class<?> sourceType = source.getClass();
        if (JavaType.isIntegralType(sourceType)) {
        	if (integralConverter != null)
        		return integralConverter.convert((Number) source);
        	else
        		return String.valueOf(source);
        } else if (JavaType.isDecimalType(sourceType)) {
        	if (decimalConverter != null)
        		return decimalConverter.convert((Number) source);
        	else if (sourceType == BigDecimal.class) {
        		return String.valueOf(source);
        	} else {
        		Double value = ((Number) source).doubleValue();
        		if (value == Math.floor(value))
        			return String.valueOf(value.longValue());
        		else
        			return String.valueOf(value);
        	}
        } else if (source instanceof Timestamp) {
        	String result;
        	if (timestampPattern != null)
        		result = new TimestampFormatter(timestampPattern).format((Timestamp) source);
        	else
        		result = new TimestampFormatter().format((Timestamp) source);
        	return applyCapitalization(timestampCapitalization, result);
        } else if (source instanceof Time) {
        	if (timePattern != null)
        		return new SimpleDateFormat(timePattern).format((Date) source);
        	else
        		return new SimpleDateFormat().format((Date) source);
        } else if (source instanceof Date) {
        	String result;
        	if (datePattern != null)
        		result = new SimpleDateFormat(datePattern).format((Date) source);
        	else
        		result = new SimpleDateFormat().format((Date) source);
        	return applyCapitalization(dateCapitalization, result);
        } else {
	        ConverterManager manager = ConverterManager.getInstance();
			Converter converter = manager.createConverter(sourceType, String.class);
	        return (String) converter.convert(source);
        }
    }

	private String applyCapitalization(Capitalization capitalization, String text) {
		if (text == null)
			return null;
		switch (capitalization) {
			case upper: return text.toUpperCase();
			case lower: return text.toLowerCase();
			default:    return text;
		}
	}

	@Override
	public boolean isThreadSafe() {
	    return true;
    }
	
	@Override
	public boolean isParallelizable() {
	    return true;
    }

	// utility methods -------------------------------------------------------------------------------------------------
	
    public static <TT> String convert(TT source, String nullString) {
    	if (source == null)
    		return nullString;
    	return singletonInstance.convert(source);
    }

}
