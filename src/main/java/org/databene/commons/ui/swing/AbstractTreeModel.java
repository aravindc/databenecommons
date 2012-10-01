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

import java.util.ArrayList;
import java.util.List;

import javax.swing.event.TreeModelEvent;
import javax.swing.event.TreeModelListener;
import javax.swing.tree.TreeModel;

/**
 * Provides handling for {@link TreeModelListener}s.<br/><br/>
 * Created: 22.08.2012 17:13:08
 * @since 0.5.18
 * @author Volker Bergmann
 */
public abstract class AbstractTreeModel implements TreeModel {

	private List<TreeModelListener> listeners;
	
	public AbstractTreeModel() {
		this.listeners = new ArrayList<TreeModelListener>();
	}
	
	@Override
	public void addTreeModelListener(TreeModelListener listener) {
		listeners.add(listener);
	}

	@Override
	public void removeTreeModelListener(TreeModelListener listener) {
		listeners.remove(listener);
	}
	
	protected void fireTreeStructureChanged(Object source, Object[] path) {
		TreeModelEvent event = new TreeModelEvent(source, path);
		for (int i = listeners.size() - 1; i >= 0; i--)
			listeners.get(i).treeStructureChanged(event);
	}
	
	protected void fireTreeNodesChanged(Object source, Object[] path, int[] childIndices, Object[] children) {
		TreeModelEvent event = new TreeModelEvent(source, path, childIndices, children);
		for (int i = listeners.size() - 1; i >= 0; i--)
			listeners.get(i).treeNodesChanged(event);
	}

	protected void fireTreeNodesInserted(Object source, Object[] path, int[] childIndices, Object[] children) {
		TreeModelEvent e = new TreeModelEvent(source, path, childIndices, children);
		for (int i = listeners.size() - 1; i >= 0; i--) {
			listeners.get(i).treeNodesInserted(e);
		}
	}
    
	protected void fireTreeNodesRemoved(Object source, Object[] path, int[] childIndices, Object[] children) {
		TreeModelEvent e = new TreeModelEvent(source, path, childIndices, children);
		for (int i = listeners.size() - 1; i >= 0; i --)
			listeners.get(i).treeNodesRemoved(e);
	}
	
}
