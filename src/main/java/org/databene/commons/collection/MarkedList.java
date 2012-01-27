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

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.databene.commons.NullSafeComparator;

/**
 * Implementation of the {@link List} interface which supports 
 * individual marking of each list element and retrieval of the 
 * marked or unmarked element sub lists.<br/><br/>
 * Created: 25.01.2012 17:03:05
 * @since 0.5.14
 * @author Volker Bergmann
 */
public class MarkedList<E> extends ListProxy<E> {
	
	private List<Boolean> marks;

	public MarkedList(List<E> realList) {
		this(realList, createMarks(realList.size()));
	}

	public MarkedList(List<E> realList, List<Boolean> marks) {
		super(realList);
		this.marks = marks;
	}
	
	// marker interface ------------------------------------------------------------------------------------------------
	
	public boolean mark(int index) {
		return marks.set(index, true);
	}

	public boolean unmark(int index) {
		return marks.set(index, false);
	}
	
	public boolean isMarked(int index) {
		return marks.get(index);
	}
	
	public List<E> getMarkedElements() {
		List<E> result = new ArrayList<E>();
		for (int i = 0; i < realList.size(); i++)
			if (isMarked(i))
				result.add(get(i));
		return result;
	}
	
	public List<E> getUnmarkedElements() {
		List<E> result = new ArrayList<E>();
		for (int i = 0; i < realList.size(); i++)
			if (!isMarked(i))
				result.add(get(i));
		return result;
	}
	
	// java.util.List overrides ----------------------------------------------------------------------------------------
	
	@Override
	public boolean add(E element) {
		marks.add(false);
		return super.add(element);
	}

	@Override
	public void add(int index, E element) {
		marks.add(index, false);
		super.add(index, element);
	}

	@Override
	public boolean addAll(Collection<? extends E> elements) {
		for (int i = elements.size(); i > 0; i--)
			marks.add(false);
		return super.addAll(elements);
	}

	@Override
	public boolean addAll(int index, Collection<? extends E> elements) {
		for (int i = elements.size(); i > 0; i--)
			marks.add(index, false);
		return super.addAll(index, elements);
	}

	@Override
	public E remove(int index) {
		marks.remove(index);
		return super.remove(index);
	}

	@Override
	public boolean remove(Object element) {
		int index = indexOf(element);
		if (index < 0)
			return false;
		remove(index);
		return true;
	}

	@Override
	public boolean removeAll(Collection<?> elementsToRemove) {
		boolean removedAny = false;
		for (int index = realList.size() - 1; index >= 0; index--) {
			Object element = realList.get(index);
			for (Object elementToRemove : elementsToRemove) {
				if (NullSafeComparator.equals(element, elementToRemove)) {
					remove(index);
					removedAny = true;
					break;
				}
			}
		}
		return removedAny;
	}

	@Override
	public boolean retainAll(Collection<?> elementsToRetain) {
		boolean changed = false;
		for (int index = realList.size() - 1; index >= 0; index--) {
			Object element = realList.get(index);
			boolean found = false;
			for (Object elementToRetain : elementsToRetain) {
				if (NullSafeComparator.equals(element, elementToRetain)) {
					found = true;
					break;
				}
			}
			if (!found)
				remove(index);
		}
		return changed;
	}

	@Override
	public List<E> subList(int fromIndex, int toIndex) {
		return new MarkedList<E>(realList.subList(fromIndex, toIndex), marks.subList(fromIndex, toIndex));
	}
	
	// private helpers -------------------------------------------------------------------------------------------------
	
	private static ArrayList<Boolean> createMarks(int size) {
		ArrayList<Boolean> result = new ArrayList<Boolean>(size);
		for (int i = 0; i < size; i++)
			result.set(i, false);
		return result;
	}

}
