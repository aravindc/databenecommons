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
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

/**
 * Abstract proxy class for a {@link List}.<br/><br/>
 * Created: 25.01.2012 17:03:57
 * @since 0.5.14
 * @author Volker Bergmann
 */
public abstract class ListProxy<E> implements List<E> {

	protected List<E> realList;
	
	public ListProxy(List<E> realList) {
		this.realList = realList;
	}

	@Override
	public boolean add(E e) {
		return realList.add(e);
	}

	@Override
	public void add(int index, E element) {
		realList.add(index, element);
	}

	@Override
	public boolean addAll(Collection<? extends E> c) {
		return realList.addAll(c);
	}

	@Override
	public boolean addAll(int index, Collection<? extends E> c) {
		return realList.addAll(index, c);
	}

	@Override
	public void clear() {
		realList.clear();
	}

	@Override
	public boolean contains(Object o) {
		return realList.contains(o);
	}

	@Override
	public boolean containsAll(Collection<?> c) {
		return realList.containsAll(c);
	}
	
	@Override
	public E get(int index) {
		return realList.get(index);
	}

	@Override
	public int indexOf(Object o) {
		return realList.indexOf(o);
	}

	@Override
	public boolean isEmpty() {
		return realList.isEmpty();
	}

	@Override
	public Iterator<E> iterator() {
		return realList.iterator();
	}

	@Override
	public int lastIndexOf(Object o) {
		return realList.lastIndexOf(o);
	}

	@Override
	public ListIterator<E> listIterator() {
		return realList.listIterator();
	}

	@Override
	public ListIterator<E> listIterator(int index) {
		return realList.listIterator(index);
	}

	@Override
	public E remove(int index) {
		return realList.remove(index);
	}

	@Override
	public boolean remove(Object o) {
		return realList.remove(o);
	}

	@Override
	public boolean removeAll(Collection<?> c) {
		return realList.removeAll(c);
	}

	@Override
	public boolean retainAll(Collection<?> c) {
		return realList.retainAll(c);
	}

	@Override
	public E set(int index, E element) {
		return realList.set(index, element);
	}

	@Override
	public int size() {
		return realList.size();
	}

	@Override
	public List<E> subList(int fromIndex, int toIndex) {
		return realList.subList(fromIndex, toIndex);
	}

	@Override
	public Object[] toArray() {
		return realList.toArray();
	}

	@Override
	public <T> T[] toArray(T[] a) {
		return realList.toArray(a);
	}
	
	@Override
	public boolean equals(Object o) {
		return realList.equals(o);
	}

	@Override
	public int hashCode() {
		return realList.hashCode();
	}
	
	@Override
	public String toString() {
		return realList.toString();
	}
	
}
