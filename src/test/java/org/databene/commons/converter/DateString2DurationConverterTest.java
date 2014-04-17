/*
 * (c) Copyright 2009-2014 by Volker Bergmann. All rights reserved.
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

import java.util.TimeZone;

import org.databene.commons.Period;

import org.junit.Test;
import static junit.framework.Assert.*;

/**
 * Tests the {@link DateString2DurationConverter}.<br/>
 * <br/>
 * Created at 05.01.2009 18:39:46
 * @since 0.4.7
 * @author Volker Bergmann
 */

public class DateString2DurationConverterTest extends AbstractConverterTest {
	
	public DateString2DurationConverterTest() {
	    super(DateString2DurationConverter.class);
    }

	@Test
	public void testSimple() {
		assertEquals(1L, convert("1970-01-01T00:00:00.001"));
		assertEquals(1L, convert("0000-00-00T00:00:00.001"));
		assertEquals(Period.DAY.getMillis(), convert("0000-00-01"));
		assertEquals(Period.DAY.getMillis() * 50, convert("0000-00-50"));
	}

	@Test
	public void testSG() throws Exception {
		TimeZone timeZone = TimeZone.getDefault();
		try {
			TimeZone.setDefault(TimeZone.getTimeZone("Asia/Singapore"));
			assertEquals(1L, convert("1970-01-01T00:00:00.001"));
			assertEquals(1L, convert("0000-00-00T00:00:00.001"));
			assertEquals(Period.DAY.getMillis(), convert("0000-00-01"));
			assertEquals(Period.DAY.getMillis() * 50, convert("0000-00-50"));
		} finally {
			TimeZone.setDefault(timeZone);
		}
	}

	private static long convert(String string) {
		return new DateString2DurationConverter().convert(string);
	}
	
}
