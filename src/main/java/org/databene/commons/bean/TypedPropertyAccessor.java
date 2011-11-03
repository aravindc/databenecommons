/*
 * (c) Copyright 2007-2009 by Volker Bergmann. All rights reserved.
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

import java.lang.reflect.Method;
import java.beans.PropertyDescriptor;

/**
 * Retrieves the value of a JavaBean property with knowledge of the bean type.<br/>
 * <br/>
 * Created: 21.07.2007 10:18:00
 * @author Volker Bergmann
 */
class TypedPropertyAccessor<E> implements PropertyAccessor<E, Object> {

    private String propertyName;
    private Method accessorMethod;
    private boolean strict;

    public TypedPropertyAccessor(Class<E> beanClass, String propertyName, boolean strict) {
        this.propertyName = propertyName;
        this.strict = strict;
        try {
            PropertyDescriptor propertyDescriptor = BeanUtil.getPropertyDescriptor(beanClass, propertyName);
            if (propertyDescriptor == null) {
                if (strict)
                    throw new ConfigurationError("No property '" + propertyName + "' found in " + beanClass);
            } else {
                this.accessorMethod = propertyDescriptor.getReadMethod();
                if (accessorMethod == null)
                    throw new ConfigurationError("No read method for property '" + propertyName + "'" +
                            " found on " + beanClass);
            }
        } catch (SecurityException e) {
            throw new RuntimeException(e);
        }
    }

    public Object getValue(Object object) {
        if (object == null)
            if (strict)
                throw new IllegalArgumentException("Trying to get property value '" + propertyName + "' from null");
            else
                return null;
        try {
            return (accessorMethod != null ? accessorMethod.invoke(object) : null);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public Class<?> getValueType() {
        return (accessorMethod != null ? accessorMethod.getReturnType() : null);
    }

    public String getPropertyName() {
        return propertyName;
    }
}
