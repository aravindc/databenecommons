/*
 * (c) Copyright 2008-2011 by Volker Bergmann. All rights reserved.
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

package org.databene.commons.ui.swing.delegate;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

import javax.swing.JCheckBox;

import org.databene.commons.BeanUtil;
import org.databene.commons.NullSafeComparator;
import org.databene.commons.bean.ObservableBean;

/**
 * {@link JCheckBox} implementation that serves as delegate of a property of a JavaBean object.<br/>
 * <br/>
 * Created at 02.12.2008 15:03:32
 * @since 0.5.13
 * @author Volker Bergmann
 */

public class PropertyCheckBox extends JCheckBox {

	private static final long serialVersionUID = 2502918170512919334L;
	
	private Object bean;
	private String propertyName;
	boolean locked;

	public PropertyCheckBox(Object bean, String propertyName, String label) {
		super(label);
		this.bean = bean;
		this.propertyName = propertyName;
		this.locked = true;
		Listener listener = new Listener();
		if (bean instanceof ObservableBean)
			((ObservableBean) bean).addPropertyChangeListener(propertyName, listener);
		this.getModel().addActionListener(listener);
		this.locked = false;
		refresh();
	}

	/**
	 * reads the current property value and writes it to the text field.
	 */
	void refresh() {
		if (!locked) {
			locked = true;
			boolean propertyValue = (Boolean) BeanUtil.getPropertyValue(bean, propertyName);
			boolean selected = isSelected();
			if (selected != propertyValue)
				setSelected(propertyValue);
			locked = false;
		}
	}
	
	/**
	 * writes the current text field content to the property.
	 */
	void update() {
		if (!locked) {
			locked = true;
			Boolean propertyValue = (Boolean) BeanUtil.getPropertyValue(bean, propertyName);
			Boolean selected = isSelected();
			if (!NullSafeComparator.equals(selected, propertyValue))
				BeanUtil.setPropertyValue(bean, propertyName, selected);
			locked = false;
		}
	}
	
	class Listener implements PropertyChangeListener, ActionListener {

		public void propertyChange(PropertyChangeEvent evt) {
			refresh();
		}

		public void actionPerformed(ActionEvent arg0) {
			update();
		}
	}
}
