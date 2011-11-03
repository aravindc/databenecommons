/*
 * (c) Copyright 2007 by Volker Bergmann. All rights reserved.
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

package org.databene.commons.bean;

import org.databene.commons.BeanUtil;
import org.databene.commons.ConfigurationError;

import java.beans.PropertyDescriptor;

/**
 * Creates PropertyAccessors that are as efficient as possible depending on the information provided.
 * The fastest accessors are available when the bean class is provided with the property name.
 * property names may express navigation over refering JavaBean entities, e.g. 'category.name'.
 * A PropertyAccessor in a non-strict mode converts the invocation argument to the correct property type
 * and behaves quietly, if it doesn not find the specified property<br/>
 * <br/>
 * Created: 06.01.2005 20:04:36
 * @author Volker Bergmann
 */
@SuppressWarnings("unchecked")
public class PropertyAccessorFactory {

    /** private constructor for preventing that the class is instantiated */
    private PropertyAccessorFactory() {
    }

    /**
     * @return a property accessor without knowledge about the bean type
     * (the slowest PropertyAccessor type) in strict mode.
     */
    @SuppressWarnings("rawtypes")
	public static PropertyAccessor getAccessor(String propertyName) {
        return getAccessor(null, propertyName);
    }

    /**
     * @return a property accessor of the specified strictness.
     */
    @SuppressWarnings("rawtypes")
	public static PropertyAccessor getAccessor(String propertyName, boolean strict) {
        return getAccessor(null, propertyName, strict);
    }

    /**
     * @return a property accessor without knowledge about the bean type
     * (the slowest PropertyAccessor type) in strict mode.
     */
/*
    public static PropertyAccessor getAccessor(String propertyName, Class propertyType) {
        return getAccessor(null, propertyName, propertyType, true);
    }
*/
    /**
     * @return a property accessor in strict mode.
     */
    @SuppressWarnings("rawtypes")
	public static PropertyAccessor getAccessor(Class<?> beanClass, String propertyName) {
        return getAccessor(beanClass, propertyName, true);
    }

    /**
     * @return a property accessor of the specified strictness.
     */
    @SuppressWarnings("rawtypes")
	public static PropertyAccessor getAccessor(Class<?> beanClass, String propertyName, boolean strict) {
        if (beanClass != null) {
            PropertyDescriptor propertyDescriptor = BeanUtil.getPropertyDescriptor(beanClass, propertyName);
            if (propertyDescriptor == null) {
                if (strict)
                    throw new ConfigurationError("No property '" + propertyName + "' found in " + beanClass);
            }
        }
        int index = propertyName.indexOf('.');
        if (index < 0) {
            if (beanClass == null)
                return new UntypedPropertyAccessor(propertyName, strict);
            else
                return new TypedPropertyAccessor(beanClass, propertyName, strict);
        } else
            return new PropertyGraphAccessor(beanClass, propertyName, strict);
    }

}
