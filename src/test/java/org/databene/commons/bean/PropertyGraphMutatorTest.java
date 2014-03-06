/*
 * (c) Copyright 2007 by Volker Bergmann. All rights reserved.
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

import org.junit.Test;
import static junit.framework.Assert.*;
import org.databene.commons.UpdateFailedException;
import org.databene.commons.bean.ABean;
import org.databene.commons.bean.PropertyGraphMutator;

/**
 * Tests the {@link PropertyGraphMutator}.<br/><br/>
 * Created: 20.02.2007 08:52:49
 * @author Volker Bergmann
 */
public class PropertyGraphMutatorTest {

	@Test
    public void testLocalProperty() throws UpdateFailedException {
        PropertyGraphMutator aNameMutator = new PropertyGraphMutator(ABean.class, "name", true, false);
        ABean a = new ABean();
        aNameMutator.setValue(a, "aName");
        assertEquals("aName", a.name);
        aNameMutator.setValue(a, null);
        assertEquals(null, a.name);
    }

	@Test
    public void testNavigatedProperty() throws UpdateFailedException {
        PropertyGraphMutator bNameMutator = new PropertyGraphMutator(ABean.class, "b.name", true, false);
        ABean a = new ABean();
        bNameMutator.setValue(a, "bName");
        assertEquals("bName", a.b.name);
        bNameMutator.setValue(a, null);
        assertEquals(null, a.b.name);
    }

	@Test
    public void testNavigatedGraph() throws UpdateFailedException {
        PropertyGraphMutator bNameMutator = new PropertyGraphMutator(ABean.class, "b.c.name", true, false);
        ABean a = new ABean();
        bNameMutator.setValue(a, "cName");
        assertEquals("cName", a.b.c.name);
        bNameMutator.setValue(a, null);
        assertEquals(null, a.b.c.name);
    }

}
