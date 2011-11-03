/*
 * (c) Copyright 2007-2011 by Volker Bergmann. All rights reserved.
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

import org.junit.Test;
import static junit.framework.Assert.*;

import java.util.Locale;

import org.databene.commons.ConversionException;
import org.databene.commons.converter.CaseConverter;

/**
 * Tests the CaseConverter.<br/><br/>
 * Created: 29.09.2006 15:50:03
 * @author Volker Bergmann
 */
public class CaseConverterTest extends AbstractConverterTest {

	public CaseConverterTest() {
	    super(CaseConverter.class);
    }

	@Test
    public void testToUpper() throws ConversionException {
        CaseConverter converter = new CaseConverter(true, Locale.ENGLISH);
        assertEquals("ABC,123", converter.convert("ABC,123"));
        assertEquals("ABC,123", converter.convert("abc,123"));
        assertEquals("", converter.convert(""));
        assertEquals(null, converter.convert(null));
    }

	@Test
    public void testToLower() throws ConversionException {
        CaseConverter converter = new CaseConverter(false, Locale.ENGLISH);
        assertEquals("abc,123", converter.convert("abc,123"));
        assertEquals("abc,123", converter.convert("ABC,123"));
        assertEquals("", converter.convert(""));
        assertEquals(null, converter.convert(null));
    }
	
}
