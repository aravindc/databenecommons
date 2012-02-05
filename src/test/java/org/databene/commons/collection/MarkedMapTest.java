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

package org.databene.commons.collection;

import static org.junit.Assert.*;

import java.util.Map;

import org.databene.commons.CollectionUtil;
import org.junit.Test;

/**
 * Tests the {@link MarkedMap}.<br/><br/>
 * Created: 03.02.2012 16:57:31
 * @since 0.5.14
 * @author Volker Bergmann
 */
public class MarkedMapTest {
	
	@Test
	public void test() {
		Map<Integer, Integer> map = CollectionUtil.buildMap(1, 11);
		// Test bulk construction
		MarkedMap<Integer, Integer> mmap = new MarkedMap<Integer, Integer>(map);
		assertEquals(1, mmap.size());
		assertEquals(11, mmap.get(1).intValue());
		// test marking
		assertFalse(mmap.isMarked(1));
		mmap.mark(1);
		assertTrue(mmap.isMarked(1));
		// add element
		mmap.put(2, 22);
		assertFalse(mmap.isMarked(2));
		// check unmarkedEntries()
		Map<Integer, Integer> unmarkedEntries = mmap.unmarkedEntries();
		assertEquals(1, unmarkedEntries.size());
		assertEquals(2, unmarkedEntries.keySet().iterator().next().intValue());
		// check markedEntries()
		Map<Integer, Integer> markedEntries = mmap.markedEntries();
		assertEquals(1, markedEntries.size());
		assertEquals(1, markedEntries.keySet().iterator().next().intValue());
		// check toString()
		assertEquals("[1=11(+), 2=22(-)]", mmap.toString());
	}
	
}
