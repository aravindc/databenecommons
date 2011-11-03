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
 * Retrieves values of a JavaBean property without knowing the bean type.<br/>
 * <br/>
 * Created: 21.07.2007 10:18:09
 * @author Volker Bergmann
 */
@SuppressWarnings("rawtypes")
class UntypedPropertyAccessor implements PropertyAccessor {

    private String propertyName;
    private Class<?> propertyType;
    private boolean strict;

    public UntypedPropertyAccessor(String propertyName, boolean strict) {
        this.propertyName = propertyName;
        this.propertyType = Object.class;
        this.strict = strict;
    }

    public Object getValue(Object bean) {
        if (bean == null)
            if (strict)
                throw new IllegalArgumentException("Trying to get property value '" + propertyName + "' from null");
            else
                return null;
        PropertyDescriptor descriptor = BeanUtil.getPropertyDescriptor(bean.getClass(), propertyName);
        if (descriptor == null)
            if (strict)
                throw new ConfigurationError("No property '" + propertyName + "' found in class " + bean.getClass());
            else
                return null;
        this.propertyType = descriptor.getPropertyType();
        return BeanUtil.invoke(bean, descriptor.getReadMethod());
    }

    public Class<?> getValueType() {
        return propertyType;
    }

    public String getPropertyName() {
        return propertyName;
    }
    
}
