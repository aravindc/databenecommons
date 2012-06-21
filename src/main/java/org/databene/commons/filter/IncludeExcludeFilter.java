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

package org.databene.commons.filter;

import java.util.ArrayList;
import java.util.List;

import org.databene.commons.Filter;

/**
 * Multi-step inclusion/exclusion filter. Note that a sequence of inclusions forms an intersection, not a union.
 * For including a union of filter result sets, include an OrFilter with the respective single filters.<br/><br/>
 * Created: 08.06.2012 19:40:57
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
