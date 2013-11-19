/*
 * (c) Copyright 2008-2010 by Volker Bergmann. All rights reserved.
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

package org.databene.commons;

/**
 * Indicates a parsing error.<br/>
 * <br/>
 * Created at 30.12.2008 08:23:05
 * @since 0.5.7
 * @author Volker Bergmann
 */

public class ParseException extends RuntimeException {
	
	private static final long serialVersionUID = -3893735778927506664L;
	
	private final String parsedText;
	private final int line;
	private final int column;
	
	
	
	// constructors ----------------------------------------------------------------------------------------------------

	public ParseException(String message, String parsedText) {
		this(message, parsedText, -1, -1);
	}

	public ParseException(String message, String parsedText, int line, int column) {
		this(message, null, parsedText, line, column);
	}
	
	public ParseException(String message, Throwable cause, String parsedText, int line, int column) {
		super(message, cause);
		this.parsedText = parsedText;
		this.line = line;
		this.column = column;
	}
	
	
	
	// properties ------------------------------------------------------------------------------------------------------

	public int getLine() {
		return line;
	}

	public int getColumn() {
		return column;
	}
	
	public String getParsedText() {
    	return parsedText;
    }
	
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder(getMessage());
		if (line >= 0 && column >= 0)
			builder.append(" at line ").append(line).append(", column ").append(column);
		if (parsedText != null)
			builder.append(" in ").append(parsedText);
	    return builder.toString();
	}
	
}
