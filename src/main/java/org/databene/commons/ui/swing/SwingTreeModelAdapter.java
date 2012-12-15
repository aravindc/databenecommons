/*
 * (c) Copyright 2010-2012 by Volker Bergmann. All rights reserved.
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

import javax.swing.tree.TreePath;

import org.databene.commons.TreeModel;

/**
 * Adaptor that maps Databene {@link TreeModel}s to Swing {@link javax.swing.tree.TreeModel}.<br/><br/>
 * Created: 02.12.2010 06:46:55
 * @since 0.5.13
 * @author Volker Bergmann
 */
public class SwingTreeModelAdapter<E> extends AbstractTreeModel {

	org.databene.commons.TreeModel<E> delegate;

	public SwingTreeModelAdapter(org.databene.commons.TreeModel<E> delegate) {
		this.delegate = delegate;
	}

	@Override
	public Object getRoot() {
		return delegate.getRoot();
	}

	public E getParent(E child) {
		return delegate.getParent(child);
	}

	@Override
	@SuppressWarnings("unchecked")
	public Object getChild(Object parent, int index) {
		return delegate.getChild((E) parent, index);
	}

	@Override
	@SuppressWarnings("unchecked")
	public int getChildCount(Object parent) {
		return delegate.getChildCount((E) parent);
	}

	@Override
	@SuppressWarnings("unchecked")
	public boolean isLeaf(Object node) {
		return delegate.isLeaf((E) node);
	}

	@Override
	@SuppressWarnings("unchecked")
	public int getIndexOfChild(Object parent, Object child) {
		return delegate.getIndexOfChild((E) parent, (E) child);
	}

	@Override
	public void valueForPathChanged(TreePath path, Object newValue) {
		// nothing to do: org.databene.commons.TreeModel does not support changing of path values
	}

}
