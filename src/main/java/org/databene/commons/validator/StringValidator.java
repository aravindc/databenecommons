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

package org.databene.commons.validator;

import org.databene.commons.Validator;

/**
 * Validates a String by length and characters.<br/>
 * <br/>
 * Created at 14.08.2008 09:19:51
 * @since 0.4.5
 * @author Volker Bergmann
 */
public class StringValidator extends StringLengthValidator {
	
	private Validator<Character> charValidator;
	
	// constructors ----------------------------------------------------------------------------------------------------

	public StringValidator() {
		this(null);
	}

	public StringValidator(Validator<Character> charValidator) {
		this(charValidator, 0, null);
	}

	public StringValidator(int minLength, Integer maxLength) {
		this(null, minLength, maxLength);
	}

	public StringValidator(Validator<Character> charValidator, int minLength, Integer maxLength) {
		super(minLength, maxLength);
		this.charValidator = charValidator;
	}

	// Validator implementation ----------------------------------------------------------------------------------------

	@Override
	public boolean valid(String text) {
		if (!super.valid(text))
			return false;
		if (charValidator != null)
			for (int i = 0 ; i < text.length(); i++)
				if (!charValidator.valid(text.charAt(i)))
					return false;
		return true;
	}
}
