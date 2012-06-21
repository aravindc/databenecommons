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

import org.junit.Test;

/**
 * Tests the {@link OrFilter}.<br/><br/>
 * Created: 08.06.2012 20:37:39
 * @since 0.5.16
 * @author Volker Bergmann
 */
@SuppressWarnings("unchecked")
public class OrFilterTest {
	
	private static final ConstantFilter<Integer> TRUE = new ConstantFilter<Integer>(true);
	private static final ConstantFilter<Integer> FALSE = new ConstantFilter<Integer>(false);
	
	@Test
	public void testTrueOrTrue() {
		assertTrue(new OrFilter<Integer>(TRUE, TRUE).accept(0));
	}
	
	@Test
	public void testTrueOrFalse() {
		assertTrue(new OrFilter<Integer>(TRUE, FALSE).accept(0));
	}
	
	@Test
	public void testFalseOrTrue() {
		assertTrue(new OrFilter<Integer>(FALSE, TRUE).accept(0));
	}
	
	@Test
	public void testFalseOrFalse() {
		assertFalse(new OrFilter<Integer>(FALSE, FALSE).accept(0));
	}
	
}
