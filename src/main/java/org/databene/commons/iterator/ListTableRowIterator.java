/*
 * (c) Copyright 2011 by Volker Bergmann. All rights reserved.
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

package org.databene.commons.iterator;

import java.util.ArrayList;
import java.util.List;

/**
 * {@link TableRowIterator} implementation that iterated through a list of object arrays.<br/><br/>
 * Created: 27.10.2011 08:43:17
 * @since 0.5.11
 * @author Volker Bergmann
 */
public class ListTableRowIterator extends AbstractTableRowIterator {

	private List<Object[]> rows;
	private int cursor;
	
	public ListTableRowIterator(String... columnLabels) {
		this(columnLabels, new ArrayList<Object[]>());
	}

	public ListTableRowIterator(String[] columnLabels, List<Object[]> rows) {
		super(columnLabels);
		this.rows = rows;
		this.cursor = 0;
	}
	
	public void addRow(Object[] row) {
		this.rows.add(row);
	}

	public boolean hasNext() {
		return cursor < rows.size();
	}

	public Object[] next() {
		return rows.get(cursor++);
	}

}
