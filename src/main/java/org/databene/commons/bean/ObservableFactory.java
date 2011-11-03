/*
 * (c) Copyright 2008 by Volker Bergmann. All rights reserved.
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

package org.databene.commons.bean;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.HashMap;
import java.util.Map;

import org.databene.commons.ArrayUtil;
import org.databene.commons.ConfigurationError;
import org.databene.commons.StringUtil;

/**
 * Creates {@link ObservableBean} implementations from interfaces.<br/>
 * <br/>
 * Created at 17.07.2008 14:57:53
 * @since 0.4.5
 * @author Volker Bergmann
 */
public class ObservableFactory {
	
	@SuppressWarnings("unchecked")
    public static <E extends ObservableBean> E create(Class<E> type) {
		if (!type.isInterface())
			throw new ConfigurationError("Not an interface: " + type);
		ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
		ObservableBeanInvocationHandler handler = new ObservableBeanInvocationHandler(type);
		E bean = (E) Proxy.newProxyInstance(classLoader, new Class[] { type }, handler);
		return bean;
	}
	
	private static class ObservableBeanInvocationHandler implements InvocationHandler {
		
		private Map<String, Object> propertyValues = new HashMap<String, Object>();
		private PropertyChangeSupport support;
		private Class<?> type;
		
		public ObservableBeanInvocationHandler(Class<?> type) {
			this.type = type;
		}

		public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
			if (support == null)
				this.support = new PropertyChangeSupport(proxy);
			String methodName = method.getName();
			if (methodName.startsWith("get") && ArrayUtil.isEmpty(args)) {
				String propertyName = methodName.substring(3);
				propertyName = StringUtil.uncapitalize(propertyName);
				return propertyValues.get(propertyName);
			} else if (methodName.startsWith("set")) {
				String propertyName = methodName.substring(3);
				propertyName = StringUtil.uncapitalize(propertyName);
				Object oldValue = propertyValues.get(propertyName);
				propertyValues.put(propertyName, args[0]);
				support.firePropertyChange(propertyName, oldValue, args[0]);
			} else if ("addPropertyChangeListener".equals(methodName)) {
				if (args.length == 2)
					support.addPropertyChangeListener((String) args[0], (PropertyChangeListener) args[1]);
				else
					support.addPropertyChangeListener((PropertyChangeListener) args[0]);
			} else if ("removePropertyChangeListener".equals(methodName)) {
				if (args.length == 2)
					support.removePropertyChangeListener((String) args[0], (PropertyChangeListener) args[1]);
				else
					support.removePropertyChangeListener((PropertyChangeListener) args[0]);
			} else if ("equals".equals(methodName)) {
				Object other = args[0];
				if (proxy == other)
					return true;
				if (other == null)
					return false;
				if (!proxy.getClass().equals(other.getClass()))
					return false;
				ObservableBeanInvocationHandler otherHandler = (ObservableBeanInvocationHandler) Proxy.getInvocationHandler(proxy);
				return (this.propertyValues.equals(otherHandler.propertyValues));
			} else if ("hashCode".equals(methodName)) {
				return propertyValues.hashCode();
			} else if ("toString".equals(methodName)) {
				return type.getName() + propertyValues;
			} else
				throw new UnsupportedOperationException("Operation not supported: " + method);
			return null;
		}
		
	}
}
