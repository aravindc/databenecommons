/*
 * (c) Copyright 2007-2013 by Volker Bergmann. All rights reserved.
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
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableRowSorter;

import org.databene.commons.BeanUtil;

import java.awt.*;
import java.awt.geom.Rectangle2D;

/**
 * Provides Swing utilities.<br/>
 * <br/>
 * Created: 23.04.2007 22:41:21
 * @since 0.5.13
 * @author Volker Bergmann
 */
public class SwingUtil {

    public static void repaintLater(final Component component) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
			public void run() {
                component.repaint();
            }
        });
    }

    public static void center(Component component) {
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        int x = (screenSize.width - component.getWidth()) / 2;
        int y = (screenSize.height - component.getHeight()) / 2;
        component.setLocation(x, y);
    }
    
    public static <T extends Component> T showInModalDialog(T mainComponent, String title, Component parentComponent) {
    	return SimpleDialog.showModalDialog(mainComponent, title, parentComponent);
    }

    public static void showInFrame(Component component, String title) {
        JFrame frame = new JFrame(title);
        frame.getContentPane().add(component, BorderLayout.CENTER);
        frame.pack();
        center(frame);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
    }
    
    public static Rectangle fitRectangles(Dimension imageSize, Dimension size) {
        double aspectX = (double) size.width / imageSize.width;
        double aspectY = (double) size.height / imageSize.height;
        double aspect = Math.min(aspectX, aspectY);
        int paintedWidth = (int) (imageSize.width * aspect);
        int paintedHeight = (int) (imageSize.height * aspect);
        int x = (size.width - paintedWidth) / 2;
        int y = (size.height - paintedHeight) / 2;
        return new Rectangle(x, y, paintedWidth, paintedHeight);
    }
    
    public static boolean isLookAndFeelNative() {
    	return UIManager.getSystemLookAndFeelClassName().equals(UIManager.getLookAndFeel().getClass().getName());
    }
    
	public static Window getWindowForComponent(Component parentComponent) {
		if (parentComponent == null)
			return null;
		if (parentComponent instanceof Frame || parentComponent instanceof Dialog)
			return (Window) parentComponent;
		return getWindowForComponent(parentComponent.getParent());
	}
	
	public static void equalizeButtonSizes(Graphics g, JButton... buttons) {

		String[] labels = BeanUtil.extractProperties(buttons, "text", String.class);

		// Get the largest width and height
		Dimension maxSize = new Dimension(0, 0);
		Rectangle2D textBounds = null;
		JButton button0 = buttons[0];
		FontMetrics metrics = button0.getFontMetrics(button0.getFont());
		for (int i = 0; i < labels.length; ++i) {
			textBounds = metrics.getStringBounds(labels[i], g);
			maxSize.width = Math
					.max(maxSize.width, (int) textBounds.getWidth());
			maxSize.height = Math.max(maxSize.height,
					(int) textBounds.getHeight());
		}

		Insets insets = button0.getBorder().getBorderInsets(button0);
		maxSize.width += insets.left + insets.right;
		maxSize.height += insets.top + insets.bottom;

		// reset preferred and maximum size since BoxLayout takes both into account
		for (JButton button : buttons) {
			button.setPreferredSize((Dimension) maxSize.clone());
			button.setMaximumSize((Dimension) maxSize.clone());
		}
	}
	
	public static JButton createTransparentButton(Action action, boolean withText) {
		JButton button = new JButton(action);
		if (withText) {
			button.setVerticalTextPosition(SwingConstants.BOTTOM);
			button.setHorizontalTextPosition(SwingConstants.CENTER);
		} else {
			button.setText("");
			button.setMargin(new Insets(0, 0, 0, 0));
		}
		button.setOpaque(false);
		button.setContentAreaFilled(false);
		button.setBorderPainted(false);
		return button;
	}
	
	public static Color getUIPanelBackground() {
		return getUIColor("Panel.background");
	}

	public static Color getUIColor(String code) {
		Color color = UIManager.getColor(code);
		// workaround for issue with com.apple.laf.AquaNativeResources$CColorPaintUIResource which seems to be rendered with alpha=0
		return new Color(color.getRed(), color.getGreen(), color.getBlue(), color.getAlpha());
	}

	public static void bindKeyToAction(int keyCode, int modifiers, Action action, JComponent component) {
		bindKeyToAction(keyCode, modifiers, action, component, JComponent.WHEN_FOCUSED);
	}
	
	public static void bindKeyToAction(int keyCode, int modifiers, Action action, JComponent component, int condition) {
		KeyStroke keyStroke = KeyStroke.getKeyStroke(keyCode, modifiers);
		component.getInputMap(condition).put(keyStroke, action);
	}
	
	public static void autoSizeTableColumns(JTable table) {
		 for (int column = 0; column < table.getColumnCount(); column++) {
			 int columnWidth = 0;
			 for (int row = 0; row < table.getRowCount(); row++) {
			     TableCellRenderer renderer = table.getCellRenderer(row, column);
			     Component comp = table.prepareRenderer(renderer, row, column);
			     columnWidth = Math.max(comp.getPreferredSize().width, columnWidth);
			 }
			 table.getColumnModel().getColumn(column).setPreferredWidth(columnWidth);
		 }
	}
	
	public static void applyRowSorter(JTable table) {
		@SuppressWarnings({ "rawtypes", "unchecked" })
		TableRowSorter<?> sorter = new TableRowSorter(table.getModel());
		sorter.setSortsOnUpdates(true);
		table.setRowSorter(sorter);
	}

}
