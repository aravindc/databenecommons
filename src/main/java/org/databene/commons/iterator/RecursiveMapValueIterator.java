/*
 * (c) Copyright 2013 by Volker Bergmann. All rights reserved.
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

import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.Stack;

/**
 * Allows linear iteration over nested {@link Set}s using a plain {@link Iterator}.<br/><br/>
 * Created: 04.08.2013 07:58:05
 * @since 0.5.24
 * @author Volker Bergmann
 */

public class RecursiveMapValueIterator<E> implements Iterator<E> {
	
	private Stack<Iterator<?>> iterators;
	private E next;
	private boolean hasNext;
	
	public RecursiveMapValueIterator(Map<?, ?> root) {
		this.iterators = new Stack<Iterator<?>>();
		this.iterators.push(root.values().iterator());
		forward();
	}

	@Override
	public boolean hasNext() {
		return hasNext;
	}

	@Override
	public E next() {
		E result = next;
		forward();
		return result;
	}

	@Override
	public void remove() {
		iterators.peek().remove();
	}
	
	@SuppressWarnings("unchecked")
	private void forward() {
		Iterator<?> iterator = iterators.peek();
		if (iterator.hasNext()) {
			Object candidate = iterator.next();
			if (candidate instanceof Map) {
				iterators.push(((Map<?,?>) candidate).values().iterator());
				forward();
			} else {
				hasNext = true;
				next = (E) candidate;
			}
		} else if (iterators.size() > 1) {
			iterators.pop();
			forward();
		} else {
			this.hasNext = false;
			this.next = null;
		}
	}

}
