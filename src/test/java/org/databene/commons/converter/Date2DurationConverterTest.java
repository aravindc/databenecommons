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

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.TimeZone;
import java.util.concurrent.Callable;

import org.databene.commons.ConversionException;
import org.databene.commons.Period;
import org.databene.commons.TimeUtil;

import org.junit.Test;
import static junit.framework.Assert.*;

/**
 * Tests the {@link Date2DurationConverter}.<br/>
 * <br/>
 * Created at 11.01.2009 06:44:42
 * @since 0.5.7
 * @author Volker Bergmann
 */

public class Date2DurationConverterTest extends AbstractConverterTest {
	
	public Date2DurationConverterTest() {
	    super(Date2DurationConverter.class);
    }

	@Test
	public void testUK() throws Exception {
		check(TimeUtil.GMT);
	}
	
	@Test
	public void testGermany() throws Exception {
		check(TimeUtil.CENTRAL_EUROPEAN_TIME);
	}
	
	@Test
	public void testSingapore() throws Exception {
		check(TimeUtil.SNGAPORE_TIME);
	}
	
	@Test
	public void testCalifornia() throws Exception {
		check(TimeUtil.PACIFIC_STANDARD_TIME);
	}
	
	private void check(TimeZone timeZone) throws Exception {
		TimeUtil.callInTimeZone(timeZone, new Callable<Object>() {
			public Object call() throws Exception {
				assertEquals(1L, convert("1970-01-01T00:00:00.001").longValue());
				assertEquals(0L, convert("0000-00-00T00:00:00.000").longValue());
				assertEquals(1L, convert("0000-00-00T00:00:00.001").longValue());
				assertEquals(Period.DAY.getMillis(), convert("0000-00-01T00:00:00.000").longValue());
				assertEquals(Period.DAY.getMillis() * 50, convert("0000-00-50T00:00:00.000").longValue());
				return null;
            }
		});
	}
	
	public static Long convert(String string) throws ConversionException, ParseException {
		return new Date2DurationConverter().convert(new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS").parse(string));
	}
	
}
