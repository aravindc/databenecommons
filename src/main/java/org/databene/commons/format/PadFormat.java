/*
 * (c) Copyright 2007 by Volker Bergmann. All rights reserved.
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

package org.databene.commons.format;

import org.databene.commons.StringUtil;
import org.databene.commons.converter.Number2StringConverter;
import org.databene.commons.converter.ToStringConverter;

import java.text.*;

/**
 * {@link Format} implementation that applies padding for formatting Strings with a fixed width.<br/>
 * <br/>
 * Created: 07.06.2007 13:23:37
 * @author Volker Bergmann
 */
public class PadFormat extends Format {

    private static final long serialVersionUID = 609871662377339019L;
    
	private int length;
    private int minimumFractionDigits;
    private int maximumFractionDigits;
    private Alignment alignment;
    private char padChar;

    public PadFormat(int length, Alignment alignment, char padChar) {
        this(length, 0, 2, alignment, padChar);
    }

    public PadFormat(int length, int fractionDigits, Alignment alignment, char padChar) {
        this(length, fractionDigits, fractionDigits, alignment, padChar);
    }

    public PadFormat(int length, int minimumFractionDigits, int maximumFractionDigits, Alignment alignment, char padChar) {
    	assert length >= 1;
    	assert minimumFractionDigits >= 0;
    	assert maximumFractionDigits >= 0;
    	assert alignment != null;
    	assert padChar != 0;
        this.length = length;
        this.minimumFractionDigits = minimumFractionDigits;
        this.maximumFractionDigits = maximumFractionDigits;
        this.alignment = alignment;
        this.padChar = padChar;
    }

    @Override
    public StringBuffer format(Object obj, StringBuffer toAppendTo, FieldPosition pos) {
        String text;
        if (obj instanceof Number)
            text = Number2StringConverter.convert((Number) obj, minimumFractionDigits, maximumFractionDigits, false);
        else
            text = ToStringConverter.convert(obj, "");
        int padLength = length - text.length();
        if (padLength < 0)
        	throw new IllegalArgumentException("Text is longer that the required pad length of " + length + ": " + text);
        switch (alignment) {
            case LEFT   : return toAppendTo.append(text).append(StringUtil.padString(padChar, padLength));
            case RIGHT  : boolean neg = (padChar == '0' && text.length() > 0 && text.charAt(0) == '-');
                          if (neg)
                              return toAppendTo.append('-').append(StringUtil.padString(padChar, padLength)).append(text.substring(1));
                          else
                                return toAppendTo.append(StringUtil.padString(padChar, padLength)).append(text);
            case CENTER : return toAppendTo.append(StringUtil.padString(padChar, padLength / 2)).append(text).append(StringUtil.padString(padChar, padLength - padLength / 2));
            default     : throw new IllegalArgumentException("Illegal Alignement: " + alignment);
        }
    }

    @Override
    public Object parseObject(String source, ParsePosition pos) {
        if (StringUtil.isEmpty(source))
            pos.setIndex(1);
        else
            pos.setIndex(source.length());
        if (source == null)
            return null;
        switch (alignment) {
            case LEFT   : return StringUtil.trimRight(source, padChar);
            case RIGHT  : boolean neg = (padChar == '0' && source.length() > 0 && source.charAt(0) == '-');
                          if (neg)
                              return '-' + StringUtil.trimLeft(source.substring(1), padChar);
                          else
                            return StringUtil.trimLeft(source, padChar);
            case CENTER : return StringUtil.trim(source, padChar);
            default     : throw new IllegalArgumentException("Illegal Alignement: " + alignment);
        }
    }

    // properties ------------------------------------------------------------------------------------------------------

    public int getLength() {
        return length;
    }

    public Alignment getAlignment() {
        return alignment;
    }

    public char getPadChar() {
        return padChar;
    }
    
	public int getMinimumFractionDigits() {
		return minimumFractionDigits;
	}

	public int getMaximumFractionDigits() {
		return maximumFractionDigits;
	}
	
	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		return ((((alignment.hashCode() * 31) +  length) * 31 
				+ minimumFractionDigits) * 31 + maximumFractionDigits) * 31 
				+ padChar;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		final PadFormat that = (PadFormat) obj;
		return (this.alignment.equals(that.alignment) 
				&& this.length == that.length 
				&& this.minimumFractionDigits == that.minimumFractionDigits
				&& this.maximumFractionDigits == that.maximumFractionDigits
				&& padChar == that.padChar);
	}

}
