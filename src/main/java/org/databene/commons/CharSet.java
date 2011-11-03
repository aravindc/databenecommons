/*
 * (c) Copyright 2007-2009 by Volker Bergmann. All rights reserved.
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

import java.util.Iterator;
import java.util.Set;
import java.util.HashSet;
import java.util.Locale;

/**
 * Represents a Set of characters and provides locale-dependent character sets
 * as well as set manipulation methods.
 *
 * Created: 18.08.2006 19:49:17
 * @author Volker Bergmann
 */
public class CharSet {
	
	private String name;
	
    /** The locale to use for letters */
    private Locale locale;

    /** the wrapped set */
    private Set<Character> set;

    // constructors ----------------------------------------------------------------------------------------------------

    /** Default constructor that initializes to an isEmpty Set of characters with the fallback locale */
    public CharSet() {
        this(LocaleUtil.getFallbackLocale());
    }

    /** Constructor that initializes to an isEmpty Set of characters with the specified locale */
    public CharSet(Locale locale) {
        set = new HashSet<Character>();
        this.locale = locale;
    }

    /** Constructor that initializes to a Set with one character with the fallback locale */
    public CharSet(char c) {
        set = new HashSet<Character>();
        set.add(Character.valueOf(c));
        this.locale = LocaleUtil.getFallbackLocale();
    }

    public CharSet(char from, char to) {
    	this(null, from, to);
    }
    
    /** Constructor that initializes to a continuous range of characters with the fallback locale */
    public CharSet(String name, char from, char to) {
    	this.name = name;
        set = new HashSet<Character>();
        for (char c = from; c <= to; c++)
            set.add(Character.valueOf(c));
        this.locale = LocaleUtil.getFallbackLocale();
    }

    /** Constructor that initializes to a predefined Set of characters with the fallback locale */
    public CharSet(CharSet charSet) {
        this(charSet.set);
    }

    /** Constructor that initializes to a predefined Set of characters with the fallback locale */
    public CharSet(Set<Character> set) {
        this(null, set);
    }

    public CharSet(String name, Set<Character> set) {
    	this.name = name;
        this.set = new HashSet<Character>(set);
        this.locale = LocaleUtil.getFallbackLocale();
    }

    // digit related interface -----------------------------------------------------------------------------------------

    public CharSet addDigits() {
        return addAll(getDigits());
    }

    public CharSet removeDigits() {
        return removeAll(getDigits());
    }

    public CharSet addHexDigits() {
        return addAll(getHexDigits());
    }

    public CharSet removeHexDigits() {
        return removeAll(getHexDigits());
    }

    public CharSet addNonDigits() {
        return addAll(getNonDigits());
    }

    public CharSet removeNonDigits() {
        return removeAll(getNonDigits());
    }

    public static Set<Character> getDigits() {
        return new CharSet('0', '9').getSet();
    }

    public static Set<Character> getHexDigits() {
        return new CharSet('0', '9').addRange('a', 'f').addRange('A', 'F').getSet();
    }

    public static Set<Character> getNonDigits() {
        return new CharSet().addAnyCharacters().removeAll(getDigits()).getSet();
    }

    // word related interface ------------------------------------------------------------------------------------------

    /** Adds all letters of the internal locale to the Set */
    public CharSet addWordChars() {
        return addWordChars(locale);
    }

    /** Adds all letters of the specified locale to the Set */
    public CharSet addWordChars(Locale locale) {
        return addAll(getWordChars(locale));
    }

    /** Removes all letters of the internal locale from the Set */
    public CharSet removeWordChars() {
        return removeWordChars(locale);
    }

    /** Removes all letters of the specified locale from the Set */
    public CharSet removeWordChars(Locale locale) {
        return removeAll(getWordChars(locale));
    }

    /** Returns all letters of the specified locale */
    public static Set<Character> getWordChars(Locale locale) {
        Set<Character> wordChars = LocaleUtil.letters(locale);
        wordChars.add('_');
        wordChars.addAll(getDigits());
        return wordChars;
    }

    /** Adds all characters that are not letters of any locale */
    public CharSet addNonWordChars() {
        return addAll(getNonWordChars());
    }

    /** Removes all characters that are not letters of any locale */
    public CharSet removeNonWordChars() {
        return removeAll(getNonWordChars());
    }

    /** Returns all characters that are not letters of any locale */
    public static Set<Character> getNonWordChars() {
        return new CharSet(' ', '@').addRange('[', '`').addRange('{', '~').getSet();
    }

    // whitespace related interface ------------------------------------------------------------------------------------

    /**
     * Adds all whitespace characters
     * @return this
     */
    public CharSet addWhitespaces() {
        return addAll(getWhitespaces());
    }

    /**
     * Removes all whitespace characters
     * @return this
     */
    public CharSet removeWhitespaces() {
        return removeAll(getWhitespaces());
    }

    /**
     * Returns all whitespace characters
     * @return a Set of all whitespace charaters
     */
    public static Set<Character> getWhitespaces() {
        return new CharSet().add(' ').add('\t').add('\n').add((char)0x0B).add('\f').add('\r').getSet();
    }

    /**
     * Adds all characters that are not white spaces
     * @return this
     */
    public CharSet addNonWhitespaces() {
        return addAll(getNonWhitespaces());
    }

    /**
     * Removes all characters that are not white spaces
     * @return this
     */
    public CharSet removeNonWhitespaces() {
        return removeAll(getNonWhitespaces());
    }

    /**
     * Returns a set of all characters that are not white spaces
     * @return a set of all characters that are not white spaces
     */
    public static Set<Character> getNonWhitespaces() {
        return new CharSet().addAnyCharacters().removeAll(getWhitespaces()).getSet();
    }

    // low level interface ---------------------------------------------------------------------------------------------

    /** Adds any character */
    public CharSet addAnyCharacters() {
        return addAll(getAnyCharacters());
    }

    /**
     * Returns any characters
     * @return a set of any characters
     */
    public static Set<Character> getAnyCharacters() {
        Set<Character> set = new HashSet<Character>();
        for (int c = 0x20; c < 0x7F; c++)
            set.add((char) c);
        return set;
    }

    /**
     * clears the set
     * @return this
     */
    public CharSet removeAll() {
        set.clear();
        return this;
    }

    /**
     * Adds a range of characters
     * @return this
     */
    public CharSet addRange(char from, char to) {
        for (char c = from ; c <= to; c++)
            set.add(c);
        return this;
    }

    /**
     * Adds a single character
     * @return this
     */
    public CharSet add(char c) {
        set.add(c);
        return this;
    }

    /**
     * Adds a set of characters
     * @return this
     */
    public CharSet addAll(Set<Character> chars) {
        set.addAll(chars);
        return this;
    }

    /**
     * Removes all characters of the specified set
     * @param chars
     * @return this
     */
    public CharSet removeAll(Set<Character> chars) {
        set.removeAll(chars);
        return this;
    }

    /**
     * Removes a range of characters
     * @param min
     * @param max
     * @return this
     */
    public CharSet removeRange(char min, char max) {
        for (char c = min; c <= max; c++)
            remove(c);
        return this;
    }

    /**
     * removes one character
     * @param c
     * @return this
     */
    public CharSet remove(char c) {
        if (set.contains(c))
            set.remove(c);
        return this;
    }

    /**
     * Returns a copy of the wrapped Set as HashSet
     * @return a copy of the wrapped Set as HashSet
     */
    public Set<Character> getSet() {
        return new HashSet<Character>(set);
    }
    
    public Iterator<Character> iterator() {
    	return set.iterator();
    }

    public boolean contains(char c) {
        return set.contains(c);
    }

    public int size() {
        return set.size();
    }

    public boolean containsAll(Set<Character> set) {
    	return (this.set.containsAll(set));
    }
    
    // java.lang.Object overrides --------------------------------------------------------------------------------------

    /**
     * Returns a String representation of the Set
     * @return a String representation of the Set
     * @see java.lang.Object
     */
    @Override
    public String toString() {
    	if (name != null)
    		return name;
    	else
    		return set.toString();
    }

    /**
     * Compares with another Set, ignoring the locale.
     * @see java.lang.Object
     */
    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;
        final CharSet charSet = (CharSet) o;
        return set.equals(charSet.set);
    }

    /**
     * Calculates the Set's hashCode, ignoring the locale.
     * @return the Set'S hash code
     * @see java.lang.Object
     */
    @Override
    public int hashCode() {
        return set.hashCode();
    }

}
