/*
 * Copyright (C) 2004-2014 Volker Bergmann (volker.bergmann@bergmann-it.de).
 * All rights reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * http://www.apache.org/licenses/LICENSE-2.0
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.databene.commons.array;

import org.databene.commons.converter.ToStringConverter;

/**
 * Helper class for constructing byte arrays.<br/><br/>
 * Created: 27.12.2010 07:45:22
 * @since 0.5.5
 * @author Volker Bergmann
 */
public class ByteArrayBuilder {

    private static final int DEFAULT_INITIAL_CAPACITY = 10;
    
    private byte[] buffer;
    private int itemCount;

    public ByteArrayBuilder() {
        this(DEFAULT_INITIAL_CAPACITY);
    }
    
    public ByteArrayBuilder(int initialCapacity) {
        this.buffer = createBuffer(initialCapacity);
    }

    public ByteArrayBuilder add(byte item) {
        if (buffer == null)
            throw new UnsupportedOperationException("ArrayBuilder cannot be reused after invoking toArray()");
        if (itemCount >= buffer.length - 1) {
            byte[] newBuffer = createBuffer(buffer.length * 2);
            System.arraycopy(buffer, 0, newBuffer, 0, buffer.length);
            buffer = newBuffer;
        }
        buffer[itemCount++] = item;
        return this;
    }
    
    public void addAll(byte[] elements) {
	    addAll(elements, 0, elements.length);
    }
    
    public void addAll(byte[] elements, int fromIndex, int toIndex) {
	    for (int i = fromIndex; i < toIndex; i++)
	    	add(elements[i]);
    }
    
    public byte[] toArray() {
        if (buffer == null)
            throw new UnsupportedOperationException("ArrayBuilder cannot be reused after invoking toArray()");
        byte[] result = new byte[itemCount];
        System.arraycopy(buffer, 0, result, 0, itemCount);
        itemCount = 0;
        buffer = null;
        return result;
    }
    
    @Override
    public String toString() {
    	StringBuilder builder = new StringBuilder();
    	for (int i = 0; i < itemCount; i++) {
    		if (i > 0)
    			builder.append(", ");
    		builder.append(ToStringConverter.convert(buffer[i], "[NULL]"));
    	}
    	return builder.toString();
    }
    
    
    // private helper method -------------------------------------------------------------------------------------------

    private static byte[] createBuffer(int capacity) {
        return new byte[capacity];
    }

}
