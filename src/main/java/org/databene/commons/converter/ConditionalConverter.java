/*
 * (c) Copyright 2011 by Volker Bergmann. All rights reserved.
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

import org.databene.commons.Condition;
import org.databene.commons.ConversionException;
import org.databene.commons.Converter;

/**
 * {@link Converter} implementation which applies another converter only if a condition is fulfilled, 
 * otherwise returns the argument itself.<br/><br/>
 * Created: 20.07.2011 18:39:51
 * @since 0.5.9
 * @author Volker Bergmann
 */
@SuppressWarnings("rawtypes")
public class ConditionalConverter extends ConverterWrapper implements Converter {

	protected Condition<Object> condition;

	@SuppressWarnings("unchecked")
	public ConditionalConverter(Condition<Object> condition, Converter realConverter) {
		super(realConverter);
		this.condition = condition;
	}

	public Class<?> getSourceType() {
		return Object.class;
	}

	public Class<?> getTargetType() {
		return Object.class;
	}

	@SuppressWarnings("unchecked")
	public Object convert(Object sourceValue) throws ConversionException {
		return (condition.evaluate(sourceValue) ? realConverter.convert(sourceValue) : sourceValue);
	}
	
}
