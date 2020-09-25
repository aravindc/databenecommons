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
package org.databene.commons.filter;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.databene.commons.ConfigurationError;
import org.databene.commons.Filter;

/**
 * Utility class which provides convenience methods related to {@link Filter}s.
 * Created: 05.06.2011 22:58:00
 * @since 0.5.8
 * @author Volker Bergmann
 */
public class FilterUtil {
	
	/** private constructor for preventing instantiation of this utility class. */
	private FilterUtil() { }

	public static <T> List<T> find(List<T> candidates, Filter<T> filter) {
		List<T> result = new ArrayList<T>();
		for (T candidate : candidates)
			if (filter.accept(candidate))
				result.add(candidate);
		return result;
	}

	public static <T> T findSingleMatch(Collection<T> candidates, Filter<T> filter) {
		T result = null;
		for (T candidate : candidates)
			if (filter.accept(candidate)) {
				if (result == null)
					result = candidate;
				else
					throw new ConfigurationError("Found multiple matches: " + candidates);
			}
		return result;
	}

    public static <T> SplitResult<T> split(T[] items, Filter<T> filter) {
        List<T> matches = new ArrayList<T>();
        List<T> mismatches = new ArrayList<T>();
        for (T item : items) {
            if (filter.accept(item))
                matches.add(item);
            else
                mismatches.add(item);
        }
        return new SplitResult<T>(matches, mismatches);
    }

    public static <T> SplitResult<T> split(List<T> list, Filter<T> filter) {
        List<T> matches = new ArrayList<T>();
        List<T> mismatches = new ArrayList<T>();
        for (T item : list) {
            if (filter.accept(item))
                matches.add(item);
            else
                mismatches.add(item);
        }
        return new SplitResult<T>(matches, mismatches);
    }

    public static <T> List<List<T>> filter(T[] items, Filter<T> ... filters) {
        List<List<T>> results = new ArrayList<List<T>>(filters.length);
        for (int i = 0; i < filters.length; i++)
            results.add(new ArrayList<T>());
        for (T item : items) {
            for (int i = 0; i < filters.length; i++) {
                Filter<T> filter = filters[i];
                if (filter.accept(item))
                    results.get(i).add(item);
            }
        }
        return results;
    }

}
