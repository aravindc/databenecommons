/*
 * (c) Copyright 2010 by Volker Bergmann. All rights reserved.
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

package org.databene.commons.operation;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

/**
 * Tests the {@link MinNumberStringOperation}.<br/><br/>
 * Created: 26.02.2010 09:49:46
 * @since 0.5.0
 * @author Volker Bergmann
 */
public class MinNumberStringOperationTest {

	private MinNumberStringOperation min;

	@Before
	public void setUp() {
		min = new MinNumberStringOperation();
	}
	
	@Test
	public void testInt() {
		assertEquals("1", min.perform("1", "2", "3"));
	}
	
	@Test
	public void testDouble() {
		assertEquals("1.", min.perform("1.", "2.", "3."));
	}
	
	@Test
	public void testFormatSustained() {
		assertEquals("1.000", min.perform("1.000", "2.", "3."));
	}
	
	@Test
	public void testMixed() {
		assertEquals("1.000", min.perform("1.000", "2"));
	}
	
}
