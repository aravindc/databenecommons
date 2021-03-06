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
import java.util.List;

import org.databene.commons.Filter;

/**
 * Multi-step inclusion/exclusion filter. Note that a sequence of inclusions forms an intersection, not a union.
 * For including a union of filter result sets, include an OrFilter with the respective single filters.
 * Created: 08.06.2012 19:40:57
 * @param <E> the type of objects to filter
 * @since 0.5.16
 * @author Volker Bergmann
 */
public class IncludeExcludeFilter<E> implements Filter<E> {

	private List<FilterStep<E>> steps;
	
	public IncludeExcludeFilter() {
		this.steps = new ArrayList<IncludeExcludeFilter.FilterStep<E>>();
	}
	
	public IncludeExcludeFilter<E> addInclusion(Filter<E> filter) {
		steps.add(new FilterStep<E>(filter, true));
		return this;
	}
	
	public IncludeExcludeFilter<E> addExclusion(Filter<E> filter) {
		steps.add(new FilterStep<E>(filter, false));
		return this;
	}
	
	@Override
	public boolean accept(E candidate) {
		for (int i = 0; i < steps.size(); i++) {
			FilterStep<E> step = steps.get(i);
			if (step.inclusion && !step.filter.accept(candidate))
				return false;
			if (!step.inclusion && step.filter.accept(candidate))
				return false;
		}
		return true;
	}
	
	private static class FilterStep<E> {
		
		public final boolean inclusion;
		public final Filter<E> filter;
		
		public FilterStep(Filter<E> filter, boolean inclusion) {
			this.inclusion = inclusion;
			this.filter = filter;
		}
		
	}
	
}
