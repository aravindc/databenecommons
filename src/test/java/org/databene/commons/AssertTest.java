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

package org.databene.commons;

import org.junit.Test;
import static junit.framework.Assert.*;

/**
 * Tests the {@link Assert} class.<br/><br/>
 * Created at 06.05.2008 12:43:45
 * @since 0.5.3
 * @author Volker Bergmann
 */
public class AssertTest {
	
	@Test
	public void testAssertEqualsStringArray() {
		String[] a1 = new String[] { "Alpha", "Beta" };
		expectNotEquals(a1, null);
		Assert.equals(a1, a1);
		Assert.equals(a1, new String[] { "Alpha", "Beta" });
		String[] a2 = new String[] { "Alpha" };
		expectNotEquals(a1, a2);
		expectNotEquals(a2, a1);
	}

	@Test
	public void testAssertEqualsByteArray() {
		byte[] a1 = new byte[] { 1, 2 };
		expectNotEquals(a1, null);
		Assert.equals(a1, a1);
		Assert.equals(a1, new byte[] { 1, 2 });
		byte[] a2 = new byte[] { 1 };
		expectNotEquals(a1, a2);
		expectNotEquals(a2, a1);
	}
	
	// private helpers -------------------------------------------------------------------------------------------------

	private void expectNotEquals(String[] a1, String[] a2) {
		try {
			Assert.equals(a1, a2);
			fail("AssertionError expected");
		} catch (AssertionError e) {
			// expected
		}
	}

	private void expectNotEquals(byte[] a1, byte[] a2) {
		try {
			Assert.equals(a1, a2);
			fail("AssertionError expected");
		} catch (AssertionError e) {
			// expected
		}
	}

}
