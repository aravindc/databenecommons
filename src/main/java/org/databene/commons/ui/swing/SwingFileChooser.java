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

import java.awt.Component;
import java.io.File;

import javax.swing.JFileChooser;

import org.databene.commons.Assert;
import org.databene.commons.ui.FileChooser;
import org.databene.commons.ui.FileOperation;
import org.databene.commons.ui.FileTypeSupport;

/**
 * Swing implementation of the {@link FileChooser} interface.
 * Created at 14.12.2008 14:34:50
 * @since 0.5.13
 * @author Volker Bergmann
 */

public class SwingFileChooser extends JFileChooser implements FileChooser {
	
	private static final long serialVersionUID = 3258145358496737942L;
	
	private FileOperation operation;

	public SwingFileChooser(FileTypeSupport supportedTypes, FileOperation operation) {
		Assert.notNull(supportedTypes, "supportedTypes");
		Assert.notNull(operation, "operation");
		this.operation = operation;
		super.setFileSelectionMode(fileSelectionMode(supportedTypes));
		super.setDialogType(dialogType(operation));
	}
	
	@Override
	public void setTitle(String title) {
		setDialogTitle(title);
	}
	
	@Override
	public File chooseFile(Component component) {
		int approval;
		switch (operation) {
			case OPEN: approval = showOpenDialog(component); break;
			case SAVE: approval = showSaveDialog(component); break;
			default:   approval = showDialog(component, "Choose"); break;
		}
		return (approval == APPROVE_OPTION ? super.getSelectedFile() : null);
	}
	
	// private helpers -------------------------------------------------------------------------------------------------

	private static int dialogType(FileOperation operation) {
		switch (operation) {
			case OPEN: return JFileChooser.OPEN_DIALOG;
			case SAVE: return JFileChooser.SAVE_DIALOG;
			default: return JFileChooser.CUSTOM_DIALOG;
		}
	}

	private static int fileSelectionMode(FileTypeSupport supportedTypes) {
		switch (supportedTypes) {
			case directoriesOnly: return JFileChooser.DIRECTORIES_ONLY;
			case filesOnly: return JFileChooser.FILES_ONLY;
			case filesAndDirectories: return JFileChooser.FILES_AND_DIRECTORIES;
			default: throw new IllegalArgumentException("Illegal option: " + supportedTypes);
		}
	}

}
