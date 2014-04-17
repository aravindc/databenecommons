/*
 * (c) Copyright 2011-2014 by Volker Bergmann. All rights reserved.
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

import org.databene.commons.Converter;
import org.junit.Test;

/**
 * Tests the {@link Number2CharConverter}.<br/><br/>
 * Created: 19.01.2011 15:35:12
 * @since 0.5.5
 * @author Volker Bergmann
 */
public class Number2CharConverterTest extends AbstractConverterTest {
	
	public Number2CharConverterTest() {
		super(Number2CharConverter.class);
	}

	@Test
	public void testNull() {
		Number2CharConverter converter = new Number2CharConverter();
		assertEquals(null, converter.convert(null));
	}

	@Test
	public void testInstance() {
		checkNumberTypes(new Number2CharConverter());
	}

	@Test
	public void testConverterManagerIntegration() {
		Converter<Number, Character> converter = ConverterManager.getInstance().createConverter(Number.class, Character.class);
		checkNumberTypes(converter);
	}

	private static void checkNumberTypes(Converter<Number, Character> converter) {
		assertEquals('A', (char) converter.convert((byte) 65));
		assertEquals('A', (char) converter.convert(65));
		assertEquals('A', (char) converter.convert((short) 65));
		assertEquals('A', (char) converter.convert((long) 65));
	}

}
