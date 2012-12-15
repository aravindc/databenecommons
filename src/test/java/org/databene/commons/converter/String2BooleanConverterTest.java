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

import org.junit.Test;

/**
 * Tests the {@link String2BooleanConverter}.<br/><br/>
 * Created: 15.12.2012 13:15:34
 * @since 0.5.21
 * @author Volker Bergmann
 */
public class String2BooleanConverterTest extends AbstractConverterTest {
	
	private static final String2BooleanConverter converter = new String2BooleanConverter();
	
	public String2BooleanConverterTest() {
		super(String2BooleanConverter.class);
	}

	@Test
	public void testNull() {
		assertNull(converter.convert(null));
	}
	
	@Test
	public void testEmptyString() {
		assertNull(converter.convert(""));
	}
	
	@Test
	public void testTrueAndFalse() {
		assertTrue(converter.convert("true"));
		assertTrue(converter.convert("TRUE"));
		assertTrue(converter.convert("True"));
		assertFalse(converter.convert("false"));
		assertFalse(converter.convert("FALSE"));
		assertFalse(converter.convert("False"));
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void testIllegalStrings() {
		converter.convert("x");
	}
	
}
