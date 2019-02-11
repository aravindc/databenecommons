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

package org.databene.commons.collection;

/**
 * Provides a ring buffer for double values.<br><br>
 * Created: 25.11.2017 19:27:12
 * @since 1.0.12
 * @author Volker Bergmann
 */

public class DoubleRingBuffer {
	
	private double[] buffer;
	private int cursor;
	private boolean full;
	private double lastValue;
	
	public DoubleRingBuffer(int capacity) {
		this.buffer = new double[capacity];
		this.cursor = 0;
		this.full = false;
	}
	
	public int size() {
		return buffer.length;
	}
	
	public void add(double value) {
		lastValue = value;
		this.buffer[cursor++] = value;
		if (cursor == buffer.length)
			cursor = 0;
	}
	
	public double last() {
		return lastValue;
	}
	
	public double min() {
		double min = buffer[0]; // TODO this is wrong if the buffer is empty
		int n = (full ? buffer.length : cursor);
		for (int i = 1; i < n; i++)
			if (buffer[i] < min)
				min = buffer[i];
		return min;
	}
	
	public double max() {
		double max = buffer[0]; // TODO this is wrong if the buffer is empty
		int n = (full ? buffer.length : cursor);
		for (int i = 1; i < n; i++)
			if (buffer[i] > max)
				max = buffer[i];
		return max;
	}

	public double sum() {
		double sum = 0.;
		for (double d : buffer)
			sum += d;
		return sum;
	}

}