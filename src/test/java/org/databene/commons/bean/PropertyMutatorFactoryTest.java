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
package org.databene.commons.bean;

import static junit.framework.Assert.*;
import org.databene.commons.ConfigurationError;
import org.databene.commons.UpdateFailedException;
import org.databene.commons.bean.PropertyGraphMutator;
import org.databene.commons.bean.PropertyMutatorFactory;
import org.databene.commons.bean.TypedPropertyMutator;
import org.databene.commons.bean.UntypedPropertyMutator;
import org.junit.Test;

/**
 * Tests the {@link PropertyMutatorFactory}.
 * Created: 20.02.2007 08:52:49
 * @author Volker Bergmann
 */
public class PropertyMutatorFactoryTest {

	@Test
    public void testSimpleProperty() {
        assertEquals(TypedPropertyMutator.class, PropertyMutatorFactory.getPropertyMutator(ABean.class, "name", true, false).getClass());
        assertEquals(TypedPropertyMutator.class, PropertyMutatorFactory.getPropertyMutator(ABean.class, "doesntExsist", false, true).getClass());
        try {
            PropertyMutatorFactory.getPropertyMutator(ABean.class, "doesntExsist");
            fail("ConfigurationError expected");
        } catch (ConfigurationError e) {
            // this is the desired behaviour
        }
        assertEquals(UntypedPropertyMutator.class, PropertyMutatorFactory.getPropertyMutator("name").getClass());
    }

	@Test
    public void testNavigatedProperty() throws UpdateFailedException {
        assertEquals(PropertyGraphMutator.class, PropertyMutatorFactory.getPropertyMutator(ABean.class, "b.name").getClass());
        assertEquals(PropertyGraphMutator.class, PropertyMutatorFactory.getPropertyMutator("b.name").getClass());
        assertEquals(PropertyGraphMutator.class, PropertyMutatorFactory.getPropertyMutator(ABean.class, "doesnt.exist", false, true).getClass());
        try {
            PropertyMutatorFactory.getPropertyMutator(ABean.class, "doesnt.exist", true, false);
            fail("ConfigurationError expected");
        } catch(ConfigurationError e) {
            // this is the desired behaviour
        }
    }

}
