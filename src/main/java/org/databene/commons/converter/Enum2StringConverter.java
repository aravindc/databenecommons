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
package org.databene.commons.converter;

import org.databene.commons.ConversionException;

/**
 * Converts an enum value to its {@link String} representation.<br/><br/>
 * Created: 25.02.2010 23:50:06
 * @since 0.5.0
 * @author Volker Bergmann
 */
public class Enum2StringConverter<E extends Enum<E>> extends ThreadSafeConverter<E, String> {

    public Enum2StringConverter(Class<E> enumClass) {
        super(enumClass, String.class);
    }

    @Override
	public String convert(E target) throws ConversionException {
        return convertToString(target);
    }

    public static <T extends Enum<?>> String convertToString(T target) throws ConversionException {
        return (target != null ? target.name() : null);
    }

}
