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
import org.databene.commons.UpdateFailedException;
import org.databene.commons.mutator.NamedMutator;

import java.util.Map;

/**
 * Instantiates JavaBeans from class name and a Properties object 
 * that contains the text representation of the bean property values.<br/>
 * <br/>
 * Created: 04.09.2007 21:13:06
 * @author Volker Bergmann
 */
public class BeanFactory {
	
	private static final ClassProvider DEFAULT_CLASS_PROVIDER = new DefaultClassProvider();
    /**
     * Creates an object of the specified type.
     * @param beanClassName the name of the class to instantiate
     * @param properties the bean properties
     * @return an object of the specified class
     */
    public static Object newBean(String beanClassName, Map<String, Object> properties) {
        return newBean(beanClassName, properties, DEFAULT_CLASS_PROVIDER);
    }

    public static Object newBean(String beanClassName, Map<String, Object> properties, ClassProvider factory) {
    	Object bean = BeanUtil.newInstance(factory.forName(beanClassName));
        for (Map.Entry<String, Object> entry : properties.entrySet()) {
            String propertyName = entry.getKey();
            NamedMutator mutator = PropertyMutatorFactory.getPropertyMutator(bean.getClass(), propertyName, false);
            try {
                mutator.setValue(bean, entry.getValue());
            } catch (UpdateFailedException e) {
                throw new RuntimeException("Unable to set property " + propertyName + " on class " + beanClassName, e);
            }
        }
        return bean;
    }

}
