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
package org.databene.commons.iterator;

import org.databene.commons.HeavyweightIterator;
import org.databene.commons.Tabular;

/**
 * Iterator for table-like structures, providing column labels in the getColumnLables() 
 * method and providing the rows as Object[] in the next() method.
 * Created: 27.10.2010 09:52:18
 * @since 0.5.4
 * @author Volker Bergmann
 */
public interface TabularIterator extends Cloneable, Tabular, HeavyweightIterator<Object[]> {
}
