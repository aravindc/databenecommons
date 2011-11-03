/*
 * (c) Copyright 2011 by Volker Bergmann. All rights reserved.
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
 * Tests the {@link EscapingConverter}.<br/><br/>
 * Created: 19.01.2011 22:11:58
 * @since 0.5.5
 * @author Volker Bergmann
 */
public class EscapingConverterTest extends AbstractConverterTest {

	public EscapingConverterTest() {
		super(EscapingConverter.class);
	}

	@Test
	public void testNull() {
		Converter<String, String> converter = new EscapingConverter();
		assertNull(converter.convert(null));
	}
	
	@Test
	public void testInstance() {
		Converter<String, String> converter = new EscapingConverter();
		assertEquals("", converter.convert(""));
		assertEquals("ABC", converter.convert("ABC"));
		assertEquals("\\t", converter.convert("\t"));
		assertEquals("\\f", converter.convert("\f"));
	}
	
}
