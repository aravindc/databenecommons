/*
 * (c) Copyright 2010 by Volker Bergmann. All rights reserved.
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

package org.databene.commons.collection;

/**
 * Represents a range of int values from a <code>min</code> to a <code>max</code> value 
 * (including min and max).<br/><br/>
 * Created: 05.10.2010 19:33:23
 * @since 0.5.4
 * @author Volker Bergmann
 */
public class IntRange {
	
	protected int min;
	protected int max;
	
	public IntRange(int min, int max) {
	    this.min = min;
	    this.max = max;
    }

	public int getMin() {
    	return min;
    }

	public void setMin(int min) {
    	this.min = min;
    }

	public int getMax() {
    	return max;
    }

	public void setMax(int max) {
    	this.max = max;
    }
	
	public boolean contains(int i) {
		return (min <= i && i <= max);
	}

	@Override
    public int hashCode() {
	    final int prime = 31;
	    int result = 1;
	    result = prime * result + max;
	    result = prime * result + min;
	    return result;
    }

	@Override
    public boolean equals(Object obj) {
	    if (this == obj)
		    return true;
	    if (obj == null || getClass() != obj.getClass())
		    return false;
	    IntRange that = (IntRange) obj;
	    return (max == that.max && min == that.min);
    }
	
	@Override
	public String toString() {
	    return (min != max ? min + "..." + max : String.valueOf(min));
	}
	
}
