/*
 * Copyright (C) 2004-2015 Volker Bergmann (volker.bergmann@bergmann-it.de).
 * All rights reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * http://www.apache.org/licenses/LICENSE-2.0
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.databene.commons.ui.swing;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import javax.swing.AbstractListModel;

/**
 * Component that allows for defining an ordered list of files.
 * Created at 30.11.2008 15:11:05
 * @since 0.5.13
 * @author Volker Bergmann
 */

public class ArrayListModel extends AbstractListModel {
	
	private static final long serialVersionUID = 3499248476952363886L;
	private ArrayList<File> elements = new ArrayList<File>();

	@Override
	public Object getElementAt(int index) {
		return elements.get(index);
	}

	@Override
	public int getSize() {
		return elements.size();
	}
	
	public List<File> getAll() {
		return elements;
	}

	public void add(File file) {
		elements.add(file);
		fireIntervalAdded(this, elements.size() - 1, elements.size() - 1);
	}

	public void set(int index, File file) {
		elements.set(index, file);
		fireContentsChanged(this, index, index);
	}

	public void remove(int index) {
		elements.remove(index);
		fireIntervalRemoved(this, index, index);
	}

	public void setAll(File ... files) {
		this.elements.clear();
		for (File file : files)
			this.elements.add(file);
	}

}
