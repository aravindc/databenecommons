/*
 * Copyright (C) 2004-2015 Volker Bergmann (volker.bergmann@bergmann-it.de).
 * All rights reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * http://www.apache.org/licenses/LICENSE-2.0
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.databene.commons;

import java.util.*;
import java.lang.reflect.Array;
import java.lang.reflect.Modifier;

import org.databene.commons.collection.SortedList;

/**
 * Provides Collection-related utility methods.
 * Created: 18.12.2006 06:46:24
 */
public final class CollectionUtil {

    public static boolean isEmpty(Collection<?> collection) {
        return (collection == null || collection.size() == 0);
    }

    /**
     * Converts an array into a list.
     * @param array the array to convert into a list.
     * @param <T> the element type
     * @return a list containing all elements of the given array.
     */
    @SafeVarargs
    public static <T> List<T> toList(T ... array) {
        List<T> result = new ArrayList<T>(array.length);
        for (T item : array)
            result.add(item);
        return result;
    }

    @SafeVarargs
    public static <P, C extends P> List<P> toListOfType(Class<P> type, C ... array) {
        List<P> result = new ArrayList<P>(array.length);
        for (C item : array)
            result.add(item);
        return result;
    }

    /**
     * Creates a HashSet filled with the specified elements
     * @param elements the content of the Set
     * @param <T> the element type
     * @return a HashSet with the elements
     */
    @SafeVarargs
    public static <T> Set<T> toSet(T ... elements) {
        HashSet<T> set = new HashSet<T>();
        if (elements != null)
	        for (T element : elements)
	            set.add(element);
        return set;
    }

    @SafeVarargs
    public static <T, U extends T> SortedSet<T> toSortedSet(U ... elements) {
        TreeSet<T> set = new TreeSet<T>();
        for (T element : elements)
            set.add(element);
        return set;
    }
    
    @SafeVarargs
    public static <T extends Comparable<T>, U extends T> SortedList<T> toSortedList(U ... elements) {
    	return new SortedList<T>(CollectionUtil.<T>toList(elements), new ComparableComparator<T>());
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
     * @param <C> the collection type
     * @param <T> the element type
     * @param <U> the common supertype of the values
     * @return the collection, extended by the contents of the array
     */
	@SafeVarargs
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
	public static <T> T[] toArray(Collection<? extends T> source) {
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

    /** Creates a new instance of a Collection. Abstract interfaces are mapped to a default implementation class. 
     * @param collectionType the type of the collection to be created
     * @param <T> the type of the requested collection
     * @param <U> the collection element type 
     * @return an empty instance of the requested collection type */ 
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

    /** Compares two lists for identical content, accepting different order. 
     * @param a1 the first list
     * @param a2 the second list
     * @param <T> the generic list type
     * @return true if both lists have the same content elements, else false */
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

	public static String formatCommaSeparatedList(Collection<?> collection, Character quoteCharacter) {
		StringBuilder builder = new StringBuilder();
		int i = 0;
		Iterator<?> iterator = collection.iterator();
		while (iterator.hasNext()) {
			if (i > 0)
				builder.append(", ");
			if (quoteCharacter != null)
				builder.append(quoteCharacter);
			builder.append(iterator.next());
			if (quoteCharacter != null)
				builder.append(quoteCharacter);
			i++;
		}
		return builder.toString();
	}

}
