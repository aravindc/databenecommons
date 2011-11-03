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

import java.sql.Time;
import java.text.SimpleDateFormat;
import java.util.Set;

import org.databene.commons.ConversionException;
import org.databene.commons.converter.AnyConverter;
import org.databene.commons.converter.JavaType;

/**
 * Tests the {@link AnyConverter}.<br/><br/>
 * Created: 29.09.2006 12:12:22<br/>
 * @author Volker Bergmann
 */
public class AnyConverterTest extends AbstractConverterTest {

    public AnyConverterTest() {
	    super(AnyConverter.class);
    }

	public static final double DELTA = 0.001;

	@Test
    public void testFromNullConversion() throws ConversionException {
        Set<Class<? extends Number>> classes = JavaType.getNumberTypes();
        for (Class<? extends Number> dstType : classes) {
            Number x = AnyConverter.convert(null, dstType);
            assertNull(x);
        }
    }

	@Test
    public void testFromIntConversion() throws ConversionException {
        Set<Class<? extends Number>> classes = JavaType.getNumberTypes();
        for (Class<? extends Number> dstType : classes) {
            Number x = AnyConverter.convert(1, dstType);
            assertEquals(JavaType.getWrapperClass(dstType), x.getClass());
            assertEquals(1., x.doubleValue(), DELTA);
        }
    }

	@Test
    public void testAnyConversion() throws ConversionException {
        Set<Class<? extends Number>> classes = JavaType.getNumberTypes();
        for (Class<? extends Number> srcType : classes) {
            for (Class<? extends Number> dstType : classes) {
                Number s = AnyConverter.convert(1, srcType);
                Number t = AnyConverter.convert(s, dstType);
                assertEquals(JavaType.getWrapperClass(dstType), t.getClass());
                assertEquals(1., t.doubleValue(), DELTA);
            }
        }
    }

	@Test
    public void testToStringConversion() throws ConversionException {
        assertEquals("true",  AnyConverter.convert(Boolean.TRUE,  String.class));
        assertEquals("false", AnyConverter.convert(Boolean.FALSE, String.class));
        assertEquals("1", AnyConverter.convert(1, String.class));
        assertEquals("0", AnyConverter.convert(0, String.class));
        assertEquals("-1", AnyConverter.convert(-1, String.class));
        assertEquals(null, AnyConverter.convert(null, String.class));
    }

	@Test
    public void testIdConversion() throws ConversionException {
        assertEquals(true, (boolean) AnyConverter.convert(true, boolean.class));
        assertEquals("text", AnyConverter.convert("text", String.class));
        assertEquals(1, (int) AnyConverter.convert(1, int.class));
    }

	@Test
    public void testFromStringConversion() throws Exception {
        assertEquals(true, (boolean) AnyConverter.convert("true", Boolean.class));
        assertEquals(true, (boolean) AnyConverter.convert("true", boolean.class));
        assertEquals(1, (int) AnyConverter.convert("1", Integer.class));
        assertEquals(1, (int) AnyConverter.convert("1", int.class));
        assertEquals(new SimpleDateFormat("S").parse("1"), AnyConverter.convert("00:00:00.001", Time.class));
    }
    
	@Test
    public void testStringToCharConversion() {
    	assertEquals('1', (char) AnyConverter.convert("1", char.class));
    }
    
	@Test
    public void testBooleanConversion() {
    	assertEquals(0, (int) AnyConverter.convert(false, int.class));
    	assertEquals(1, (int) AnyConverter.convert(true, int.class));
    	assertEquals(1, (int) AnyConverter.convert(Boolean.TRUE, int.class));
    	assertEquals(1L, (long) AnyConverter.convert(Boolean.TRUE, Long.class));
    }
	
}
