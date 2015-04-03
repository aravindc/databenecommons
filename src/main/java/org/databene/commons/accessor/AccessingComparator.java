/*
 * Copyright (C) 2004-2014 Volker Bergmann (volker.bergmann@bergmann-it.de).
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
package org.databene.commons.accessor;

import org.databene.commons.Accessor;

import java.util.Comparator;

/**
 * Compares to objects by the values returned from an Accessor that is applied to them.
 * Created: 23.02.2006 18:57:09
 * @author Volker Bergmann
 */
public class AccessingComparator<C, V> implements Comparator<C> {

    private Accessor<C, V> accessor;
    private Comparator<V> comparator;

    public AccessingComparator(Accessor<C, V> accessor, Comparator<V> comparator) {
        this.accessor = accessor;
        this.comparator = comparator;
    }

    @Override
	public int compare(C o1, C o2) {
        return comparator.compare(accessor.getValue(o1), accessor.getValue(o2));
    }
}
