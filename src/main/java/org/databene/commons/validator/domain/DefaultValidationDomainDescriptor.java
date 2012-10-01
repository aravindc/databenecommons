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

package org.databene.commons.validator.domain;

import java.lang.reflect.Modifier;
import java.util.List;

import org.databene.commons.BeanUtil;
import org.databene.commons.Filter;
import org.databene.commons.ValidationDomainDescriptor;
import org.databene.commons.Validator;
import org.databene.commons.filter.FilterUtil;

/**
 * Default implementation of the {@link ValidationDomainDescriptor} interface.<br/><br/>
 * Created: 20.12.2011 16:53:55
 * @since 0.5.14
 * @author Volker Bergmann
 */
public class DefaultValidationDomainDescriptor extends AbstractValidationDomainDescriptor {
	
	private List<Class<? extends Validator<?>>> validatorClasses;
	
	/** This constructor is assumed to be used by child classes - it used the child class' package name
	 * to search Validator instances. */
	protected DefaultValidationDomainDescriptor() {
		init(getClass().getPackage().getName());
	}
	
	public DefaultValidationDomainDescriptor(String packageName) {
		init(packageName);
	}
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	private void init(String packageName) {
		List<Class<?>> candidates = BeanUtil.getClasses(packageName);
		this.validatorClasses = (List) FilterUtil.find(candidates, new ValidatorClassFilter());
	}

	public class ValidatorClassFilter implements Filter<Class<?>> {
		@Override
		public boolean accept(Class<?> candidate) {
			return Validator.class.isAssignableFrom(candidate) 
				&& !Modifier.isAbstract(candidate.getModifiers());
		}
	}

	@Override
	public List<Class<? extends Validator<?>>> getValidatorClasses() {
		return validatorClasses;
	}

}
