/*
 * (c) Copyright 2008-2009 by Volker Bergmann. All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, is permitted under the terms of the
 * GNU General Public License.
 *
 * For redistributing this software or a derivative work under a license other
 * than the GPL-compatible Free Software License as defined by the Free
 * Software Foundation or approved by OSI, you must first obtain a commercial
 * license to this software product from Volker Bergmann.
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

package org.databene.commons.condition;

import org.databene.commons.ArrayUtil;

import org.junit.Test;
import static junit.framework.Assert.*;

/**
 * Tests the {@link ComparationCondition}.<br/><br/>
 * Created at 29.04.2008 18:18:45
 * @since 0.4.2
 * @author Volker Bergmann
 */
public class ComparationConditionTest {

	@Test
	public void testEqual() {
		ComparationCondition<Integer> condition = new ComparationCondition<Integer>(ComparationCondition.EQUAL);
		assertTrue(condition.evaluate(ArrayUtil.toArray(1, 1)));
		assertFalse(condition.evaluate(ArrayUtil.toArray(1, 2)));
	}
    
	@Test
	public void testNotEqual() {
		ComparationCondition<Integer> condition = new ComparationCondition<Integer>(ComparationCondition.NOT_EQUAL);
		assertFalse(condition.evaluate(ArrayUtil.toArray(1, 1)));
		assertTrue(condition.evaluate(ArrayUtil.toArray(1, 2)));
	}

	@Test
	public void testGreaterOrEqual() {
		ComparationCondition<Integer> condition = new ComparationCondition<Integer>(ComparationCondition.GREATER_OR_EQUAL);
		assertTrue(condition.evaluate(ArrayUtil.toArray(1, 1)));
		assertFalse(condition.evaluate(ArrayUtil.toArray(1, 2)));
		assertTrue(condition.evaluate(ArrayUtil.toArray(2, 1)));
	}

	@Test
	public void testGreater() {
		ComparationCondition<Integer> condition = new ComparationCondition<Integer>(ComparationCondition.GREATER);
		assertFalse(condition.evaluate(ArrayUtil.toArray(1, 1)));
		assertFalse(condition.evaluate(ArrayUtil.toArray(1, 2)));
		assertTrue(condition.evaluate(ArrayUtil.toArray(2, 1)));
	}

	@Test
	public void testLessOrEqual() {
		ComparationCondition<Integer> condition = new ComparationCondition<Integer>(ComparationCondition.LESS_OR_EQUAL);
		assertTrue(condition.evaluate(ArrayUtil.toArray(1, 1)));
		assertTrue(condition.evaluate(ArrayUtil.toArray(1, 2)));
		assertFalse(condition.evaluate(ArrayUtil.toArray(2, 1)));
	}

	@Test
	public void testLess() {
		ComparationCondition<Integer> condition = new ComparationCondition<Integer>(ComparationCondition.LESS);
		assertFalse(condition.evaluate(ArrayUtil.toArray(1, 1)));
		assertTrue(condition.evaluate(ArrayUtil.toArray(1, 2)));
		assertFalse(condition.evaluate(ArrayUtil.toArray(2, 1)));
	}
	
}
