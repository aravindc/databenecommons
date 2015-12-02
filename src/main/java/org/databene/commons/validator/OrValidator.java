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
package org.databene.commons.validator;

import org.databene.commons.Validator;

/**
 * Composite validator that requires at least one component to return true. 
 * If no components exists, false is returned.
 * Created: 20.11.2007 09:50:13
 * @author Volker Bergmann
 */
public class OrValidator<E> extends CompositeValidator<E> {

    public OrValidator(Validator<E> ... subValidators) {
        super(subValidators);
    }

    @Override
	public boolean valid(E object) {
        for (Validator<E> validator : subValidators)
            if (validator.valid(object))
                return true;
        return false;
    }
}
