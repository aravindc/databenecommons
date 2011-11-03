/*
 * (c) Copyright 2010-2011 by Volker Bergmann. All rights reserved.
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

import java.sql.Timestamp;

import org.databene.commons.TimeUtil;
import org.junit.Test;

/**
 * Tests the {@link TimestampFormatter}.<br/><br/>
 * Created: 18.02.2010 17:54:24
 * @since 0.5.0
 * @author Volker Bergmann
 */
public class TimestampFormatterTest extends AbstractConverterTest {
	
	Timestamp timestamp = TimeUtil.timestamp(1971, 1, 3, 13, 14, 15, 123456789);

	public TimestampFormatterTest() {
		super(TimestampFormatter.class);
	}

	@Test
	public void testDefaultFormat() {
		assertEquals("1971-02-03T13:14:15.123456789", new TimestampFormatter().format(timestamp));
	}

	@Test
	public void testMillisFormat() {
		assertEquals("1971-02-03 13:14:15.123", new TimestampFormatter("yyyy-MM-dd HH:mm:ss.SSS").format(timestamp));
	}
	
	@Test
	public void testCentisFormat() {
		assertEquals("1971-02-03 13:14:15.123", new TimestampFormatter("yyyy-MM-dd HH:mm:ss.SSS").format(timestamp));
	}
	
	@Test
	public void testNanosFormat() {
		assertEquals("1971-02-03 13:14:15.123456789", new TimestampFormatter("yyyy-MM-dd HH:mm:ss.SSSSSSSSS").format(timestamp));
	}
	
	@Test
	public void testSecondsFormat() {
		assertEquals("1971-02-03 13:14:15", new TimestampFormatter("yyyy-MM-dd HH:mm:ss").format(timestamp));
	}
	
	@Test
	public void testNull() {
		assertEquals(null, new TimestampFormatter().format(null));
	}
	
}
