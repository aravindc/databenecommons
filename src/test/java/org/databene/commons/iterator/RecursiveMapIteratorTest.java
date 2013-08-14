/*
 * (c) Copyright 2013 by Volker Bergmann. All rights reserved.
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

package org.databene.commons.iterator;

import java.util.Map;

import org.databene.commons.CollectionUtil;
import org.databene.commons.OrderedMap;
import org.junit.Test;

/**
 * TODO Document class.<br/><br/>
 * Created: 04.08.2013 08:26:19
 * @since TODO version
 * @author Volker Bergmann
 */

public class RecursiveMapIteratorTest extends IteratorTestCase {
	
	@SuppressWarnings({ "cast", "unchecked" })
	@Test
	public void testPlain() {
		Map<Integer, Integer> map = (Map<Integer, Integer>) CollectionUtil.buildMap(1, 2, 3, 4);
		RecursiveMapValueIterator<Integer> iterator = new RecursiveMapValueIterator<Integer>(map);
		expectNextElements(iterator, 2, 4);
	}
	
	@SuppressWarnings({ "cast", "unchecked" })
	@Test
	public void testPlainRecursion() {
		Map<Integer, Integer> map1 = (Map<Integer, Integer>) CollectionUtil.buildMap(1, 2, 3, 4);
		Map<Integer, Integer> map2 = (Map<Integer, Integer>) CollectionUtil.buildMap(5, 6, 7, 8);
		Map<Integer, ?> map = (Map<Integer, ?>) CollectionUtil.buildMap(11, map1, 13, map2);
		RecursiveMapValueIterator<Integer> iterator = new RecursiveMapValueIterator<Integer>(map);
		expectNextElements(iterator, 2, 4, 6, 8).withNoNext();
	}
	
	@Test
	public void testMixedRecursion() {
		Map<Integer, ?> map1 = buildMap(1, 2, 3, 4);
		Map<Integer, ?> map2 = buildMap(5, 6, 7, 8);
		Map<Integer, ?> map = buildMap(9, 10, 11, map1, 13, 14, 15, map2, 17, 18);
		RecursiveMapValueIterator<Integer> iterator = new RecursiveMapValueIterator<Integer>(map);
		expectNextElements(iterator, 10, 2, 4, 14, 6, 8, 18).withNoNext();
	}
	
	private static OrderedMap<Integer, ?> buildMap(Object... keyValuePairs) {
		OrderedMap<Integer, Object> result = new OrderedMap<Integer, Object>();
		for (int i = 0; i < keyValuePairs.length; i += 2) {
			Integer key = (Integer) keyValuePairs[i];
			Object value = keyValuePairs[i + 1];
			result.put(key, value);
		}
		return result;
	}
	
}
