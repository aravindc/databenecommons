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

import org.databene.commons.HeavyweightIterable;
import org.databene.commons.HeavyweightIterator;

/**
 * Wraps an {@link Iterable} with a {@link HeavyweightIterable}.
 * On calls to <code>iterator()</code>, Iterators of the wrapped 
 * Iterable will be wrapped to be {@link HeavyweightIterator}s.
 * Created at 17.10.2008 01:29:37
 * @param <E> the type to iterate
 * @since 0.4.6
 * @author Volker Bergmann
 */
public class HeavyweightIterableAdapter<E> implements HeavyweightIterable<E> {
	
	private Iterable<E> source;

	public HeavyweightIterableAdapter(Iterable<E> source) {
		this.source = source;
	}

	@Override
	public HeavyweightIterator<E> iterator() {
		return new HeavyweightIteratorProxy<E>(source.iterator());
	}

}
