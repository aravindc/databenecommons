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
package org.databene.commons.converter;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import org.databene.commons.ConversionException;
import org.databene.commons.Converter;
import org.databene.commons.TimeUtil;

/**
 * Parses a {@link String} as a {@link Calendar}.
 * Created at 13.07.2009 18:49:00
 * @since 0.5.0
 * @author Volker Bergmann
 */

public class String2GregorianCalendarConverter extends ConverterWrapper<String, Date> 
		implements Converter<String, GregorianCalendar> {
	
    public String2GregorianCalendarConverter() {
	    super(new String2DateConverter<Date>());
    }

    @Override
	public GregorianCalendar convert(String sourceValue) throws ConversionException {
	    Date date = realConverter.convert(sourceValue);
		return TimeUtil.gregorianCalendar(date);
    }

	@Override
	public Class<String> getSourceType() {
	    return String.class;
    }

	@Override
	public Class<GregorianCalendar> getTargetType() {
	    return GregorianCalendar.class;
    }

}
