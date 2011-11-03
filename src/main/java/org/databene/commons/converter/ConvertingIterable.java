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

import java.io.Closeable;

import org.databene.commons.Converter;
import org.databene.commons.HeavyweightIterator;
import org.databene.commons.HeavyweightTypedIterable;
import org.databene.commons.IOUtil;
import org.databene.commons.iterator.ConvertingIterator;

/**
 * Iterable that provides {@link ConvertingIterator}s.<br/>
 * <br/>
 * Created: 28.08.2007 08:57:16
 * @author Volker Bergmann
 */
public class ConvertingIterable<S, T> implements HeavyweightTypedIterable<T> {

    protected Iterable<S> iterable;
    protected Converter<S, T> converter;

    public ConvertingIterable(Iterable<S> iterable, Converter<S, T> converter) {
        this.iterable = iterable;
        this.converter = converter;
    }
    
    // interface -------------------------------------------------------------------------------------------------------

    public Class<T> getType() {
        return converter.getTargetType();
    }

    public HeavyweightIterator<T> iterator() {
        return new ConvertingIterator<S, T>(this.iterable.iterator(), converter);
    }
    
	public void close() {
		 if (iterable instanceof Closeable)
			 IOUtil.close((Closeable) iterable);
	}

	// java.lang.Object overrides --------------------------------------------------------------------------------------
	
    @Override
    public String toString() {
    	return getClass().getSimpleName() + '[' + iterable + " -> " + converter + ']';
    }

}
