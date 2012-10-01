/*
 * (c) Copyright 2011-2012 by Volker Bergmann. All rights reserved.
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

package org.databene.commons.filter;

import org.databene.commons.Filter;

/**
 * {@link Filter} implementation which accepts objects that implement a certain class ({@link #acceptedClass})
 * or (if {@link #acceptingSubClasses} is true) a sub class.<br/><br/>
 * Created: 07.06.2011 14:01:42
 * @since 0.5.8
 * @author Volker Bergmann
 */
public class ClassFilter<E> implements Filter<E> {
	
	private Class<? extends E> acceptedClass;
	private boolean acceptingSubClasses;

	public ClassFilter(Class<? extends E> acceptedClass, boolean acceptingSubClasses) {
		this.acceptedClass = acceptedClass;
		this.acceptingSubClasses = acceptingSubClasses;
	}

	@Override
	public boolean accept(E candidate) {
		if (candidate == null)
			return false;
		else if (acceptingSubClasses)
			return acceptedClass.isAssignableFrom(candidate.getClass());
		else
			return acceptedClass == candidate.getClass();
	}

}
