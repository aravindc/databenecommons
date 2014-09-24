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
package org.databene.commons;

import org.junit.Test;
import static junit.framework.Assert.*;

import java.util.Comparator;

/**
 * Tests the {@link ComparableComparator}.<br/><br/>
 * Created: 21.06.2007 08:30:00
 * @author Volker Bergmann
 */
public class ComparableComparatorTest {

	@Test
    public void test() {
        Comparator<String> comparator = new ComparableComparator<String>();
        assertEquals( 0, comparator.compare("1", "1"));
        assertEquals( 1, comparator.compare("1", "0"));
        assertEquals(-1, comparator.compare("0", "1"));
    }
	
}
