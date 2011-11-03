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

import org.databene.commons.bean.PropertyAccessorFactory;

import org.junit.Test;
import static junit.framework.Assert.*;

/**
 * Tests the {@link PropertyAccessorFactory}.<br/><br/>
 * Created: 21.07.2007 15:03:40
 * @author Volker Bergmann
 */
public class PropertyAccessorFactoryTest {

	@SuppressWarnings("unchecked")
    @Test
    public void test() {
        ABean a = new ABean();
        a.name = "aName";
        a.b = new BBean();
        a.b.name = "bName";
        a.b.c = new CBean();
        a.b.c.name = "cName";
        // test simple properties
        assertEquals("aName", PropertyAccessorFactory.getAccessor("name").getValue(a));
        assertEquals(null, PropertyAccessorFactory.getAccessor("doesntExist", false).getValue(a));
        assertEquals(null, PropertyAccessorFactory.getAccessor("doesntExist", false).getValue(null));
        assertEquals("aName", PropertyAccessorFactory.getAccessor(ABean.class, "name").getValue(a));
        assertEquals(null, PropertyAccessorFactory.getAccessor(ABean.class, "doesntExist", false).getValue(a));
        assertEquals(null, PropertyAccessorFactory.getAccessor(ABean.class, "doesntExist", false).getValue(null));
        // test navigated properties
        assertEquals("bName", PropertyAccessorFactory.getAccessor("b.name").getValue(a));
        assertEquals(null, PropertyAccessorFactory.getAccessor("b.doesntExist", false).getValue(a));
        assertEquals(null, PropertyAccessorFactory.getAccessor("b.doesntExist", false).getValue(null));
        assertEquals("bName", PropertyAccessorFactory.getAccessor(ABean.class, "b.name").getValue(a));
        assertEquals(null, PropertyAccessorFactory.getAccessor(ABean.class, "b.doesntExist", false).getValue(a));
        assertEquals(null, PropertyAccessorFactory.getAccessor(ABean.class, "b.doesntExist", false).getValue(null));
        // test twofold navigated properties
        assertEquals("cName", PropertyAccessorFactory.getAccessor("b.c.name").getValue(a));
        assertEquals(null, PropertyAccessorFactory.getAccessor("b.c.doesntExist", false).getValue(a));
        assertEquals(null, PropertyAccessorFactory.getAccessor("b.c.doesntExist", false).getValue(null));
        assertEquals("cName", PropertyAccessorFactory.getAccessor(ABean.class, "b.c.name").getValue(a));
        assertEquals(null, PropertyAccessorFactory.getAccessor(ABean.class, "b.c.doesntExist", false).getValue(a));
        assertEquals(null, PropertyAccessorFactory.getAccessor(ABean.class, "b.c.doesntExist", false).getValue(null));
    }
    
}
