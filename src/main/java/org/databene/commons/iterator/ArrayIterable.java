/*
 * (c) Copyright 2009-2012 by Volker Bergmann. All rights reserved.
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

package org.databene.commons.iterator;

import java.util.Iterator;

import org.databene.commons.ArrayFormat;
import org.databene.commons.TypedIterable;

/**
 * Implementation of the {@link Iterable} interface which creates {@link Iterator} that iterate over an array.
 * <br/>
 * Created at 30.06.2009 09:30:02
 * @since 0.5.0
 * @author Volker Bergmann
 */

public class ArrayIterable<E> implements TypedIterable<E> {
	
	protected Class<E> type;
	private E[] source;

    public ArrayIterable(E[] source, Class<E> type) {
	    this.source = source;
	    this.type = type;
    }

	@Override
	public Class<E> getType() {
	    return type;
    }

    @Override
	public Iterator<E> iterator() {
	    return new ArrayIterator<E>(source);
    }

    @Override
    public String toString() {
        return getClass().getSimpleName() + '[' + ArrayFormat.format(source) + ']';
    }
    
}
