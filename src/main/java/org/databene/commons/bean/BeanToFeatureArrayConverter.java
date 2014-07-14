/*
 * (c) Copyright 2014 by Volker Bergmann. All rights reserved.
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

package org.databene.commons.bean;

import org.databene.commons.Accessor;
import org.databene.commons.ConversionException;
import org.databene.commons.accessor.FeatureAccessor;
import org.databene.commons.converter.ThreadSafeConverter;

/**
 * Maps a bean's feature (attributes, properties, Map contents) values to an array of values.<br/><br/>
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
