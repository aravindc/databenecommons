/*
 * (c) Copyright 2013 by Volker Bergmann. All rights reserved.
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

package org.databene.commons.format;

import java.text.FieldPosition;
import java.text.Format;
import java.text.ParsePosition;

/**
 * Wraps another {@link Format} and overrides the mapping from a null value to a string and vice versa.<br/><br/>
 * Created: 28.02.2013 16:20:55
 * @since 0.5.21
 * @author Volker Bergmann
 */
public class NullSafeFormat extends Format {
	
	private static final long serialVersionUID = 2203854824964382584L;
	
	private Format realFormat;
	private String nullString;

	public NullSafeFormat(Format realFormat, String nullString) {
		this.realFormat = realFormat;
		this.nullString = nullString;
	}

	@Override
	public StringBuffer format(Object obj, StringBuffer toAppendTo, FieldPosition pos) {
		if (obj == null)
			return toAppendTo.append(nullString);
		return realFormat.format(obj, toAppendTo, pos);
	}

	@Override
	public Object parseObject(String source, ParsePosition pos) {
		if (nullString.equals(source.substring(pos.getIndex())))
			return null;
		return realFormat.parseObject(source, pos);
	}
	
}
