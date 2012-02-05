/*
 * (c) Copyright 2012 by Volker Bergmann. All rights reserved.
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

package org.databene.commons.bean;

import java.util.Arrays;

import org.databene.commons.ArrayFormat;

/**
 * Wrapper for an array which implements {@link #equals(Object)}, {@link #hashCode()} and {@link #toString()}
 * based on the array's element values.<br/><br/>
 * Created: 03.02.2012 16:34:14
 * @since 0.5.4
 * @author Volker Bergmann
 */
public class ArrayWithIdentity {

	private Object[] elements;

	public ArrayWithIdentity(Object[] elements) {
	    this.elements = elements;
    }

	public int getElementCount() {
		return elements.length;
	}
	
	// java.lang.Object overrides --------------------------------------------------------------------------------------
	
	@Override
	public int hashCode() {
		return Arrays.hashCode(elements);
    }

	@Override
    public boolean equals(Object obj) {
	    if (this == obj)
		    return true;
	    if (obj == null || getClass() != obj.getClass())
		    return false;
	    ArrayWithIdentity that = (ArrayWithIdentity) obj;
	    return Arrays.equals(this.elements, that.elements);
    }
	
	@Override
	public String toString() {
		return ArrayFormat.format(elements);
    }

}
