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

package org.databene.commons.collection;

import java.util.Map;

import org.databene.commons.NullSafeComparator;
import org.databene.commons.OrderedMap;

/**
 * {@link OrderedMap} implementation which assigns name strings to objects preserving the capitalization 
 * of stored names but allowing retrieval in a different capitalization.<br/><br/>
 * Created: 12.12.2012 11:12:16
 * @since 0.5.21
 * @author Volker Bergmann
 */
public class CaseInsensitiveOrderedNameMap<E> extends OrderedMap<String, E> {
	
	private static final long serialVersionUID = 5774443959123444148L;

	// constructors + factory methods ----------------------------------------------------------------------------------
	
    public CaseInsensitiveOrderedNameMap() {
	}
    
    public CaseInsensitiveOrderedNameMap(Map<String, E> that) {
		super(that);
	}

    // Map interface implementation ------------------------------------------------------------------------------------
    
	@Override
	public boolean containsKey(Object key) {
        return containsKey((String) key);
    }

	public boolean containsKey(String key) {
        boolean result = super.containsKey(key);
        if (result)
        	return result;
        for (String tmp : super.keySet())
        	if (tmp.equalsIgnoreCase(key))
        		return true;
		return result;
    }

	@Override
	public E get(Object key) {
		return get((String) key);
	}
	
	public E get(String key) {
        E result = super.get(key);
        if (result != null)
        	return result;
        for (Map.Entry<String, E> entry : super.entrySet()) {
	        String tmp = entry.getKey();
	        if ((tmp == null && key == null) || (tmp != null && tmp.equalsIgnoreCase(key)))
        		return entry.getValue();
        }
		return null;
    }

	@Override
	public Map.Entry<String, E> getEntry(String key) {
        String normalizedKey = key;
		E value = super.get(normalizedKey);
        if (value != null)
        	return new MapEntry<String, E>(key, value);
        for (Map.Entry<String, E> entry : super.entrySet()) {
	        String tmp = entry.getKey();
	        if ((tmp == null && key == null) || (tmp != null && tmp.equalsIgnoreCase(key))) {
				String resultKey = entry.getKey();
				return new MapEntry<String, E>(resultKey, entry.getValue());
			}
        }
		return null;
    }

    @Override
    public E put(String key, E value) {
        return super.put(key, value); 
    }

    public E remove(String key) {
        E result = super.remove(key);
        if (result != null)
        	return result;
        for (Map.Entry<String, E> entry : super.entrySet())
        	if (NullSafeComparator.equals(entry.getKey(), key))
        		return super.remove(entry.getKey());
        return null;
    }

}
