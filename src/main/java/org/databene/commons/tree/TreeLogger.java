/*
 * (c) Copyright 2010 by Volker Bergmann. All rights reserved.
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

package org.databene.commons.tree;

import org.databene.commons.Filter;
import org.databene.commons.TreeModel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Logs the structure represented by a {@link org.databene.commons.TreeModel} implementor.<br/><br/>
 * Created: 10.11.2010 10:21:59
 * @since 0.5.4
 * @author Volker Bergmann
 */
public class TreeLogger {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(TreeLogger.class);

	String indent = "";
	
	// interface ---------------------------------------------------------------
	
	public <T> void log(TreeModel<T> model) {
	    log(model.getRoot(), false, model, null);
    }
	
	public <T> void log(TreeModel<T> model, Filter<T> filter) {
	    log(model.getRoot(), false, model, filter);
    }
	
	// private helper methods --------------------------------------------------

	private <T> void log(T node, boolean hasSiblings, TreeModel<T> model, Filter<T> filter) {
		if (filter != null && !filter.accept(node))
			return;
	    LOGGER.info(indent + node);
	    if (!model.isLeaf(node)) {
			increaseIndent(hasSiblings);
			int n = model.getChildCount(node);
			for (int i = 0; i < n; i++)
		    	log(model.getChild(node, i), i < n - 1, model, filter);
		    reduceIndent();
	    }
    }

	private void increaseIndent(boolean hasSiblings) {
	    if (indent.length() == 0)
	    	indent = "+-";
	    else if (hasSiblings)
	    	indent = indent.substring(0, indent.length() - 2) + "| " + indent.substring(indent.length() - 2);
	    else
	    	indent = indent.substring(0, indent.length() - 2) + "  " + indent.substring(indent.length() - 2);
    }

	private void reduceIndent() {
	    if (indent.length() >= 4)
	    	indent = indent.substring(0, indent.length() - 4) + indent.substring(indent.length() - 2);
	    else
	    	indent = "";
    }

}
