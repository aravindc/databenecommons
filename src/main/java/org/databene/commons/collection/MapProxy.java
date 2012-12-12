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
import java.util.Map;
import java.util.Set;

/**
 * Proxy class for a {@link Map}.<br/><br/>
 * Created: 12.12.2012 11:49:35
 * @since 0.5.21
 * @author Volker Bergmann
 */
public class MapProxy<M extends Map<K, V>, K, V> implements Map<K, V> {
	
	protected final M realMap;
	
	protected MapProxy(M realMap) {
		this.realMap = realMap;
	}

	@Override
	public void clear() {
		realMap.clear();
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
	public Set<java.util.Map.Entry<K, V>> entrySet() {
		return realMap.entrySet();
	}

	@Override
	public V get(Object key) {
		return realMap.get(key);
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
		return realMap.put(key, value);
	}

	@Override
	public void putAll(Map<? extends K, ? extends V> m) {
		realMap.putAll(m);
	}

	@Override
	public V remove(Object key) {
		return realMap.remove(key);
	}

	@Override
	public int size() {
		return realMap.size();
	}

	@Override
	public Collection<V> values() {
		return realMap.values();
	}
	
	@Override
	public int hashCode() {
		return realMap.hashCode();
	}

	@Override
	public boolean equals(Object o) {
		return realMap.equals(o);
	}
	
	@Override
	public String toString() {
		return realMap.toString();
	}
	
}
