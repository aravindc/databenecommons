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

import org.databene.commons.ConversionException;

/**
 * Extracts a sub string from a string.<br/><br/>
 * Created: 26.02.2010 10:55:11
 * @since 0.5.0
 * @author Volker Bergmann
 */
public class SubstringExtractor extends ThreadSafeConverter<String, String> {
	
	private int from;
	private Integer to;

	public SubstringExtractor() {
	    this(0);
    }

	public SubstringExtractor(int from) {
	    this(from, null);
    }

	public SubstringExtractor(int from, Integer to) {
	    super(String.class, String.class);
	    this.from = from;
	    this.to = to;
    }

	public void setFrom(int from) {
    	this.from = from;
    }

	public void setTo(Integer to) {
    	this.to = to;
    }

	@Override
	public String convert(String sourceValue) throws ConversionException {
		if (sourceValue == null)
			return null;
		if (to == null)
			return sourceValue.substring(relativeIndex(from, sourceValue));
		else
			return sourceValue.substring(relativeIndex(from, sourceValue), relativeIndex(to, sourceValue));
    }

	private static int relativeIndex(int index, String sourceValue) {
	    return (index >= 0 ? index : sourceValue.length() + index);
    }

}
