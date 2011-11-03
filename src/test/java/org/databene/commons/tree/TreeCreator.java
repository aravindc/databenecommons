/*
 * (c) Copyright 2007-2009 by Volker Bergmann. All rights reserved.
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
import org.databene.commons.tree.DefaultTreeModel;
import org.databene.commons.tree.DefaultTreeNode;

/**
 * Creates trees for testing.<br/><br/>
 * Created: 08.05.2007 19:47:45
 * @author Volker Bergmann
 */
public class TreeCreator {

    public static TreeModel<DefaultTreeNode<String>> createTreeModel() {
        DefaultTreeNode<String> root = new DefaultTreeNode<String>(null, "root", false);
        TreeModel<DefaultTreeNode<String>> model = new DefaultTreeModel<String>(root);

        // create 1st level sub nodes
        root.addChild(DefaultTreeNode.createLeaf(root, "a1l"));
        DefaultTreeNode<String> a2f = DefaultTreeNode.createFolder(root, "a2f");
        root.addChild(a2f);
        root.addChild(DefaultTreeNode.createLeaf(root, "a3l"));

        // create 2nd level sub nodes
        DefaultTreeNode<String> b1f = DefaultTreeNode.createFolder(a2f, "b1f");
        a2f.addChild(b1f);

        // create 3nd level sub nodes
        DefaultTreeNode<String> c1l = DefaultTreeNode.createLeaf(b1f, "c1l");
        b1f.addChild(c1l);

        return model;
    }

}
