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

package org.databene.commons.version;

import java.text.ParseException;
import java.text.ParsePosition;
import java.util.ArrayList;
import java.util.List;

import org.databene.commons.Parser;
import org.databene.commons.StringUtil;

/**
 * Parses a {@link VersionNumber}.<br/><br/>
 * Created: 10.03.2011 16:28:06
 * @since 0.5.8
 * @author Volker Bergmann
 */
public class VersionNumberParser extends Parser<VersionNumber>{

	@Override
	public VersionNumber parseObject(String text, ParsePosition pos) {
		List<VersionNumberComponent> components = new ArrayList<VersionNumberComponent>();
		List<String> delimiters = new ArrayList<String>();
		if (StringUtil.isEmpty(text)) {
			components.add(new NumberVersionNumberComponent(1));
		} else {
			String delimiter;
			do {
				components.add(parseComponent(text, pos));
				delimiter = parseDelimiter(text, pos);
				if (delimiter != null)
					delimiters.add(delimiter);
			} while (delimiter != null);
		}
		return new VersionNumber(components, delimiters);
	}

	private String parseDelimiter(String number, ParsePosition pos) {
		int index = pos.getIndex();
		if (index >= number.length())
			return null;
		char c = number.charAt(index);
		if (c == '.' || c == '-' || c == '_') {
			pos.setIndex(pos.getIndex() + 1);
			return String.valueOf(c);
		} else
			return (Character.isLetterOrDigit(c) ? "" : null);
	}

	private VersionNumberComponent parseComponent(String number, ParsePosition pos) {
		char c = number.charAt(pos.getIndex());
		if (Character.isDigit(c))
			return parseNumberOrDateComponent(number, pos);
		else
			return new StringVersionNumberComponent(parseLetters(number, pos));
	}

	private VersionNumberComponent parseNumberOrDateComponent(String text, ParsePosition pos) {
	    String number = parseNonNegativeInteger(text, pos);
	    if (number.length() == 8) {
	    	try {
	    		return new DateVersionNumberComponent(number);
	    	} catch (ParseException e) {
	    		// oops - no date. Fall back to NumberVersionNumberComponent in the following code
	    	}
	    }
    	return new NumberVersionNumberComponent(number);
    }

	private String parseNonNegativeInteger(String number, ParsePosition pos) {
		int index = pos.getIndex();
		StringBuffer result = new StringBuffer(2);
		char c;
		while (index < number.length() && Character.isDigit(c = number.charAt(index))) {
			result.append(c);
			index++;
		}
		pos.setIndex(index);
		return result.toString();
	}
	
	private String parseLetters(String number, ParsePosition pos) {
		int index = pos.getIndex();
		StringBuffer result = new StringBuffer(10);
		char c;
		while (index < number.length() && Character.isLetter(c = number.charAt(index))) {
			result.append(c);
			index++;
		}
		pos.setIndex(index);
		return result.toString();
	}

}
