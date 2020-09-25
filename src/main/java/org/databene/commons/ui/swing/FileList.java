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

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.event.ActionEvent;
import java.io.File;
import java.util.List;

import javax.swing.AbstractAction;
import javax.swing.Box;
import javax.swing.Icon;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.ListSelectionModel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import org.databene.commons.CollectionUtil;
import org.databene.commons.ui.I18NSupport;

/**
 * Component that allows for defining an ordered list of files.
 * Created at 30.11.2008 14:46:48
 * @since 0.5.13
 * @author Volker Bergmann
 */

public class FileList extends JPanel {

	private static final long serialVersionUID = -5042653089516904515L;
	
	I18NSupport i18n;
	JList list;
	protected ArrayListModel model;
	private JFileChooser chooser;
	
	public FileList(I18NSupport i18n) {
		super(new BorderLayout());
		this.i18n = i18n;
		model = new ArrayListModel();
		list = new JList(model);
		list.setVisibleRowCount(4);
		list.setSelectionMode(ListSelectionModel.SINGLE_INTERVAL_SELECTION);
		list.setCellRenderer(new FilePathListCellRenderer());
		add(new JScrollPane(list), BorderLayout.CENTER);
		add(createButtonPane(), BorderLayout.EAST);
		chooser = new JFileChooser(".");
		chooser.setMultiSelectionEnabled(true);
	}
	
	public List<File> getFiles() {
		return model.getAll();
	}
	
	public void setFiles(File[] files) {
		model.setAll(files);
	}
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public List<File> getSelectedFiles() {
		return (List) CollectionUtil.toList(list.getSelectedValues());
	}

	void add() {
        int action = chooser.showOpenDialog(this);
        if (action == JFileChooser.APPROVE_OPTION ) {
            File[] selectedFiles = chooser.getSelectedFiles();
            for (File file : selectedFiles)
            	model.add(file);
        }
	}

	void remove() {
		int[] selectedIndices = list.getSelectedIndices();
		if (selectedIndices != null && selectedIndices.length > 0)
			for (int i = selectedIndices.length - 1; i >= 0; i--)
				model.remove(selectedIndices[i]);
	}

	void up() {
		ListSelectionModel selectionModel = list.getSelectionModel();
		int from = selectionModel.getMinSelectionIndex();
		int to = selectionModel.getMaxSelectionIndex();
		if (from > 0) {
			File top = (File) model.getElementAt(from - 1);
			for (int i = from; i <= to; i++)
				model.set(i - 1, (File) model.getElementAt(i));
			model.set(to, top);
			selectionModel.setSelectionInterval(from - 1, to - 1);
		}
	}

	void down() {
		ListSelectionModel selectionModel = list.getSelectionModel();
		int from = selectionModel.getMinSelectionIndex();
		int to = selectionModel.getMaxSelectionIndex();
		if (from >= 0 && to < model.getSize() - 1) {
			File bottom = (File) model.getElementAt(to + 1);
			for (int i = to; i >= from; i--)
				model.set(i + 1, (File) model.getElementAt(i));
			model.set(from, bottom);
			selectionModel.setSelectionInterval(from + 1, to + 1);
		}
	}

	private Component createButtonPane() {
		Box box = Box.createVerticalBox();
		
		box.add(new JButton(new AddAction()));
		box.add(new JButton(new RemoveAction()));
		box.add(new JButton(new UpAction()));
		box.add(new JButton(new DownAction()));
		return box;
	}
	
	abstract class I18NAction extends AbstractAction {

		private static final long serialVersionUID = -2715029879380393355L;

		public I18NAction(String name, Icon icon) {
			super(i18n.getString(name), icon);
		}

		public I18NAction(String name) {
			super(i18n.getString(name));
		}
	}
	
	abstract class ItemAction extends I18NAction implements ListSelectionListener {

		private static final long serialVersionUID = -8786683453337626445L;

		public ItemAction(String name, Icon icon) {
			super(name, icon);
			list.getSelectionModel().addListSelectionListener(this);
			setEnabled(false);
		}

		public ItemAction(String name) {
			this(name, null);
		}
		
		@Override
		public void valueChanged(ListSelectionEvent evt) {
			setEnabled(list.getSelectedValues().length > 0);
		}
	}

	class AddAction extends I18NAction {
		
		private static final long serialVersionUID = -3461862378813809410L;

		public AddAction() {
			super("add");
		}

		@Override
		public void actionPerformed(ActionEvent evt) {
			add();
		}
	}

	private class RemoveAction extends ItemAction {
		
		private static final long serialVersionUID = 5780452799713264135L;

		public RemoveAction() {
			super("remove");
		}

		@Override
		public void actionPerformed(ActionEvent evt) {
			remove();
		}
	}

	private class UpAction extends ItemAction {
		
		private static final long serialVersionUID = 7999681931602008188L;

		public UpAction() {
			super("up");
		}

		@Override
		public void actionPerformed(ActionEvent evt) {
			up();
		}
	}

	private class DownAction extends ItemAction {
		
		private static final long serialVersionUID = 7651483775902229133L;

		public DownAction() {
			super("down");
		}

		@Override
		public void actionPerformed(ActionEvent evt) {
			down();
		}
	}
}
