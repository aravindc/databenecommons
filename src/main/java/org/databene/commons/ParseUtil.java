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

package org.databene.commons;

import java.io.PushbackReader;
import java.io.IOException;
import java.io.Reader;
import java.io.BufferedReader;
import java.text.ParseException;
import java.text.ParsePosition;
import java.util.List;
import java.util.ArrayList;
import java.math.BigInteger;

/**
 * Provides methods for parsing PushbackReaders and Strings.
 * Created: 20.03.2005 16:32:00
 * @author Volker Bergmann
 */
public final class ParseUtil {

    public static double parseDecimal(PushbackReader reader) throws IOException, ParseException {
        double result = parseInteger(reader);
        double postfix = parseOptionalPostfix(reader);
        if (result >= 0)
            result += postfix;
        else
            result -= postfix;
        return result;
    }

    public static double parseOptionalPostfix(PushbackReader reader) throws IOException {
        int c = reader.read();
        if (c != '.') {
            if (c != -1)
                reader.unread(c);
            return 0.;
        }
        double result = 0;
        double base = 0.1;
        while ((c = reader.read()) != -1 && Character.isDigit((char)c)) {
            result += (c - '0') * base;
            base *= 0.1;
        }
        if (c != -1)
            reader.unread(c);
        return result;
    }

    public static long parseInteger(PushbackReader reader) throws IOException, ParseException {
        boolean negative = parseOptionalSign(reader);
        return parseNonNegativeInteger(reader) * (negative ? -1 : 1);
    }

    public static long parseNonNegativeInteger(String source, ParsePosition pos) throws ParseException {
        int digit;
        if (pos.getIndex() > source.length() || !Character.isDigit(digit = source.charAt(pos.getIndex())))
            throw new ParseException("Number expected", 0);
        pos.setIndex(pos.getIndex() + 1);
        long result = digit - '0';
        while(pos.getIndex() < source.length() && Character.isDigit(digit = source.charAt(pos.getIndex()))) {
            result = result * 10 + digit - '0';
            pos.setIndex(pos.getIndex() + 1);
        }
        return result;
    }

    public static long parseNonNegativeInteger(PushbackReader reader) throws IOException, ParseException {
        int digit;
        if ((digit = reader.read()) == -1 || !Character.isDigit((char)digit))
            throw new ParseException("Long expected", 0);
        long result = digit - '0';
        while((digit = reader.read()) != -1 && Character.isDigit((char)digit))
            result = result * 10 + digit - '0';
        if (digit != -1)
            reader.unread(digit);
        return result;
    }

    public static boolean parseOptionalSign(PushbackReader reader) throws IOException {
        skipWhitespace(reader);
        int optionalSign = reader.read();
        if (optionalSign == '-')
            return true;
        if (optionalSign != -1)
            reader.unread(optionalSign);
        return false;
    }

    public static void skipWhitespace(PushbackReader reader) throws IOException {
        int c;
        do {
            c = reader.read();
        } while (c != -1 && Character.isWhitespace((char)c));
        if (c != -1)
            reader.unread(c);
    }
    
    public static String parseUnit(PushbackReader reader) throws IOException {
        StringBuilder result = new StringBuilder();
        int c;
        while ((c = reader.read()) != -1 && Character.isUpperCase((char)c))
            result.append((char)c);
        if (c != -1)
            reader.unread(c);
        return (result.length() > 0 ? result.toString() : null);
    }
    
    public static boolean parseEstimated(PushbackReader reader) throws IOException {
        int c = reader.read();
        if (c == 'e')
            return true;
        if (c != -1)
            reader.unread(c);
        return false;
    }

    public static boolean isEmpty(Object object) {
        return (object == null || StringUtil.isEmpty((String) object));
    }

    public static int nextNonWhitespaceIndex(String source, int startIndex) {
        int i;
        for (i = startIndex; i < source.length() && Character.isWhitespace(source.charAt(i)); i++) {
        }
        if (i >= source.length())
            i = -1;
        return i;
    }

    public static String[][] parseEmptyLineSeparatedFile(Reader src) throws IOException {
        BufferedReader reader = null;
        List<List<String>> sections = new ArrayList<List<String>>();
        List<String> lines = null;
        try {
            reader = new BufferedReader(src);
            String line;
            while ((line = reader.readLine()) != null) {
                if (line.length() > 0) {
                    if (lines == null) {
                        // start a new section
                        lines = new ArrayList<String>();
                        sections.add(lines);
                    }
                    lines.add(line);
                } else {
                    // end of the section
                    if (lines != null) {
                        lines = null;
                    } else {
                        // add empty section
                        sections.add(new ArrayList<String>());
                    }
                }
            }
            return StringUtil.toArrayArray(sections);
        } finally {
            IOUtil.close(reader);
        }
    }

    public static Object[] splitNumbers(String text) {
        List<Object> parts = new ArrayList<Object>();
        Boolean numMode = null;
        int start = 0;
        for (int i = 0; i < text.length(); i++) {
            char c = text.charAt(i);
            boolean newMode = Character.isDigit(c);
            if (numMode != null && !numMode.equals(newMode)) {
                String partString = text.substring(start, i);
                addPart(partString, parts, numMode);
                start = i;
            }
            numMode = newMode;
        }
        addPart(text.substring(start), parts, numMode);
        return parts.toArray();
    }

    public static boolean isNonNegativeNumber(String text) {
        for (int i = 0; i < text.length(); i++)
            if (!Character.isDigit(text.charAt(i)))
                return false;
        return true;
    }

    public static String from(String s, String separator) {
        return from(s, separator, null);
    }

    public static String from(String s, String separator, String notFoundValue) {
        int separatorIndex = s.indexOf(separator);
        return (separatorIndex >= 0 ? s.substring(separatorIndex) : notFoundValue);
    }

    public static String before(String s, String separator) {
        int separatorIndex = s.indexOf(separator);
        return (separatorIndex >= 0 ? s.substring(0, separatorIndex) : s);
    }

    public static boolean isHex(String s) {
        for (int i = s.length() - 1; i >= 0; i--)
            if (!isHex(s.charAt(i)))
                return false;
        return true;
    }

    public static boolean isHex(char c) {
        return ('0' <= c && c <= '9') || ('a' <= c && c <= 'f') || ('A' <= c && c <= 'F');
    }

    public static String[] parseAssignment(String line, String operator, boolean lhsRequired) {
    	if (line == null)
    		return null;
        int sep = line.indexOf(operator);
        if (sep < 0 || (lhsRequired && sep == 0))
            return null;
        return new String[] {
                line.substring(0, sep).trim(),
                (sep < line.length() - 1 ? line.substring(sep + 1) : null)
        };
    }
    
    public static boolean isNMToken(String testName) {
        if (testName == null || testName.length() == 0)
            return false;
        char c = testName.charAt(0);
        if (!Character.isLetter(c) && c != '_' && c != ':')
            return false;
        for (int i = 1; i < testName.length(); i++) {
            c = testName.charAt(i);
            if (!Character.isLetter(c) && c != '.' && c != '-' && c != '_' && c != ':') 
                return false;
        }
        return true;
    }

	public static void skipWhiteSpace(String text, ParsePosition pos) {
		int i;
		while ((i = pos.getIndex()) < text.length() && Character.isWhitespace(text.charAt(i)))
			pos.setIndex(i + 1);
	}

    public static Boolean parseBoolean(String s) {
    	return parseBoolean(s, false);
    }

    public static Boolean parseBoolean(String s, boolean acceptWhitespace) {
    	if (s == null)
    		return null;
    	if (acceptWhitespace)
    		s = s.trim();
    	if ("true".equalsIgnoreCase(s))
    		return true;
    	else if ("false".equalsIgnoreCase(s))
    		return false;
    	else
    		throw new SyntaxError("Not a boolean value", s);
    }

	// private helpers -------------------------------------------------------------------------------------------------

    private static void addPart(String partString, List<Object> parts, Boolean numMode) {
        Object part = (numMode ? new BigInteger(partString) : partString);
        parts.add(part);
    }

}
