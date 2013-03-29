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

import java.util.*;
import java.lang.reflect.Array;
import java.lang.reflect.Modifier;

import org.databene.commons.collection.SortedList;

/**
 * Provides Collection-related utility methods.<br/>
 * <br/>
 * Created: 18.12.2006 06:46:24
 */
public final class CollectionUtil {

    public static boolean isEmpty(Collection<?> collection) {
        return (collection == null || collection.size() == 0);
    }

    /**
     * Converts an array into a list.
     * @param array the array to convert into a list.
     * @return a list containing all elements of the given array.
     */
    public static <T, U extends T> List<T> toList(U ... array) {
        List<T> result = new ArrayList<T>(array.length);
        for (T item : array)
            result.add(item);
        return result;
    }

    public static <P, C extends P> List<P> toListOfType(Class<P> type, C ... array) {
        List<P> result = new ArrayList<P>(array.length);
        for (C item : array)
            result.add(item);
        return result;
    }

    /**
     * Creates a HashSet filled with the specified elements
     * @param elements the content of the Set
     * @return a HashSet with the elements
     */
    public static <T> Set<T> toSet(T ... elements) {
        HashSet<T> set = new HashSet<T>();
        if (elements != null)
	        for (T element : elements)
	            set.add(element);
        return set;
    }

    public static <T, U extends T> SortedSet<T> toSortedSet(U ... elements) {
        TreeSet<T> set = new TreeSet<T>();
        for (T element : elements)
            set.add(element);
        return set;
    }
    
    public static <T extends Comparable<T>, U extends T> SortedList<T> toSortedList(U ... elements) {
    	return new SortedList<T>(CollectionUtil.<T,U>toList(elements), new ComparableComparator<T>());
    }
    
	public static Set<Character> toCharSet(char[] chars) {
        HashSet<Character> set = new HashSet<Character>();
        if (chars != null)
	        for (char element : chars)
	            set.add(element);
        return set;
	}
	
    /**
     * Adds the content of an array to a collection
     * @param target the collection to be extended
     * @param values the values to add
     */
    public static <T, U extends T, C extends Collection<? super T>> C add(C target, U ... values) {
        for (T item : values)
            target.add(item);
        return target;
    }

    public static <T> List<T> copy(List<? extends T> src, int offset, int length) {
        List<T> items = new ArrayList<T>(length);
        for (int i = 0; i < length; i++)
            items.add(src.get(offset + i));
        return items;
    }

    @SuppressWarnings("unchecked")
	public static <T, U> T[] toArray(Collection<? extends T> source) {
        if (source.size() == 0)
            throw new IllegalArgumentException("For empty collections, a componentType needs to be specified.");
        Class<T> componentType = (Class<T>) source.iterator().next().getClass();
        T[] array = (T[]) Array.newInstance(componentType, source.size());
        return source.toArray(array);
    }

    @SuppressWarnings("unchecked")
	public static <T> T[] toArray(Collection<? extends T> source, Class<T> componentType) {
        T[] array = (T[]) Array.newInstance(componentType, source.size());
        return source.toArray(array);
    }

    @SuppressWarnings("unchecked")
	public static <T> T[] extractArray(List<? extends T> source, Class<T> componentType, int fromIndex, int toIndex) {
        T[] array = (T[]) Array.newInstance(componentType, toIndex - fromIndex);
        return source.subList(fromIndex, toIndex).toArray(array);
    }

    public static char[] toCharArray(Collection<Character> source) {
        char[] result = new char[source.size()];
        int i = 0;
        for (Character c : source)
            result[i++] = c;
        return result;
    }

    public static <K, V> Map<K, V> buildMap(K key, V value) {
        Map<K, V> map = new HashMap<K, V>();
        map.put(key, value);
        return map;
    }

    @SuppressWarnings({ "unchecked", "rawtypes" })
    public static Map buildMap(Object ... keyValuePairs) {
        Map map = new HashMap();
        if (keyValuePairs.length % 2 != 0)
            throw new IllegalArgumentException("Invalid numer of arguments. " +
                    "It must be even to represent key-value-pairs");
        for (int i = 0; i < keyValuePairs.length; i += 2)
            map.put(keyValuePairs[i], keyValuePairs[i + 1]);
        return map;
    }

    @SuppressWarnings({ "unchecked", "rawtypes" })
    public static Map buildOrderedMap(Object ... keyValuePairs) {
        Map map = new OrderedMap();
        if (keyValuePairs.length % 2 != 0)
            throw new IllegalArgumentException("Invalid numer of arguments. " +
                    "It must be even to represent key-value-pairs");
        for (int i = 0; i < keyValuePairs.length; i += 2)
            map.put(keyValuePairs[i], keyValuePairs[i + 1]);
        return map;
    }

    /** Creates a new instance of a Collection. Abstract interfaces are mapped to a default implementation class. */ 
    @SuppressWarnings("unchecked")
    public static <T extends Collection<U>, U> T newInstance(Class<T> collectionType) {
        if ((collectionType.getModifiers() & Modifier.ABSTRACT) == 0)
            return BeanUtil.newInstance(collectionType);
        else if (Collection.class.equals(collectionType)  || List.class.equals(collectionType))
            return (T) new ArrayList<Object>();
        else if (SortedSet.class.equals(collectionType))
            return (T) new TreeSet<Object>();
        else if (Set.class.equals(collectionType))
            return (T) new TreeSet<Object>();
        else
            throw new UnsupportedOperationException("Not a supported collection type: " + collectionType.getName());
    }

    /** Compares two lists for identical content, accepting different order. */
    public static <T> boolean equalsIgnoreOrder(List<T> a1, List<T> a2) {
        if (a1 == a2)
            return true;
        if (a1 == null)
            return false;
        if (a1.size() != a2.size())
            return false;
        List<T> l1 = new ArrayList<T>(a1.size());
        for (T item : a1)
            l1.add(item);
        for (int i = a1.size() - 1; i >= 0; i--)
            if (a2.contains(a1.get(i)))
                l1.remove(i);
            else
                return false;
        return l1.size() == 0;
    }
    
    public static <V> V getCaseInsensitive(String key, Map<String, V> map) {
    	V result = map.get(key);
    	if (result != null || key == null)
    		return result;
    	String lcKey = key.toLowerCase();
    	for (String candidate : map.keySet())
			if (candidate != null && lcKey.equals(candidate.toLowerCase()))
				return map.get(candidate);
		return null;
    }

    public static <V> boolean containsCaseInsensitive(String key, Map<String, V> map) {
    	if (map.containsKey(key))
    		return true;
    	String lcKey = key.toLowerCase();
    	for (String candidate : map.keySet())
			if (candidate != null && lcKey.equals(candidate.toLowerCase()))
				return true;
		return false;
    }

	public static <T> boolean ofEqualContent(List<T> list, T[] array) {
		if (list == null || list.isEmpty())
			return (array == null || array.length == 0);
		if (array == null || list.size() != array.length)
			return false;
		for (int i = list.size() - 1; i >= 0; i--)
			if (!NullSafeComparator.equals(list.get(i), array[i]))
				return false;
		return true;
	}

	public static <T> T lastElement(List<T> list) {
		return list.get(list.size() - 1);
	}

    @SuppressWarnings("rawtypes")
	private static final List EMPTY_LIST = Collections.emptyList();
	
    @SuppressWarnings("unchecked")
    public static <T> List<T> emptyList() {
	    return EMPTY_LIST;
    }

	@SuppressWarnings("unchecked")
	public static <S, T extends S> List<T> extractItemsOfExactType(Class<T> itemType, Collection<S> items) {
		List<T> result = new ArrayList<T>();
		for (S item : items)
			if (itemType == item.getClass())
				result.add((T) item);
		return result;
	}
	
	@SuppressWarnings("unchecked")
	public static <S, T extends S> List<T> extractItemsOfCompatibleType(Class<T> itemType, Collection<S> items) {
		List<T> result = new ArrayList<T>();
		for (S item : items)
			if (itemType.isAssignableFrom(item.getClass()))
				result.add((T) item);
		return result;
	}

	public static String formatCommaSeparatedList(List<?> list, Character quoteCharacter) {
		StringBuilder builder = new StringBuilder();
		for (int i = 0; i < list.size(); i++) {
			if (i > 0)
				builder.append(", ");
			if (quoteCharacter != null)
				builder.append(quoteCharacter);
			builder.append(list.get(i));
			if (quoteCharacter != null)
				builder.append(quoteCharacter);
		}
		return builder.toString();
	}

}
