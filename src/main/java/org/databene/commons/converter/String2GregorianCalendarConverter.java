/*
 * (c) Copyright 2009-2012 by Volker Bergmann. All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, is permitted under the terms of the
 * GNU General Public License.
 *
 * For redistributing this software or a derivative work under a license other
 * than the GPL-compatible Free Software License as defined by the Free
 * Software Foundation or approved by OSI, you must first obtain a commercial
 * license to this software product from Volker Bergmann.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS"
 * WITHOUT A WARRANTY OF ANY KIND. ALL EXPRESS OR IMPLIED CONDITIONS,
 * REPRESENTATIONS AND WARRANTIES, INCLUDING ANY IMPLIED WARRANTY OF
 * MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE OR NON-INFRINGEMENT, ARE
 * HEREBY EXCLUDED. IN NO EVENT SHALL THE COPYRIGHT OWNER OR CONTRIBUTORS BE
 * LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR
 * CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF
 * SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS
 * INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN
 * CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE)
 * ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE
 * POSSIBILITY OF SUCH DAMAGE.
 */

package org.databene.commons.converter;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import org.databene.commons.ConversionException;
import org.databene.commons.Converter;
import org.databene.commons.TimeUtil;

/**
 * Parses a {@link String} as a {@link Calendar}.<br/>
 * <br/>
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
