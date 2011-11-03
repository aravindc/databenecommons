/*
 * (c) Copyright 2007-2011 by Volker Bergmann. All rights reserved.
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
import org.databene.commons.Patterns;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Converts any source type to any target type. It also makes use of the ConverterManager.<br/>
 * <br/>
 * Created: 16.06.2007 11:34:42
 * @author Volker Bergmann
 */
public class AnyConverter<E> extends FormatHolder implements Converter<Object, E> {
	
    private static final Logger logger = LoggerFactory.getLogger(AnyConverter.class);

    private Class<E> targetType;
    
    public AnyConverter(Class<E> targetType) {
        this(targetType, Patterns.DEFAULT_DATE_PATTERN);
    }

    public AnyConverter(Class<E> targetType, String datePattern) {
    	this.targetType = targetType;
        this.datePattern = datePattern;
    }

    public Class<Object> getSourceType() {
        return Object.class;
    }
    
    public Class<E> getTargetType() {
	    return targetType;
    }

	public E convert(Object sourceValue) throws ConversionException {
        return convert(sourceValue, targetType, datePattern, timePattern, timestampPattern);
    }

	public boolean isParallelizable() {
	    return true;
    }

	public boolean isThreadSafe() {
	    return true;
    }
	
    public static <TT> TT convert(Object source, Class<TT> targetType) throws ConversionException {
        return convert(source, targetType, null, null, null);
    }
    
    /**
     * Converts an object of a given type to an object of the target type.
     * @param source the object to convert
     * @param targetType the target type of the conversion
     * @return an object of the target type
     */
    @SuppressWarnings({ "unchecked", "rawtypes" })
    public static <TT> TT convert(Object source, Class<TT> targetType, String datePattern, 
    		String timePattern, String timestampPattern) throws ConversionException {
        if (logger.isDebugEnabled())
            logger.debug("Converting " + source + (source != null ? " (" + source.getClass().getName() + ")" : "") + " to " + targetType);
    	if (source == null || targetType.equals(source.getClass()))
    		return (TT) source;
        Converter converter = ConverterManager.getInstance().createConverter(source.getClass(), targetType);
		return (TT) converter.convert(source);
    }

    @Override
    public String toString() {
        return getClass().getSimpleName() + '(' + targetType.getSimpleName() + ')';
    }
    
}
