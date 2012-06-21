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

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

/**
 * Tests the {@link AndFilter}.<br/><br/>
 * Created: 08.06.2012 21:00:24
 * @since 0.5.16
 * @author Volker Bergmann
 */
@SuppressWarnings("unchecked")
public class AndFilterTest {

	private static final ConstantFilter<Integer> TRUE = new ConstantFilter<Integer>(true);
	private static final ConstantFilter<Integer> FALSE = new ConstantFilter<Integer>(false);
	
	@Test
	public void testTrueAndTrue() {
		assertTrue(new AndFilter<Integer>(TRUE, TRUE).accept(0));
	}
	
	@Test
	public void testTrueAndFalse() {
		assertFalse(new AndFilter<Integer>(TRUE, FALSE).accept(0));
	}
	
	@Test
	public void testFalseAndTrue() {
		assertFalse(new AndFilter<Integer>(FALSE, TRUE).accept(0));
	}
	
	@Test
	public void testFalseAndFalse() {
		assertFalse(new AndFilter<Integer>(FALSE, FALSE).accept(0));
	}
	
}
