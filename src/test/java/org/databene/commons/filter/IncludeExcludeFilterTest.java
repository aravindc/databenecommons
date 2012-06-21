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

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.databene.commons.CollectionUtil;
import org.databene.commons.Filter;
import org.junit.Test;

/**
 * Tests the {@link IncludeExcludeFilter}.<br/><br/>
 * Created: 08.06.2012 19:55:15
 * @since 0.5.16
 * @author Volker Bergmann
 */
public class IncludeExcludeFilterTest {
	
	private static final Filter<Integer> ODD = new Filter<Integer>() {
		public boolean accept(Integer n) {
			return (n % 2) == 1;
		}
	};
	
	private static final Filter<Integer> SMALL = new Filter<Integer>() {
		public boolean accept(Integer n) {
			return n < 10;
		}
	};
	
	private static final Filter<Integer> PRIME = new Filter<Integer>() {
		final Set<Integer> PRIMES = CollectionUtil.toSet(2, 3, 5, 7, 11, 13, 17, 19);
		public boolean accept(Integer n) {
			return PRIMES.contains(n);
		}
	};
	
	// single-step tests -----------------------------------------------------------------------------------------------
	
	@Test
	public void testSmall() {
		check(new IncludeExcludeFilter<Integer>().addInclusion(SMALL), 
				0, 1, 2, 3, 4, 5, 6, 7, 8, 9);
	}

	@Test
	public void testNotSmall() {
		check(new IncludeExcludeFilter<Integer>().addExclusion(SMALL), 
				10, 11, 12, 13, 14, 15, 16, 17, 18, 19);
	}

	// two-step tests --------------------------------------------------------------------------------------------------
	
	@Test
	public void testSmallAndOdd() {
		check(new IncludeExcludeFilter<Integer>().addInclusion(SMALL).addInclusion(ODD), 
				1, 3, 5, 7, 9);
	}
	
	@Test
	public void testSmallAndNotOdd() {
		check(new IncludeExcludeFilter<Integer>().addInclusion(SMALL).addExclusion(ODD), 
				0, 2, 4, 6, 8);
	}
	
	@Test
	public void testNotSmallAndOdd() {
		check(new IncludeExcludeFilter<Integer>().addExclusion(SMALL).addInclusion(ODD), 
				11, 13, 15, 17, 19);
	}
	
	@Test
	public void testNotSmallAndNotOdd() {
		check(new IncludeExcludeFilter<Integer>().addExclusion(SMALL).addExclusion(ODD), 
				10, 12, 14, 16, 18);
	}
	
	// three-step tests ------------------------------------------------------------------------------------------------
	
	@Test
	public void testSmallAndOddAndPrime() {
		check(new IncludeExcludeFilter<Integer>().addInclusion(SMALL).addInclusion(ODD).addInclusion(PRIME), 
				3, 5, 7);
	}
	
	@Test
	public void testSmallAndOddAndNotPrime() {
		check(new IncludeExcludeFilter<Integer>().addInclusion(SMALL).addInclusion(ODD).addExclusion(PRIME), 
				1, 9);
	}
	
	@Test
	public void testSmallAndNotOddAndPrime() {
		check(new IncludeExcludeFilter<Integer>().addInclusion(SMALL).addExclusion(ODD).addInclusion(PRIME), 
				2);
	}
	
	@Test
	public void testSmallAndNotOddAndNotPrime() {
		check(new IncludeExcludeFilter<Integer>().addInclusion(SMALL).addExclusion(ODD).addExclusion(PRIME), 
				0, 4, 6, 8);
	}
	
	@Test
	public void testNotSmallAndOddAndPrime() {
		check(new IncludeExcludeFilter<Integer>().addExclusion(SMALL).addInclusion(ODD).addInclusion(PRIME), 
				11, 13, 17, 19);
	}
	
	@Test
	public void testNotSmallAndOddAndNotPrime() {
		check(new IncludeExcludeFilter<Integer>().addExclusion(SMALL).addInclusion(ODD).addExclusion(PRIME), 
				15);
	}
	
	@Test
	public void testNotSmallAndNotOddAndPrime() {
		check(new IncludeExcludeFilter<Integer>().addExclusion(SMALL).addExclusion(ODD).addInclusion(PRIME));
	}
	
	@Test
	public void testNotSmallAndNotOddAndNotPrime() {
		check(new IncludeExcludeFilter<Integer>().addExclusion(SMALL).addExclusion(ODD).addExclusion(PRIME), 
				10, 12, 14, 16, 18);
	}
	
	// check helper ----------------------------------------------------------------------------------------------------
	
	private void check(IncludeExcludeFilter<Integer> filter, Integer... expected) {
		List<Integer> actual = new ArrayList<Integer>();
		for (int i = 0; i < 20; i++)
			if (filter.accept(i))
				actual.add(i);
		assertEquals(CollectionUtil.toList(expected), actual);
	}

}
