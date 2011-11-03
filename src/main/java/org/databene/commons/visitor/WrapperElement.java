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

package org.databene.commons.visitor;

import org.databene.commons.Visitor;

/**
 * Element implementation that serves as proxy for another Element.<br/>
 * <br/>
 * Created: 04.02.2007 08:17:20
 * @author Volker Bergmann
 */
public abstract class WrapperElement<E> extends AbstractElement<E> {

    protected E wrappedObject;

    protected WrapperElement(E wrappedObject) {
        this.wrappedObject = wrappedObject;
    }

    public E getWrappedObject() {
        return wrappedObject;
    }

    @Override
    protected void acceptImpl(Visitor<E> visitor) {
        visitor.visit(wrappedObject);
    }

    @SuppressWarnings("rawtypes")
	@Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;
		final WrapperElement that = (WrapperElement) o;
        return !(wrappedObject != null ? !wrappedObject.equals(that.wrappedObject) : that.wrappedObject != null);
    }

    @Override
    public int hashCode() {
        return (wrappedObject != null ? wrappedObject.hashCode() : 0);
    }

    @Override
    public String toString() {
        return wrappedObject.toString();
    }
}
