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

import org.databene.commons.Converter;

import java.math.BigInteger;
import java.math.BigDecimal;

/**
 * Converts Number objects of one type to another Number type.<br/>
 * <br/>
 * Created: 16.06.2007 11:51:14
 * @author Volker Bergmann
 */
public class NumberToNumberConverter<S extends Number, T extends Number> extends ConverterProxy<S, T> {

	@SuppressWarnings("unchecked")
	public NumberToNumberConverter(Class<T> targetType) {
        this((Class<S>) Number.class, targetType);
    }
    
    @SuppressWarnings("unchecked")
	public NumberToNumberConverter(Class<S> sourceType, Class<T> targetType) {
        super((Converter<S, T>) createConverter(targetType));
    }
    
	private static <TT extends Number> Converter<Number, ? extends Number> createConverter(Class<TT> targetType) {
		if (Integer.class == targetType || int.class == targetType)
			return new Number2IntegerConverter();
		else if (Long.class == targetType || long.class == targetType)
			return new Number2LongConverter();
		else if (Byte.class == targetType || byte.class == targetType)
			return new Number2ByteConverter();
		else if (Short.class == targetType || short.class == targetType)
			return new Number2ShortConverter();
		else if (Double.class == targetType || double.class == targetType)
			return new Number2DoubleConverter();
		else if (Float.class.equals(targetType) || float.class == targetType)
			return new Number2FloatConverter();
		else if (BigInteger.class.equals(targetType))
			return new Number2BigIntegerConverter();
		else if (BigDecimal.class.equals(targetType))
			return new Number2BigDecimalConverter();
		else 
		    throw new IllegalArgumentException("Not a supported number type: " + targetType);
	}

	/**
     * Converts a number of one number type to another number type.
     * @param src the number to convert
     * @param targetType the target number type of the conversion
     * @return an object of the target number type
     */
    @SuppressWarnings("unchecked")
    public static <TT extends Number> TT convert(Number src, Class<TT> targetType) {
    	if (src == null)
    		return null;
    	else if (Integer.class == targetType || int.class == targetType)
            return (TT) Integer.valueOf(src.intValue());
    	else if (Long.class == targetType || long.class == targetType)
            return (TT) Long.valueOf(src.longValue());
    	else if (Byte.class == targetType || byte.class == targetType)
            return (TT) Byte.valueOf(src.byteValue());
    	else if (Short.class == targetType || short.class == targetType)
            return (TT) Short.valueOf(src.byteValue());
    	else if (Double.class == targetType || double.class == targetType)
            return (TT) Double.valueOf(src.doubleValue());
    	else if (Float.class.equals(targetType) || float.class == targetType)
            return (TT) Float.valueOf(src.floatValue());
    	else if (BigInteger.class.equals(targetType))
            return (TT) BigInteger.valueOf(src.longValue());
        else if (BigDecimal.class.equals(targetType))
            return (TT) BigDecimal.valueOf(src.doubleValue());
		else 
		    throw new IllegalArgumentException("Not a supported number type: " + targetType);
    }

}
