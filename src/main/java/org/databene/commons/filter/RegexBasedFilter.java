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

package org.databene.commons.filter;

import java.util.regex.Pattern;

import org.databene.commons.Filter;

/**
 * {@link Filter} implementation which filters strings by regular expressions for inclusion and exclusion.<br/><br/>
 * Created: 11.06.2011 15:48:30
 * @since 0.5.8
 * @author Volker Bergmann
 */
public class RegexBasedFilter implements Filter<String> {
	
	private Pattern exclusionPattern;
	private Pattern inclusionPattern;

	public RegexBasedFilter(String inclusionPattern, String exclusionPattern) {
		this.inclusionPattern = (inclusionPattern != null ? Pattern.compile(inclusionPattern) : null);
		this.exclusionPattern = (exclusionPattern != null ? Pattern.compile(exclusionPattern) : null);
	}
	
	public boolean accept(String name) {
		if (exclusionPattern != null && exclusionPattern.matcher(name).matches())
			return false;
	    return (inclusionPattern == null || inclusionPattern.matcher(name).matches());
	}

}
