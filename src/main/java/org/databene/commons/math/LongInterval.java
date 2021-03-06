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
package org.databene.commons.math;

import org.databene.commons.comparator.LongComparator;

/**
 * {@link Interval} implementation using {@link Long} as generic type.
 * Created: 30.12.2011 22:08:21
 * @since 0.5.14
 * @author Volker Bergmann
 */
public class LongInterval extends Interval<Long> {

	private static final long serialVersionUID = -7172324515734804326L;

	public LongInterval(long min, long max) {
		this(min, true, max, true);
	}

	public LongInterval(long min, boolean minInclusive, long max, boolean maxInclusive) {
		super(min, minInclusive, max, maxInclusive, new LongComparator());
	}

}
