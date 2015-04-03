/*
 * Copyright (C) 2004-2014 Volker Bergmann (volker.bergmann@bergmann-it.de).
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
package org.databene.commons.bean;

import org.databene.commons.Accessor;
import org.databene.commons.ConversionException;
import org.databene.commons.accessor.FeatureAccessor;
import org.databene.commons.converter.ThreadSafeConverter;

/**
 * Maps a bean's feature (attributes, properties, Map contents) values to an array of values.
 * Created: 14.07.2014 15:24:31
 * @since 0.5.33
 * @author Volker Bergmann
 */

public class BeanToFeatureArrayConverter<E> extends ThreadSafeConverter<E, Object[]> {

    private Accessor<E, ?>[] accessors;

    public BeanToFeatureArrayConverter(String... featureNames) {
        this(null, featureNames);
    }

    @SuppressWarnings("unchecked")
    public BeanToFeatureArrayConverter(Class<E> beanClass, String... featureNames) {
    	super(beanClass, Object[].class);
        this.accessors = new Accessor[featureNames.length];
        for (int i = 0; i < featureNames.length; i++)
            this.accessors[i] = new FeatureAccessor<E, Object>(featureNames[i]);
    }

    @Override
	public Object[] convert(E bean) throws ConversionException {
        Object[] propertyValues = new Object[accessors.length];
        for (int i = 0; i < accessors.length; i++)
            propertyValues[i] = accessors[i].getValue(bean);
        return propertyValues;
    }
    
}
