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

package org.databene.commons.bean;

import static junit.framework.Assert.*;

import org.databene.commons.UpdateFailedException;
import org.databene.commons.bean.UntypedPropertyMutator;
import org.junit.Test;

/**
 * Tests the {@link UntypedPropertyMutator}.<br/><br/>
 * Created: 20.02.2007 08:52:49
 * @since 0.2
 * @author Volker Bergmann
 */
public class UntypedPropertyMutatorTest {

	@Test
    public void testLocalProperty() throws UpdateFailedException {
        UntypedPropertyMutator aNameMutator = new UntypedPropertyMutator("name", true);
        ABean a = new ABean();
        aNameMutator.setValue(a, "aName");
        assertEquals("aName", a.name);
        aNameMutator.setValue(a, null);
        assertEquals(null, a.name);
    }
    
	@Test
    public void testNonStrict() {
	    UntypedPropertyMutator mutator = new UntypedPropertyMutator("bla", false);
		mutator.setValue(null, null);
		mutator.setValue(new ABean(), null);
	    UntypedPropertyMutator readOnly = new UntypedPropertyMutator("readOnly", false);
	    readOnly.setValue(new ABean(), "bla");
    }

	@Test
    public void testStrictSetOnNull() {
    	try {
	    	UntypedPropertyMutator mutator = new UntypedPropertyMutator("bla", true);
	    	mutator.setValue(null, null);
	    	fail(UpdateFailedException.class.getSimpleName() + " expected");
    	} catch (UpdateFailedException e) {
    		// expected
    	}
    }

	@Test
    public void testStrictMissingProperty() {
    	try {
	    	UntypedPropertyMutator mutator = new UntypedPropertyMutator("bla", true);
	    	mutator.setValue(new ABean(), null);
	    	fail(UpdateFailedException.class.getSimpleName() + " expected");
    	} catch (UpdateFailedException e) {
    		// expected
    	}
    }

	@Test
    public void testStrictReadOnlyProperty() {
    	try {
	    	UntypedPropertyMutator mutator = new UntypedPropertyMutator("readOnly", true);
	    	mutator.setValue(new ABean(), null);
	    	fail(UpdateFailedException.class.getSimpleName() + " expected");
    	} catch (UpdateFailedException e) {
    		// expected
    	}
    }
	
}
