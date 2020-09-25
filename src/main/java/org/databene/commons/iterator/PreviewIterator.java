/*
 * Copyright (C) 2004-2015 Volker Bergmann (volker.bergmann@bergmann-it.de).
 * All rights reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * http://www.apache.org/licenses/LICENSE-2.0
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.databene.commons.iterator;

import java.util.Iterator;

/**
 * {@link Iterator} implementation which allows to preview the {@link #next()} value 
 * without actually consuming it using the {@link #peek()} method.
 * Created: 25.01.2012 14:11:17
 * @param <E> the type to iterate
 * @since 0.5.14
 * @author Volker Bergmann
 */
public class PreviewIterator<E> extends IteratorProxy<E> {
	
	private boolean hasNext;
	private E next;

	public PreviewIterator(Iterator<E> source) {
		super(source);
		fetchNext();
	}
	
	public E peek() {
		return this.next;
	}

	// Iterator interface implementation -------------------------------------------------------------------------------
	
	@Override
	public boolean hasNext() {
		return hasNext;
	}
	
	@Override
	public E next() {
		E result = this.next;
		fetchNext();
		return result;
	}
	
	// private helpers -------------------------------------------------------------------------------------------------
	
	private void fetchNext() {
		this.hasNext = source.hasNext();
		this.next = (this.hasNext ? source.next() : null);
	}
	
}
