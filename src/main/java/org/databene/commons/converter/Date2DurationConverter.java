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

import java.util.Date;
import java.util.TimeZone;

import org.databene.commons.ConversionException;

/**
 * Interprets a Date as duration specification, e.g. '0000-00-00T00:00:00.001' as one millisecond, 
 * '0001-00-00T00:00:00.000' as one year. Dates after 1970-01-01 will be interpreted relative to that date.<br/>
 * <br/>
 * Created at 11.01.2009 06:39:28
 * @since 0.5.7
 * @author Volker Bergmann
 */

public class Date2DurationConverter extends ThreadSafeConverter<Date, Long> {

	public Date2DurationConverter() {
		super(Date.class, Long.class);
	}

	public Long convert(Date sourceValue) throws ConversionException {
		if (sourceValue == null)
			return null;
		long source = sourceValue.getTime();
		// for time zone problems, see http://mail-archives.apache.org/mod_mbox/struts-user/200502.mbox/%3C42158AA9.3050001@gridnode.com%3E 
		Long result = source + TimeZone.getDefault().getOffset(0L); // That's relative to 1970-01-01
		if (result < 0) // if it's before 1970-01-01, interpret it relative to 0001-01-01
			result = source + TimeZone.getDefault().getOffset(-62170156800000L) + 62170156800000L;
		return result;
	}

}
