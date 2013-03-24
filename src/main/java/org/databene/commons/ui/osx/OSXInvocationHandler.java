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

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

import org.databene.commons.BeanUtil;
import org.databene.commons.ui.JavaApplication;

/**
 * InvocationHandler interface for Mac OS X.<br/><br/>
 * Created: 10.09.2010 09:18:57
 * @since 0.5.13
 * @author Volker Bergmann
 */
public class OSXInvocationHandler implements InvocationHandler {
	
	private JavaApplication application;
	
	public OSXInvocationHandler(JavaApplication application) {
	    this.application = application;
    }

	@Override
	public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
		if ("handleQuit".equals(method.getName())) {
			application.exit();
		} else if ("handleAbout".equals(method.getName())) {
			BeanUtil.invoke(args[0], "setHandled", true);
			application.about();
		} else if ("handlePreferences".equals(method.getName())) {
			application.preferences();
		}
		return null;
	}

}
