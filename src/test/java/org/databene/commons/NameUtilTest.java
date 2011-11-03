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

package org.databene.commons;

import static org.junit.Assert.*;

import java.util.Arrays;
import java.util.List;

import org.junit.Test;

/**
 * Tests the {@link NameUtil} class.<br/><br/>
 * Created: 12.08.2010 11:47:03
 * @since 0.5.4
 * @author Volker Bergmann
 */
public class NameUtilTest {
	
	Named AB  = new X("AB");
	Named A_B = new X("A_B");
	Named AC  = new X("AC");
	Named A_C = new X("A_C");
	
	@Test
	public void testOrderByName() {
		Named[] array = new Named[] { A_C, AC, A_B, AB };
		NameUtil.orderByName(array);
		Named[] expected = new Named[] { AB, AC, A_B, A_C };
		assertTrue(Arrays.equals(expected, array));
	}
	
	@Test
	public void testIndexOf_list() {
		List<Named> list = CollectionUtil.toList(A_C, AC, A_B, AB);
		assertEquals(-1, NameUtil.indexOf("XY", list));
		assertEquals( 0, NameUtil.indexOf("A_C", list));
		assertEquals( 3, NameUtil.indexOf("AB", list));
	}
	
	@Test
	public void testIndexOf_array() {
		Named[] list = new Named[] { A_C, AC, A_B, AB };
		assertEquals(-1, NameUtil.indexOf("XY", list));
		assertEquals( 0, NameUtil.indexOf("A_C", list));
		assertEquals( 3, NameUtil.indexOf("AB", list));
	}
	
	private static final class X implements Named {
		
		private String name;

		public X(String name) {
	        this.name = name;
        }

		public String getName() {
	        return name;
        }
		
		@Override
		public String toString() {
		    return name;
		}
	}
}
