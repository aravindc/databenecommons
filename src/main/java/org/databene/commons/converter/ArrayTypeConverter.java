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

import org.databene.commons.Converter;

/**
 * Converts arrays from one component type to arrays of another component type.<br/>
 * <br/>
 * Created: 07.06.2007 14:35:18
 * @author Volker Bergmann
 */
public class ArrayTypeConverter<T> extends ArrayConverter<Object, T> {

    public ArrayTypeConverter(Class<T> targetArrayComponentType, Class<? extends T> ... elementTypes) {
        super(Object.class, targetArrayComponentType, createConverters(elementTypes));
    }

    @SuppressWarnings({ "unchecked", "rawtypes" })
    private static <T> Converter<Object, T>[] createConverters(Class<? extends T> ... elementTypes) {
        Converter<Object, T>[] converters = new Converter[elementTypes.length];
        for (int i = 0; i < elementTypes.length; i++)
            converters[i] = new AnyConverter(elementTypes[i]);
        return converters;
    }
    
    public static Object[] convert(Object[] args, Class<?>[] elementTypes) {
        return new ArrayTypeConverter<Object>(Object.class, elementTypes).convert(args);
    }

    public static <T> T[] convert(Object[] args, Class<T> componentType) {
        return new ArrayTypeConverter<T>(componentType).convert(args);
    }
    
    @Override
    public String toString() {
    	return getClass().getSimpleName() + "[" + getTargetType() + "]";
    }
    
}
