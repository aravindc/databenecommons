/*
 * (c) Copyright 2011-2012 by Volker Bergmann. All rights reserved.
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

package org.databene.commons.converter;

import static org.junit.Assert.*;

import java.util.Arrays;

import org.databene.commons.ArrayFormat;
import org.databene.commons.ConversionException;
import org.junit.Test;

/**
 * Tests the ArrayConverter.<br/><br/>
 * Created: 20.07.2011 07:14:40
 * @since 0.5.9
 * @author Volker Bergmann
 */
public class ArrayConverterTest {
	
	private static final Integer[] INT_1_3 = new Integer[] {1, 3};
	private static final Integer[] INT_2_4 = new Integer[] {2, 4};
	private static final String[] STRING_1_3 = new String[] {"1", "3"};
	
	private final IncrementConverter inc = new IncrementConverter();
	
	@Test
	public void testConvertWith() {
		assertEqualArrays(INT_2_4, ArrayConverter.convertWith(inc, Integer.class, STRING_1_3));
	}
	
	@Test
	public void testArrayTypeConversion() {
		@SuppressWarnings("unchecked")
		ArrayConverter<String, Integer> converter = new ArrayConverter<String, Integer>(String.class, Integer.class);
		assertEqualArrays(INT_1_3, converter.convert(STRING_1_3));
	}
	
	@SuppressWarnings("unchecked")
	@Test
	public void testArrayElementConversion() {
		ArrayConverter<String, Integer> converter = new ArrayConverter<String, Integer>(String.class, Integer.class, inc, inc);
		assertEqualArrays(INT_2_4, converter.convert(STRING_1_3));
	}
	
	private void assertEqualArrays(Object[] array1, Object[] array2) {
		assertTrue("Expected [" + ArrayFormat.format(array1) + "] but was [" + ArrayFormat.format(array2) + "]", 
				Arrays.equals(array1, array2));
	}

	public class IncrementConverter extends UnsafeConverter<String, Integer> {
		protected IncrementConverter() {
			super(String.class, Integer.class);
		}

		@Override
		public Integer convert(String sourceValue) throws ConversionException {
			return Integer.parseInt(sourceValue) + 1;
		}
	}

}
