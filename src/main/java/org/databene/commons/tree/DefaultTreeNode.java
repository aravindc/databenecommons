/*
 * (c) Copyright 2007-2010 by Volker Bergmann. All rights reserved.
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

import java.util.List;
import java.util.ArrayList;

/**
 * Default implementation of a tree node.<br/>
 * <br/>
 * Created: 08.05.2007 19:06:49
 * @author Volker Bergmann
 */
public class DefaultTreeNode<E> {

    private DefaultTreeNode<E> parent;
    private boolean leaf;
    private List<DefaultTreeNode<E>> children;
    protected E object;

    public static <T> DefaultTreeNode<T> createLeaf(DefaultTreeNode<T> parent, T object) {
        return new DefaultTreeNode<T>(parent, object, true);
    }

    public static <T> DefaultTreeNode<T> createFolder(DefaultTreeNode<T> parent, T object) {
        return new DefaultTreeNode<T>(parent, object, false);
    }

    public DefaultTreeNode(E object) {
        this(null, object);
    }

    public DefaultTreeNode(DefaultTreeNode<E> parent, E object) {
        this(parent, object, false);
    }

    public DefaultTreeNode(DefaultTreeNode<E> parent, E object, boolean leaf) {
        this.parent = parent;
        this.leaf = leaf;
        this.children = new ArrayList<DefaultTreeNode<E>>();
        this.object = object;
    }

    public void addChild(DefaultTreeNode<E> child) {
        if (leaf)
            throw new IllegalStateException("Can't add a child to a leaf");
        children.add(child);
    }

    public DefaultTreeNode<E> getParent() {
        return parent;
    }

    public DefaultTreeNode<E> getChild(int index) {
        return children.get(index);
    }

    public int getChildCount() {
        return children.size();
    }

    public boolean isLeaf() {
        return leaf;
    }

    public int getIndexOfChild(DefaultTreeNode<E> child) {
        return children.indexOf(child);
    }

    public E getObject() {
        return object;
    }

    @Override
    public String toString() {
        return String.valueOf(object);
    }
}
