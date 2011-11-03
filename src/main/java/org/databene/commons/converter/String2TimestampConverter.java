/*
 * (c) Copyright 2009-2010 by Volker Bergmann. All rights reserved.
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

import java.sql.Timestamp;
import java.util.Date;

import org.databene.commons.ConversionException;
import org.databene.commons.Converter;
import org.databene.commons.StringUtil;

/**
 * Parses Strings converting them to {@link Timestamp}s.<br/>
 * <br/>
 * Created at 01.10.2009 10:53:20
 * @since 0.5.0
 * @author Volker Bergmann
 */

public class String2TimestampConverter extends ConverterWrapper<String, Date> 
		implements Converter<String, Timestamp> {

    public String2TimestampConverter() {
        super(new String2DateConverter<Date>());
    }

	public Class<String> getSourceType() {
	    return String.class;
    }

	public Class<Timestamp> getTargetType() {
	    return Timestamp.class;
    }

    public Timestamp convert(String sourceValue) throws ConversionException {
        if (StringUtil.isEmpty(sourceValue))
            return null;
        
        // separate the String into Date and nano parts 
    	sourceValue = sourceValue.trim();
        String datePart;
        String nanoPart;
        if (sourceValue.contains(".")) {
        	String[] parts = StringUtil.splitOnFirstSeparator(sourceValue, '.');
            datePart = parts[0];
            nanoPart = parts[1];
        } else {
        	datePart = sourceValue;
        	nanoPart = null; 
        }
        
        // calculate date part
        Date date = realConverter.convert(datePart);
        Timestamp result = new Timestamp(date.getTime());
            
        // calculate nano part
        if (!StringUtil.isEmpty(nanoPart)) {
        	nanoPart = StringUtil.padRight(nanoPart, 9, '0');
        	int nanos = Integer.parseInt(nanoPart);
        	result.setNanos(nanos);
        }
		return result;
    }

}
