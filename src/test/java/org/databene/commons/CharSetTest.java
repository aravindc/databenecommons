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

package org.databene.commons;

import java.util.Locale;

import org.junit.Test;
import static junit.framework.Assert.*;

/**
 * Tests the {@link CharSet}.<br/><br/>
 * Created: 21.06.2007 08:28:50
 * @author Volker Bergmann
 */
public class CharSetTest {

	@Test
    public void testDefaultConstructor() {
        CharSet set = new CharSet();
        assertEquals("Set is expected to be empty after default construction.", 0, set.size());
    }
    
	@Test
    public void testGerman() {
        CharSet set = new CharSet(Locale.GERMAN);
        assertEquals("Set is expected to be empty after construction with Locale.", 0, set.size());
        set.addWordChars();
        assertEquals(70, set.size());
        assertTrue(set.contains('a'));
        assertTrue(set.contains('ä'));
        assertTrue(set.contains('_'));
        assertTrue(set.contains('9'));
        set.removeDigits();
        assertEquals(60, set.size());
        assertFalse(set.contains('9'));
        set.removeAll();
        assertEquals(0, set.size());
    }
    
	@Test
    public void testEqualsAndHashCode() {
        CharSet sg = new CharSet(Locale.GERMAN);
        CharSet se = new CharSet(Locale.ENGLISH);
        assertTrue(sg.equals(se));
        assertEquals(sg.hashCode(), se.hashCode());
        sg.add('a');
        se.add('a');
        assertTrue(sg.equals(se));
        assertEquals(sg.hashCode(), se.hashCode());
        sg.add('ä');
        assertFalse(sg.equals(se));
        assertTrue(sg.hashCode() != se.hashCode());
    }
	
}
