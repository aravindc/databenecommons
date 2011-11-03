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

package org.databene.commons.bean;

import java.util.Arrays;

import org.databene.commons.ArrayFormat;

/**
 * Wraps a plain object or an array and provides equals() and hashCode() 
 * that works consistently for both.<br/><br/>
 * Created: 09.09.2010 09:53:11
 * @since 0.5.4
 * @author Volker Bergmann
 */
public class ObjectOrArray {

	private Object realObject;

	public ObjectOrArray(Object realObject) {
	    this.realObject = realObject;
    }

	@Override
	public int hashCode() {
		if (realObject.getClass().isArray())
			return Arrays.hashCode((Object[]) realObject);
		else
			return realObject.hashCode();
    }

	@Override
    public boolean equals(Object obj) {
	    if (this == obj)
		    return true;
	    if (obj == null || getClass() != obj.getClass())
		    return false;
	    ObjectOrArray that = (ObjectOrArray) obj;
	    if (this.realObject.getClass().isArray())
		    return Arrays.equals((Object[]) this.realObject, (Object[]) that.realObject);
	    else
	    	return this.realObject.equals(that.realObject);
    }
	
	@Override
	public String toString() {
		if (realObject.getClass().isArray())
			return ArrayFormat.format((Object[]) realObject);
		else
			return String.valueOf(realObject);
    }
	
}
