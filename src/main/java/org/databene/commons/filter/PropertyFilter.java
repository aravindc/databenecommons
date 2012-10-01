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

package org.databene.commons.filter;

import org.databene.commons.BeanUtil;
import org.databene.commons.Condition;
import org.databene.commons.ExceptionMapper;
import org.databene.commons.Filter;

import java.lang.reflect.Method;
import java.lang.reflect.InvocationTargetException;

/**
 * Filter that matches a JavaBean by checking a Condition for one of its property values.<br/>
 * <br/>
 * Created: 04.02.2007 00:47:13
 * @since 0.1
 * @author Volker Bergmann
 */
public class PropertyFilter<E, P> implements Filter<E> {

    private Method propertyReadMethod;
    private Condition<P> propertyCondition;

    public PropertyFilter(Class<E> type, String propertyName, Condition<P> propertyCondition) {
        try {
            this.propertyReadMethod = type.getMethod(BeanUtil.readMethodName(propertyName, type));
            this.propertyCondition = propertyCondition;
        } catch (NoSuchMethodException e) {
            throw new RuntimeException(e); // How could this ever happen!
        }
    }

    @Override
	@SuppressWarnings("unchecked")
    public boolean accept(E candidate) {
        try {
            P propertyValue = (P) propertyReadMethod.invoke(candidate);
            return propertyCondition.evaluate(propertyValue);
        } catch (IllegalAccessException e) {
            throw ExceptionMapper.configurationException(e, propertyReadMethod);
        } catch (InvocationTargetException e) {
            throw ExceptionMapper.configurationException(e, propertyReadMethod);
        }
    }
    
}
