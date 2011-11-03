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

import org.databene.commons.CollectionUtil;
import org.databene.commons.ConversionException;

import java.util.Collection;
import java.util.List;

/**
 * Converts arrays and collections to collections of same content, everything else is converted to a collection of size 1.<br/>
 * <br/>
 * Created: 26.08.2007 16:16:15
 */
@SuppressWarnings({ "unchecked", "rawtypes" })
public class ToCollectionConverter<C extends Collection> extends ThreadSafeConverter<Object, C> {

    public ToCollectionConverter() {
        this((Class<C>) List.class);
    }

    public ToCollectionConverter(Class<C> targetType) {
        super(Object.class, targetType);
    }

    public C convert(Object sourceValue) throws ConversionException {
        return (C) convert(sourceValue, targetType);
    }

    public static Collection convert(Object sourceValue, Class targetType) {
        if (sourceValue == null)
            return null;
        if (sourceValue.getClass() == targetType)
            return (Collection) sourceValue;
        Collection collection = CollectionUtil.newInstance(targetType);
        if (sourceValue instanceof Collection)
            collection.addAll((Collection) sourceValue);
        else if (sourceValue.getClass().isArray()) {
            Object[] array = (Object[]) sourceValue;
            for (Object o : array)
                collection.add(o);
        } else
            collection.add(sourceValue);
        return collection;
    }
}
