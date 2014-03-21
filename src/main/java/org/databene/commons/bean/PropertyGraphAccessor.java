/*
 * (c) Copyright 2007-2014 by Volker Bergmann. All rights reserved.
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
import org.databene.commons.accessor.TypedAccessor;
import org.databene.commons.accessor.TypedAccessorChain;

/**
 * Accesses JavaBean object graphs.<br/>
 * <br/>
 * Created: 21.07.2007 10:18:17
 * @author Volker Bergmann
 */
@SuppressWarnings("rawtypes")
public class PropertyGraphAccessor extends TypedAccessorChain implements PropertyAccessor {

    private String propertyName;

    public PropertyGraphAccessor(Class<?> beanClass, String propertyName, boolean strict) {
        super(createSubAccessors(beanClass, propertyName, strict));
        this.propertyName = propertyName;
    }
    
    @Override
	public String getPropertyName() {
        return propertyName;
    }

    
    // static utility methods ------------------------------------------------------------------------------------------
    
    public static Object getPropertyGraph(String path, Object bean) {
    	String[] tokens = StringUtil.splitOnFirstSeparator(path, '.');
    	Object tmp = BeanUtil.getPropertyValue(bean, tokens[0]);
    	if (tokens[1] != null)
    		return getPropertyGraph(tokens[1], tmp);
    	else
    		return tmp;
    }

    private static TypedAccessor[] createSubAccessors(Class<?> beanClass, String propertyName, boolean strict) {
        String[] nodeNames = StringUtil.tokenize(propertyName, '.');
        PropertyAccessor[] nodes = new PropertyAccessor[nodeNames.length];
        Class<?> intermediateClass = beanClass;
        for (int i = 0; i < nodeNames.length; i++) {
            PropertyAccessor node = PropertyAccessorFactory.getAccessor(intermediateClass, nodeNames[i], strict);
            nodes[i] = node;
            if (intermediateClass != null)
                intermediateClass = node.getValueType();
        }
        return nodes;
    }
    
}
