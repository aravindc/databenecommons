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

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.databene.commons.iterator.ArrayIterator;

/**
 * Provides array-related operations.<br/>
 * <br/>
 * Created: 09.06.2006 21:31:49
 * @since 0.1
 * @author Volker Bergmann
 */
public final class ArrayUtil {

    public static <T> T[] copyOfRange(T[] array, int offset, int length) {
        return copyOfRange(array, offset, length, componentType(array));
    }

	@SuppressWarnings("unchecked")
    public static <T> Class<T> componentType(T[] array) {
	    Class<T[]> resultType = (Class<T[]>) array.getClass();
        Class<T> componentType = (Class<T>) resultType.getComponentType();
	    return componentType;
    }

    @SuppressWarnings("unchecked")
    public static <T> T[] copyOfRange(Object[] array, int offset, int length, Class<T> componentType) {
        T[] result = (T[]) Array.newInstance(componentType, length);
        System.arraycopy(array, offset, result, 0, length);
        return result;
    }

    public static <T> T[] removeElement(T item, T[] array) {
    	int index = indexOf(item, array);
        return remove(index, array);
    }

    @SuppressWarnings("unchecked")
    public static <T> T[] remove(int indexToRemove, T[] array) {
        Class<T> componentType = componentType(array);
        T[] result = (T[]) Array.newInstance(componentType, array.length - 1);
        if (indexToRemove > 0)
            System.arraycopy(array, 0, result, 0, indexToRemove);
        System.arraycopy(array, indexToRemove + 1, result, indexToRemove, array.length - indexToRemove - 1);
        return result;
    }

	public static <T> T[] removeAll(T[] toRemove, T[] target) {
        Class<T> componentType = componentType(target);
		ArrayBuilder<T> builder = new ArrayBuilder<T>(componentType);
		for (T element : target)
			if (!contains(element, toRemove))
				builder.add(element);
		return builder.toArray();
	}

    // containment check -----------------------------------------------------------------------------------------------

    /**
     * Tells if an array contains a specific element
     * @param element the element to search
     * @param array the array to scan
     * @return true if the element was found, else false
     */
    public static boolean contains(Object element, Object array) {
    	int length = Array.getLength(array);
        for (int i = 0; i < length; i++) {
        	Object o = Array.get(array, i);
            if (NullSafeComparator.equals(o, element))
                return true;
        }
        return false;
    }

	public static <T> boolean containsAll(T[] subArray, T[] superArray) {
		for (T t : subArray)
			if (!contains(t, superArray))
				return false;
		return true;
	}

    public static int indexOf(byte[] subArray, byte[] array) {
        return indexOf(subArray, 0, array);
    }

    public static int indexOf(byte[] subArray, int fromIndex, byte[] array) {
        for (int i = fromIndex; i <= array.length - subArray.length; i++) {
        	boolean match = true;
        	for (int j = 0; j < subArray.length; j++) {
	            if (array[i + j] != subArray[j])
	                match = false;
        	}
        	if (match)
        		return i;
        }
        return -1;
    }

    /**
     * Tells if an array ends with a specified sub array
     * @param candidates the array to scan
     * @param searched the sub array that is searched
     * @return true if the array ands with or equals the searched sub array
     */
    public static <T> boolean endsWithSequence(T[] candidates, T[] searched) {
        if (searched.length > candidates.length)
            return false;
        for (int i = 0; i < searched.length; i++) {
            if (!candidates[candidates.length - searched.length + i].equals(searched[i]))
                return false;
        }
        return true;
    }

    @SuppressWarnings("unchecked")
    public static <T> T[] commonElements(T[]... sources) {
        Class<T> componentType = null;
        for (int arrayNumber = 0; arrayNumber < sources.length && componentType == null; arrayNumber++) {
            T[] source = sources[arrayNumber];
            for (int index = 0; index < source.length && componentType == null; index++)
                if (source[index] != null)
                    componentType = (Class<T>) source[index].getClass();
        }
        return commonElements(componentType, sources);
    }

    public static <T> T[] commonElements(Class<T> componentType, T[]... sources) {
        ArrayBuilder<T> builder = new ArrayBuilder<T>(componentType);
        T[] firstArray = sources[0];
        for (T element : firstArray) {
            boolean common = true;
            for (int i = 1; i < sources.length; i++)
                if (!ArrayUtil.contains(element, sources[i])) {
                    common = false;
                    break;
                }
            if (common)
                builder.add(element);
        }
        return builder.toArray();
    }


    // identity checks -------------------------------------------------------------------------------------------------

    /**
     * Tells if two arrays have the same content, independent of the ordering
     * @param a1 the first array to compare
     * @param a2 the first array to compare
     * @return true if the array have the same content, independent of the ordering
     */
    public static <T> boolean equalsIgnoreOrder(T[] a1, T[] a2) {
        if (a1 == a2)
            return true;
        if (a1 == null)
            return false;
        if (a1.length != a2.length)
            return false;
        List<T> l1 = new ArrayList<T>(a1.length);
        for (T item : a1)
            l1.add(item);
        for (int i = a1.length - 1; i >= 0; i--)
            if (contains(a1[i], a2))
                l1.remove(i);
            else
                return false;
        return l1.size() == 0;
    }

    public static boolean equals(Object a1, Object a2) {
        if (a1 == a2)
            return true;
        if (a1 == null || !(a1.getClass().isArray()) || !(a2.getClass().isArray()))
            return false;
        int length = Array.getLength(a1);
		if (length != Array.getLength(a2))
            return false;
        List<Object> l1 = new ArrayList<Object>(length);
        for (int i = 0; i < length ; i++)
            l1.add(Array.get(a1, i));
        for (int i = length - 1; i >= 0; i--) {
            if (contains(Array.get(a1, i), a2))
                l1.remove(i);
            else
                return false;
        }
        return l1.size() == 0;
    }

    /**
     * Tells the first index under which an item is found in an array.
     * @param searchedItem
     * @param array
     * @return the index of the searched item
     */
    public static <T> int indexOf(T searchedItem, T[] array) {
        for (int i = 0; i < array.length; i++) {
            T candidate = array[i];
            if (NullSafeComparator.equals(candidate, searchedItem))
                return i;
        }
        return -1;
    }

    @SuppressWarnings("unchecked")
    public static <T> T[] toArray(T... values) {
    	Class<T> componentType = (Class<T>) (values.length > 0 ? values[0].getClass() : Object.class);
    	return buildObjectArrayOfType(componentType, values);
    }

    public static int[] toIntArray(int... values) {
        int[] array = new int[values.length];
        System.arraycopy(values, 0, array, 0, values.length);
        return array;
    }

    public static char[] toCharArray(char... values) {
        char[] array = new char[values.length];
        System.arraycopy(values, 0, array, 0, values.length);
        return array;
    }

    public static Object buildArrayOfType(Class<?> componentType, Object ... values) {
        Object array = Array.newInstance(componentType, values.length);
        for (int i = 0; i < values.length; i++)
        	Array.set(array, i, values[i]); // explicit assignment since System.arraycopy() does not perform autoboxing
        return array;
    }
    
    @SuppressWarnings("unchecked")
    public static <T> T[] buildObjectArrayOfType(Class<T> componentType, T ... values) {
        T[] array = (T[]) Array.newInstance(componentType, values.length);
        System.arraycopy(values, 0, array, 0, values.length);
        return array;
    }
    
    public static <T> Iterator<T> iterator(T[] array) {
    	return new ArrayIterator<T>(array);
    }

    public static <T> T[] revert(T[] array) {
        for (int i = (array.length >> 1) - 1 ; i >= 0; i--) {
            T tmp = array[i];
            array[i] = array[array.length - 1 - i];
            array[array.length - 1 - i] = tmp;
        }
        return array;
    }

    public static char[] revert(char[] array) {
        for (int i = (array.length >> 1) - 1; i >= 0; i--) {
            char tmp = array[i];
            array[i] = array[array.length - 1 - i];
            array[array.length - 1 - i] = tmp;
        }
        return array;
    }
    
    @SuppressWarnings("rawtypes")
	public static Class arrayType(Class componentType) {
    	if (componentType == byte.class)
    		return byte[].class;
    	else if (componentType == char.class)
    		return char[].class;
    	else if (componentType == int.class)
    		return int[].class;
    	else if (componentType == long.class)
    		return long[].class;
    	else if (componentType == short.class)
    		return short[].class;
    	else if (componentType == double.class)
    		return double[].class;
    	else if (componentType == float.class)
    		return float[].class;
    	else if (componentType == boolean.class)
    		return boolean[].class;
        Object[] array = (Object[]) Array.newInstance(componentType, 0);
        return array.getClass();
    }

    @SuppressWarnings("unchecked")
    public static <T> T[] newInstance(Class<T> componentType, int length) {
        return (T[]) Array.newInstance(componentType, length);
    }

    public static <T> T[] append(T[] newValues, T[] array) {
        if (array == null) {
            return newValues.clone();
        } else {
            T[] newArray = newInstance(componentType(array), array.length + newValues.length);
            System.arraycopy(array, 0, newArray, 0, array.length);
            for (int i = 0; i < newValues.length; i++)
            	newArray[array.length + i] = newValues[i];
            return newArray;
        }
    }

    @SuppressWarnings("unchecked")
    public static <T> T[] append(T value, T[] array) {
        if (array == null) {
            return toArray(value);
        } else {
            T[] newArray = newInstance(componentType(array), array.length + 1);
            System.arraycopy(array, 0, newArray, 0, array.length);
            newArray[array.length] = value;
            return newArray;
        }
    }

    public static byte[] append(byte value, byte[] array) {
        if (array == null) {
            return new byte[] { value };
        } else {
            byte[] newArray = new byte[array.length + 1];
            System.arraycopy(array, 0, newArray, 0, array.length);
            newArray[array.length] = value;
            return newArray;
        }
    }

    public static boolean isEmpty(Object values) {
        return (values == null || Array.getLength(values) == 0);
    }

    public static <T> T lastElementOf(T[] array) {
    	if (isEmpty(array))
    		return null;
    	return array[array.length - 1];
    }

    public static Integer lastElementOf(int[] array) {
    	if (array == null || array.length == 0)
    		return -1;
    	return array[array.length - 1];
    }

	public static boolean allNull(Object[] values) {
		if (values == null)
			return true;
		for (Object value : values)
			if (value != null)
				return false;
	    return true;
    }

}
