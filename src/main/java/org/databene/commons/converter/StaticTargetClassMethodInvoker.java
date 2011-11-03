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

import java.lang.reflect.Method;

import org.databene.commons.BeanUtil;
import org.databene.commons.ConversionException;
import org.databene.commons.Converter;

/**
 * {@link Converter} implementation which invokes a static method of an arbitrary class 
 * with the object to be converted as argument.<br/><br/>
 * Created: 27.02.2010 06:49:13
 * @since 0.5.0
 * @author Volker Bergmann
 */
public class StaticTargetClassMethodInvoker<S, T> extends ThreadSafeConverter<S, T> {
	
	private Method method;

	protected StaticTargetClassMethodInvoker(Class<S> sourceType, Class<T> targetType, Method method) {
	    super(sourceType, targetType);
	    this.method = method;
    }

	@SuppressWarnings("unchecked")
    public T convert(S sourceValue) throws ConversionException {
		return (T) BeanUtil.invoke(null, method, false, sourceValue);
	}

}
