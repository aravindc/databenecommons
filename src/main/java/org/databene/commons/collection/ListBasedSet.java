/*
 * (c) Copyright 2007 by Volker Bergmann. All rights reserved.
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

package org.databene.commons.collection;

import java.util.*;

/**
 * Implements a Set based on a List. This provides for ensuring element uniqueness
 * while maintaining the order in which elements were inserted.<br/>
 * <br/>
 * Created: 03.08.2007 18:47:13
 */
public class ListBasedSet<E> implements Set<E> {

    private List<E> list;

    public ListBasedSet() {
        this.list = new ArrayList<E>();
    }

    public ListBasedSet(int initialCapacity) {
        this.list = new ArrayList<E>(initialCapacity);
    }

    public ListBasedSet(Collection<E> collection) {
        this.list = new ArrayList<E>(collection);
    }

    public ListBasedSet(E... elements) {
        this.list = new ArrayList<E>(elements.length);
        for (E element : elements)
        	this.list.add(element);
    }

    public int size() {
        return list.size();
    }

    public boolean isEmpty() {
        return (size() == 0);
    }

    public boolean contains(Object o) {
        return list.contains(o);
    }

    public Iterator<E> iterator() {
        return list.iterator();
    }

    public Object[] toArray() {
        return list.toArray();
    }

    public <T>T[] toArray(T[] a) {
        return list.toArray(a);
    }

    public boolean add(E o) {
        if (list.contains(o))
            return true;
        else
            return list.add(o);
    }

    public boolean remove(Object o) {
        return list.remove(o);
    }

    public boolean containsAll(Collection<?> c) {
        return list.containsAll(c);
    }

    public boolean addAll(Collection<? extends E> c) {
        return list.addAll(c);
    }

    public boolean retainAll(Collection<?> c) {
        return list.retainAll(c);
    }

    public boolean removeAll(Collection<?> c) {
        return list.removeAll(c);
    }

    public void clear() {
        list.clear();
    }

    // List interface --------------------------------------------------------------------------------------------------

    public E get(int index) {
        return list.get(index);
    }
    
    @Override
    public String toString() {
    	return list.toString();
    }
}
