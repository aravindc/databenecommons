/*
 * (c) Copyright 2010 by Volker Bergmann. All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, is permitted under the terms of the
 * GNU General Public License (GPL).
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

import java.util.Date;
import java.util.TimeZone;

import org.databene.commons.ConversionException;

/**
 * Interprets {@link Long} values as milliseconds since 1970-01-01 and 
 * converts them to {@link Date} objects.<br/><br/>
 * Created: 26.02.2010 08:19:48
 * @since 0.5.0
 * @author Volker Bergmann
 */
public class Long2DateConverter extends ThreadSafeConverter<Long, Date>{

	private TimeZone timeZone;
	
	// constructors ----------------------------------------------------------------------------------------------------

	public Long2DateConverter() {
		this(TimeZone.getDefault());
	}

	public Long2DateConverter(TimeZone timeZone) {
		super(Long.class, Date.class);
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
	public Class<Date> getTargetType() {
		return Date.class;
	}

	public Date convert(Long target) throws ConversionException {
		if (target == null)
			return null;
		return new Date(target - timeZone.getRawOffset());
	}

}
