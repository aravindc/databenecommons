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
import java.io.File;

import org.databene.commons.BeanUtil;
import org.databene.commons.NullSafeComparator;
import org.databene.commons.bean.ObservableBean;
import org.databene.commons.ui.FileOperation;
import org.databene.commons.ui.FileTypeSupport;
import org.databene.commons.ui.swing.FileField;

/**
 * {@link FileField} implementation that serves as delegate of a property of a JavaBean object.<br/>
 * <br/>
 * Created at 30.11.2008 00:22:14
 * @since 0.5.13
 * @author Volker Bergmann
 */
public class PropertyFileField extends FileField {
	
	// attributes ------------------------------------------------------------------------------------------------------
	
	private static final long serialVersionUID = -3970525222511845399L;
	
	private Object bean;
	private String propertyName;

	boolean locked;
	
	// constructors ----------------------------------------------------------------------------------------------------
	
	public PropertyFileField(Object bean, String propertyName, int length, FileTypeSupport typeSupport, FileOperation operation) {
		super(length, null, typeSupport, operation);
		init(bean, propertyName);
	}

	private void init(Object bean, String propertyName) {
		this.bean = bean;
		this.propertyName = propertyName;
		this.locked = true;
		refresh();
		Listener listener = new Listener();
		if (bean instanceof ObservableBean)
			((ObservableBean) bean).addPropertyChangeListener(propertyName, listener);
		this.addActionListener(listener);
		this.locked = false;
		File value = (File) BeanUtil.getPropertyValue(bean, propertyName);
		if (value != null)
			setFile(value);
	}
	
	// event handlers --------------------------------------------------------------------------------------------------

	/**
	 * reads the current property value and writes it to the file field.
	 */
	void refresh() {
		if (!locked) {
			locked = true;
			Object propertyValue = BeanUtil.getPropertyValue(bean, propertyName);
			File file = (File) propertyValue;
			if (!NullSafeComparator.equals(getFile(), file))
				setFile(file);
			locked = false;
		}
	}
	
	/**
	 * writes the current file field content to the property.
	 */
	void update() {
		if (!locked) {
			locked = true;
			File file = getFile();
			if (!NullSafeComparator.equals(file, BeanUtil.getPropertyValue(bean, propertyName)))
				BeanUtil.setPropertyValue(bean, propertyName, file);
			locked = false;
		}
	}
	
	class Listener implements PropertyChangeListener, ActionListener {

		public void propertyChange(PropertyChangeEvent evt) {
			refresh();
		}

		public void actionPerformed(ActionEvent evt) {
			update();
		}

	}
}
