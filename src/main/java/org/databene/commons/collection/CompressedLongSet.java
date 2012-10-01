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
 * Set of {@link Long} values which stores subsequent values in a compressed format.<br/><br/>
 * Created: 18.10.2010 08:32:15
 * @since 0.5.4
 * @author Volker Bergmann
 */
public class CompressedLongSet {
	
	protected TreeMap<Long, LongRange> numbers;
	protected long size;
	
	public CompressedLongSet() {
	    this.numbers = new TreeMap<Long, LongRange>();
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

	public void add(long i) {
		if (numbers.isEmpty()) {
			// if the set is empty, insert the number
			insertNumber(i);
			size = 1;
		} else {
			// search the highest entry which is less or equals to i
			Entry<Long, LongRange> floorEntry = numbers.floorEntry(i);
			LongRange rangeBelow;
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
				    LongRange upperNeighbor = numbers.get(i + 1);
				    if (upperNeighbor != null) {
				    	numbers.remove(i + 1);
				    	rangeBelow.setMax(upperNeighbor.getMax());
				    }
				} else
					extendRangeAboveOrInsertNumber(i);
			}
		}
    }

	private void extendRangeAboveOrInsertNumber(long i) {
	    LongRange rangeAbove = numbers.get(i + 1);
	    if (rangeAbove != null) {
	    	numbers.remove(i + 1);
	    	rangeAbove.setMin(i);
	    	numbers.put(i, rangeAbove);
	    } else
	        insertNumber(i);
	    size++;
    }

	private void insertNumber(long i) {
	    numbers.put(i, new LongRange(i, i));
    }

	public boolean contains(long i) {
		Entry<Long, LongRange> floorEntry = numbers.floorEntry(i);
		return (floorEntry != null && floorEntry.getValue().contains(i));
    }
	

	public boolean remove(long i) {
		Entry<Long, LongRange> floorEntry = numbers.floorEntry(i);
		if (floorEntry == null || !floorEntry.getValue().contains(i)) 
			return false;
		LongRange range = floorEntry.getValue();
		if (i == range.getMax() && range.getMax() > range.getMin()) {
			range.setMax(i - 1);
		} else if (i == range.getMin()) {
			numbers.remove(i);
			if (range.getMax() > i) {
				range.setMin(i + 1);
				numbers.put(i + 1, range);
			}
		} else {
			long max = range.getMax();
			range.setMax(i - 1);
			LongRange range2 = new LongRange(i + 1, max);
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
	
	public Iterator<Long> iterator() {
		return new CompressedSetIterator();
	}
	
	// java.lang.Object overrrides -------------------------------------------------------------------------------------

	@Override
	public String toString() {
	    return numbers.values().toString();
	}

	@Override
    public boolean equals(Object obj) {
	    if (this == obj)
		    return true;
	    if (obj == null || getClass() != obj.getClass())
		    return false;
	    CompressedLongSet that = (CompressedLongSet) obj;
	    return this.equals(that.numbers);
    }
	
	
	
	// Iterator class --------------------------------------------------------------------------------------------------
	
    public class CompressedSetIterator implements Iterator<Long> {
    	
    	protected Iterator<LongRange> longRangeIterator;
    	protected LongRange currentLongRange;
    	protected Long lastLong;
    	
    	protected CompressedSetIterator() {
    		longRangeIterator = numbers.values().iterator();
    		currentLongRange = null;
    		lastLong = null;
    	}

	    @Override
		public boolean hasNext() {
	    	if (currentLongRange == null) {
	    		if (longRangeIterator != null && longRangeIterator.hasNext()) {
	    			currentLongRange = longRangeIterator.next();
	    			lastLong = null;
	    			return true;
	    		} else {
	    			longRangeIterator = null;
	    			return false;
	    		}
	    	}
	    	return (lastLong == null || lastLong < currentLongRange.max);
	    }

	    @Override
		public Long next() {
	    	if (longRangeIterator != null && currentLongRange == null) {
	    		if (longRangeIterator.hasNext()) {
	    			currentLongRange = longRangeIterator.next();
	    		} else {
	    			longRangeIterator = null;
	    			currentLongRange = null;
	    		}
	    	}
	    	if (longRangeIterator == null || currentLongRange == null)
    			throw new IllegalStateException("No 'next' value available. Check hasNext() before calling next().");
	    	lastLong = (lastLong != null ? ++lastLong : currentLongRange.min);
	    	if (lastLong == currentLongRange.max)
	    		currentLongRange = null;
	    	return lastLong;
	    }

	    @Override
		public void remove() {
	    	CompressedLongSet.this.remove(lastLong);
	    }

    }
	
}
