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
package org.databene.commons.wrapper;

import org.databene.commons.Named;

/**
 * Wraps a {@link Named} object.
 * Created: 20.06.2013 06:48:24
 * @param <E> the object type to wrap
 * @since 0.5.24
 * @author Volker Bergmann
 */

public class NamedWrapper<E> implements Named {
	
	private String name;
	private E wrapped;
	
	public NamedWrapper(String name, E wrapped) {
		this.name = name;
		this.wrapped = wrapped;
	}

	@Override
	public String getName() {
		return name;
	}
	
	public E getWrapped() {
		return wrapped;
	}
	
	public void setWrapped(E wrapped) {
		this.wrapped = wrapped;
	}
	
	@Override
	public String toString() {
		return name;
	}

}
