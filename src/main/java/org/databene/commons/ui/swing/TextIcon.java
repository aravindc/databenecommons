/*
 * (c) Copyright 2013 by Volker Bergmann. All rights reserved.
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

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;

import javax.swing.Icon;
import javax.swing.UIManager;

/**
 * {@link Icon} implementation that renders a text.<br/><br/>
 * Created: 13.12.2013 08:28:56
 * @since 0.5.25
 * @author Volker Bergmann
 */

public class TextIcon implements Icon {

	private String text;
	private Color background;
	private Color foreground;
	private Font font;

	private int iconWidth;
	private int iconHeight;
	private int textWidth;
	private int ascent;


	// constructors ----------------------------------------------------------------------------------------------------

	public TextIcon(String text) {
		this(text, Color.BLACK, null);
	}

	public TextIcon(String text, Color foreground, Color background) {
		this(text, foreground, background, false, false);
	}

	public TextIcon(String text, Color foreground, Color background, boolean square, boolean bold) {
		this(text, foreground, background, square, defaultFont(bold));
	}

	public TextIcon(String text, Color foreground, Color background, boolean square, Font font) {
		this.text = text;
		this.font = font;
		FontMetrics metrics = new Canvas().getFontMetrics(font);
		this.textWidth = metrics.stringWidth(text);
		this.iconWidth = textWidth;
		this.iconHeight = font.getSize() + 2;
		if (square) {
			this.iconWidth = Math.max(this.iconWidth, this.iconHeight);
			this.iconHeight = this.iconWidth;
		}
		this.ascent = metrics.getAscent();
		this.foreground = foreground;
		this.background = background;
	}


	// properties ------------------------------------------------------------------------------------------------------

	@Override
	public int getIconHeight() {
		return iconHeight;
	}

	public void setIconHeight(int iconHeight) {
		this.iconHeight = iconHeight;
	}

	@Override
	public int getIconWidth() {
		return iconWidth;
	}

	public void setIconWidth(int iconWidth) {
		this.iconWidth = iconWidth;
	}


	// rendering methods -----------------------------------------------------------------------------------------------

	@Override
	public void paintIcon(Component c, Graphics g, int x, int y) {
		if (background != null) {
			g.setColor(background);
			g.fillRect(x, y, iconWidth, iconHeight);
		}
		g.setColor(foreground);
		Font origFont = g.getFont();
		g.setFont(font);
		g.drawString(text, x + (iconWidth - textWidth) / 2, y + ascent - 1);
		g.setFont(origFont);
	}


	// private helpers -------------------------------------------------------------------------------------------------

	private static Font defaultFont(boolean bold) {
		Font tableFont = UIManager.getDefaults().getFont("Table.font");
		if (tableFont.isBold() != bold)
			return new Font(tableFont.getFamily(), (bold ? Font.BOLD : Font.PLAIN), tableFont.getSize());
		else
			return tableFont;
	}

}
