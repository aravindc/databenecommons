/*
 * (c) Copyright 2007 by Volker Bergmann. All rights reserved.
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

/**
 * Wraps a JDK {@link Iterator} into a {@link BidirectionalIterator}, 
 * making the unsupported operations throw an {@link UnsupportedOperationException}.<br/>
 * <br/>
 * Created: 12.06.2007 19:51:44
 * @author Volker Bergmann
 */
public class JDKIteratorWrapper<E> implements BidirectionalIterator<E> {

    private Iterator<E> realIterator;

    public JDKIteratorWrapper(Iterator<E> realIterator) {
        this.realIterator = realIterator;
    }

    @Override
	public boolean hasNext() {
        return realIterator.hasNext();
    }

    @Override
	public E next() {
        return realIterator.next();
    }

    @Override
	public void remove() {
        realIterator.remove();
    }

    @Override
	public E first() {
        throw new UnsupportedOperationException("Operation not supported");
    }

    @Override
	public boolean hasPrevious() {
        throw new UnsupportedOperationException("Operation not supported");
    }

    @Override
	public E previous() {
        throw new UnsupportedOperationException("Operation not supported");
    }

    @Override
	public E last() {
        throw new UnsupportedOperationException("Operation not supported");
    }
}
