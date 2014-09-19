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

package org.databene.commons.converter;

import org.databene.commons.BeanUtil;
import org.databene.commons.ConversionException;
import org.databene.commons.Mutator;
import org.databene.commons.StringUtil;
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

	private ClassProvider<Object[]> beanClassProvider;
	private String[] attributePaths;
	private Mutator[] mutators;
	private ReferenceResolver referenceResolver;
	
	public PropertyArray2JavaBeanConverter(ClassProvider<Object[]> beanClassProvider, String[] attributePaths, ReferenceResolver referenceResolver) {
		super(Object[].class, Object.class);
		this.beanClassProvider = beanClassProvider;
		this.attributePaths = attributePaths;
		this.referenceResolver = referenceResolver;
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
		} else {
			return AnyMutator.setFeatureDefault(bean, featurePath);
		}
	}

}
