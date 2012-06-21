/*
 * (c) Copyright 2010-2012 by Volker Bergmann. All rights reserved.
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

package org.databene.commons.ui.osx;

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Proxy;

import javax.imageio.ImageIO;

import org.databene.commons.BeanUtil;
import org.databene.commons.ui.JavaApplication;

/**
 * Provides utility methods for Mac OS X.<br/><br/>
 * Created: 10.09.2010 09:30:01
 * @since 0.5.13
 * @author Volker Bergmann
 */
public class OSXUtil {

	public static void configureApplication(JavaApplication application) {
		// Get OSX Application
    	Class<?> applicationClass = BeanUtil.forName("com.apple.eawt.Application");
    	Object osxApplication = BeanUtil.invokeStatic(applicationClass, "getApplication");
    	// add ApplicationListener
        Class<?> applicationListenerClass = BeanUtil.forName("com.apple.eawt.ApplicationListener");
        ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
		Object osxAdapterProxy = Proxy.newProxyInstance(
				classLoader, 
				new Class[] { applicationListenerClass }, 
				new OSXInvocationHandler(application));
		BeanUtil.invoke(osxApplication, "addApplicationListener", new Object[] { osxAdapterProxy });
		// 
			String iconPath = application.iconPath();
			if (iconPath != null) {
				try {
					InputStream icon = ClassLoader.getSystemResourceAsStream(iconPath);
					BeanUtil.invoke(osxApplication, "setDockIconImage", ImageIO.read(icon));
				} catch (IOException e) {
					// ignore errors 
				}
			}
    }

}
