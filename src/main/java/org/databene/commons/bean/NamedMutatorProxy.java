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
package org.databene.commons.bean;

import org.databene.commons.Mutator;
import org.databene.commons.UpdateFailedException;

/**
 * Wraps a Mutator, forwards calls to {@link #setValue(Object, Object)} and adds a name property.
 * Created: 28.02.2013 16:42:38
 * @since 0.5.21
 * @author Volker Bergmann
 */
public class NamedMutatorProxy extends AbstractNamedMutator {
	
	protected Mutator realMutator;
	
	public NamedMutatorProxy(String name, Mutator realMutator) {
		super(name);
		this.realMutator = realMutator;
	}

	@Override
	public void setValue(Object target, Object value) throws UpdateFailedException {
		realMutator.setValue(target, value);
	}

}
