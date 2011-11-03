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

import org.junit.Test;

/**
 * Tests the {@link SubstringExtractor}.<br/><br/>
 * Created: 26.02.2010 11:05:31
 * @since 0.5.0
 * @author Volker Bergmann
 */
public class SubstringExtractorTest extends AbstractConverterTest {

	public SubstringExtractorTest() {
	    super(SubstringExtractor.class);
    }

	@Test
	public void testDefault() {
		assertEquals("ABC", new SubstringExtractor().convert("ABC"));
	}
	
	@Test
	public void testWithoutTo() {
		assertEquals("ABC", new SubstringExtractor(0).convert("ABC"));
		assertEquals("BC", new SubstringExtractor(1).convert("ABC"));
		assertEquals("BC", new SubstringExtractor(-2).convert("ABC"));
		assertEquals("", new SubstringExtractor(3).convert("ABC"));
	}
	
	@Test
	public void testWithTo() {
		assertEquals("ABC", new SubstringExtractor(0, 3).convert("ABC"));
		assertEquals("", new SubstringExtractor(0, 0).convert("ABC"));
		assertEquals("", new SubstringExtractor(3, 3).convert("ABC"));
		assertEquals("AB", new SubstringExtractor(0, 2).convert("ABC"));
		assertEquals("B", new SubstringExtractor(1, 2).convert("ABC"));
		assertEquals("B", new SubstringExtractor(-2, 2).convert("ABC"));
		assertEquals("C", new SubstringExtractor(-1, 3).convert("ABC"));
		assertEquals("AB", new SubstringExtractor(-3, -1).convert("ABC"));
	}
	
}
