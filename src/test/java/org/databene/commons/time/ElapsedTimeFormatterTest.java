/*
 * (c) Copyright 2010 by Volker Bergmann. All rights reserved.
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

package org.databene.commons.time;

import static org.junit.Assert.*;

import java.util.Locale;

import org.junit.Test;

/**
 * Tests the {@link ElapsedTimeFormatter}.<br/><br/>
 * Created: 14.12.2010 13:50:35
 * @since 0.5.5
 * @author Volker Bergmann
 */
public class ElapsedTimeFormatterTest {

	@Test
	public void testGerman() {
		ElapsedTimeFormatter format = new ElapsedTimeFormatter(Locale.GERMAN, " ");
		assertEquals("123 ms", format.convert(123L));
		assertEquals("1,2 s",  format.convert(1234L));
		assertEquals("1,3 s",  format.convert(1256L));
		assertEquals("3 s",    format.convert(2999L));
		assertEquals("12 s",   format.convert(12345L));
		assertEquals("2,1 min",  format.convert(123456L));
		assertEquals("3 min",    format.convert(180000L));
		assertEquals("3,4 h",  format.convert(12345678L));
		assertEquals("1,4 d",  format.convert(123456789L));
	}
	
	@Test
	public void testUS() {
		ElapsedTimeFormatter format = new ElapsedTimeFormatter(Locale.US, " ");
		assertEquals("123 ms", format.convert(123L));
		assertEquals("1.2 s",  format.convert(1234L));
	}
	
	@Test
	public void testHtml() {
		ElapsedTimeFormatter format = new ElapsedTimeFormatter(Locale.US, "&nbsp;");
		assertEquals("123&nbsp;ms", format.convert(123L));
		assertEquals("1.2&nbsp;s",  format.convert(1234L));
	}
	
}
