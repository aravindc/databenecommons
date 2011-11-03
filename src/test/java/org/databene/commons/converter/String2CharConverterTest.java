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

import org.junit.Test;

/**
 * Tests the {@link String2CharConverter}.<br/><br/>
 * Created: 29.07.2010 17:24:32
 * @since 0.6.3
 * @author Volker Bergmann
 */
public class String2CharConverterTest extends AbstractConverterTest {

	private static final String2CharConverter CONVERTER = new String2CharConverter();

	public String2CharConverterTest() {
		super(String2CharConverter.class);
	}

	@Test
	public void testTypes() {
		assertEquals(String.class, CONVERTER.getSourceType());
		assertEquals(Character.class, CONVERTER.getTargetType());
	}
	
	@Test
	public void testStandardConversions() {
		assertEquals('A', CONVERTER.convert("A").charValue());
		assertEquals('1', CONVERTER.convert("1").charValue());
	}
	
	@Test
	public void testNullConversion() {
		assertNull(CONVERTER.convert(null));
	}
	
}
