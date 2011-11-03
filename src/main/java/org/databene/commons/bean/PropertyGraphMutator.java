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
import org.databene.commons.StringUtil;
import org.databene.commons.UpdateFailedException;
import org.databene.commons.mutator.NamedMutator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Mutates JavaBean object graphs.<br/>
 * <br/>
 * Created: 08.05.2005 06:24:41
 * @author Volker Bergmann
 */
class PropertyGraphMutator implements NamedMutator {

    private static Logger logger = LoggerFactory.getLogger(PropertyGraphMutator.class);

    private boolean strict;
    private PropertyAccessor<Object, ?>[] subAccessors;
    private NamedMutator lastMutator;
    private String propertyName;

    // constructors ----------------------------------------------------------------------------------------------------

    public PropertyGraphMutator(String propertyName) {
        this(propertyName, true);
    }

    public PropertyGraphMutator(String propertyName, boolean strict) {
        this(null, propertyName, strict);
    }

    public PropertyGraphMutator(Class<?> beanClass, String propertyName) {
        this(beanClass, propertyName, true);
    }

    @SuppressWarnings("unchecked")
    public PropertyGraphMutator(Class<?> beanClass, String propertyName, boolean strict) {
        this.propertyName = propertyName;
        this.strict = strict;
        int separatorIndex = propertyName.indexOf('.');
        if (separatorIndex >= 0) {
            String[] nodeNames = StringUtil.tokenize(propertyName, '.');
            Class<?> nodeClass = beanClass;
            subAccessors = new PropertyAccessor[nodeNames.length - 1];
            for (int i = 0; i < nodeNames.length - 1; i++) {
                subAccessors[i] = PropertyAccessorFactory.getAccessor(nodeClass, nodeNames[i], strict);
                nodeClass = subAccessors[i].getValueType();
            }
            String lastNodeName = nodeNames[nodeNames.length - 1];
            if (beanClass != null)
                lastMutator = PropertyMutatorFactory.getPropertyMutator(
                    subAccessors[subAccessors.length - 1].getValueType(), lastNodeName, strict);
            else
                lastMutator = new UntypedPropertyMutator(lastNodeName, strict);
        } else
            lastMutator = new TypedPropertyMutator(beanClass, propertyName, strict);
    }

    // PropertyMutator interface ---------------------------------------------------------------------------------------

    public String getName() {
        return propertyName;
    }

    public void setValue(Object bean, Object propertyValue) throws UpdateFailedException {
        if (bean == null)
            if (strict)
                throw new IllegalArgumentException("Cannot set a property on null");
            else
                return;
        logger.debug("setting property '" + getName() + "' to '" + propertyValue + "' on bean " + bean);
        Object superBean = bean;
        if (subAccessors != null) {
            for (PropertyAccessor<Object, ?> subAccessor : subAccessors) {
                Object subBean = subAccessor.getValue(superBean);
                if (subBean == null && propertyValue != null) {
                    // bean is null but since there is something to set create one
                    Class<?> propertyType = subAccessor.getValueType();
                    subBean = BeanUtil.newInstance(propertyType);
                    BeanUtil.setPropertyValue(superBean, subAccessor.getPropertyName(), subBean);
                }
                superBean = subBean;
            }
        }
        lastMutator.setValue(superBean, propertyValue);
    }

}
