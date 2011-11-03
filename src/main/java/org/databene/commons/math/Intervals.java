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

import java.util.ArrayList;
import java.util.List;

/**
 * Represents a collection of intervals and provides a {@link #contains(Object)} 
 * method for checking if one of them contains a certain value.<br/><br/>
 * Created: 10.03.2011 17:28:50
 * @since 0.5.8
 * @author Volker Bergmann
 */
public class Intervals<E> {

	private List<Interval<E>> intervals;
	
	public Intervals() {
		this.intervals = new ArrayList<Interval<E>>();
	}
	
	public Intervals<E> add(Interval<E> interval) {
		intervals.add(interval);
		return this;
	}
	
	public boolean contains(E x) {
		for (Interval<E> interval : intervals)
			if (interval.contains(x))
				return true;
		return false;
	}
	
	public int intervalCount() {
		return intervals.size();
	}
	
	public Interval<E> getInterval(int i) {
		return intervals.get(i);
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		if (intervals.size() > 0)
			builder.append(intervals.get(0));
		for (int i = 1; i < intervals.size(); i++)
			builder.append(", ").append(intervals.get(i));
		return builder.toString();
	}
	
}
