/*
 * (c) Copyright 2009-2011 by Volker Bergmann. All rights reserved.
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

import java.util.Locale;

import org.databene.commons.LocaleUtil;
import org.junit.Test;
import static junit.framework.Assert.*;

/**
 * Tests the {@link ToStringConverter}.<br/>
 * <br/>
 * Created at 15.03.2009 20:32:19
 * @since 0.5.8
 * @author Volker Bergmann
 */

public class ToStringConverterTest extends AbstractConverterTest {

	public ToStringConverterTest() {
	    super(ToStringConverter.class);
    }

	@Test
	public void testEmpty() {
		assertEquals("", new ToStringConverter().convert(null));
		assertEquals("", new ToStringConverter().convert(""));
    }
	
	@Test
	public void testInteger() {
		assertEquals(   "0", new ToStringConverter().convert(0));
		assertEquals( "-11", new ToStringConverter().convert(-11));
		assertEquals("1000", new ToStringConverter().convert(1000));
    }
	
	@Test
	public void testDecimal_US() {
		LocaleUtil.runInLocale(Locale.US, new Runnable() {
			public void run() {
				checkDecimalConversions();
            }
		});
	}
	
	@Test
	public void testDecimal_DE() {
		LocaleUtil.runInLocale(Locale.GERMANY, new Runnable() {
			public void run() {
				checkDecimalConversions();
            }
		});
	}
	
	void checkDecimalConversions() {
        ToStringConverter converter = new ToStringConverter();
		// trim trailing zeros on default configuration
		assertEquals("0", converter.convert(0.));
		assertEquals("9876543210", converter.convert(9876543210.));
		// default should be US
		assertEquals("0.5", converter.convert(0.5));
		assertEquals("1000.5", converter.convert(1000.5));
		// decimal pattern
		converter.setDecimalPattern("0.00");
		assertEquals("0.50", converter.convert(0.5));
		// decimal separator
		converter.setDecimalSeparator(',');
		assertEquals("0,50", converter.convert(0.5));
		// grouping
		converter.setDecimalPattern("#,##0");
		assertEquals("1,000", converter.convert(1000.5));
		// grouping and decimal
		converter.setDecimalPattern("#,##0.00");
		converter.setGroupingSeparator('.');
		assertEquals("1.000,50", converter.convert(1000.5));
    }
}
