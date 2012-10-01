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

package org.databene.commons.iterator;

import org.databene.commons.ConfigurationError;
import org.databene.commons.StringUtil;

/**
 * Iterates through another {@link TabularIterator}, 
 * picking and possibly reordering a sub set of its columns.<br/><br/>
 * Created: 26.01.2012 18:33:10
 * @since 0.5.14
 * @author Volker Bergmann
 */
public class SelectiveTabularIterator extends IteratorProxy<Object[]> implements TabularIterator {
	
	private String[] columnNames;
	private int[] sourceIndexes;
	
	public SelectiveTabularIterator(TabularIterator source, String[] columnNames) {
		super(source);
		String[] sourceColumnNames = source.getColumnNames();
		if (columnNames != null) {
			this.columnNames = columnNames;
			this.sourceIndexes = new int[columnNames.length];
			for (int i = 0; i < columnNames.length; i++) {
				String columnName = columnNames[i];
				int sourceIndex = StringUtil.indexOfIgnoreCase(columnName, sourceColumnNames);
				if (sourceIndex < 0)
					throw new ConfigurationError("Column '" + columnName + "' not defined in source: " + source);
				this.sourceIndexes[i] = sourceIndex;
			}
		} else {
			this.columnNames = sourceColumnNames;
			this.sourceIndexes = null;
		}
	}

	@Override
	public String[] getColumnNames() {
		return columnNames;
	}

	@Override
	public Object[] next() {
		Object[] sourceArray = super.next();
		if (sourceIndexes == null)
			return sourceArray;
		Object[] result = new Object[sourceIndexes.length];
		for (int i = 0; i < sourceIndexes.length; i++)
			result[i] = (i < sourceArray.length ? sourceArray[sourceIndexes[i]] : null);
		return result;
	}

}
