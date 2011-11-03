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

import org.databene.commons.Converter;
import org.databene.commons.bean.HashCodeBuilder;

/**
 * Converter id class for the ConverterManager.<br/><br/>
 * Created: 27.02.2010 05:45:43
 * @since 0.5.0
 * @author Volker Bergmann
 */
class ConversionTypes {

	public final Class<?> sourceType;
	public final Class<?> targetType;
	
	public ConversionTypes(Converter<?,?> converter) {
	    this(converter.getSourceType(), converter.getTargetType());
    }
	
	public ConversionTypes(Class<?> sourceType, Class<?> targetType) {
	    this.sourceType = sourceType;
	    this.targetType = targetType;
    }

	@Override
    public int hashCode() {
	    return HashCodeBuilder.hashCode(sourceType, targetType);
    }

	@Override
    public boolean equals(Object obj) {
	    if (this == obj)
		    return true;
	    if (obj == null)
		    return false;
	    ConversionTypes that = (ConversionTypes) obj;
	    return (this.sourceType == that.sourceType && this.targetType == that.targetType);
    }
	
	@Override
	public String toString() {
	    return sourceType.getName() + "->" + targetType.getName();
	}
	
}
