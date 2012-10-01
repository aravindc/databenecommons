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

import org.databene.commons.ArrayUtil;
import org.databene.commons.ConversionException;
import org.databene.commons.Converter;

/**
 * Converts arrays from one component type to arrays of another component type.
 * If there are no converters registered, it only transforms the array type.
 * If there is a single converter, all elements are converted with the same converter.
 * If there are several converters, the number of converters and array elements are 
 * assumed to be equal and each element is converted with the converter of the same index.
<br/>
 * <br/>
 * Created: 07.06.2007 14:35:18
 * @author Volker Bergmann
 */
public class ArrayConverter<S, T> extends MultiConverterWrapper<S, T> implements Converter<S[], T[]> {

    private Class<T> targetComponentType;
    private Class<S[]> sourceType;
    private Class<T[]> targetType;

    @SuppressWarnings("unchecked")
    public ArrayConverter(Class<S> sourceComponentType, Class<T> targetComponentType, Converter<S, T> ... converters) {
    	super(converters);
        this.targetComponentType = targetComponentType;
        this.sourceType = ArrayUtil.arrayType(sourceComponentType);
        this.targetType = ArrayUtil.arrayType(targetComponentType);
    }

	@Override
	public Class<S[]> getSourceType() {
	    return sourceType;
    }

	@Override
	public Class<T[]> getTargetType() {
	    return targetType;
    }
    
	/** 
	 * If there are no converters, it only transforms the array type; 
	 * if there is a single converter, all elements are converted with the same converter;
	 * if there are several converters, the number of converters and array elements are 
	 * assumed to be equal and each element is converted with the converter of the same index.
	 */
    @Override
	public T[] convert(S[] sourceValues) throws ConversionException {
        if (sourceValues == null)
            return null;
        if (components.length == 0)
        	return convertWith(null, targetComponentType, sourceValues);
        else if (components.length == 1)
        	return convertWith(components[0], targetComponentType, sourceValues);
        else {
        	if (sourceValues.length != components.length)
        		throw new IllegalArgumentException("Array has a different size than the converter list");
            T[] result = ArrayUtil.newInstance(targetComponentType, components.length);
            for (int i = 0; i < components.length; i++)
	            result[i] = components[i].convert(sourceValues[i]);
            return result;
        }
    }

    /** Converts all array elements with the same {@link Converter}. */
    public static <S, T> T[] convertWith(Converter<S, T> converter, Class<T> componentType, S[] sourceValues) throws ConversionException {
        T[] result = ArrayUtil.newInstance(componentType, sourceValues.length);
        for (int i = 0; i < sourceValues.length; i++) {
        	Object tmp = (converter != null ? converter.convert(sourceValues[i]) : sourceValues[i]);
            result[i] = AnyConverter.convert(tmp, componentType);
        }
        return result;
    }

}
