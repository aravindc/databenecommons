/*
 * (c) Copyright 2008-2012 by Volker Bergmann. All rights reserved.
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

import java.io.Closeable;
import java.util.Iterator;

import org.databene.commons.HeavyweightIterator;
import org.databene.commons.IOUtil;

/**
 * Wraps an {@link Iterator} with a {@link HeavyweightIterator}. 
 * If the wrapped iterator implements {@link Closeable}, calls to 
 * <code>close()</code> are forwarded, otherwise ignored<br/>.
 * <br/>
 * Created at 17.10.2008 01:27:08
 * @since 0.4.6
 * @author Volker Bergmann
 */
public abstract class HeavyweightIteratorAdapter<S,P> implements HeavyweightIterator<P> {
	
	protected Iterator<S> source;
	
	public HeavyweightIteratorAdapter(Iterator<S> source) {
		this.source = source;
	}

	@Override
	public boolean hasNext() {
		return source.hasNext();
	}

	@Override
	public void remove() {
		 source.remove();
	}

    @Override
	public void close() {
		 if (source instanceof Closeable)
			 IOUtil.close((Closeable) source);
	}

}
