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

import org.databene.commons.Filter;

import java.util.Iterator;

/**
 * Filters elements of another {@link Iterator} or {@link BidirectionalIterator} 
 * by a {@link Filter} element.<br/>
 * <br/>
 * Created: 08.05.2007 19:37:33
 * @author Volker Bergmann
 */
public class FilteringIterator<E> extends BidirectionalIteratorProxy<E> {

    protected Filter<E> filter;

    private E next;
    private E previous;

    public FilteringIterator(Iterator<E> realIterator, Filter<E> filter) {
        this(new JDKIteratorWrapper<E>(realIterator), filter);
    }

    public FilteringIterator(BidirectionalIterator<E> realIterator, Filter<E> filter) {
        super(realIterator);
        this.filter = filter;
    }

    @Override
    public boolean hasNext() {
        if (next != null)
            return true;
        while (super.hasNext()) {
            E tmp = super.next();
            if (filter.accept(tmp)) {
                this.next = tmp;
                return true;
            }
        }
        return false;
    }

    @Override
    public E next() {
        if (next == null && !hasNext())
            throw new IllegalStateException("Nothing more to iterate");
        E result = next;
        next = null;
        previous = null;
        return result;
    }

    @Override
    public E first() {
        next = null;
        previous = null;
        E first = super.first();
        if (filter.accept(first))
            return first;
        while (super.hasNext()) {
            E tmp = super.next();
            if (filter.accept(tmp)) {
                return tmp;
            }
        }
        return null;
    }

    @Override
    public boolean hasPrevious() {
        if (previous != null)
            return true;
        while (super.hasPrevious()) {
            E tmp = super.previous();
            if (filter.accept(tmp)) {
                this.previous = tmp;
                return true;
            }
        }
        return false;
    }

    @Override
    public E previous() {
        if (previous == null && !hasPrevious())
            throw new IllegalStateException("Nothing more to iterate");
        E result = previous;
        previous = null;
        next = null;
        return result;
    }

    @Override
    public E last() {
        next = null;
        previous = null;
        E last = super.last();
        if (filter.accept(last))
            return last;
        while (super.hasPrevious()) {
            E tmp = super.previous();
            if (filter.accept(tmp)) {
                return tmp;
            }
        }
        return null;
    }

}
