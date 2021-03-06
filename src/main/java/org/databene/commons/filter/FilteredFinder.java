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

import org.databene.commons.Element;
import org.databene.commons.Filter;
import org.databene.commons.Visitor;

import java.util.Collection;
import java.util.List;
import java.util.ArrayList;

/**
 * Iterates through a tree for searching items that match a filter.
 * Created: 04.02.2007 11:59:03
 * @author Volker Bergmann
 */
public class FilteredFinder {

    public static <T> Collection<T> find(Element<T> root, Filter<T> filter) {
        HelperVisitor<T> visitor = new HelperVisitor<T>(filter);
        root.accept(visitor);
        return visitor.getMatches();
    }

    private static class HelperVisitor<E> implements Visitor<E> {

        private Filter<E> filter;
        private List<E> matches;

        public HelperVisitor(Filter<E> filter) {
            this.filter = filter;
            this.matches = new ArrayList<E>();
        }

        @Override
		public <C extends E >void visit(C element) {
            if (filter.accept(element))
                matches.add(element);
        }

        public List<E> getMatches() {
            return matches;
        }
    }
}
