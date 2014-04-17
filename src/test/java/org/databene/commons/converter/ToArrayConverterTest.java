/*
 * (c) Copyright 2008-2014 by Volker Bergmann. All rights reserved.
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

package org.databene.commons.converter;

import java.util.ArrayList;

import org.databene.commons.ArrayUtil;
import org.databene.commons.Assert;
import org.databene.commons.CollectionUtil;

import org.junit.Test;

/**
 * Tests the {@link ToArrayConverter}.<br/><br/>
 * Created at 04.05.2008 08:00:15
 * @since 0.5.3
 * @author Volker Bergmann
 */
public class ToArrayConverterTest extends AbstractConverterTest {

	public ToArrayConverterTest() {
	    super(ToArrayConverter.class);
    }

	@Test
	public void testNull() {
		check(new Object[0], null, Object.class);
	}
	
	@Test
	public void testArray() {
		check(new Integer[0], new Integer[0], Integer.class);
		check(ArrayUtil.toArray(1), ArrayUtil.toArray(1), Integer.class);
	}

	@Test
	public void testList() {
		check(new Integer[0], new ArrayList<Integer>(), Integer.class);
		check(ArrayUtil.toArray(1), CollectionUtil.toList(1), Integer.class);
		check(ArrayUtil.toArray(1, 2), CollectionUtil.toList(1, 2), Integer.class);
	}

	@Test
	public void testString() {
		check(new byte[0], "");
		check(new byte[] { 'A', 'B' }, "AB");
	}

	@Test
	public void testSingleObject() {
		check(ArrayUtil.toArray("Alpha"), "Alpha", String.class);
	}
	
	// helpers ---------------------------------------------------------------------------------------------------------
	
	@SuppressWarnings("unchecked")
    private static <T> void check(T[] expected, Object source, Class<T> componentType) {
		Assert.equals(expected, (T[]) ToArrayConverter.convert(source, componentType));
	}

	private static <T> void check(byte[] expected, Object source) {
		Assert.equals(expected, (byte[]) ToArrayConverter.convert(source, byte.class));
	}
	
}
