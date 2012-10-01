/*
 * (c) Copyright 2007-2012 by Volker Bergmann. All rights reserved.
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

import org.databene.commons.TreeModel;

import java.util.Map;
import java.util.HashMap;

/**
 * Adapts a ChildModel to a TreeModel.<br/>
 * <br/>
 * Created: 31.07.2007 06:32:41
 * @author Volker Bergmann
 */
public class ChildTreeModel<I, V> implements TreeModel<V> {

    private ChildModel<I, V> childModel;
    private DefaultTreeNode<V> root;
    private Map<I, DefaultTreeNode<V>> elements;

    @SuppressWarnings({ "unchecked", "rawtypes" })
    public ChildTreeModel() {
        this(new DefaultChildModel());
    }

    public ChildTreeModel(ChildModel<I, V> childModel) {
        this.childModel = childModel;
        this.elements = new HashMap<I, DefaultTreeNode<V>>();
    }

    public void add(V element) {
        DefaultTreeNode<V> elementNode;
        I id = childModel.getId(element);
        I parentId = childModel.getParentId(element);
        if (id == null) {
            elementNode = new DefaultTreeNode<V>(element);
            this.root = elementNode;
        } else {
            DefaultTreeNode<V> parentNode = elements.get(parentId);
            if (parentNode == null)
                if (root == null) {
                    parentNode = new DefaultTreeNode<V>(null);
                    elements.put(parentId, parentNode);
                    this.root = parentNode;
                } else
                    throw new IllegalArgumentException("Multiple roots.");
            elementNode = new DefaultTreeNode<V>(parentNode, element);
            parentNode.addChild(elementNode);
        }
        elements.put(id, elementNode);
    }

    @Override
	public V getRoot() {
        return root.getObject();
    }

    @Override
	public V getParent(V child) {
        return elements.get(childModel.getParentId(child)).getObject();
    }

    @Override
	public V getChild(V parent, int index) {
        return elements.get(childModel.getId(parent)).getChild(index).getObject();
    }

    @Override
	public int getChildCount(V parent) {
        return elements.get(childModel.getId(parent)).getChildCount();
    }

    @Override
	public boolean isLeaf(V node) {
        return elements.get(childModel.getId(node)).isLeaf();
    }

    @Override
	public int getIndexOfChild(V parent, V child) {
        return elements.get(childModel.getId(parent)).getIndexOfChild(new DefaultTreeNode<V>(child));
    }
    
}
