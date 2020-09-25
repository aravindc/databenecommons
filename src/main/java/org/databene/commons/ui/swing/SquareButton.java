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

import java.awt.Dimension;

import javax.swing.Action;
import javax.swing.Icon;
import javax.swing.JButton;

/**
 * Provides a square button.<br><br>
 * Created: 25.05.2016 17:28:12
 * @since 1.0.10
 * @author Volker Bergmann
 */

public class SquareButton extends JButton {

	private static final long serialVersionUID = 1L;

	public SquareButton() {
		super();
	}

	public SquareButton(Action action) {
		super(action);
	}

	public SquareButton(String text) {
		super(text);
	}

	public SquareButton(Icon icon) {
		super(icon);
	}

	@Override
	public Dimension getPreferredSize() {
		Dimension preferredSize = super.getPreferredSize();
		preferredSize.width = preferredSize.height;
		return preferredSize;
	}

}
