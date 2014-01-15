/*
 * (c) Copyright 2013 by Volker Bergmann. All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, is permitted under the terms of the
 * GNU General Public License (GPL).
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

package org.databene.commons.array;

import org.databene.commons.converter.ToStringConverter;

/**
 * Helper class for constructing double arrays.<br/><br/>
 * Created: 30.12.2013 21:53:43
 * @since 0.5.26
 * @author Volker Bergmann
 */

public class DoubleArrayBuilder {
	
    private static final int DEFAULT_INITIAL_CAPACITY = 10;
    
    private double[] buffer;
    private int itemCount;

    public DoubleArrayBuilder() {
        this(DEFAULT_INITIAL_CAPACITY);
    }
    
    public DoubleArrayBuilder(int initialCapacity) {
        this.buffer = createBuffer(initialCapacity);
    }

	public DoubleArrayBuilder add(double item) {
        if (buffer == null)
            throw new UnsupportedOperationException(getClass().getName() + " cannot be reused after invoking toArray()");
        if (itemCount >= buffer.length - 1) {
        	double[] newBuffer = createBuffer(buffer.length * 2);
            System.arraycopy(buffer, 0, newBuffer, 0, buffer.length);
            buffer = newBuffer;
        }
        buffer[itemCount++] = item;
        return this;
    }
    
    public void addAll(double[] elements) {
	    addAll(elements, 0, elements.length);
    }
    
    public void addAll(double[] elements, int fromIndex, int toIndex) {
	    for (int i = fromIndex; i < toIndex; i++)
	    	add(elements[i]);
    }
    
    public double[] toArray() {
        if (buffer == null)
            throw new UnsupportedOperationException(getClass().getName() + " cannot be reused after invoking toArray()");
        double[] result = new double[itemCount];
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

    private static double[] createBuffer(int capacity) {
        return new double[capacity];
    }

}
