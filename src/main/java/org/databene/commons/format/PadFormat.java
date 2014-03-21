/*
 * (c) Copyright 2007-2014 by Volker Bergmann. All rights reserved.
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

import org.databene.commons.NullSafeComparator;
import org.databene.commons.StringUtil;
import org.databene.commons.converter.ToStringConverter;

import java.text.*;

/**
 * {@link Format} implementation that applies padding for formatting Strings with a fixed width.<br/>
 * <br/>
 * Created: 07.06.2007 13:23:37
 * @author Volker Bergmann
 */
public class PadFormat extends Format {

    private static final long serialVersionUID = -8263536650454913565L;
    
	private Format format;
    private StringPadder padder;

    public PadFormat(int length, Alignment alignment, char padChar) {
        this(null, length, alignment, padChar);
    }

    public PadFormat(Format format, int length, Alignment alignment, char padChar) {
    	assert alignment != null;
    	assert padChar != 0;
        this.format = format;
        this.padder = (length > 0 ? new StringPadder(length, alignment, padChar) : null);
    }
    
    // properties ------------------------------------------------------------------------------------------------------

    public int getLength() {
        return padder.getLength();
    }

    public Alignment getAlignment() {
        return padder.getAlignment();
    }

    public char getPadChar() {
        return padder.getPadChar();
    }
    
	
	// Format interface implementation ---------------------------------------------------------------------------------
	
    @Override
    public StringBuffer format(Object object, StringBuffer toAppendTo, FieldPosition pos) {
        String text = (format != null ? format.format(object) : ToStringConverter.convert(object, ""));
        if (padder != null)
        	text = padder.convert(text);
        return toAppendTo.append(text);
    }

    @Override
    public Object parseObject(String source, ParsePosition pos) {
        if (source == null) {
            pos.setIndex(1);
            return null;
        }
        String trimmed = source;
        char padChar = getPadChar();
        switch (getAlignment()) { // TODO the following code ignores the pos
            case LEFT   : trimmed =  StringUtil.trimRight(trimmed, padChar); break;
            case RIGHT  : boolean neg = (padChar == '0' && trimmed.length() > 0 && trimmed.charAt(0) == '-');
                          if (neg)
                        	  trimmed =  '-' + StringUtil.trimLeft(trimmed.substring(1), padChar);
                          else
                        	  trimmed = StringUtil.trimLeft(trimmed, padChar);
                          break;
            case CENTER : trimmed = StringUtil.trim(trimmed, padChar); break;
            default     : throw new IllegalArgumentException("Illegal Alignement: " + getAlignment());
        }
        Object result;
        if (format != null) {
        	result = format.parseObject(trimmed, pos);
        } else {
        	result = trimmed;
            if (StringUtil.isEmpty(source))
                pos.setIndex(1);
            else
            	pos.setIndex(source.length());
        }
        return result;
    }
    
    
	// java.lang.Object overrides --------------------------------------------------------------------------------------
    
	@Override
	public int hashCode() {
		return padder.hashCode() * 31 +  format.hashCode();
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null || getClass() != obj.getClass())
			return false;
		final PadFormat that = (PadFormat) obj;
		return (NullSafeComparator.equals(this.padder, that.padder) && 
				NullSafeComparator.equals(this.format, that.format));
	}
	
	@Override
	public String toString() {
		return padder.toString();
	}
	
}
