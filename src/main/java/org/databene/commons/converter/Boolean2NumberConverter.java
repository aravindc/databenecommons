/*
 * (c) Copyright 2010-2012 by Volker Bergmann. All rights reserved.
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

import org.databene.commons.ConversionException;
import org.databene.commons.Converter;

/**
 * Converting {@link Boolean} values to {@link Number}s: <code>false</code> to <code>0<code>, 
 * <code>true</code> to <code>1</code>.<br/><br/>
 * Created: 27.02.2010 09:57:17
 * @since 0.5.0
 * @author Volker Bergmann
 */
public class Boolean2NumberConverter<T extends Number> extends ConverterWrapper<Integer, T> 
	implements Converter<Boolean, T> {
	
	public Boolean2NumberConverter(Class<T> targetType) {
	    super(new NumberToNumberConverter<Integer, T>(Integer.class, targetType));
    }

	@Override
	public T convert(Boolean sourceValue) throws ConversionException {
	    return realConverter.convert(sourceValue ? 1 : 0);
    }

	@Override
	public Class<Boolean> getSourceType() {
	    return Boolean.class;
    }

	@Override
	public Class<T> getTargetType() {
	    return realConverter.getTargetType();
    }

}
