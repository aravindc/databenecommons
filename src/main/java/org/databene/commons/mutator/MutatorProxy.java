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
package org.databene.commons.mutator;

import org.databene.commons.Mutator;
import org.databene.commons.UpdateFailedException;

/**
 * Proxy for a Mutator.
 * Created: 12.05.2005 18:54:36
 */
public abstract class MutatorProxy extends MutatorWrapper {

    public MutatorProxy(Mutator realMutator) {
        super(realMutator);
    }

    /**
     * @see org.databene.commons.Mutator#setValue(java.lang.Object, java.lang.Object)
     */
    @Override
	public void setValue(Object target, Object value) throws UpdateFailedException {
        realMutator.setValue(target, value);
    }

}
