/*
 * (c) Copyright 2007-2013 by Volker Bergmann. All rights reserved.
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

import java.io.Serializable;
import java.util.*;

import org.databene.commons.collection.ListBasedSet;
import org.databene.commons.collection.MapEntry;
import org.databene.commons.collection.MapProxy;

/**
 * Map implementation that tracks the order in which elements where added
 * and returns them in that order by the <i>values()</i> method. <br/>
 * <br/>
 * This is useful for all cases in which elements will be queried by a key
 * and processed but need to be stored in the original order.<br/>
 * <br/>
 * Created: 06.01.2007 09:04:17
 * @author Volker Bergmann
 */
public class OrderedMap<K,V> implements Map<K,V>, Serializable {

    private static final long serialVersionUID = -6081918861041975388L;
    
	private Map<K, Integer> keyIndices;
    protected List<V> values;

    // constructors ----------------------------------------------------------------------------------------------------

    public OrderedMap() {
        keyIndices = new HashMap<K, Integer>();
        values = new ArrayList<V>();
    }

    public OrderedMap(int initialCapacity) {
        keyIndices = new HashMap<K, Integer>(initialCapacity);
        values = new ArrayList<V>(initialCapacity);
    }

    public OrderedMap(int initialCapacity, float loadFactor) {
        keyIndices = new HashMap<K, Integer>(initialCapacity, loadFactor);
        values = new ArrayList<V>(initialCapacity);
    }

    public OrderedMap(Map<K,V> source) {
        this(source.size());
        for (Entry<K, V> entry : source.entrySet())
            put(entry.getKey(), entry.getValue());
    }
    
    
    
    // custom interface ------------------------------------------------------------------------------------------------
    
	public Map.Entry<K, V> getEntry(K key) {
		if (containsKey(key))
			return new MapEntry<K, V>(key, get(key));
		else
			return null;
    }
	
	
	
    // Map interface implementation ------------------------------------------------------------------------------------

    @Override
	public int size() {
        return values.size();
    }

    @Override
	public boolean isEmpty() {
        return values.isEmpty();
    }

    @Override
	public boolean containsKey(Object key) {
        return keyIndices.containsKey(key);
    }

    @Override
	public boolean containsValue(Object value) {
        return values.contains(value);
    }

    @Override
	public V get(Object key) {
        Integer index = keyIndices.get(key);
        if (index == null)
            return null;
        return values.get(index);
    }

    @Override
	public V put(K key, V value) {
        Integer index = keyIndices.get(key);
        if (index != null)
            return values.set(index, value);
        else {
            keyIndices.put(key, values.size());
            values.add(value);
            return null;
        }
    }

    @Override
	public V remove(Object key) {
        Integer index = keyIndices.remove(key);
        if (index != null) {
            V oldValue = values.get(index);
            values.remove((int)index);
            for (Entry<K, Integer> entry : keyIndices.entrySet()) {
                int entryIndex = entry.getValue();
                if (entryIndex > index)
                    entry.setValue(entryIndex - 1);
            }
            return oldValue;
        } else
            return null;
    }

    @Override
	public void putAll(Map<? extends K, ? extends V> t) {
        for (Map.Entry<? extends K, ? extends V> entry : t.entrySet())
            put(entry.getKey(), entry.getValue());
    }

    @Override
	public void clear() {
        keyIndices.clear();
        values.clear();
    }

    @Override
	public Set<K> keySet() {
        List<K> tmp = new ArrayList<K>(values.size());
        // set the used array size by adding nulls
        for (int i = 0; i < values.size(); i++)
            tmp.add(null);
        // set the array elements themselves
        for (Entry<K, Integer> entry : keyIndices.entrySet())
            tmp.set(entry.getValue(), entry.getKey());
        return new ListBasedSet<K>(tmp);
    }

    @Override
	public List<V> values() {
        return new ArrayList<V>(values);
    }

    @Override
	@SuppressWarnings("unchecked")
    public Set<Map.Entry<K, V>> entrySet() {
    	Map.Entry<K, V>[] tmp = new Map.Entry[values.size()];
        for (Map.Entry<K, Integer> entry : keyIndices.entrySet()) {
            Integer index = entry.getValue();
            tmp[index] = new ProxyEntry(entry.getKey(), index);
        }
        return new ListBasedSet<Map.Entry<K, V>>(tmp);
    }

    // List/Vector interface -------------------------------------------------------------------------------------------

    public V valueAt(int index) {
        return values.get(index);
    }
    
    public int indexOfValue(V value) {
    	return values.indexOf(value);
    }

    /**
     * Returns an array containing all of the values in this map in proper sequence.
     * Obeys the general contract of the Collection.toArray method.
     * @return an array containing all of the elements in this list in proper sequence.
     */
    public Object[] toArray() {
        return values.toArray();
    }

    /**
     * Returns an array containing all of the values in this map in proper sequence;
     * the runtime type of the returned array is that of the specified array.
     * Obeys the general contract of the Collection.toArray(Object[]) method.
     * @param a the array into which the elements of this list are to be stored, if it is big enough; otherwise, a new array of the same runtime type is allocated for this purpose.
     * @return an array containing the values of this map.
     * @throws ArrayStoreException if the runtime type of the specified array is not a supertype of the runtime type of every element in this list.
     * @throws NullPointerException - if the specified array is null.
     */
    public <T>T[] toArray(T[] a) {
        return values.toArray(a);
    }
    
    // specific interface ----------------------------------------------------------------------------------------------
    
    public List<V> internalValues() {
        return values;
    }

    public boolean equalsIgnoreOrder(Map<K, V> that) {
        if (this == that)
            return true;
        if (this.size() != that.size())
        	return false;
        for (Map.Entry<K, V> entry : that.entrySet()) {
            K key = entry.getKey();
            if (!this.containsKey(key))
                return false;
            if (!NullSafeComparator.equals(this.get(key), that.get(key)))
                return false;
        }
        return true;
    }
    
    private class ProxyEntry implements Map.Entry<K, V> {
    	
    	private K key;
    	private int index;
    	
		public ProxyEntry(K key, int index) {
			this.key = key;
			this.index = index;
		}

		@Override
		public K getKey() {
			return key;
		}

		@Override
		public V getValue() {
			return OrderedMap.this.values.get(index);
		}

		@Override
		public V setValue(V value) {
			return OrderedMap.this.values.set(index, value);
		}
    	
		@Override
		public String toString() {
			return String.valueOf(key) + '=' + getValue();
		}
    }

    // java.lang.Object overrides --------------------------------------------------------------------------------------

    @SuppressWarnings("rawtypes")
    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || !(o instanceof Map))
            return false;
        while (o instanceof MapProxy)
        	o = ((MapProxy) o).getRealMap();
        final OrderedMap that = (OrderedMap) o;
        return (this.values.equals(that.values) && this.keyIndices.equals(that.keyIndices));
    }

    @Override
    public int hashCode() {
        return keyIndices.hashCode() * 29 + values.hashCode();
    }

    @Override
    public String toString() {
        ListBasedSet<Entry<K, V>> entries = (ListBasedSet<Entry<K, V>>)entrySet();
        StringBuilder buffer = new StringBuilder("{");
        if (entries.size() > 0)
            buffer.append(entries.get(0));
        for (int i = 1; i < entries.size(); i++)
            buffer.append(", ").append(entries.get(i));
        buffer.append('}');
        return buffer.toString();
    }
    
}