/*
 * (c) Copyright 2010-2014 by Volker Bergmann. All rights reserved.
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

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertNull;

import org.databene.SomeEnum;
import org.databene.commons.ConversionException;
import org.junit.Test;

/**
 * Tests the {@link Enum2StringConverter}.<br/><br/>
 * Created: 25.02.2010 23:55:44
 * @since 0.5.0
 * @author Volker Bergmann
 */
public class Enum2StringConverterTest extends AbstractConverterTest {

	public Enum2StringConverterTest() {
	    super(Enum2StringConverter.class);
    }

	@Test
    public void testNull() throws ConversionException {
        assertNull(Enum2StringConverter.convertToString(null));
    }

	@Test
    public void testNormal() throws ConversionException {
        for (SomeEnum instance : SomeEnum.values()) {
            check(instance);
        }
    }

    // private helpers -------------------------------------------------------------------------------------------------

    private static void check(SomeEnum instance) throws ConversionException {
    	Enum2StringConverter<SomeEnum> converter = new Enum2StringConverter<SomeEnum>(SomeEnum.class);
        assertEquals(instance.name(), converter.convert(instance));
    }
    
}
