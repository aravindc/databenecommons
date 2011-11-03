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

import org.databene.commons.ConversionException;

/**
 * Maps true, false and null to configured aliases, e.g. '1', '0' and '?'.
 * By default booleans are converted to the strings 'true', 'false' and null. <br/>
 * <br/>
 * Created at 11.03.2009 19:40:33
 * @since 0.5.8
 * @author Volker Bergmann
 */

public class BooleanMapper<T> extends ThreadSafeConverter<Boolean, T> {

    private T trueValue;
	private T falseValue;
	private T nullValue;

	@SuppressWarnings("unchecked")
    public BooleanMapper() {
	    this((T) "true", (T) "false", null);
    }

	@SuppressWarnings("unchecked")
    public BooleanMapper(T trueValue, T falseValue, T nullValue) {
	    super(Boolean.class, (Class<T>) trueValue.getClass());
	    this.trueValue = trueValue;
	    this.falseValue = falseValue;
	    this.nullValue = nullValue;
    }

    public T convert(Boolean sourceValue) throws ConversionException {
	    return (sourceValue != null ? (sourceValue ? trueValue : falseValue) : nullValue);
    }

}
