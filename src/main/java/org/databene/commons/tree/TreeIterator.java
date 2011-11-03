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

package org.databene.commons.tree;

import org.databene.commons.NullSafeComparator;
import org.databene.commons.TreeModel;
import org.databene.commons.iterator.BidirectionalIterator;

/**
 * Iterates a tree forward and backward.<br/>
 * <br/>
 * Created: 08.05.2007 18:05:24
 * @author Volker Bergmann
 */
public class TreeIterator<E> implements BidirectionalIterator<E> {

    private TreeModel<E> treeModel;
    private E cursor;

    private Boolean hasNext;
    private E next;
    private Boolean hasPrevious;
    private E previous;
    
    // constructors ----------------------------------------------------------------------------------------------------

    public TreeIterator(TreeModel<E> treeModel) {
        this.treeModel = treeModel;
        this.cursor = treeModel.getRoot();
        this.hasNext = true;
        this.next = cursor;
        this.hasPrevious = null;
        this.previous = null;
    }
    
    // BidirectionalIterator interface implementation ------------------------------------------------------------------

    public E first() {
        this.cursor = treeModel.getRoot();
        this.hasNext = null;
        this.next = null;
        this.hasPrevious = null;
        this.previous = null;
        return this.cursor;
    }

    public boolean hasPrevious() {
        if (hasPrevious == null) {
            previous = nodeBefore(cursor, treeModel);
            hasPrevious = (previous != null);
        }
        return hasPrevious;
    }

    public E previous() {
        if (!hasPrevious())
            throw new IllegalStateException("No object available for previous()");
        hasNext = true;
        next = cursor;
        cursor = previous;
        hasPrevious = null;
        previous = null;
        return cursor;
    }

    public E last() {
        hasNext = false;
        next = null;
        hasPrevious = null;
        previous = null;
        E tmp = lastChild(treeModel.getRoot(), treeModel);
        while (treeModel.getChildCount(tmp) > 0) {
            E candidate = lastChild(tmp, treeModel);
            if (candidate == null) {// empty directory
                cursor = tmp;
                return cursor;
            }
            tmp = candidate;
        }
        cursor = tmp;
        return cursor;
    }

    public boolean hasNext() {
        if (hasNext == null) {
            next = nodeAfter(cursor, treeModel);
            hasNext = (next != null);
        }
        return hasNext;
    }

    public E next() {
        if (!hasNext())
            throw new IllegalStateException("No object available for next()");
        hasPrevious = true;
        previous = cursor;
        cursor = next;
        hasNext = null;
        next = null;
        return cursor;
    }

    public void remove() {
        throw new UnsupportedOperationException("remove() is not supported on " + getClass());
    }
    
    // private helpers -------------------------------------------------------------------------------------------------
    
    private static <T> T nodeBefore(T cursor, TreeModel<T> treeModel) {
        // root is the very first node
        if (cursor == treeModel.getRoot())
            return null;
        // check previous siblings
        T parent = treeModel.getParent(cursor);
        for (int i = treeModel.getIndexOfChild(parent, cursor); i > 0; i--) {
            T tmp = treeModel.getChild(parent, i - 1);
            if (treeModel.getChildCount(tmp) == 0)
                return tmp;
            else {
                T candidate = lastSubNode(tmp, treeModel);
                if (candidate != null)
                    return candidate;
            }
        }
        /*
        // check sibling of parent
        if (!treeModel.getRoot().equals(parent))
            return nodeBefore(parent, treeModel);
        else // return root
        	return treeModel.getRoot();
        */
        return parent;
    }

    private static <T> T lastChild(T node, TreeModel<T> treeModel) {
        int childCount = treeModel.getChildCount(node);
        if (childCount > 0)
            return treeModel.getChild(node, childCount - 1);
        else
            return null;
    }

    private static <T> T lastSubNode(T node, TreeModel<T> treeModel) {
        T candidate = lastChild(node, treeModel);
        while (candidate != null) {
            if (treeModel.getChildCount(candidate) == 0)
                return candidate;
            else
                candidate = lastChild(candidate, treeModel);
        }
        return null;
    }

    private static <T> T nodeAfter(T cursor, TreeModel<T> treeModel) {
        // find next
        T tmp = null;
        if (treeModel.getChildCount(cursor) > 0)
                tmp = treeModel.getChild(cursor, 0);
        T parent = treeModel.getParent(cursor);
        if (tmp == null && parent != null) {
            int cursorIndex = treeModel.getIndexOfChild(parent, cursor);
            if (cursorIndex < treeModel.getChildCount(parent) - 1)
                tmp = treeModel.getChild(parent, cursorIndex + 1);
        }
        while (tmp == null && parent != null && !NullSafeComparator.equals(parent, treeModel.getRoot())) {
            T parentsParent = treeModel.getParent(parent);
            int parentsIndex = treeModel.getIndexOfChild(parentsParent, parent);
            int parentLevelCount = treeModel.getChildCount(parentsParent);
            if (parentsIndex < parentLevelCount - 1)
                tmp = treeModel.getChild(parentsParent, parentsIndex + 1);
            parent = parentsParent;
        }
        return tmp;
    }

}
