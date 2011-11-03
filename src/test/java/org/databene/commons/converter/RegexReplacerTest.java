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
 * Tests the {@link RegexReplacer}.<br/><br/>
 * Created: 22.02.2010 07:18:56
 * @since 0.5.0
 * @author Volker Bergmann
 */
public class RegexReplacerTest extends AbstractConverterTest {

	public RegexReplacerTest() {
	    super(RegexReplacer.class);
    }

	@Test
	public void testConvertOneArg() {
		RegexReplacer replacer = new RegexReplacer("\\d", "x");
		assertEquals("AxBxCxD", replacer.convert("A1B2C3D"));
	}
	
	@Test
	public void testConvertTwoArg() {
		RegexReplacer replacer = new RegexReplacer("\\d", null);
		assertEquals("AxBxCxD", replacer.convert("A1B2C3D", "x"));
	}
	
	@Test
	public void testConvertStatic() {
		assertEquals("AxBxCxD", RegexReplacer.convert("A1B2C3D", "\\d", "x"));
	}
	
}
