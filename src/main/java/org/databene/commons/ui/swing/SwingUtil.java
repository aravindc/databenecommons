/*
 * Copyright (C) 2004-2014 Volker Bergmann (volker.bergmann@bergmann-it.de).
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

import javax.swing.*;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableRowSorter;

import org.databene.commons.BeanUtil;

import java.awt.*;
import java.awt.geom.Rectangle2D;

/**
 * Provides Swing utilities.
 * Created: 23.04.2007 22:41:21
 * 
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
		Dimension screenSize = getScreenSize();
		int x = (screenSize.width - component.getWidth()) / 2;
		int y = (screenSize.height - component.getHeight()) / 2;
		component.setLocation(x, y);
	}

	public static Dimension getScreenSize() {
		return Toolkit.getDefaultToolkit().getScreenSize();
	}

	public static <T extends Component> T showInModalDialog(T mainComponent, String title, boolean cancellable,
			Component parentComponent) {
		return SimpleDialog.showModalDialog(mainComponent, title, cancellable, parentComponent);
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
			maxSize.width = Math.max(maxSize.width, (int) textBounds.getWidth());
			maxSize.height = Math.max(maxSize.height, (int) textBounds.getHeight());
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

	public static void scrollToTableCell(JTable table, int rowIndex, int colIndex) {
		if (!(table.getParent() instanceof JViewport))
			return;
		JViewport viewport = (JViewport) table.getParent();
		Rectangle rect = table.getCellRect(rowIndex, colIndex, true);
		Point p = viewport.getViewPosition();
		rect.setLocation(rect.x - p.x, rect.y - p.y);
		table.scrollRectToVisible(rect);
	}
	
}
