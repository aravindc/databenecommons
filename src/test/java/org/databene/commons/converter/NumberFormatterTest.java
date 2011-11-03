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
 * Tests the {@link NumberParser}.<br/>
 * <br/>
 * Created at 22.03.2009 08:02:42
 * @since 0.4.9
 * @author Volker Bergmann
 */

public class NumberFormatterTest extends AbstractConverterTest {

	public NumberFormatterTest() {
	    super(NumberFormatter.class);
    }

	@Test
	public void testEmpty() {
		NumberFormatter converter = new NumberFormatter();
		assertEquals("", converter.convert(null));
	}
	
	@Test
	public void testIntegralNumber() {
		NumberFormatter converter = new NumberFormatter();
		assertEquals("-1", converter.convert(-1));
		assertEquals("1000", converter.convert(1000));
	}
	
	@Test
	public void testConvert_US() {
		LocaleUtil.runInLocale(Locale.US, new Runnable() {
			public void run() {
				checkConversions();
			}
		});
	}

	@Test
	public void testConvert_DE() {
		LocaleUtil.runInLocale(Locale.GERMANY, new Runnable() {
			public void run() {
				checkConversions();
			}
		});
	}

	void checkConversions() {
        NumberFormatter converter = new NumberFormatter();
		// default
		assertEquals("0", converter.convert(0.));
		assertEquals("1000", converter.convert(1000.));
		// pattern
		converter.setPattern("0.00");
		assertEquals("0.00", converter.convert(0.));
		// decimal separator
		converter.setDecimalSeparator(',');
		assertEquals("0,00", converter.convert(0.));
		// grouping pattern
		converter.setPattern("#,##0");
		assertEquals("1,000", converter.convert(1000.));
		// decimal and grouping separator
		converter.setPattern("#,##0.00");
		converter.setGroupingSeparator('.');
		assertEquals("1.000,00", converter.convert(1000.));
    }

}
