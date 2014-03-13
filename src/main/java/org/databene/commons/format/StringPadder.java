/*
 * (c) Copyright 2014 by Volker Bergmann. All rights reserved.
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

package org.databene.commons.format;


import org.databene.commons.Assert;
import org.databene.commons.ConversionException;
import org.databene.commons.StringUtil;
import org.databene.commons.converter.ThreadSafeConverter;

/**
 * Pads a string with a pad character to a given length with left/center or right alignment.<br/><br/>
 * Created: 13.03.2014 11:40:07
 * @since 0.5.28
 * @author Volker Bergmann
 */

public class StringPadder extends ThreadSafeConverter<String, String> {

	private int length;
    private Alignment alignment;
    private char padChar;

    public StringPadder(int length, Alignment alignment, char padChar) {
    	super(String.class, String.class);
    	
    	// check preconditions
    	if (length < 1)
    		throw new IllegalArgumentException("Not a supported padding length: " + length);
    	Assert.notNull(alignment, "alignment");
    	if (padChar == 0)
    		throw new IllegalArgumentException("padChar must not be null");
    	
    	// initialize attributes
        this.length = length;
        this.alignment = alignment;
        this.padChar = padChar;
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
    
    
    // Converter implementation ----------------------------------------------------------------------------------------
    
	@Override
	public String convert(String text) throws ConversionException {
        int padLength = length - text.length();
        if (padLength < 0)
        	throw new IllegalArgumentException("Text is longer that the pad length of " + length + " characters: " + text);
        switch (alignment) {
            case LEFT   : return text + StringUtil.padString(padChar, padLength);
            case RIGHT  : boolean neg = (padChar == '0' && text.length() > 0 && text.charAt(0) == '-');
                          if (neg)
                              return "-" + StringUtil.padString('0', padLength) + text.substring(1);
                          else
                                return StringUtil.padString(padChar, padLength) + text;
            case CENTER : return StringUtil.padString(padChar, padLength / 2) + text + StringUtil.padString(padChar, padLength - padLength / 2);
            default     : throw new IllegalArgumentException("Not a supported Alignement: " + alignment);
        }
    }
	
    
    // java.lang.Object overrides --------------------------------------------------------------------------------------
    
	@Override
	public int hashCode() {
		return ((alignment.hashCode() * 31) +  length) * 31 + padChar;
	}
	
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null || getClass() != obj.getClass())
			return false;
		final StringPadder that = (StringPadder) obj;
		return (this.alignment.equals(that.alignment) 
				&& this.length == that.length 
				&& padChar == that.padChar);
	}
	
	@Override
	public String toString() {
		return "" + length + alignment.getId() + padChar;
	}
	
}
