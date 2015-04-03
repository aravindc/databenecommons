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
package org.databene.commons.operation;

import org.databene.commons.ArrayUtil;
import org.databene.commons.Operation;

/**
 * Determines the elements common to all array arguments.
 * @since 0.4.0
 * @author Volker Bergmann
 */
public class ArrayIntersectionOperation<E> implements Operation<E[], E[]>{

    private Class<E> componentType;

    public ArrayIntersectionOperation(Class<E> componentType) {
        this.componentType = componentType;
    }

    @Override
	public E[] perform(E[]... sources) {
        return ArrayUtil.commonElements(componentType, sources);
    }
    
}
