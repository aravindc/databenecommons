/*
 * (c) Copyright 2009-2012 by Volker Bergmann. All rights reserved.
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

package org.databene.commons.log;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

import org.databene.commons.ArrayFormat;
import org.databene.commons.BeanUtil;
import org.databene.commons.Level;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Creates a proxy to a class which logs all invocations of parent interface methods.<br/>
 * <br/>
 * Created at 17.09.2009 10:54:08
 * @since 0.5.0
 * @author Volker Bergmann
 */

public class LoggingProxyFactory {
	
	private LoggingProxyFactory() {}

    public static <T> T createProxy(Class<T> interfaceClass, T realObject) {
		return createProxy(interfaceClass, realObject, Level.debug);
	}
	
    public static <T> T createProxy(Class<T> interfaceClass, T realObject, Level level) {
		return createProxy(interfaceClass, realObject, level, BeanUtil.getContextClassLoader());
	}
	
    @SuppressWarnings("unchecked")
    public static <T> T createProxy(Class<T> interfaceClass, T realObject, Level level, ClassLoader classLoader) {
		LoggingInvocationHandler handler = new LoggingInvocationHandler(realObject, level);
		return (T) Proxy.newProxyInstance(classLoader, new Class[] { interfaceClass }, handler);
	}
	
	protected static class LoggingInvocationHandler implements InvocationHandler {
		
		private Object realObject;
		private Logger logger;
		private Level level;
		
        public LoggingInvocationHandler(Object realObject, Level level) {
        	this.realObject = realObject;
        	this.logger = LoggerFactory.getLogger(realObject.getClass());
        	this.level = level;
        }

		@Override
		public Object invoke(Object object, Method method, Object[] args) throws Throwable {
			String message = method.getName() + '(' + ArrayFormat.format(args) + ')';
			switch (level) {
				case ignore: break;
				case trace: logger.trace(message); break;
				case debug: logger.debug(message); break;
				case info:  logger.info(message);  break;
				case warn:  logger.warn(message);  break;
				case error: logger.error(message); break;
				case fatal: logger.error(message); break;
			}
	        return BeanUtil.invoke(realObject, method, args);
        }

	}
	
}
