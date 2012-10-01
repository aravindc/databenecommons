/*
 * (c) Copyright 2010-2012 by Volker Bergmann. All rights reserved.
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

import static junit.framework.Assert.assertEquals;

import java.util.Locale;

import org.databene.commons.LocaleUtil;
import org.junit.Test;

/**
 * Tests the {@link NumberParser}.<br/><br/>
 * Created: 26.02.2010 08:52:12
 * @since 0.5.0
 * @author Volker Bergmann
 */
public class NumberParserTest extends AbstractConverterTest {

	public NumberParserTest() {
	    super(NumberParser.class);
    }

	@Test
	public void testEmpty() {
		NumberParser converter = new NumberParser();
		assertEquals(null, converter.convert(""));
		assertEquals(null, converter.convert(null));
	}
	
	@Test
	public void testIntegral() {
		NumberParser converter = new NumberParser();
		assertEquals(0, converter.convert("0").intValue());
		assertEquals(1000, converter.convert("1000").intValue());
	}
	
	@Test
	public void testConvert_US() {
		LocaleUtil.runInLocale(Locale.US, new Runnable() {
			@Override
			public void run() {
				checkConversion();
			}
		});
	}
	
	@Test
	public void testConvert_DE() {
		LocaleUtil.runInLocale(Locale.GERMANY, new Runnable() {
			@Override
			public void run() {
				checkConversion();
			}
		});
	}
	
	void checkConversion() {
        NumberParser converter = new NumberParser();
		// default
		assertEquals(-1., converter.convert("-1.0").doubleValue());
		assertEquals(0., converter.convert("0.0").doubleValue());
		assertEquals(1000., converter.convert("1000.0").doubleValue());
		// pattern
		converter.setPattern("0.00");
		assertEquals(0., converter.convert("0.00").doubleValue());
		// decimal separator
		converter.setDecimalSeparator(',');
		assertEquals(0., converter.convert("0,00").doubleValue());
		// grouping
		converter.setGroupingSeparator('.');
		converter.setPattern("#,##0");
		assertEquals(1000, converter.convert("1.000").intValue());
		// grouping and decimal separator
		converter.setPattern("#,##0.00");
		assertEquals(1000., converter.convert("1.000,00").doubleValue());
    }

}
