/*
 * (c) Copyright 2011 by Volker Bergmann. All rights reserved.
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

package org.databene.commons.validator;

import org.databene.commons.Converter;
import org.databene.commons.Validator;
import org.databene.commons.converter.SubstringExtractor;

/**
 * Uses another validator to validate sub strings.<br/><br/>
 * Created: 02.08.2011 07:19:04
 * @since 0.5.9
 * @author Volker Bergmann
 */
public class SubStringValidator extends AbstractValidator<String> {

	protected Converter<String, String> substringExtractor;
	protected Validator<String> realValidator;

	public SubStringValidator(int from, Integer to, Validator<String> realValidator) {
		this.substringExtractor = new SubstringExtractor(from, to);
		this.realValidator = realValidator;
	}


	public boolean valid(String value) {
		if (value == null)
			return false;
		return realValidator.valid(substringExtractor.convert(value));
	}

}
