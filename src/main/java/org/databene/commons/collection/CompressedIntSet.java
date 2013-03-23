/*
 * (c) Copyright 2010-2012 by Volker Bergmann. All rights reserved.
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

import java.util.Iterator;
import java.util.TreeMap;
import java.util.Map.Entry;

/**
 * Collects int values in a compressed way.<br/><br/>
 * Created: 05.10.2010 19:17:30
 * @since 0.5.4
 * @author Volker Bergmann
 */
public class CompressedIntSet {
	
	protected TreeMap<Integer, IntRange> numbers;
	protected long size;
	
	public CompressedIntSet() {
	    this.numbers = new TreeMap<Integer, IntRange>();
	    this.size = 0;
    }

	public void clear() {
		numbers.clear();
		this.size = 0;
    }
	
	public void addAll(int... numbers) {
		for (int number : numbers)
			add(number);
	}

	public void add(int i) {
		if (numbers.isEmpty()) {
			// if the set is empty, insert the number
			insertNumber(i);
			size = 1;
		} else {
			// search the highest entry which is less or equals to i
			Entry<Integer, IntRange> floorEntry = numbers.floorEntry(i);
			IntRange rangeBelow;
			if (floorEntry == null)
				extendRangeAboveOrInsertNumber(i); // no range below found, check above
			else {
				// check found range
				rangeBelow = floorEntry.getValue();
				if (rangeBelow.contains(i))
					return;
				if (rangeBelow.getMax() + 1 == i) {
					// extend found range if applicable
					rangeBelow.setMax(i);
					size++;
					// check if two adjacent ranges can be merged
				    IntRange upperNeighbor = numbers.get(i + 1);
				    if (upperNeighbor != null) {
				    	numbers.remove(i + 1);
				    	rangeBelow.setMax(upperNeighbor.getMax());
				    }
				} else
					extendRangeAboveOrInsertNumber(i);
			}
		}
    }

	private void extendRangeAboveOrInsertNumber(int i) {
	    IntRange rangeAbove = numbers.get(i + 1);
	    if (rangeAbove != null) {
	    	numbers.remove(i + 1);
	    	rangeAbove.setMin(i);
	    	numbers.put(i, rangeAbove);
	    } else
	        insertNumber(i);
	    size++;
    }

	private void insertNumber(int i) {
	    numbers.put(i, new IntRange(i, i));
    }

	public boolean contains(int i) {
		Entry<Integer, IntRange> floorEntry = numbers.floorEntry(i);
		return (floorEntry != null && floorEntry.getValue().contains(i));
    }

	public boolean remove(int i) {
		Entry<Integer, IntRange> floorEntry = numbers.floorEntry(i);
		if (floorEntry == null || !floorEntry.getValue().contains(i)) 
			return false;
		IntRange range = floorEntry.getValue();
		if (i == range.getMax() && range.getMax() > range.getMin()) {
			range.setMax(i - 1);
		} else if (i == range.getMin()) {
			numbers.remove(i);
			if (range.getMax() > i) {
				range.setMin(i + 1);
				numbers.put(i + 1, range);
			}
		} else {
			int max = range.getMax();
			range.setMax(i - 1);
			IntRange range2 = new IntRange(i + 1, max);
			numbers.put(i + 1, range2);
		}
		return true;
    }

	public boolean isEmpty() {
	    return numbers.isEmpty();
    }
	
	public long size() {
		return size;
	}
	
	public Iterator<Integer> iterator() {
		return new CompressedSetIterator();
	}
	
	// java.lang.Object overrides --------------------------------------------------------------------------------------

	@Override
    public boolean equals(Object obj) {
	    if (this == obj)
		    return true;
	    if (obj == null || getClass() != obj.getClass())
		    return false;
	    CompressedIntSet that = (CompressedIntSet) obj;
	    return this.equals(that.numbers);
    }
	
    @Override
	public int hashCode() {
		return numbers.hashCode();
	}
    
	@Override
	public String toString() {
	    return numbers.values().toString();
	}
	
	
	
	// Iterator class --------------------------------------------------------------------------------------------------
	
	public class CompressedSetIterator implements Iterator<Integer> {
    	
    	protected Iterator<IntRange> intRangeIterator;
    	protected IntRange currentIntRange;
    	protected Integer lastInt;
    	
    	protected CompressedSetIterator() {
    		intRangeIterator = numbers.values().iterator();
    		currentIntRange = null;
    		lastInt = null;
    	}

	    @Override
		public boolean hasNext() {
	    	if (currentIntRange == null) {
	    		if (intRangeIterator != null && intRangeIterator.hasNext()) {
	    			currentIntRange = intRangeIterator.next();
	    			lastInt = null;
	    			return true;
	    		} else {
	    			intRangeIterator = null;
	    			return false;
	    		}
	    	}
	    	return (lastInt == null || lastInt < currentIntRange.max);
	    }

	    @Override
		public Integer next() {
	    	if (intRangeIterator != null && currentIntRange == null) {
	    		if (intRangeIterator.hasNext()) {
	    			currentIntRange = intRangeIterator.next();
	    		} else {
	    			intRangeIterator = null;
	    			currentIntRange = null;
	    		}
	    	}
	    	if (intRangeIterator == null || currentIntRange == null)
    			throw new IllegalStateException("No 'next' value available. Check hasNext() before calling next().");
	    	lastInt = (lastInt != null ? ++lastInt : currentIntRange.min);
	    	if (lastInt == currentIntRange.max)
	    		currentIntRange = null;
	    	return lastInt;
	    }

	    @Override
		public void remove() {
	    	CompressedIntSet.this.remove(lastInt);
	    }
    }
	
}
