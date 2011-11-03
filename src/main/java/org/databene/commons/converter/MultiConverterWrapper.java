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

import org.databene.commons.ArrayUtil;
import org.databene.commons.Converter;

/**
 * Parent class for {@link Converter} implementations that 
 * holds references to several other converter objects.<br/><br/>
 * Created: 26.02.2010 13:50:43
 * @since 0.5.0
 * @author Volker Bergmann
 */
public abstract class MultiConverterWrapper<S, T> implements Cloneable {

	protected Converter<S, T>[] components;

	protected MultiConverterWrapper(Converter<S, T>[] components) {
	    this.components = components;
    }

	// properties ------------------------------------------------------------------------------------------------------

	public Converter<S, T>[] getComponents() {
        return components;
    }
	
	public void setComponents(Converter<S, T>[] converters) {
        this.components = converters;
    }
	
	public void addComponent(Converter<S, T> converter) {
        this.components = ArrayUtil.append(converter, this.components);
    }
	
	// Converter interface implementation ------------------------------------------------------------------------------

	public boolean isThreadSafe() {
		for (Converter<?,?> converter : components)
			if (!converter.isThreadSafe())
				return false;
		return true;
	}
	
	public boolean isParallelizable() {
		for (Converter<?,?> converter : components)
			if (!converter.isParallelizable())
				return false;
		return true;
	}
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
    @Override
    public Object clone() {
        try {
    		MultiConverterWrapper copy = (MultiConverterWrapper) super.clone();
			copy.components = ConverterManager.cloneIfSupported(this.components);
			return copy;
		} catch (CloneNotSupportedException e) {
	        throw new RuntimeException(e);
        }
	}
	
}
