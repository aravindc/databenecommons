/*
 * (c) Copyright 2016 by Volker Bergmann. All rights reserved. 
 * 
 * This program and the accompanying materials are made available under the 
 * terms of the GNU Lesser General Public License (LGPL) version 2.1 
 * which accompanies this distribution, and is available at 
 * http://www.gnu.org/licenses/lgpl-2.1.html
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

import java.awt.Toolkit;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;
import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;
import javax.swing.JTextField;

/**
 * Copies the selected content of a {@link JTextField} component to the clipboard. 
 * Created: 10.04.2016 11:29:03
 * @since 1.0.9
 * @author Volker Bergmann
 */

public class CopyFromTextFieldAction extends AbstractAction {
	
	private static final long serialVersionUID = 1L;

	private JTextField textField;
	
	public CopyFromTextFieldAction(JTextField textField) {
		super("Copy");
		this.textField = textField;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
		String textToCopy = textField.getSelectedText();
		if (textToCopy == null)
			textToCopy = textField.getText();
		StringSelection selection = new StringSelection(textToCopy);
	    clipboard.setContents(selection, selection);
	}
	
}
