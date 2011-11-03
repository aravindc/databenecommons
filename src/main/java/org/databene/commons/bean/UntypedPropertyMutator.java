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
import org.databene.commons.ConversionException;
import org.databene.commons.UpdateFailedException;
import org.databene.commons.converter.AnyConverter;

import java.beans.PropertyDescriptor;
import java.lang.reflect.Method;

/**
 * Mutates the value of a property on a JavaBean target object.<br/>
 * <br/>
 * Created: 21.07.2007 09:01:19
 * @author Volker Bergmann
 */
public class UntypedPropertyMutator extends AbstractNamedMutator {

    private boolean strict;

    public UntypedPropertyMutator(String propertyName, boolean strict) {
        super(propertyName);
        this.strict = strict;
    }

    public void setValue(Object target, Object value) throws UpdateFailedException {
        setValue(target, value, this.strict);
    }

    public void setValue(Object bean, Object propertyValue, boolean strict) throws UpdateFailedException {
        if (bean == null)
            if (strict)
                throw new UpdateFailedException("Cannot set a property on a null pointer");
            else
                return;
        PropertyDescriptor propertyDescriptor = BeanUtil.getPropertyDescriptor(bean.getClass(), name);
        if (propertyDescriptor == null)
            if (strict)
                throw new UpdateFailedException("property '" + name + "' not found in class " + bean.getClass());
            else
                return;
		Method writeMethod = propertyDescriptor.getWriteMethod();
        if (writeMethod == null) {
            if (strict)
                throw new UpdateFailedException("No write method found for property '" + name + "' in class " + bean.getClass());
            else
                return;
        }
        if (!strict && propertyValue != null) {
            Class<?> sourceType = propertyValue.getClass();
            Class<?> targetType = writeMethod.getParameterTypes()[0];
            try {
                if (!targetType.isAssignableFrom(sourceType))
                    propertyValue = AnyConverter.convert(propertyValue, targetType);
            } catch (ConversionException e) {
                throw new ConfigurationError(e);
            }
        }
        BeanUtil.invoke(bean, writeMethod, propertyValue);
    }
}
