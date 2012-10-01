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

package org.databene.commons.ui.swing;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;

import javax.swing.AbstractAction;
import javax.swing.Box;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JDialog;
import javax.swing.KeyStroke;

/**
 * {@link JDialog} which applies useful standard behaviour.<br/><br/>
 * Created: 22.08.2012 07:31:08
 * @since 0.5.18
 * @author Volker Bergmann
 */
@SuppressWarnings("serial")
public class SimpleDialog<E extends Component> extends JDialog {

	private E mainComponent;
	
	protected boolean cancelled;

	AbstractAction okAction;
	AbstractAction cancelAction;

	public SimpleDialog(Component parentComponent, String title, boolean modal, E mainComponent) {
		super(SwingUtil.getWindowForComponent(parentComponent), title, ModalityType.APPLICATION_MODAL);
		this.cancelled = false;
		
		// Set up main component
		this.mainComponent = mainComponent;
		add(mainComponent, BorderLayout.CENTER);
		
		// setup buttons
		this.okAction = new AbstractAction("OK") {
			@Override
			public void actionPerformed(ActionEvent e) {
				cancelled = false;
				setVisible(false);
			}
		};
		this.cancelAction = new AbstractAction("Cancel") {
			@Override
			public void actionPerformed(ActionEvent e) {
				cancelled = true;
				setVisible(false);
			}
		};
		add(createButtonBar(), BorderLayout.SOUTH);
		
		// assure that the dialog is closed if the user hits Escape
	    getRootPane().registerKeyboardAction(cancelAction, 
	    		KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0),
	            JComponent.WHEN_IN_FOCUSED_WINDOW);
	    
	    // pack and position the dialog 
		setResizable(false);
		pack();
		setLocationRelativeTo(parentComponent);
	}

	public static <T extends Component> T showModalDialog(T mainComponent, String title, Component parentComponent) {
		SimpleDialog<T> dialog = new SimpleDialog<T>(parentComponent, title, true, mainComponent);
		dialog.setVisible(true);
		dialog.dispose();
		return (dialog.wasCancelled() ? null : dialog.getMainComponent());
	}

	public E getMainComponent() {
		return mainComponent;
	}
	
	public boolean wasCancelled() {
		return cancelled;
	}

	private Component createButtonBar() {
		Box box = Box.createHorizontalBox();
		box.add(Box.createHorizontalGlue());
		JButton okButton = new JButton(okAction);
		box.add(okButton);
		getRootPane().setDefaultButton(okButton);
		box.add(new JButton(cancelAction));
		box.add(Box.createHorizontalGlue());
		return box;
	}

}
