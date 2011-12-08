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

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

import javax.swing.JTextField;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.text.BadLocationException;
import javax.swing.text.Document;

import org.databene.commons.BeanUtil;
import org.databene.commons.StringUtil;
import org.databene.commons.bean.ObservableBean;
import org.databene.commons.converter.ToStringConverter;

/**
 * {@link JTextField} implementation that serves as delegate of a property of a JavaBean object.<br/>
 * <br/>
 * Created at 17.07.2008 14:38:14
 * @since 0.5.13
 * @author Volker Bergmann
 */
public class PropertyTextField extends JTextField {
	
	/** uid for serialization */
	private static final long serialVersionUID = -6986676080916477887L;
	
	// attributes ------------------------------------------------------------------------------------------------------
	
	private Object bean;
	private String propertyName;

	private ToStringConverter toStringConverter;
	boolean locked;
	
	// constructor -----------------------------------------------------------------------------------------------------
	
	public PropertyTextField(Object bean, String propertyName, int length) {
		super(length);
		this.bean = bean;
		this.propertyName = propertyName;
		this.toStringConverter = new ToStringConverter();
		this.locked = true;
		Listener listener = new Listener();
		if (bean instanceof ObservableBean)
			((ObservableBean) bean).addPropertyChangeListener(propertyName, listener);
		this.getDocument().addDocumentListener(listener);
		this.locked = false;
		refresh();
	}
	
	// event handlers --------------------------------------------------------------------------------------------------

	/**
	 * reads the current property value and writes it to the text field.
	 */
	void refresh() {
		if (!locked) {
			locked = true;
			Object propertyValue = BeanUtil.getPropertyValue(bean, propertyName);
			String text = toStringConverter.convert(propertyValue);
			text = StringUtil.escape(text);
			if (!getText().equals(text))
				setText(text);
			locked = false;
		}
	}
	
	/**
	 * writes the current text field content to the property.
	 */
	void update() {
		if (!locked) {
			locked = true;
			Document document = getDocument();
			String text;
			try {
				text = document.getText(0, document.getLength());
			} catch (BadLocationException e) {
				throw new RuntimeException(e);
			}
			text = StringUtil.unescape(text);
			if (!text.equals(BeanUtil.getPropertyValue(bean, propertyName)))
				BeanUtil.setPropertyValue(bean, propertyName, text);
			locked = false;
		}
	}
	
	class Listener implements PropertyChangeListener, DocumentListener {

		public void propertyChange(PropertyChangeEvent evt) {
			refresh();
		}

		public void changedUpdate(DocumentEvent evt) {
			 update();
		}

		public void insertUpdate(DocumentEvent evt) {
			 update();
		}

		public void removeUpdate(DocumentEvent evt) {
			 update();
		}
		
	}
}
