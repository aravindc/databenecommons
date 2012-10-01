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

package org.databene.commons.ui.swing.delegate;

import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

import javax.swing.DefaultListCellRenderer;
import javax.swing.JComboBox;
import javax.swing.JList;
import javax.swing.event.ListDataEvent;
import javax.swing.event.ListDataListener;

import org.databene.commons.BeanUtil;
import org.databene.commons.NullSafeComparator;
import org.databene.commons.bean.ObservableBean;
import org.databene.commons.converter.ToStringConverter;
import org.databene.commons.ui.I18NSupport;

/**
 * {@link JComboBox} implementation that serves as delegate of a property of a JavaBean object.<br/>
 * <br/>
 * Created at 01.12.2008 07:44:32
 * @since 0.5.13
 * @author Volker Bergmann
 */

public class PropertyComboBox extends JComboBox {

	private static final long serialVersionUID = -5039037135360506124L;
	
	private Object bean;
	private String propertyName;
	boolean locked;
	

	public PropertyComboBox(Object bean, String propertyName, I18NSupport i18n, String prefix, Object ... values) {
		super(values);
		this.bean = bean;
		this.propertyName = propertyName;
		this.locked = true;
		Listener listener = new Listener();
		this.addActionListener(listener);
		if (bean instanceof ObservableBean)
			((ObservableBean) bean).addPropertyChangeListener(propertyName, listener);
		this.getModel().addListDataListener(listener);
		this.setRenderer(new Renderer(i18n, prefix));
		this.locked = false;
		refresh();
	}

	/**
	 * reads the current property value and writes it to the text field.
	 */
	void refresh() {
		if (!locked) {
			locked = true;
			Object propertyValue = BeanUtil.getPropertyValue(bean, propertyName);
			Object selectedItem = getSelectedItem();
			if (!NullSafeComparator.equals(selectedItem, propertyValue))
				setSelectedItem(propertyValue);
			locked = false;
		}
	}
	
	/**
	 * writes the current text field content to the property.
	 */
	void update() {
		if (!locked) {
			locked = true;
			Object propertyValue = BeanUtil.getPropertyValue(bean, propertyName);
			Object selectedItem = getSelectedItem();
			if (!NullSafeComparator.equals(selectedItem, propertyValue))
				BeanUtil.setPropertyValue(bean, propertyName, selectedItem);
			locked = false;
		}
	}
	
	class Listener implements PropertyChangeListener, ListDataListener, ActionListener {

		@Override
		public void propertyChange(PropertyChangeEvent evt) {
			refresh();
		}

		@Override
		public void contentsChanged(ListDataEvent evt) {
			update();
		}

		@Override
		public void intervalAdded(ListDataEvent evt) {
			update();
		}

		@Override
		public void intervalRemoved(ListDataEvent evt) {
			update();
		}

		@Override
		public void actionPerformed(ActionEvent e) {
	        update();
        }
		
	}
	
	static final class Renderer extends DefaultListCellRenderer {
		
		private static final long serialVersionUID = 8358429951305253637L;
		private ToStringConverter converter = new ToStringConverter();
		private I18NSupport i18n;
		private String prefix;

		public Renderer(I18NSupport i18n, String prefix) {
			this.i18n = i18n;
			this.prefix = prefix;
		}

		@Override
		public Component getListCellRendererComponent(JList list, Object value,
				int index, boolean isSelected, boolean cellHasFocus) {
			String text = (i18n != null ? i18n.getString(prefix + converter.convert(value)) : String.valueOf(value));
			return super.getListCellRendererComponent(list, text, index, isSelected, cellHasFocus);
		}
	}

}
