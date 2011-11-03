/*
 * (c) Copyright 2007-2010 by Volker Bergmann. All rights reserved.
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

package org.databene.commons.converter;

import org.databene.commons.ArrayUtil;
import org.databene.commons.BeanUtil;

import java.lang.reflect.Array;
import java.lang.reflect.Method;
import java.util.Collection;

/**
 * Converts arrays and collections to arrays and other object to an array of size 1.
 * Note: The target type is not declared Object[], since we also want to create byte[].<br/>
 * <br/>
 * Created: 26.08.2007 16:01:38
 * @author Volker Bergmann
 */
@SuppressWarnings({ "unchecked", "rawtypes" })
public class ToArrayConverter extends ThreadSafeConverter {

    private Class componentType;
    private boolean nullToEmpty;

    // constructors ----------------------------------------------------------------------------------------------------
    
    public ToArrayConverter() {
        this(Object.class);
    }

    public ToArrayConverter(Class componentType) {
        this(componentType, true);
    }

    public ToArrayConverter(Class componentType, boolean nullToEmpty) {
    	super(Object.class, ArrayUtil.arrayType(componentType));
        this.componentType = componentType;
        this.nullToEmpty = nullToEmpty;
    }
    
    // properties ------------------------------------------------------------------------------------------------------

    public void setNullToEmpty(boolean nullToEmpty) {
    	this.nullToEmpty = nullToEmpty;
    }

    // Converter interface implementation ------------------------------------------------------------------------------

    public Object convert(Object sourceValue) {
        return convert(sourceValue, componentType, nullToEmpty);
    }
    
    // static utility methods ------------------------------------------------------------------------------------------

    public static Object convert(Object sourceValue, Class componentType) {
    	return convert(sourceValue, componentType, true);
    }

    @SuppressWarnings("cast")
    public static Object convert(Object sourceValue, Class componentType, boolean nullToEmpty) {
    	if (sourceValue == null)
    		return (nullToEmpty ? ArrayUtil.buildArrayOfType(componentType) : null);
        if (sourceValue instanceof Collection) {
            Collection col = (Collection) sourceValue;
            Object[] array = (Object[]) Array.newInstance(componentType, col.size());
            int count = 0;
            for (Object item : col)
                array[count++] = item;
            return array;
        } else if (componentType == byte.class) {
            Method method = BeanUtil.getMethod(sourceValue.getClass(), "getBytes");
            if (method != null)
                return (byte[]) BeanUtil.invoke(sourceValue, method);
            else
                throw new UnsupportedOperationException("Conversion not supported: " + sourceValue.getClass() + " -> " + componentType + "[]");
        } else if (sourceValue.getClass().isArray()) {
            return ArrayUtil.buildArrayOfType(componentType, (Object[]) sourceValue);
	    } else  {
	        return ArrayUtil.buildArrayOfType(componentType, sourceValue);
	    }
    }

    @Override
    public String toString() {
    	return getClass().getSimpleName() + "(" + componentType + ")";
    }
    
}
