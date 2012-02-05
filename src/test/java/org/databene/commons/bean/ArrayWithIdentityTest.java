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

package org.databene.commons.bean;

import static org.junit.Assert.*;

import java.util.Arrays;

import org.junit.Test;

/**
 * Tests the {@link ArrayWithIdentity}.<br/><br/>
 * Created: 05.02.2012 09:40:15
 * @since 0.5.14
 * @author Volker Bergmann
 */
public class ArrayWithIdentityTest {

	@Test
	public void testGetElementCount() {
		assertEquals(2, new ArrayWithIdentity(new Object[] { 1, 2 }).getElementCount());
	}
	
	@Test
	public void testEquals() {
		ArrayWithIdentity array1 = new ArrayWithIdentity(new Object[] { 1, 2 });
		ArrayWithIdentity array2 = new ArrayWithIdentity(new Object[] { 1, 2 });
		ArrayWithIdentity array3 = new ArrayWithIdentity(new Object[] { 2, 1 });
		assertEquals(array1, array2);
		assertEquals(array2, array1);
		assertFalse(array1.equals(array3));
		assertFalse(array3.equals(array1));
	}
	
	@Test
	public void testHashcode() {
		assertEquals(Arrays.hashCode(new int[] { 1, 2 }), new ArrayWithIdentity(new Object[] { 1, 2 }).hashCode());
		assertEquals(Arrays.hashCode(new int[] { 1, 3 }), new ArrayWithIdentity(new Object[] { 1, 3 }).hashCode());
	}
	
	@Test
	public void testToString() {
		assertEquals("1, 2", new ArrayWithIdentity(new Object[] { 1, 2 }).toString());
	}
	
}
