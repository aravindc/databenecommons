/*
 * (c) Copyright 2005-2011 by Volker Bergmann. All rights reserved.
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

import javax.swing.*;
import java.awt.*;

/**
 * A panel that allows for easy component aligning by an underlying {@link GridBagLayout}.<br/>
 * <br/>
 * Created: 20.03.2005 11:19:06
 * @since 0.5.13
 * @author Volker Bergmann
 */
public class AlignedPane extends JPanel implements SwingConstants {

	private static final long serialVersionUID = -5911452561809191394L;
	
	private int orientation;
    private GridBagConstraints gbc;
    private int columns;

    public static AlignedPane createHorizontalPane() {
        return new AlignedPane(HORIZONTAL, 0);
    }

    public static AlignedPane createVerticalPane() {
        return createVerticalPane(2);
    }

    public static AlignedPane createVerticalPane(int columns) {
        return new AlignedPane(VERTICAL, columns);
    }

    protected AlignedPane() {
        this(VERTICAL, 2);
    }

    protected AlignedPane(int orientation, int columns) {
    	this.columns = columns;
        this.orientation = orientation;
        if (orientation == VERTICAL) {
            setLayout(new GridBagLayout());
            gbc = new GridBagConstraints(
                0, 0, 1, 1, 0, 0,
                GridBagConstraints.NORTHWEST, GridBagConstraints.BOTH,
                new Insets(2, 2, 2, 2), 2, 2);

        } else if (orientation == HORIZONTAL)
            setLayout(new FlowLayout());
        else
            throw new IllegalArgumentException();

    }
    
    // interface -------------------------------------------------------------------------------------------------------

    public void addElement(String title, Component component) {
        addElement(title, component, 1);
    }

    public void addElement(String title, Component component, int gridwidth) {
        JLabel label = new JLabel(title);
        if (orientation == HORIZONTAL) {
            add(label);
            add(component);
        } else {
        	if (gbc.gridx > 0)
        		label.setHorizontalAlignment(RIGHT);
            addElement(label, 1);
            addElement(component, gridwidth);
        }
    }

    public void addElement(Component component) {
    	addElement(component, 1);
    }

    public void addElement(Component component, int gridwidth) {
        if (orientation == HORIZONTAL)
            add(component);
        else {
            gbc.weightx = (component instanceof JLabel ? 0 : 1);
            gbc.weighty = (component instanceof JTextArea ? 1 : 0);
            gbc.gridwidth = gridwidth;
            add(component, gbc);
            gbc.gridx += gridwidth;
        }
    }

    public void endRow() {
        if (orientation == VERTICAL && gbc.gridx > 0)
            newRow();
    }

	private void newRow() {
		gbc.gridy++;
		gbc.gridx = 0;
	}

    public void addRow(String title, Component component) {
        JLabel label = new JLabel(title);
        if (orientation == HORIZONTAL) {
            add(label);
            add(component);
        } else {
            endRow();
            addElement(title, component, columns - 1);
            endRow();
        }
    }

    public void addRow(Component component) {
        if (orientation == HORIZONTAL) {
            add(component, gbc);
            gbc.gridx++;
        } else {
            endRow();
            gbc.weightx = 1;
            gbc.weighty = (component instanceof JTextArea ? 1 : 0);
            gbc.gridwidth = columns;
            add(component, gbc);
            newRow();
        }
    }

    public void addTallRow(Component component) {
        if (orientation == HORIZONTAL) {
            add(component);
        } else {
            endRow();
            gbc.weightx = 1;
            gbc.weighty = 1;
            gbc.gridwidth = columns;
            add(component, gbc);
            newRow();
        }
    }

    public void addSeparator() {
        addLabelRow(" ");
    }

    public void addLabelRow(String text) {
        addRow(new JLabel(text));
    }
    
}
