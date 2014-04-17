/*
 * (c) Copyright 2012-2014 by Volker Bergmann. All rights reserved.
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

import java.util.Map;

import org.databene.commons.OrderedMap;

/**
 * Maps name strings to objects ignoring the capitalization of the name.<br/><br/>
 * Created: 12.12.2012 11:08:44
 * @since 0.5.21
 * @author Volker Bergmann
 */
public class CaseIgnorantOrderedNameMap<E> extends OrderedMap<String, E> {
	
    private static final long serialVersionUID = -3134506770888057108L;

	// constructors + factory methods ----------------------------------------------------------------------------------
	
	public CaseIgnorantOrderedNameMap() {
	}
    
    public CaseIgnorantOrderedNameMap(Map<String, E> that) {
		super(that);
	}

    // Map interface implementation ------------------------------------------------------------------------------------
    
	@Override
	public boolean containsKey(Object key) {
        return containsKey((String) key);
    }

	public boolean containsKey(String key) {
        return super.containsKey(normalizeKey(key));
    }

	@Override
	public E get(Object key) {
		return get((String) key);
	}
	
	public E get(String key) {
        return super.get(normalizeKey(key));
    }

	@Override
	public Map.Entry<String, E> getEntry(String key) {
        String normalizedKey = normalizeKey(key);
		E value = super.get(normalizedKey);
        return new MapEntry<String, E>(normalizedKey, value);
    }

    @Override
    public E put(String key, E value) {
        return super.put(normalizeKey(key), value); 
    }

    public E remove(String key) {
        return super.remove(normalizeKey(key));
    }

    // private helpers -------------------------------------------------------------------------------------------------
    
    private static String normalizeKey(String key) {
		return (key != null ? key.toLowerCase() : key);
	}
    
}
