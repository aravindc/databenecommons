/*
 * (c) Copyright 2011 by Volker Bergmann. All rights reserved.
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

package org.databene.commons.math;

import java.util.Comparator;

import org.databene.commons.ComparableComparator;
import org.databene.commons.NullSafeComparator;

/**
 * Represents an interval between to endpoints. 
 * The endpoints can be of any class for which a {@link Comparator} can be provided.
 * Using the parameters {@link #minInclusive} and {@link #maxInclusive}, one can 
 * specify whether the interval shall contain the endpoint values themselves.<br/>
 * <br/>
 * Created: 10.03.2011 15:20:36
 * @since 0.5.8
 * @author Volker Bergmann
 */
public class Interval<E> {

	public final E min;
	public final boolean minInclusive;
	
	public final E max;
	public final boolean maxInclusive;
	
	public final Comparator<E> comparator;

	public Interval(E min, boolean minInclusive, E max, boolean maxInclusive,
			Comparator<E> comparator) {
		this.min = min;
		this.minInclusive = minInclusive;
		this.max = max;
		this.maxInclusive = maxInclusive;
		this.comparator = comparator;
	}
	
	public static <T extends Comparable<T>> Interval<T> createClosedInterval(T min, T max) {
		return new Interval<T>(min, true, max, true, new ComparableComparator<T>());
	}
	
	public E getMin() {
		return min;
	}
	
	public boolean isMinInclusive() {
		return minInclusive;
	}
	
	public E getMax() {
		return max;
	}
	
	public boolean isMaxInclusive() {
		return maxInclusive;
	}
	
	public static <T> Interval<T> createInfiniteInterval() {
		return new Interval<T>(null, false, null, false, null);
	}

	public boolean contains(E x) {
		if (min != null) {
			int minComp = comparator.compare(min, x);
			if (minComp > 0 || (!minInclusive && minComp == 0))
				return false;
		}
		if (max == null)
			return true;
		int maxComp = comparator.compare(x, max);
		return (maxComp < 0 || (maxInclusive && maxComp == 0));
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((max == null) ? 0 : max.hashCode());
		result = prime * result + (maxInclusive ? 1231 : 1237);
		result = prime * result + ((min == null) ? 0 : min.hashCode());
		result = prime * result + (minInclusive ? 1231 : 1237);
		return result;
	}

	@Override
	public boolean equals(Object other) {
		if (this == other)
			return true;
		if (other == null || getClass() != other.getClass())
			return false;
		@SuppressWarnings("rawtypes")
		Interval that = (Interval) other;
		return 
			(NullSafeComparator.equals(this.min, that.min)
			&& this.minInclusive == that.minInclusive 
			&& NullSafeComparator.equals(this.max, that.max) 
			&& this.maxInclusive == that.maxInclusive);
	}

	@Override
	public String toString() {
		if (min != null && max != null && comparator.compare(min, max) == 0)
			return String.valueOf(min);
		else
			return (minInclusive ? '[' : ']') + String.valueOf(min) + ',' + String.valueOf(max) + (maxInclusive ? ']' : '[');
	}

}
