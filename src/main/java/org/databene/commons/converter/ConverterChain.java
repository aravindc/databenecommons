/*
 * (c) Copyright 2007-2010 by Volker Bergmann. All rights reserved.
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
import org.databene.commons.Converter;

/**
 * Aggregates other (sub) converters and implements conversion by subsequent invocation of the sub converters,
 * each converting the result of the preceding converter.<br/>
 * <br/>
 * Created: 13.05.2005 17:43:04
 * @since 0.1
 * @author Volker Bergmann
 */
@SuppressWarnings({ "unchecked", "rawtypes" })
public class ConverterChain<S, T> extends MultiConverterWrapper implements Converter<S, T> {

	public ConverterChain(Converter... components) {
		super(components);
    }
	
	// Converter interface implementation ------------------------------------------------------------------------------

	public Class<S> getSourceType() {
		return (components.length > 0 ? components[0].getSourceType() : Object.class);
	}

	public Class<T> getTargetType() {
        return (components.length > 0 ? components[components.length - 1].getTargetType() : Object.class);
    }

	public T convert(Object source) throws ConversionException {
        Object tmp = source;
        for (Converter converter : components) {
            tmp = converter.convert(tmp);
        }
        return (T) tmp;
    }

}
