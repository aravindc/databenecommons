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

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import org.databene.commons.OrderedMap;

/**
 * {@link Map} proxy which allow to attach a mark to each entry.<br/><br/>
 * Created: 03.02.2012 16:40:07
 * @since 0.5.14
 * @author Volker Bergmann
 */
public class MarkedMap<K, V> implements Map<K, V> {

	private Map<K, V> realMap;
	private Map<K, Boolean> marks;

	public MarkedMap() {
		this(new HashMap<K, V>());
	}

	public MarkedMap(Map<K, V> realMap) {
		this.realMap = realMap;
		this.marks = new HashMap<K, Boolean>(realMap.size());
		for (K key : realMap.keySet())
			marks.put(key, false);
	}

	// marker interface ------------------------------------------------------------------------------------------------
	
	public boolean mark(K key) {
		return marks.put(key, true);
	}

	public boolean unmark(K key) {
		return marks.put(key, false);
	}
	
	public boolean isMarked(K key) {
		return marks.get(key);
	}
	
	public Map<K, V> unmarkedEntries() {
		Map<K, V> result = new OrderedMap<K, V>();
		for (Map.Entry<K, V> entry : realMap.entrySet())
			if (!isMarked(entry.getKey()))
				result.put(entry.getKey(), entry.getValue());
		return result;
	}
	
	public Map<K, V> markedEntries() {
		Map<K, V> result = new OrderedMap<K, V>();
		for (Map.Entry<K, V> entry : realMap.entrySet())
			if (isMarked(entry.getKey()))
				result.put(entry.getKey(), entry.getValue());
		return result;
	}
		
	
	
	// Map interface implementation ------------------------------------------------------------------------------------
	
	@Override
	public void clear() {
		realMap.clear();
		marks.clear();
	}

	@Override
	public boolean containsKey(Object key) {
		return realMap.containsKey(key);
	}

	@Override
	public boolean containsValue(Object value) {
		return realMap.containsValue(value);
	}

	@Override
	public Set<Entry<K, V>> entrySet() {
		return realMap.entrySet();
	}

	@Override
	public V get(Object value) {
		return realMap.get(value);
	}

	@Override
	public boolean isEmpty() {
		return realMap.isEmpty();
	}

	@Override
	public Set<K> keySet() {
		return realMap.keySet();
	}

	@Override
	public V put(K key, V value) {
		V result = realMap.put(key, value);
		marks.put(key, false);
		return result;
	}

	@Override
	public void putAll(Map<? extends K, ? extends V> otherMap) {
		realMap.putAll(otherMap);
		this.marks = new HashMap<K, Boolean>(realMap.size());
		for (K key : realMap.keySet())
			marks.put(key, false);
	}

	@Override
	public V remove(Object value) {
		V result = realMap.remove(value);
		marks.remove(value);
		return result;
	}

	@Override
	public int size() {
		return realMap.size();
	}

	@Override
	public Collection<V> values() {
		return realMap.values();
	}
	
	
	
	// java.lang.Object overrides --------------------------------------------------------------------------------------
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((marks == null) ? 0 : marks.hashCode());
		result = prime * result + ((realMap == null) ? 0 : realMap.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object other) {
		if (this == other)
			return true;
		if (other == null || getClass() != other.getClass())
			return false;
		@SuppressWarnings("unchecked")
		MarkedMap<K, V> that = (MarkedMap<K, V>) other;
		return this.marks.equals(that.marks) && this.realMap.equals(that.realMap);
	}
	
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("[");
		boolean first = true;
		for (Map.Entry<K, V> entry : realMap.entrySet()) {
			if (!first)
				builder.append(", ");
			builder.append(entry.getKey()).append('=').append(entry.getValue());
			builder.append(marks.get(entry.getKey()) ? "(+)" : "(-)");
			first = false;
		}
		builder.append("]");
		return builder.toString();
	}

}
