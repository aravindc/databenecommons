/*
 * (c) Copyright 2007-2012 by Volker Bergmann. All rights reserved.
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

package org.databene.commons.ui.swing;

import org.databene.commons.SystemInfo;
import org.databene.commons.file.FilenameFormat;
import org.databene.commons.ui.FileChooser;
import org.databene.commons.ui.FileOperation;
import org.databene.commons.ui.FileTypeSupport;
import org.databene.commons.ui.awt.AwtFileChooser;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

import java.io.File;
import java.awt.BorderLayout;
import java.awt.TextField;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.util.List;
import java.util.ArrayList;

/**
 * Lets the user choose a {@link File} with {@link TextField} and {@link JFileChooser}.<br/>
 * <br/>
 * Created: 05.05.2007 00:10:38
 * @since 0.5.13
 * @author Volker Bergmann
 */
public class FileField extends Box {
	
	private static final long serialVersionUID = 2088339867450647264L;
	
    boolean fullPathDisplayed;
    private FilenameFormat filenameFormat;
    JTextField filenameField;
    FileChooser chooser;
    private List<ActionListener> actionListeners;
    FileOperation operation;
    String approveButtonText;
    JButton button;
    
    // constructors ----------------------------------------------------------------------------------------------------

    public FileField() {
        this(20);
    }

    public FileField(int columns) {
        this(columns, null, FileTypeSupport.filesOnly, FileOperation.OPEN);
    }

    public FileField(int columns, File file, FileTypeSupport fileTypeSupport) {
    	this(columns, file, fileTypeSupport, (FileOperation) null);
    }

    public FileField(int columns, File file, FileTypeSupport supportedTypes, FileOperation operation) {
    	super(BoxLayout.X_AXIS);
    	setBorder(null);
        this.fullPathDisplayed = true;
        this.operation = operation;
        filenameField = new JTextField(columns);
        if (SystemInfo.isMacOsx())
        	chooser = new AwtFileChooser(null, operation, supportedTypes);
        else
	        chooser = new SwingFileChooser(supportedTypes, operation);
        if (file != null && file.exists()) {
            chooser.setSelectedFile(file);
            filenameField.setText(file.getAbsolutePath());
        }
        add(filenameField, BorderLayout.CENTER);
        filenameField.getDocument().addDocumentListener(new TextFieldListener());
        button = new JButton("...");
        add(button, BorderLayout.EAST);
        button.addActionListener(new ButtonListener());
        filenameFormat = new FilenameFormat(true);
        this.actionListeners = new ArrayList<ActionListener>();
    }

    // properties ------------------------------------------------------------------------------------------------------
    
    public File getFile() {
    	return new File(filenameField.getText());
    }

    public void setFile(File file) {
		chooser.setSelectedFile(file);
        filenameField.setText(file.getAbsolutePath());
    }

    public boolean isFullPathUsed() {
        return filenameFormat.isFullPathUsed();
    }

    public void setFullPathUsed(boolean fullPathUsed) {
        filenameFormat.setFullPathUsed(fullPathUsed);
    }

    public void addActionListener(ActionListener listener) {
        actionListeners.add(listener);
    }

    public void removeActionListener(ActionListener listener) {
        actionListeners.remove(listener);
    }
    
    @Override
    public void setEnabled(boolean enabled) {
        super.setEnabled(enabled);
        filenameField.setEnabled(enabled);
        button.setEnabled(enabled);
    }
    
    // private helpers -------------------------------------------------------------------------------------------------

    void fireAction(String command) {
        ActionEvent e = new ActionEvent(this, 0, command);
        for (int i = actionListeners.size() - 1; i >= 0; i--)
            actionListeners.get(i).actionPerformed(e);
    }

    class ButtonListener implements ActionListener {
        @Override
		public void actionPerformed(ActionEvent e) {
            File file = null;
            String path = filenameField.getText();
            if (path.length() > 0) {
                file = new File(path);
                if (!file.exists())
                    file = null;
            }
            if (file != null) {
                chooser.setCurrentDirectory(file.getParentFile());
                chooser.setSelectedFile(file);
            }
            File selectedFile = chooser.chooseFile(FileField.this);
            if (selectedFile != null) {
                filenameField.setText(selectedFile.getAbsolutePath());
                fireAction("files");
            }
        }
    }

	public class TextFieldListener implements DocumentListener {

		@Override
		public void changedUpdate(DocumentEvent e) {
			update(e);
		}

		@Override
		public void insertUpdate(DocumentEvent e) {
			update(e);
		}

		@Override
		public void removeUpdate(DocumentEvent e) {
			update(e);
		}

		private void update(DocumentEvent e) {
			fireAction("files");
		}

	}

}
