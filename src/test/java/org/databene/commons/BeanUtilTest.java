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

package org.databene.commons;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static junit.framework.Assert.*;

import java.util.*;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.io.PrintWriter;

/**
 * Tests the BeanUtil class.<br/><br/>
 * Created: 05.04.2007 07:51:00
 * @since 0.1
 * @author Volker Bergmann
 */
public class BeanUtilTest {

    // type info tests -------------------------------------------------------------------------------------------------

	@Test
	public void testCommonSuperType() {
		assertNull(BeanUtil.commonSuperType(CollectionUtil.toList()));
		assertEquals(Integer.class, BeanUtil.commonSuperType(CollectionUtil.toList(1, 2, 3)));
		assertEquals(Object.class, BeanUtil.commonSuperType(CollectionUtil.toList(new Object(), 3)));
	}
	
	@Test
	public void testCommonSubType() {
		assertNull(BeanUtil.commonSubType(CollectionUtil.toList()));
		assertEquals(Integer.class, BeanUtil.commonSubType(CollectionUtil.toList(1, 2, 3)));
		assertEquals(Integer.class, BeanUtil.commonSubType(CollectionUtil.toList(new Object(), 3)));
	}
	
	@Test
    public void testIsSimpleTypeByName() {
        assertTrue(BeanUtil.isSimpleType("int"));
        assertTrue(BeanUtil.isSimpleType("java.lang.Integer"));
        assertTrue(BeanUtil.isSimpleType("java.lang.String"));
        assertFalse(BeanUtil.isSimpleType("java.lang.StringBuffer"));
        assertFalse(BeanUtil.isSimpleType("org.databene.commons.BeanUtil"));
        assertFalse(BeanUtil.isSimpleType(null));
    }

	@Test
    public void testIsPrimitive() {
        assertTrue(BeanUtil.isPrimitiveType(int.class.getName()));
        assertFalse(BeanUtil.isPrimitiveType(Integer.class.getName()));
        assertTrue(BeanUtil.isPrimitiveType(char.class.getName()));
        assertFalse(BeanUtil.isPrimitiveType(Character.class.getName()));
        assertFalse(BeanUtil.isPrimitiveType(String.class.getName()));
        assertFalse(BeanUtil.isPrimitiveType(StringBuffer.class.getName()));
        assertFalse(BeanUtil.isPrimitiveType(BeanUtil.class.getName()));
        assertFalse(BeanUtil.isPrimitiveType(null));
    }

	@Test
    public void testIsPrimitiveNumber() {
        assertTrue(BeanUtil.isPrimitiveType(int.class.getName()));
        assertFalse(BeanUtil.isPrimitiveType(Integer.class.getName()));
        assertTrue(BeanUtil.isPrimitiveType(char.class.getName()));
        assertFalse(BeanUtil.isPrimitiveType(Character.class.getName()));
        assertFalse(BeanUtil.isPrimitiveType(String.class.getName()));
        assertFalse(BeanUtil.isPrimitiveType(StringBuffer.class.getName()));
        assertFalse(BeanUtil.isPrimitiveType(BeanUtil.class.getName()));
        assertFalse(BeanUtil.isPrimitiveType(null));
    }

	@Test
    public void testGetWrapper() {
        assertEquals(Integer.class, BeanUtil.getWrapper(int.class.getName()));
        assertEquals(Character.class, BeanUtil.getWrapper(char.class.getName()));
    }

	@Test
    public void testIsCollectionType() {
        assertTrue(BeanUtil.isCollectionType(Collection.class));
        assertTrue(BeanUtil.isCollectionType(List.class));
        assertTrue(BeanUtil.isCollectionType(ArrayList.class));
        assertTrue(BeanUtil.isCollectionType(Set.class));
        assertTrue(BeanUtil.isCollectionType(HashSet.class));
        assertFalse(BeanUtil.isCollectionType(Map.class));
    }

    // field tests -----------------------------------------------------------------------------------------------------

	@Test
    public void testGetAttributeValue() {
        P p = new P();
        assertEquals(1, BeanUtil.getAttributeValue(p, "val"));
    }

	@Test
    public void testSetAttribute() {
        P p = new P();
        BeanUtil.setAttributeValue(p, "val", 2);
        assertEquals(2, p.val);
    }

	@Test
    public void testGetStaticAttributeValue() {
        B.stat = "x";
        assertEquals("x", BeanUtil.getStaticAttributeValue(B.class, "stat"));
    }

	@Test
    public void testSetStaticAttribute() {
        BeanUtil.setStaticAttributeValue(B.class, "stat", "y");
        assertEquals("y", B.stat);
    }

	@Test
    public void testGetGenericTypes() throws NoSuchFieldException {
        Object o = new Object() {
            @SuppressWarnings("unused")
			public List<Integer> list;
        };
        Class<?> c = o.getClass();
        Field f = c.getField("list");
        assertTrue("Test for generic type failed", 
                Arrays.deepEquals(new Class[] { Integer.class }, BeanUtil.getGenericTypes(f)));
    }

    // instantiation tests ---------------------------------------------------------------------------------------------

	@Test
	public void testFindClasses() throws Exception {
		List<Class<?>> classes;
		// test directory in class path
		classes = BeanUtil.getClasses(BeanUtil.class.getPackage().getName());
		assertTrue(classes.contains(BeanUtil.class));
		assertTrue(classes.contains(TimeUtil.class));
		// test jar in class path
		classes = BeanUtil.getClasses(Logger.class.getPackage().getName());
		assertTrue(classes.contains(Logger.class));
		assertTrue(classes.contains(LoggerFactory.class));
	}
	
	@Test
    public void testForName() {
        Class<P> type = BeanUtil.forName("org.databene.commons.BeanUtilTest$P");
        assertEquals(P.class, type);
    }

	@Test
    public void testNewInstanceWithConstructorParams() {
        P p = BeanUtil.newInstance(P.class, null);
        assertEquals(1, p.val);
        p = BeanUtil.newInstance(P.class, new Object[] { 2 });
        assertEquals(2, p.val);
    }

	@Test
    public void testNewInstanceWithParamConversion() {
        P p = BeanUtil.newInstance(P.class, false, new Object[] { 2 });
        assertEquals(2, p.val);
        p = BeanUtil.newInstance(P.class, false, new Object[] { "2" });
        assertEquals(2, p.val);
    }

	@Test
    public void testNewInstanceFromClassName() {
        P p = (P) BeanUtil.newInstance(P.class.getName());
        assertEquals(1, p.val);
    }

	@Test
    public void testNewInstanceFromConstructor() throws SecurityException, NoSuchMethodException {
        Constructor<P> constructor = P.class.getDeclaredConstructor(int.class);
        P p = BeanUtil.newInstance(constructor, 1000);
        assertEquals(1000, p.val);
    }

    // method tests ----------------------------------------------------------------------------------------------------

	@Test
    public void testGetMethod() throws IllegalAccessException, InvocationTargetException {
        Method method = BeanUtil.getMethod(P.class, "getVal");
        P p = new P();
        assertEquals(1, method.invoke(p));
        method = BeanUtil.getMethod(P.class, "setVal", Integer.class);
        method.invoke(p, 2);
        assertEquals(2, p.val);
        try {
            BeanUtil.findMethod(P.class, "setBlaBla", Integer.class);
        } catch (ConfigurationError e) {
            // desired behavior
        }
    }

	@Test
    public void testFindMethod() throws IllegalAccessException, InvocationTargetException {
        Method method = BeanUtil.findMethod(P.class, "getVal");
        P p = new P();
        assertEquals(1, method.invoke(p));
        method = BeanUtil.findMethod(P.class, "setVal", Integer.class);
        method.invoke(p, 2);
        assertEquals(2, p.val);
        assertNull(BeanUtil.findMethod(P.class, "setBlaBla", Integer.class));
    }

	@Test
    public void testInvoke() {
        P p = new P();
        assertEquals(1, BeanUtil.invoke(p, "getVal"));
        BeanUtil.invoke(p, "setVal", 2);
        assertEquals(2, p.val);
    }

	@Test
    public void testInvokeStatic() {
        B.setStat("x");
        assertEquals("x", BeanUtil.invokeStatic(B.class, "getStat"));
        BeanUtil.invokeStatic(B.class, "setStat", "u");
        assertEquals("u", B.stat);
    }

	@Test
    public void testInvokeVarargs() {
        V v = new V();
        assertEquals(0, BeanUtil.invoke(v, "varargs1"));
        assertEquals(1, BeanUtil.invoke(false, v, "varargs1", 1));
        assertEquals(2, BeanUtil.invoke(false, v, "varargs1", 1, 2));
        assertEquals(-1, BeanUtil.invoke(false, v, "varargs2", 1));
        assertEquals(1, BeanUtil.invoke(false, v, "varargs2", 1, 1));
        assertEquals(2, BeanUtil.invoke(false, v, "varargs2", 1, 1, 2));
    }

	@Test
    public void testTypesMatch() {
		// no-arg method calls
        assertTrue(BeanUtil.typesMatch(new Class[] { }, new Class[] { }));
        assertFalse(BeanUtil.typesMatch(new Class[] { String.class }, new Class[] {  }));
        // Identical types
        assertTrue(BeanUtil.typesMatch(new Class[] { String.class }, new Class[] { String.class }));
        assertTrue(BeanUtil.typesMatch(new Class[] { C.class }, new Class[] { B.class }));
        // incompatible types
        assertFalse(BeanUtil.typesMatch(new Class[] { B.class }, new Class[] { C.class }));
        assertTrue(BeanUtil.typesMatch(new Class[] { A.class }, new Class[] { I.class }));
        // autoboxing
        assertTrue(BeanUtil.typesMatch(new Class[] { int.class }, new Class[] { Integer.class }));
        assertTrue(BeanUtil.typesMatch(new Class[] { Integer.class }, new Class[] { int.class }));
        // varargs
        assertTrue(BeanUtil.typesMatch(new Class[] { }, new Class[] { Integer[].class }));
        assertTrue(BeanUtil.typesMatch(new Class[] { Integer.class }, new Class[] { Integer[].class }));
        assertTrue(BeanUtil.typesMatch(new Class[] { Integer.class }, new Class[] { Integer.class, Integer[].class }));
        assertTrue(BeanUtil.typesMatch(new Class[] { Integer.class, Integer.class }, 
        		new Class[] { Integer.class, Integer[].class }));
    }

    // property tests --------------------------------------------------------------------------------------------------

	@Test
    public void testGetPropertyDescriptor() throws IllegalAccessException, InvocationTargetException {
        PropertyDescriptor desc = BeanUtil.getPropertyDescriptor(P.class, "val");
        assertEquals("val", desc.getName());
        P p = new P();
        desc.getWriteMethod().invoke(p, 2);
        assertEquals(2, p.val);
    }

	@Test
    public void testHasProperty() {
        assertTrue(BeanUtil.hasProperty(B.class, "val"));
        assertFalse(BeanUtil.hasProperty(B.class, "blaBla"));
    }

	@Test
    public void testReadMethodName() {
        assertEquals("getVal", BeanUtil.readMethodName("val", int.class));
        assertEquals("isValid", BeanUtil.readMethodName("valid", boolean.class));
        assertEquals("isValid", BeanUtil.readMethodName("valid", Boolean.class));
    }

	@Test
    public void testWriteMethodName() {
        assertEquals("setVal", BeanUtil.writeMethodName("val"));
        assertEquals("setValid", BeanUtil.writeMethodName("valid"));
    }

	@Test
    public void testGetPropertyDescriptors() {
        PropertyDescriptor[] descriptors = BeanUtil.getPropertyDescriptors(B.class);
        assertEquals(2, descriptors.length);
    }

	@Test
    public void testGetPropertyValue() {
        P p = new P();
        assertEquals(p.getVal(), BeanUtil.getPropertyValue(p, "val"));
    }

	@Test
    public void testSetPropertyValue() {
        P p = new P();
        BeanUtil.setPropertyValue(p, "val", 2);
        assertEquals(2, p.getVal());
    }

    // class tests -----------------------------------------------------------------------------------------------------

	@Test
    public void testPrintClassInfo() {
        BeanUtil.printClassInfo(B.class, new PrintWriter(System.out));
    }

	@Test
    public void testCheckJavaBean() {
        BeanUtil.checkJavaBean(B.class);
    }
    
	@Test
    public void testDeprecated() {
        assertFalse(BeanUtil.deprecated(Object.class));
        assertTrue(BeanUtil.deprecated(Dep.class));
    }
    
	@Test
    public void testEqualsIgnoreType() {
    	assertTrue(BeanUtil.equalsIgnoreType("1", 1));
    	assertTrue(BeanUtil.equalsIgnoreType(1., 1));
    	assertFalse(BeanUtil.equalsIgnoreType(1., 2));
    }

    // Test classes ----------------------------------------------------------------------------------------------------

    public static interface I {
    }

    public static class A implements I {
        P b = new P();
        
        public P getP() {
            return b;
        }

    }

    public static class B<E extends Collection<Integer>> {

        public int val;
        public static String stat = "x";
        public E gen;

        public B() {
            this(1);
        }

        public B(int val) {
            this.val = val;
        }

        public int getVal() {
            return val;
        }

        public void setVal(int val) {
            this.val = val;
        }

        public static String getStat() {
            return stat;
        }

        public static void setStat(String stat) {
            B.stat = stat;
        }
    }

    public static class C extends B<Set<Integer>> {
    }
    
    @Deprecated
    public static class Dep {
        
    }
    
    public static class P {
        
        public int val;
        
        public P() {
            this(1);
        }
        
        public P(int val) {
            this.val = val;
        }

        public int getVal() {
            return val;
        }

        public void setVal(int val) {
            this.val = val;
        }
    }
    
    public static class V {
    	public int varargs1(int... args) {
    		return (args.length > 0 ? ArrayUtil.lastElementOf(args) : 0);
    	}
    	
    	public int varargs2(int x) {
    		return -1;
    	}

    	public int varargs2(int x, int... args) {
    		return (args.length > 0 ? ArrayUtil.lastElementOf(args) : 0);
    	}
    }
}
