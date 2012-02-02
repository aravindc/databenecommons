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

import static org.junit.Assert.*;

import java.util.List;

import org.databene.commons.ValidationDomainDescriptor;
import org.databene.commons.Validator;
import org.junit.Test;

/**
 * Tests the {@link DefaultValidationDomainDescriptor}.<br/><br/>
 * Created: 20.12.2011 17:07:08
 * @since 0.5.14
 * @author Volker Bergmann
 */
public class DefaultValidationDomainDescriptorTest {

	@Test
	public void testConstructorWithClassName() {
		// given the ValidationDomainDescriptor is constructed with the explicit name of this package
		String packageName = getClass().getPackage().getName();
		ValidationDomainDescriptor descriptor = new DefaultValidationDomainDescriptor(packageName);
		// when querying the validator classes
		List<Class<? extends Validator<?>>> validatorClasses = descriptor.getValidatorClasses();
		// then it must list all Validator classes in this package
		assertEquals(1, validatorClasses.size());
		assertEquals(OneValidator.class, validatorClasses.get(0));
	}
	
	@Test
	public void testDefaultConstructor() {
		// given an anonymous class which uses the default constructor
		ValidationDomainDescriptor descriptor = new DefaultValidationDomainDescriptor() {
		};
		// when querying the validator classes
		List<Class<? extends Validator<?>>> validatorClasses = descriptor.getValidatorClasses();
		// then it must list all Validator classes in this package
		assertEquals(1, validatorClasses.size());
		assertEquals(OneValidator.class, validatorClasses.get(0));
	}
	
}
