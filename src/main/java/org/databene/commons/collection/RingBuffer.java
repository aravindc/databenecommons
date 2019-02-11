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

package org.databene.commons.collection;

/**
 * Implements a generic ring buffer.<br><br>
 * Created: 25.11.2017 23:34:53
 * @since 1.0.12
 * @author Volker Bergmann
 * @param <E> the type of object to be buffered
 */

public class RingBuffer<E> {
	
	private Object[] buffer;
	private int cursor;

	public RingBuffer(int capacity) {
		this.buffer = new Object[capacity];
		this.cursor = 0;
	}
	
	public boolean contains(E object) {
		for (Object o : buffer)
			if (o != null && o.equals(object))
				return true;
		return false;
	}

	public void add(E object) {
		buffer[cursor++] = object;
		if (cursor == buffer.length)
			cursor = 0;
	}
	
}