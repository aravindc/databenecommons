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

package org.databene.commons.ui.swing;

import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.JTree;
import javax.swing.tree.TreeModel;
import javax.swing.tree.TreePath;

import org.databene.commons.ArrayUtil;

/**
 * {@link Action} class which expands or collapses nodes of a {@link JTree}.<br/><br/>
 * Created: 23.08.2012 07:47:04
 * @since 0.5.18
 * @author Volker Bergmann
 */
@SuppressWarnings("serial")
public class ExpandOrCollapseTreeNodesAction extends AbstractAction {
	
	private JTree tree;
	private boolean expand;
	private boolean recursive;

	public ExpandOrCollapseTreeNodesAction(JTree tree, boolean expand, boolean recursive) {
		super(expand ? "expand" : "collapse");
		putValue(SHORT_DESCRIPTION, (expand ? "Expand tree nodes" : "Collapse tree nodes"));
		this.tree = tree;
		this.expand = expand;
		this.recursive = recursive;
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		tree.cancelEditing();
		TreePath[] selectionPaths = tree.getSelectionPaths();
		if (!ArrayUtil.isEmpty(selectionPaths)) {
			for (TreePath path : selectionPaths)
				handlePath(path);
		} else {
			handlePath(new TreePath(tree.getModel().getRoot()));
		}
	}

	private void handlePath(TreePath path) {
		Object node = path.getLastPathComponent();
		TreeModel model = tree.getModel();
		if (!model.isLeaf(node)) {
			if (recursive) {
				for (int i = model.getChildCount(node) - 1; i >= 0; i--)
					handlePath(path.pathByAddingChild(model.getChild(node, i)));
			}
			if (expand)
				tree.expandPath(path);
			else
				tree.collapsePath(path);
		}
	}

}
