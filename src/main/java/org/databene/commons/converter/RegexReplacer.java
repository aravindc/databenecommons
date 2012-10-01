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

package org.databene.commons.converter;

import java.util.regex.Pattern;

import org.databene.commons.ConversionException;

/**
 * Converts strings using a regular expression.
 * Each part of the 'input' string that matches the regular expression 'pattern' 
 * is replaced with the 'replacement' string.<br/><br/>
 * Created: 22.02.2010 07:12:12
 * @since 0.5.0
 * @author Volker Bergmann
 */
public class RegexReplacer extends ThreadSafeConverter<String, String> {
	
	private Pattern pattern;
	private String replacement;

	public RegexReplacer() {
	    this(null, null);
    }

	public RegexReplacer(String pattern, String replacement) {
	    super(String.class, String.class);
	    setPattern(pattern);
	    setReplacement(replacement);
    }

	private void setReplacement(String replacement) {
	    this.replacement = replacement;
    }

	public void setPattern(String pattern) {
	    this.pattern = Pattern.compile(pattern);
    }

	@Override
	public String convert(String input) throws ConversionException {
	    return this.pattern.matcher(input).replaceAll(this.replacement);
    }

	public String convert(String input, String replacement) throws ConversionException {
	    return this.pattern.matcher(input).replaceAll(replacement);
    }

	public static String convert(String input, String pattern, String replacement) {
		return new RegexReplacer(pattern, replacement).convert(input);
	}
	
}
