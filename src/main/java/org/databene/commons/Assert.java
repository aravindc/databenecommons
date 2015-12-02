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

import java.util.Arrays;
import java.util.Collection;

/**
 * An assertion utility.
 * Created at 25.04.2008 17:59:43
 * @since 0.4.2
 * @author Volker Bergmann
 */
public class Assert {
	
	private static final double TOLERANCE = 0.00001;
	
	private Assert() {}
	
	public  void end(String text, String end) {
		if (text == null) {
			if (end != null)
				throw new AssertionError("String is supposed to end with '" + end + ", but is null.");
		} else if (!text.endsWith(end))
			throw new AssertionError("String is supposed to end with '" + end + ", but is: " + text);
	}

	public static void endIgnoreCase(String text, String end) {
		if (text == null) {
			if (end != null)
				throw new AssertionError("String is supposed to end with '" + end + ", but is null.");
		} else if (!text.endsWith(end))
			throw new AssertionError("String is supposed to case-insensitively end with '" + end 
					+ ", but is: " + text);
	}

	public static <T> T notNull(T object, String objectRole) {
		if (object == null)
			throw new AssertionError(objectRole + " is not supposed to be null");
		return object;
	}
	
	public static String notEmpty(String text, String message) {
		if (text == null || text.length() == 0)
			throw new AssertionError(message);
		return text;
	}

	public static void notEmpty(Collection<?> collection, String message) {
		if (collection == null || collection.size() == 0)
			throw new AssertionError(message);
	}

	public static void notEmpty(Object[] array, String message) {
		if (array == null || array.length == 0)
			throw new AssertionError(message);
	}

    @SuppressWarnings("null")
    public static void equals(Object a1, Object a2, String message) {
		if (a1 == null && a2 == null)
			return;
		else if ((a1 == null && a2 != null) || (a1 != null && a2 == null))
			throw new AssertionError(message);
		else if (!a1.equals(a2))
			throw new AssertionError(message);
    }

	@SuppressWarnings("null")
    public static <T> void equals(T[] a1, T[] a2) {
		if (a1 == null && a2 == null)
			return;
		else if ((a1 == null && a2 != null) || (a1 != null && a2 == null))
			throw new AssertionError("Arrays are not equal, one of them is null");
		else if (a1.length != a2.length)
			throw new AssertionError("Arrays are not equal, the size differs: [" + 
					ArrayFormat.format(a1) + "] vs. [" + ArrayFormat.format(a2) + ']');
		else if (!Arrays.deepEquals(a1, a2))
			throw new AssertionError("Arrays are not equal, content differs: [" + 
					ArrayFormat.format(a1) + "] vs. [" + ArrayFormat.format(a2) + ']');
	}
	
	@SuppressWarnings("null")
    public static void equals(byte[] a1, byte[] a2) {
		if (a1 == null && a2 == null)
			return;
		else if ((a1 == null && a2 != null) || (a1 != null && a2 == null))
			throw new AssertionError("Arrays are not equal, one of them is null");
		else if (a1.length != a2.length)
			throw new AssertionError("Arrays are not equal, the size differs: [" + 
					ArrayFormat.formatBytes(",", a1) + "] vs. [" + ArrayFormat.formatBytes(",", a2) + ']');
		else if (!Arrays.equals(a1, a2))
			throw new AssertionError("Arrays are not equal, content differs: [" + 
					ArrayFormat.formatBytes(",", a1) + "] vs. [" + ArrayFormat.formatBytes(",", a2) + ']');
	}

	public static void length(String string, int length) {
		if (string == null || string.length() != length)
			throw new AssertionError("Unexpected string length: Expected string of length " + length + ", found: " 
					+ (string != null ? string.length() : "null"));
	}
	
	public static void startsWith(String prefix, String string) {
		if (string == null || !string.startsWith(prefix))
			throw new AssertionError("Expected prefix '" + prefix + "' is missing in: " + string);
	}
	
	public static void endsWith(String suffix, String string) {
		if (string == null || !string.endsWith(suffix))
			throw new AssertionError("Expected suffix '" + suffix + "' is missing in: " + string);
	}
	
	public static void instanceOf(Object object, Class<?> type, String name) {
		 if (object == null)
			 throw new AssertionError(name + " is not supposed to be null");
		 if (!type.isAssignableFrom(object.getClass()))
			 throw new AssertionError(name + " is expected to be of type " + type.getName() + ", but is " + object.getClass());
	}

	public static void isTrue(boolean expression, String message) {
		if (!expression)
			throw new AssertionError(message);
	}

	public static void found(Object object, String name) {
		 if (object == null)
			 throw new AssertionError(name + " not found");
	}

	public static void notNegative(Number value, String role) {
		if (value.doubleValue() < 0)
			 throw new AssertionError(role + " is less than zero: " + value);
	}

	public static void positive(Number value, String role) {
		if (value.doubleValue() <= 0)
			 throw new AssertionError(role + " is not positive: " + value);
	}

	public static void negative(Number value, String role) {
		if (value.doubleValue() >= 0)
			 throw new AssertionError(role + " is not negative: " + value);
	}

	public static void that(boolean flag, String message) {
		if (!flag)
			throw new AssertionError(message);
	}

	public static void notEquals(double d1, double d2, String message) {
		if (Math.abs(d1 - d2) < TOLERANCE)
			throw new AssertionError(message);
	}

}
