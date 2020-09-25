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
package org.databene.commons.filter;

import java.util.ArrayList;

import org.databene.commons.Filter;

/**
 * Abstract parent filter which combines several filter components.
 * Created: 08.06.2012 20:29:58
 * @param <E> the type of objects to be filtered
 * @since 0.5.16
 * @author Volker Bergmann
 */
public abstract class CompositeFilter<E> {
	
	protected ArrayList<Filter<E>> components;
	
	protected CompositeFilter(Filter<E>... components) {
		this.components = new ArrayList<Filter<E>>();
		for (Filter<E> component : components)
			this.components.add(component);
	}
	
	public CompositeFilter<E> add(Filter<E> filter) {
		this.components.add(filter);
		return this;
	}
	
}
