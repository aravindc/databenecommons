/*
 * (c) Copyright 2008-2009 by Volker Bergmann. All rights reserved.
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
import org.databene.commons.iterator.BidirectionalIteratorTest;
import org.junit.Test;

/**
 * Tests the {@link TreeIterator}.<br/><br/>
 * Created at 04.05.2008 09:17:07
 * @since 0.4.3
 * @author Volker Bergmann
 */
public class TreeIteratorTest extends BidirectionalIteratorTest {
	
	@Test
	@SuppressWarnings("unchecked")
    public void test() {
		DefaultTreeNode<Integer> root = new DefaultTreeNode<Integer>(0);
		DefaultTreeNode<Integer> c1 = new DefaultTreeNode<Integer>(root, 1);
		root.addChild(c1);
		DefaultTreeNode<Integer> c11 = new DefaultTreeNode<Integer>(c1, 11);
		c1.addChild(c11);
		DefaultTreeNode<Integer> c2 = new DefaultTreeNode<Integer>(root, 2);
		root.addChild(c2);
		DefaultTreeNode<Integer> c3 = new DefaultTreeNode<Integer>(root, 3);
		root.addChild(c3);
		TreeModel<DefaultTreeNode<Integer>> model = new DefaultTreeModel<Integer>(root);
		TreeIterator<DefaultTreeNode<Integer>> iterator = new TreeIterator<DefaultTreeNode<Integer>>(model);
		expectNextElements(iterator, root, c1, c11, c2, c3).withNoNext();
		expectPreviousElements(iterator, c2, c11, c1, root).withNoPrevious();
	}
	
}
