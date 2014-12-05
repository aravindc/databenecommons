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

package org.databene.commons.converter;

import org.databene.commons.Assert;
import org.databene.commons.BeanUtil;
import org.databene.commons.ConversionException;
import org.databene.commons.Mutator;
import org.databene.commons.StringUtil;
import org.databene.commons.accessor.FeatureAccessor;
import org.databene.commons.converter.util.ClassProvider;
import org.databene.commons.converter.util.ReferenceResolver;
import org.databene.commons.mutator.AnyMutator;
import org.databene.commons.mutator.EmptyMutator;

/**
 * Converts an array of property values to a JavaBean instance.<br/><br/>
 * Created: 19.09.2014 10:24:05
 * @since 1.0.0
 * @author Volker Bergmann
 */

public class PropertyArray2JavaBeanConverter extends UnsafeConverter<Object[], Object> {

	private ClassProvider<Object> beanClassProvider;
	private String[] attributePaths;
	private Mutator[] mutators;
	private ReferenceResolver referenceResolver;
	
	public PropertyArray2JavaBeanConverter(ClassProvider<Object> beanClassProvider, String[] attributePaths, ReferenceResolver referenceResolver) {
		super(Object[].class, Object.class);
		this.beanClassProvider = Assert.notNull(beanClassProvider, "beanClassProvider");
		this.attributePaths = Assert.notNull(attributePaths, "attributePaths");
		this.referenceResolver = Assert.notNull(referenceResolver, "referenceResolver");
		createMutators(attributePaths);
	}

	@Override
	public Object convert(Object[] propertyArray) throws ConversionException {
		if (propertyArray == null)
			return null;
		Class<?> beanClass = beanClassProvider.classFor(propertyArray);
		Object bean = BeanUtil.newInstance(beanClass);
		for (int i = 0; i < propertyArray.length; i++) {
			String attributePath = attributePaths[i];
			String[] pathParts = StringUtil.splitOnLastSeparator(attributePath, '.');
			Object target = haveTargetObject(bean, pathParts[0]);
			mutators[i].setValue(bean, referenceResolver.resolveReferences(propertyArray[i], target, pathParts[1]));
		}
		return bean;
	}


	// private helpers -------------------------------------------------------------------------------------------------

	private void createMutators(String[] attributePaths) {
		this.mutators = new Mutator[attributePaths.length];
        for (int i = 0; i < attributePaths.length; i++) {
            String attributePath = attributePaths[i];
            if ("class".equals(attributePath)) {
            	this.mutators[i] = new EmptyMutator();
            } else {
            	this.mutators[i] = new AnyMutator(attributePath, false, true);
            }
        }
	}

	private Object haveTargetObject(Object bean, String featurePath) {
		if (featurePath == null) {
			return bean;
		} else if (featurePath.contains(".")) {
			String[] pathParts = StringUtil.splitOnFirstSeparator(featurePath, '.');
			Object child = haveTargetObject(bean, pathParts[0]);
			return haveTargetObject(child, pathParts[1]);
		} 
		Object value = FeatureAccessor.getValue(bean, featurePath, false);
		if (value != null)
			return value;
		else
			return AnyMutator.setFeatureDefault(bean, featurePath);
	}

}
