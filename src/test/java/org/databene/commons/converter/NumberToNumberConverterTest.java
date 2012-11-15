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

package org.databene.commons.converter;

import static org.junit.Assert.*;

import java.math.BigDecimal;
import java.math.BigInteger;

import org.junit.Test;

/**
 * Tests the {@link NumberToNumberConverter}.<br/><br/>
 * Created: 15.11.2012 10:34:24
 * @since 0.5.20
 * @author Volker Bergmann
 */
public class NumberToNumberConverterTest {

	@Test
	public void testStaticConversion() {
		checkStaticConversion(5L, Long.class);
		checkStaticConversion(5L, long.class);
		checkStaticConversion(5, Integer.class);
		checkStaticConversion(5, int.class);
		checkStaticConversion((short) 5, Short.class);
		checkStaticConversion((short) 5, short.class);
		checkStaticConversion((byte) 5, Byte.class);
		checkStaticConversion((byte) 5, byte.class);
		checkStaticConversion((double) 5, Double.class);
		checkStaticConversion((double) 5, double.class);
		checkStaticConversion((float) 5, Float.class);
		checkStaticConversion((float) 5, float.class);
		checkStaticConversion(new BigInteger("5"), BigInteger.class);
		checkStaticConversion(new BigDecimal("5.0"), BigDecimal.class);
	}

	@Test
	public void testInstanceConversion() {
		checkInstanceConversion(5L, Long.class);
		checkInstanceConversion(5L, long.class);
		checkInstanceConversion(5, Integer.class);
		checkInstanceConversion(5, int.class);
		checkInstanceConversion((short) 5, Short.class);
		checkInstanceConversion((short) 5, short.class);
		checkInstanceConversion((byte) 5, Byte.class);
		checkInstanceConversion((byte) 5, byte.class);
		checkInstanceConversion((double) 5, Double.class);
		checkInstanceConversion((double) 5, double.class);
		checkInstanceConversion((float) 5, Float.class);
		checkInstanceConversion((float) 5, float.class);
		checkInstanceConversion(new BigInteger("5"), BigInteger.class);
		checkInstanceConversion(new BigDecimal("5.0"), BigDecimal.class);
	}

	private void checkStaticConversion(Number expectedResult, Class<? extends Number> targetType) {
		Number[] sourceValues = new Number[] { (long) 5, 5, (short) 5, (byte) 5, (double) 5, (float) 5, new BigInteger("5"), new BigDecimal("5")};
		for (Number sourceValue : sourceValues)
			assertEquals(expectedResult, NumberToNumberConverter.convert(sourceValue, targetType));
	}
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	private void checkInstanceConversion(Number expectedResult, Class<? extends Number> targetType) {
		Number[] sourceValues = new Number[] { (long) 5, 5, (short) 5, (byte) 5, (double) 5, (float) 5, new BigInteger("5"), new BigDecimal("5")};
		for (Number sourceValue : sourceValues)
			assertEquals(expectedResult, new NumberToNumberConverter(sourceValue.getClass(), targetType).convert(sourceValue));
	}
	
}
