/*
 * (c) Copyright 2007-2009 by Volker Bergmann. All rights reserved.
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

package org.databene.commons.validator;

import org.databene.commons.validator.StringLengthValidator;

import org.junit.Test;
import static junit.framework.Assert.*;

/**
 * Tests the StringLengthValidator.<br/><br/>
 * Created: 29.09.2006 16:32:44
 * @since 0.1
 * @author Volker Bergmann
 */
public class StringLengthValidatorTest {

	@Test
    public void testUnlimited() {
        StringLengthValidator validator = new StringLengthValidator();
        assertFalse(validator.valid(null));
        assertTrue(new StringLengthValidator(0, null, true).valid(null));
        assertTrue(validator.valid(""));
        assertTrue(validator.valid("abc"));
        assertTrue(validator.valid("abcdefghijklmnopqrstuvwxyz1234567890!"));
    }

	@Test
    public void testMaxLength10() {
        StringLengthValidator validator = new StringLengthValidator(10);
        assertFalse(validator.valid(null));
        assertTrue(validator.valid(""));
        assertTrue(validator.valid("abc"));
        assertFalse(validator.valid("abcdefghijklmnopqrstuvwxyz1234567890!"));
    }

	@Test
    public void testMinLength5MaxLength10() {
        StringLengthValidator validator = new StringLengthValidator(5, 10);
        assertFalse(validator.valid(null));
        assertFalse(validator.valid(""));
        assertFalse(validator.valid("abcd"));
        assertTrue(validator.valid("abcde"));
        assertTrue(validator.valid("abcdefghij"));
        assertFalse(validator.valid("abcdefghijk"));
        assertFalse(validator.valid("abcdefghijklmnopqrstuvwxyz1234567890!"));
    }

	@Test
    public void testMinLength5() {
        StringLengthValidator validator = new StringLengthValidator(5, null);
        assertFalse(validator.valid(null));
        assertFalse(validator.valid(""));
        assertFalse(validator.valid("abcd"));
        assertTrue(validator.valid("abcde"));
        assertTrue(validator.valid("abcdefghij"));
        assertTrue(validator.valid("abcdefghijk"));
        assertTrue(validator.valid("abcdefghijklmnopqrstuvwxyz1234567890!"));
    }
	
}
