/*
 * (c) Copyright 2007-2010 by Volker Bergmann. All rights reserved.
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

import java.util.Map;
import java.util.HashMap;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;
import java.math.BigInteger;
import java.math.BigDecimal;

/**
 * Utility class for operations related to Java types, e.g. mapping number types and their wrappers.<br/>
 * <br/>
 * Created: 29.09.2006 12:29:48
 */
public class JavaType {

    // static maps for JavaType lookup by different criteria ---------------------------------------------------------

    /** Maps the NumberTypes by name */
    private static Map<String,JavaType> instancesByName = new HashMap<String,JavaType>();

    /** Maps the NumberTypes by primitive class */
    private static Map<Class<?>,JavaType> instancesByPrimitive = new HashMap<Class<?>,JavaType>();

    /** Maps the NumberTypes by wrapper class */
    private static Map<Class<?>,JavaType> instancesByWrapper = new HashMap<Class<?>,JavaType>();

    /** Collects all Number types, primitives and wrappers */
    private static Set<Class<? extends Number>> numberTypes = new HashSet<Class<? extends Number>>();

    // instances -------------------------------------------------------------------------------------------------------

    public static final JavaType BOOLEAN = new JavaType("boolean", boolean.class, Boolean.class);
    public static final JavaType CHAR = new JavaType("char", short.class, Short.class);
    public static final JavaType BYTE = new JavaType("byte", byte.class, Byte.class);
    public static final JavaType SHORT = new JavaType("short", short.class, Short.class);
    public static final JavaType INT  = new JavaType("int", int.class, Integer.class);
    public static final JavaType LONG = new JavaType("long", long.class, Long.class);
    public static final JavaType FLOAT = new JavaType("float", float.class, Float.class);
    public static final JavaType DOUBLE = new JavaType("double", double.class, Double.class);
    public static final JavaType BIG_INT = new JavaType("big_int", BigInteger.class, BigInteger.class);
    public static final JavaType BIG_DECIMAL = new JavaType("big_decimal", BigDecimal.class, BigDecimal.class);

    // attributes ------------------------------------------------------------------------------------------------------

    /** logic name */
    private final String name;

    /** primitive class of the JavaType */
    private final Class<?> primitiveClass;

    /** wrapper class of the JavaType */
    private final Class<?> wrapperClass;

    // private constructor ---------------------------------------------------------------------------------------------

    /** Initializes a JavaType instance and puts it into all lookup maps */
    @SuppressWarnings("unchecked")
    private JavaType(String name, Class<?> primitiveClass, Class<?> objectClass) {
        this.name = name;
        this.primitiveClass = primitiveClass;
        this.wrapperClass = objectClass;
        instancesByName.put(name, this);
        instancesByPrimitive.put(primitiveClass, this);
        instancesByWrapper.put(objectClass, this);
        if (Number.class.isAssignableFrom(wrapperClass))
            numberTypes.add((Class<? extends Number>) wrapperClass);
        if (Number.class.isAssignableFrom(primitiveClass))
            numberTypes.add((Class<? extends Number>) primitiveClass);
    }

    // property getters ------------------------------------------------------------------------------------------------

    /** returns the name */
    public String getName() {
        return name;
    }

    /** returns the primitive class */
    public Class<?> getPrimitiveClass() {
        return primitiveClass;
    }

    /** returns the wrapper class */
    public Class<?> getWrapperClass() {
        return wrapperClass;
    }

    // static query methods --------------------------------------------------------------------------------------------

    public static Collection<JavaType> getInstances() {
        return instancesByName.values();
    }

    /** returns an instance by name */
    public static JavaType getInstance(String name) {
        return instancesByName.get(name);
    }

    /** finds the wrapper class for primitive number types */
    public static Class<?> getWrapperClass(Class<?> numberType) {
        JavaType resultType = instancesByPrimitive.get(numberType);
        if (resultType == null)
            resultType = instancesByWrapper.get(numberType);
        return (resultType != null ? resultType.getWrapperClass() : null);
    }

    /** finds the primitive class for primitive number types */
    public static Class<?> getPrimitiveClass(Class<?> numberType) {
        JavaType resultType = instancesByWrapper.get(numberType);
        if (resultType == null)
            resultType = instancesByPrimitive.get(numberType);
        return (resultType != null ? resultType.getPrimitiveClass() : null);
    }

    /** provides all Java number types */
    public static Set<Class<? extends Number>> getNumberTypes() {
        return numberTypes;
    }
    
    public static boolean isIntegralType(Class<?> type) {
    	return (type == Integer.class  || type == int.class 
    			|| type == Long.class  || type == long.class 
    			|| type == Byte.class  || type == byte.class 
    			|| type == Short.class || type == short.class 
    			|| type == BigInteger.class);
    }

    public static boolean isDecimalType(Class<?> type) {
    	return (type == Double.class || type == double.class || type == Float.class 
    			|| type == float.class || type == BigDecimal.class);
    }

}
