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

/**
 * Instantiates enum instances by their name.<br/>
 * <br/>
 * Created: 20.08.2007 07:11:16
 * @author Volker Bergmann
 */
@SuppressWarnings("rawtypes")
public class String2EnumConverter<E extends Enum> extends ThreadSafeConverter<String, E> {

    public String2EnumConverter(Class<E> enumClass) {
        super(String.class, enumClass);
    }

    public E convert(String sourceValue) throws ConversionException {
        return convert(sourceValue, targetType);
    }

    public static <T extends Enum> T convert(String sourceValue, Class<T> enumClass) throws ConversionException {
        if (sourceValue == null)
            return null;
        T[] enumConstants = enumClass.getEnumConstants();
        for (T enumConstant : enumConstants)
            if (enumConstant.toString().equals(sourceValue))
                return enumConstant;
        throw new ConversionException(enumClass + " does not have an instance of name " + sourceValue);
    }

}
