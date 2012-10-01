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

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.File;
import java.util.List;

import javax.swing.event.ListDataEvent;
import javax.swing.event.ListDataListener;

import org.databene.commons.BeanUtil;
import org.databene.commons.CollectionUtil;
import org.databene.commons.bean.ObservableBean;
import org.databene.commons.ui.I18NSupport;
import org.databene.commons.ui.swing.FileList;

/**
 * {@link FileList} implementation that serves as delegate of a property of a JavaBean object.<br/>
 * <br/>
 * Created at 30.11.2008 15:06:20
 * @since 0.5.13
 * @author Volker Bergmann
 */

public class PropertyFileList extends FileList {

	// attributes ------------------------------------------------------------------------------------------------------
	
	private static final long serialVersionUID = -1259803129031396860L;
	
	private Object bean;
	private String propertyName;

	boolean locked;
	
	// constructor -----------------------------------------------------------------------------------------------------
	
	public PropertyFileList(Object bean, String propertyName, I18NSupport i18n) {
		super(i18n);
		this.bean = bean;
		this.propertyName = propertyName;
		this.locked = true;
		refresh();
		Listener listener = new Listener();
		model.addListDataListener(listener);
		if (bean instanceof ObservableBean)
			((ObservableBean) bean).addPropertyChangeListener(propertyName, listener);
		this.locked = false;
	}
	
	// event handlers --------------------------------------------------------------------------------------------------

	/**
	 * reads the current property value and writes it to the file list.
	 */
	void refresh() {
		if (!locked) {
			locked = true;
			Object propertyValue = BeanUtil.getPropertyValue(bean, propertyName);
			File[] files = (File[]) propertyValue;
			if (!CollectionUtil.ofEqualContent(getFiles(), files))
				setFiles(files);
			locked = false;
		}
	}
	
	/**
	 * writes the current file field content to the property.
	 */
	void update() {
		if (!locked) {
			locked = true;
			File[] modelFiles = (File[]) BeanUtil.getPropertyValue(bean, propertyName);
			List<File> viewFiles = getFiles();
			if (!CollectionUtil.ofEqualContent(viewFiles, modelFiles)) {
				File[] files = CollectionUtil.toArray(viewFiles, File.class);
				BeanUtil.setPropertyValue(bean, propertyName, files);
			}
			locked = false;
		}
	}
	
	class Listener implements PropertyChangeListener, ListDataListener {

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

	}
}
