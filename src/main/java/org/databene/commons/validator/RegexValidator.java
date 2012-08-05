/*
 * (c) Copyright 2009 by Volker Bergmann. All rights reserved.
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

import java.util.HashMap;
import java.util.Map;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Pattern.Flag;

import org.databene.commons.Validator;
import org.databene.commons.validator.bean.AbstractConstraintValidator;

/**
 * Databene {@link Validator} and JSR 303 {@link ConstraintValidator} implementation 
 * that validates a String by a regular expression.<br/>
 * <br/>
 * Created at 15.07.2009 15:22:21
 * @since 0.5.0
 * @author Volker Bergmann
 */

public class RegexValidator extends AbstractConstraintValidator<Pattern, String> {
	
	private static final Map<Flag, Integer> flagConstants;
	
	static {
		flagConstants = new HashMap<Flag, Integer>();
		flagConstants.put(Flag.CANON_EQ, java.util.regex.Pattern.CANON_EQ);
		flagConstants.put(Flag.CASE_INSENSITIVE, java.util.regex.Pattern.CASE_INSENSITIVE);
		flagConstants.put(Flag.COMMENTS, java.util.regex.Pattern.COMMENTS);
		flagConstants.put(Flag.DOTALL, java.util.regex.Pattern.DOTALL);
		flagConstants.put(Flag.MULTILINE, java.util.regex.Pattern.MULTILINE);
		flagConstants.put(Flag.UNICODE_CASE, java.util.regex.Pattern.UNICODE_CASE);
		flagConstants.put(Flag.UNIX_LINES, java.util.regex.Pattern.UNIX_LINES);
	}
	
	private String regexp;
	private Flag[] flags = new Flag[0];
	private java.util.regex.Pattern pattern;
	private boolean nullValid;

    public RegexValidator() {
	    this(null);
    }

    public RegexValidator(String regexp) {
	    this(regexp, new Flag[0]);
    }

    public RegexValidator(String regexp, Flag... flags) {
	    setRegexp(regexp);
	    setFlags(flags);
    }

    public RegexValidator(String regexp, boolean nullValid, Flag... flags) {
    	this.nullValid = nullValid;
	    setRegexp(regexp);
	    setFlags(flags);
    }

	public boolean isNullValid() {
		return nullValid;
	}
	
	public void setNullValid(boolean nullValid) {
		this.nullValid = nullValid;
	}

	@Override
    public void initialize(Pattern params) {
	    setRegexp(params.regexp());
	    setFlags(params.flags());
    }

	public boolean isValid(String string, ConstraintValidatorContext context) {
		if (string == null)
			return nullValid;
    	return pattern.matcher(string).matches();
    }

    public String getRegexp() {
	    return regexp;
    }

    public void setRegexp(String regexp) {
	    this.regexp = regexp;
	    if (this.regexp != null)
	    	this.pattern = java.util.regex.Pattern.compile(regexp, flagsAsNumber());
    }

    public Flag[] getFlags() {
    	return flags;
    }

    public void setFlags(Flag[] flags) {
	    this.flags = flags;
	    if (this.regexp != null)
	    	this.pattern = java.util.regex.Pattern.compile(regexp, flagsAsNumber());
    }

    private int flagsAsNumber() {
	    int bits = 0;
	    for (Flag flag : flags)
	    	bits |= flagConstants.get(flag).intValue();
	    return bits;
    }
    
    @Override
    public String toString() {
    	return super.toString();
    }
    
}
