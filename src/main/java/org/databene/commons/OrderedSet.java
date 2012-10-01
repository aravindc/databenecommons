/*
 * (c) Copyright 2009-2012 by Volker Bergmann. All rights reserved.
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

import java.util.Collection;
import java.util.Iterator;
import java.util.Set;

/**
 * {@link Set} implementation that tracks the order in which elements where added
 * and returns them in that order by the <i>iterator()</i> method. <br/>
 * <br/>
 * This is useful for all cases in which elements need to be unique
 * but processed in the original order.<br/>
 * <br/> * Created at 28.02.2009 12:26:35
 * @since 0.4.8
 * @author Volker Bergmann
 */

public class OrderedSet<E> implements Set<E> {
	
	private OrderedMap<E, E> map;
	
    public OrderedSet() {
        map = new OrderedMap<E, E>();
    }

    public OrderedSet(int initialCapacity) {
        map = new OrderedMap<E, E>(initialCapacity);
    }

    public OrderedSet(int initialCapacity, float loadFactor) {
        map = new OrderedMap<E, E>(initialCapacity, loadFactor);
    }

    public OrderedSet(Collection<E> source) {
        map = new OrderedMap<E, E>(source.size());
        addAll(source);
    }
    
    // Set interface implementation ------------------------------------------------------------------------------------

    @Override
	public boolean add(E item) {
	    return (map.put(item, item) == null);
    }

    @Override
	public boolean addAll(Collection<? extends E> source) {
    	boolean changed = false;
    	for (E item : source)
        	changed |= add(item);
    	return changed; 
    }

    @Override
	public void clear() {
	    map.clear();
    }

    @Override
	public boolean contains(Object item) {
	    return map.containsKey(item);
    }

    @Override
	public boolean containsAll(Collection<?> items) {
	    for (Object o : items)
	    	if (!map.containsKey(o))
	    		return false;
	    return true;
    }

    @Override
	public boolean isEmpty() {
	    return map.isEmpty();
    }

    @Override
	public Iterator<E> iterator() {
	    return map.keySet().iterator();
    }

    @Override
	public boolean remove(Object item) {
	    return (map.remove(item) != null);
    }

    @Override
	public boolean removeAll(Collection<?> items) {
    	boolean changed = false;
	    for (Object item : items)
	    	changed |= remove(item);
	    return changed;
    }

    @Override
	public boolean retainAll(Collection<?> items) {
    	boolean changed = false;
	    for (E item : map.keySet())
	    	if (!items.contains(item)) {
	    		map.remove(item);
	    		changed = true;
	    	}
	    return changed;
    }

    @Override
	public int size() {
	    return map.size();
    }

    @Override
	public Object[] toArray() {
	    return map.keySet().toArray();
    }

    @Override
	public <T> T[] toArray(T[] array) {
	    return map.keySet().toArray(array);
    }
    
    // List interface --------------------------------------------------------------------------------------------------

    public E get(int index) {
        return map.elementAt(index);
    }
    
    // java.lang.Object overrides --------------------------------------------------------------------------------------

	@Override
    public int hashCode() {
	    return map.hashCode();
    }

	@SuppressWarnings("rawtypes")
    @Override
    public boolean equals(Object obj) {
	    if (this == obj)
		    return true;
	    if (obj == null || getClass() != obj.getClass())
		    return false;
	    return (this.map.equals(((OrderedSet) obj).map));
    }

    @Override
    public String toString() {
        return map.toString();
    }
}
