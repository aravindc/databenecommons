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

package org.databene.commons.collection;

import org.junit.Test;
import static junit.framework.Assert.*;

/**
 * Tests the {@link NamedValueList}.<br/><br/>
 * Created at 09.05.2008 21:19:22
 * @since 0.5.4
 * @author Volker Bergmann
 */
public class NamedValueListTest {

	@Test
	public void testCaseIgnorantList() {
		NamedValueList<Integer> list = NamedValueList.createCaseIgnorantList();
		list.add("ONE", 1);
		assertEquals(1, list.size());
		assertEquals("one", list.getName(0));
		assertEquals(1, list.getValue(0).intValue());
		assertEquals(1, list.someValueOfName("One").intValue());

		list.add("oNE", 11);
		assertEquals(2, list.size());
		assertEquals("one", list.getName(1));
		assertEquals(11, list.getValue(1).intValue());
		assertEquals(11, list.someValueOfName("One").intValue());
	}

	@Test
	public void testCaseInsensitiveList() {
		NamedValueList<Integer> list = NamedValueList.createCaseInsensitiveList();
		list.add("ONE", 1);
		assertEquals(1, list.size());
		assertEquals("ONE", list.getName(0));
		assertEquals(1, list.getValue(0).intValue());
		int index = list.someValueOfName("One").intValue();
		assertEquals(1, index);

		list.add("oNE", 11);
		assertEquals(2, list.size());
		assertEquals("oNE", list.getName(1));
		assertEquals(11, list.getValue(1).intValue());
		assertTrue(index == 11 || index == 1);
	}

	@Test
	public void testCaseSensitiveList() {
		NamedValueList<Integer> list = NamedValueList.createCaseSensitiveList();
		list.add("ONE", 1);
		assertEquals(1, list.size());
		assertEquals("ONE", list.getName(0));
		assertEquals(1, list.getValue(0).intValue());
		assertEquals(1, list.someValueOfName("ONE").intValue());
		assertEquals(null, list.someValueOfName("One"));

		list.add("oNE", 11);
		assertEquals(2, list.size());
		assertEquals("oNE", list.getName(1));
		assertEquals(11, list.getValue(1).intValue());
		assertEquals(11, list.someValueOfName("oNE").intValue());
		assertEquals(1, list.someValueOfName("ONE").intValue());
	}
	
}
