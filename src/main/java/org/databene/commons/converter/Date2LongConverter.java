/*
 * (c) Copyright 2007-2012 by Volker Bergmann. All rights reserved.
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

import org.databene.commons.ConversionException;

import java.util.Date;
import java.util.TimeZone;

/**
 * Converts a {@link Date} into the number of milliseconds since 1970-01-01 in a certain time zone and back.
 * By default it uses the system's default time zone.<br/>
 * <br/>
 * Created: 05.08.2007 07:10:25
 * @author Volker Bergmann
 */
public class Date2LongConverter extends ThreadSafeConverter<Date, Long>{

	private TimeZone timeZone;
	
	// construcors -----------------------------------------------------------------------------------------------------

	public Date2LongConverter() {
		this(TimeZone.getDefault());
	}

	public Date2LongConverter(TimeZone timeZone) {
		super(Date.class, Long.class);
		this.timeZone = timeZone;
	}
	
	// properties ------------------------------------------------------------------------------------------------------

	public TimeZone getTimeZone() {
		return timeZone;
	}

	public void setTimeZone(TimeZone timeZone) {
		this.timeZone = timeZone;
	}
	
	// BidirectionalConverter interface implementation -----------------------------------------------------------------

	@Override
	public Class<Long> getTargetType() {
		return Long.class;
	}

	@Override
	public Long convert(Date sourceValue) throws ConversionException {
		if (sourceValue == null)
			return null;
		return sourceValue.getTime() + timeZone.getRawOffset();
	}

}
