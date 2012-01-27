/*
 * (c) Copyright 2012 by Volker Bergmann. All rights reserved.
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

/**
 * {@link Iterator} implementation which allows to preview the {@link #next()} value 
 * without actually consuming it using the {@link #peek()} method.<br/><br/>
 * Created: 25.01.2012 14:11:17
 * @since 0.5.14
 * @author Volker Bergmann
 */
public class PreviewIterator<E> extends IteratorProxy<E> {
	
	private boolean hasNext;
	private E next;

	public PreviewIterator(Iterator<E> source) {
		super(source);
		fetchNext();
	}
	
	public E peek() {
		return this.next;
	}

	// Iterator interface implementation -------------------------------------------------------------------------------
	
	@Override
	public boolean hasNext() {
		return hasNext;
	}
	
	@Override
	public E next() {
		E result = this.next;
		fetchNext();
		return result;
	}
	
	// private helpers -------------------------------------------------------------------------------------------------
	
	private void fetchNext() {
		this.hasNext = source.hasNext();
		this.next = (this.hasNext ? source.next() : null);
	}
	
}
