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

package org.databene.commons.iterator;

import java.io.Closeable;
import java.io.IOException;
import java.util.Iterator;

import org.databene.commons.HeavyweightIterator;

/**
 * Proxy for an {@link Iterator} with additional support for 
 * iterators that implement the {@link Closeable} interface.<br/><br/>
 * Created: 13.10.2010 13:22:46
 * @since 0.5.4
 * @author Volker Bergmann
 */
public class IteratorProxy<E> implements HeavyweightIterator<E> {
	
	protected Iterator<E> source;

	public IteratorProxy(Iterator<E> source) {
	    this.source = source;
    }

	public boolean hasNext() {
	    return source.hasNext();
    }

	public E next() {
	    return source.next();
    }

	public void remove() {
	    source.remove();
    }

	public void close() throws IOException {
		if (source instanceof Closeable)
			((Closeable) source).close();
    }
	
	@Override
	public String toString() {
		return source.toString();
	}
	
}
