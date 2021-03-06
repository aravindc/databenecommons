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
package org.databene.commons;

/**
 * Enumeration which provides constants for Binary scales.
 * Created: 06.03.2011 15:29:47
 * @since 0.5.8
 * @author Volker Bergmann
 */
public enum BinaryScale {

	TERA(1099511627776L, "T"), 
	GIGA(1073741824L, "G"),
	MEGA(1048576L, "M"), 
	KILO(1024L, "K"),
	NONE(1L, ""); 

	private double factor;
	private String designator;

	private BinaryScale(double factor, String designator) {
		this.factor = factor;
		this.designator = designator;
	}
	
	public double getFactor() {
		return factor;
	}
	
	public String getDesignator() {
		return designator;
	}
	
}
