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
    
    private String nullString;
	private Format format;
    private StringPadder padder;

    public PadFormat(String nullString, int length, Alignment alignment, char padChar) {
        this(null, nullString, length, alignment, padChar);
    }

    public PadFormat(Format format, String nullString, int length, Alignment alignment, char padChar) {
    	assert alignment != null;
    	assert padChar != 0;
        this.format = format;
        this.nullString = nullString;
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
    	String text;
    	if (object == null)
    		text = nullString;
    	else if (format != null)
    		text = format.format(object);
    	else
    		text = ToStringConverter.convert(object, nullString);
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
        String tmp = source.substring(pos.getIndex());
        char padChar = getPadChar();
        switch (getAlignment()) {
            case LEFT   : tmp =  StringUtil.trimRight(tmp, padChar); break;
            case RIGHT  : boolean neg = (padChar == '0' && tmp.length() > 0 && tmp.charAt(0) == '-');
                          if (neg)
                        	  tmp =  '-' + StringUtil.trimLeft(tmp.substring(1), padChar);
                          else
                        	  tmp = StringUtil.trimLeft(tmp, padChar);
                          break;
            case CENTER : tmp = StringUtil.trim(tmp, padChar); break;
            default     : throw new IllegalArgumentException("Illegal Alignement: " + getAlignment());
        }
        Object result;
        if (format != null) {
        	result = format.parseObject(tmp, pos);
        } else {
        	result = tmp;
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
