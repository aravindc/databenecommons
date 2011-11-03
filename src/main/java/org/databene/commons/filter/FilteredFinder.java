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

package org.databene.commons.filter;

import org.databene.commons.Element;
import org.databene.commons.Filter;
import org.databene.commons.Visitor;

import java.util.Collection;
import java.util.List;
import java.util.ArrayList;

/**
 * Iterates through a tree for searching items that maz´tch a filter.<br/>
 * <br/>
 * Created: 04.02.2007 11:59:03
 * @author Volker Bergmann
 */
public class FilteredFinder {

    public static <T> Collection<T> find(Element<T> root, Filter<T> filter) {
        HelperVisitor<T> visitor = new HelperVisitor<T>(filter);
        root.accept(visitor);
        return visitor.getMatches();
    }

    private static class HelperVisitor<E> implements Visitor<E> {

        private Filter<E> filter;
        private List<E> matches;

        public HelperVisitor(Filter<E> filter) {
            this.filter = filter;
            this.matches = new ArrayList<E>();
        }

        public void visit(E element) {
            if (filter.accept(element))
                matches.add(element);
        }

        public List<E> getMatches() {
            return matches;
        }
    }
}
