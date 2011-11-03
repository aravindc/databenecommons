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
import org.databene.commons.ConfigurationError;
import org.databene.commons.UpdateFailedException;
import org.databene.commons.bean.PropertyGraphMutator;
import org.databene.commons.bean.PropertyMutatorFactory;
import org.databene.commons.bean.TypedPropertyMutator;
import org.databene.commons.bean.UntypedPropertyMutator;
import org.junit.Test;

/**
 * Tests the {@link PropertyMutatorFactory}.<br/><br/>
 * Created: 20.02.2007 08:52:49
 * @author Volker Bergmann
 */
public class PropertyMutatorFactoryTest {

	@Test
    public void testSimpleProperty() {
        assertEquals(TypedPropertyMutator.class, PropertyMutatorFactory.getPropertyMutator(ABean.class, "name", true).getClass());
        assertEquals(TypedPropertyMutator.class, PropertyMutatorFactory.getPropertyMutator(ABean.class, "doesntExsist", false).getClass());
        try {
            PropertyMutatorFactory.getPropertyMutator(ABean.class, "doesntExsist");
            fail("ConfigurationError expected");
        } catch (ConfigurationError e) {
            // this is the desired behaviour
        }
        assertEquals(UntypedPropertyMutator.class, PropertyMutatorFactory.getPropertyMutator("name").getClass());
    }

	@Test
    public void testNavigatedProperty() throws UpdateFailedException {
        assertEquals(PropertyGraphMutator.class, PropertyMutatorFactory.getPropertyMutator(ABean.class, "b.name").getClass());
        assertEquals(PropertyGraphMutator.class, PropertyMutatorFactory.getPropertyMutator("b.name").getClass());
        assertEquals(PropertyGraphMutator.class, PropertyMutatorFactory.getPropertyMutator(ABean.class, "doesnt.exist", false).getClass());
        try {
            PropertyMutatorFactory.getPropertyMutator(ABean.class, "doesnt.exist", true);
            fail("ConfigurationError expected");
        } catch(ConfigurationError e) {
            // this is the desired behaviour
        }
    }

}
