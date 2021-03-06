/*
 * Copyright (C) 2004-2015 Volker Bergmann (volker.bergmann@bergmann-it.de).
 * All rights reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * http://www.apache.org/licenses/LICENSE-2.0
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.databene.commons.converter;

import org.databene.commons.ArrayUtil;
import org.databene.commons.ConversionException;

/**
 * Retrieves the value at a given array index from an array.
 * Created at 30.06.2009 18:02:10
 * @param <E> the component type of the arrays to process
 * @since 0.5.0
 * @author Volker Bergmann
 */

public class ArrayElementExtractor<E> extends ThreadSafeConverter<E[], E>{
	
	private int index;
	
    @SuppressWarnings("unchecked")
    public ArrayElementExtractor(Class<E> componentType, int index) {
    	super(ArrayUtil.arrayType(componentType), componentType);
	    this.index = index;
    }

    @Override
	public E convert(E[] sourceValue) throws ConversionException {
	    return sourceValue[index];
    }

}
