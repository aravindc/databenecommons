/*
 * (c) Copyright 2009 by Volker Bergmann. All rights reserved.
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

package org.databene.commons;

import java.util.concurrent.Callable;

/**
 * Provides system related utility methods.<br/><br/>
 * Created: 21.10.2009 19:26:24
 * @since 0.5.0
 * @author Volker Bergmann
 */
public class SysUtil {

	private SysUtil() { }
	
	public static void runWithSystemProperty(String name, String value, Runnable runner) {
		String oldValue = System.getProperty(name);
		try {
			System.setProperty(name, value);
			runner.run();
		} finally {
			System.setProperty(name, (oldValue != null ? oldValue : ""));
		}
	}
	
	public static <T> T callWithSystemProperty(String name, String value, Callable<T> callee) throws Exception {
		String oldValue = System.getProperty(name);
		try {
			System.setProperty(name, value);
			return callee.call();
		} finally {
			System.setProperty(name, oldValue);
		}
	}

}
