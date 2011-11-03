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

package org.databene.commons;

import java.lang.reflect.Array;

import org.databene.commons.converter.ToStringConverter;

/**
 * Helper class for building arrays.
 * @param <E>
 * @author Volker Bergmann
 * @since 0.2.04
 */
public class ArrayBuilder<E> {
    
    private static final int DEFAULT_INITIAL_CAPACITY = 10;
    
    private static Escalator escalator = new LoggerEscalator();
    
    private Class<E> componentType;
    private E[] buffer;
    private int elementCount;

    public ArrayBuilder(Class<E> componentType) {
        this(componentType, DEFAULT_INITIAL_CAPACITY);
    }
    
    public ArrayBuilder(Class<E> componentType, int initialCapacity) {
        this.componentType = componentType;
        this.buffer = createBuffer(initialCapacity);
    }

    /** @deprecated replaced with add(Element) */
    @Deprecated
    public ArrayBuilder<E> append(E element) {
    	escalator.escalate(getClass().getName() + ".append() is deprecated, please use the add() method", 
    			getClass(), null);
        return add(element);
    }
    
    public ArrayBuilder<E> add(E element) {
        if (buffer == null)
            throw new UnsupportedOperationException("ArrayBuilder cannot be reused after invoking toArray()");
        if (elementCount >= buffer.length - 1) {
            E[] newBuffer = createBuffer(buffer.length * 2);
            System.arraycopy(buffer, 0, newBuffer, 0, buffer.length);
            buffer = newBuffer;
        }
        buffer[elementCount++] = element;
        return this;
    }
    
    public void addAll(E[] elements) {
	    for (E element : elements)
	    	add(element);
    }
    
    public E[] toArray() {
        E[] result = ArrayUtil.newInstance(componentType, elementCount);
        System.arraycopy(buffer, 0, result, 0, elementCount);
        elementCount = 0;
        buffer = null;
        return result;
    }
    
    @Override
    public String toString() {
    	StringBuilder builder = new StringBuilder();
    	for (int i = 0; i < elementCount; i++) {
    		if (i > 0)
    			builder.append(", ");
    		builder.append(ToStringConverter.convert(buffer[i], "[NULL]"));
    	}
    	return builder.toString();
    }

    // private helpers -------------------------------------------------------------------------------------------------
    
    @SuppressWarnings("unchecked")
    private E[] createBuffer(int initialCapacity) {
        return (E[]) Array.newInstance(componentType, initialCapacity);
    }

}
