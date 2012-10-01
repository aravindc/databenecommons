/*
 * (c) Copyright 2008-2012 by Volker Bergmann. All rights reserved.
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

package org.databene.commons.ui.awt;

import java.awt.Component;
import java.awt.FileDialog;
import java.awt.Frame;
import java.io.File;

import org.databene.commons.SystemInfo;
import org.databene.commons.ui.FileOperation;
import org.databene.commons.ui.FileTypeSupport;
import org.databene.commons.ui.FileChooser;

/**
 * AWT based implementation of the {@link FileChooser} interface.<br/>
 * <br/>
 * Created at 14.12.2008 14:36:28
 * @since 0.5.13
 * @author Volker Bergmann
 */

public class AwtFileChooser extends FileDialog implements FileChooser {

	private static final long serialVersionUID = -3217299586317875276L;
	
	FileTypeSupport supportedTypes;
	
	public AwtFileChooser(String prompt, FileOperation operation, FileTypeSupport supportedTypes) {
		super((Frame) null, prompt, (operation == FileOperation.OPEN ? FileDialog.LOAD : FileDialog.SAVE));
		this.supportedTypes = supportedTypes;
	}

	@Override
	public File chooseFile(Component owner) {
		if (supportedTypes == FileTypeSupport.directoriesOnly)
				System.setProperty("apple.awt.fileDialogForDirectories", "true");
		setVisible(true);
		System.setProperty("apple.awt.fileDialogForDirectories", "false");
		return getSelectedFile();
	}

	@Override
	public void setCurrentDirectory(File currentDirectory) {
		if (currentDirectory == null)
			currentDirectory = new File(SystemInfo.getCurrentDir());
		setDirectory(currentDirectory.getAbsolutePath());
	}
	
	@Override
	public void setSelectedFile(File file) {
		setDirectory(file.getParent());
		setFile(file.getName());
	}

	@Override
	public File getSelectedFile() {
		if (getFile() == null)
			return null;
		return new File(getDirectory(), getFile());
	}

}
