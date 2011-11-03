/*
 * (c) Copyright 2010 by Volker Bergmann. All rights reserved.
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

import java.util.Collection;

/**
 * Provides utility methods for threading.<br/><br/>
 * Created: 26.03.2010 19:26:07
 * @since 0.5.0
 * @author Volker Bergmann
 */
public class ThreadUtil {
	
	private ThreadUtil() {}

	public static <C extends Collection<T>, T extends ThreadAware> boolean allThreadSafe(C elements) {
		for (ThreadAware element : elements)
			if (!element.isThreadSafe())
				return false;
		return true;
    }

	public static <T extends ThreadAware> boolean allThreadSafe(T[] elements) {
		for (ThreadAware element : elements)
			if (!element.isThreadSafe())
				return false;
		return true;
    }

	public static <C extends Collection<T>, T extends ThreadAware> boolean allParallelizable(C elements) {
		for (ThreadAware element : elements)
			if (!element.isParallelizable())
				return false;
		return true;
    }

	public static <T extends ThreadAware> boolean allParallelizable(T[] elements) {
		for (ThreadAware element : elements)
			if (!element.isParallelizable())
				return false;
		return true;
    }

}
