/*
 * (c) Copyright 2008-2009 by Volker Bergmann. All rights reserved.
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

package org.databene.commons.mutator;

import java.util.HashMap;
import java.util.Map;

import org.databene.commons.Context;
import org.databene.commons.context.DefaultContext;
import org.databene.commons.mutator.AnyMutator;

import org.junit.Test;
import static junit.framework.Assert.*;

/**
 * Tests the {@link AnyMutator}.<br/><br/>
 * Created: 01.02.2008 08:06:11
 * @author Volker Bergmann
 */
public class AnyMutatorTest {

	@Test
    public void testBeanProperty() {
        A a = new A();
        assertEquals(1, a.x);
        AnyMutator.setValue(a, "x", 2);
        assertEquals(2, a.x);
    }
    
	@Test
    public void testBeanPropertyGraph() {
        A a = new A();
        a.setB(new B());
        assertEquals("alpha", a.b.y);
        AnyMutator.setValue(a, "b.y", "bravo");
        assertEquals("bravo", a.b.y);
    }
    
	@Test
    public void testMap() {
        Map<String, String> map = new HashMap<String, String>();
        assertNull(map.get("alpha"));
        AnyMutator.setValue(map, "alpha", "bravo");
        assertEquals("bravo", map.get("alpha"));
    }
    
	@Test
    public void testContext() {
        Context context = new DefaultContext();
        assertNull(context.get("alpha"));
        AnyMutator.setValue(context, "alpha", "bravo");
        assertEquals("bravo", context.get("alpha"));
    }
    
	@Test
    public void testGraph() {
        Context context = new DefaultContext();
        Map<String, Object> map = new HashMap<String, Object>();
        context.set("map", map);
        A a = new A();
        map.put("a", a);
        B b = new B();
        a.setB(b);
        AnyMutator.setValue(context, "map.a.b.y", "it works!");
        assertEquals("it works!", b.y);
    }
    
    // tested classes --------------------------------------------------------------------------------------------------
    
    public static class A {
        
        public int x = 1;
        
        public void setX(int x) { 
            this.x = x; 
        }
        
        public B b;

        public B getB() {
            return b;
        }

        public void setB(B b) {
            this.b = b;
        }
    }
    
    public static class B {
        
        public String y = "alpha";
        
        public void setY(String y) {
            this.y = y;
        }
    }
    
}
