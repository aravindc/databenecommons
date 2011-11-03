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

package org.databene.commons.validator;

import org.databene.commons.Validator;

/**
 * Validates a String for a minimum and a maximum length.<br/>
 * <br/>
 * Created: 20.09.2006 21:38:20
 * @author Volker Bergmann
 */
public class StringLengthValidator implements Validator<String> {

    /** the minimum length of the string */
    private int minLength;

    /** the maximum length of the string. If null, the size is not limited */
    private Integer maxLength;

    private boolean nullAllowed;

    // constructors ----------------------------------------------------------------------------------------------------

    /** Creates a validator that accepts any string */
    public StringLengthValidator() {
        this(0, null);
    }

    /** Creates a validator of a maximum length */
    public StringLengthValidator(int maxLength) {
        this(0, maxLength);
    }

    /** Creates a validator of a minimum and maximum length */
    public StringLengthValidator(int minLength, Integer maxLength) {
        setMinLength(minLength);
        setMaxLength(maxLength);
    }

    public StringLengthValidator(int minLength, Integer maxLength, boolean nullAllowed) {
        setMinLength(minLength);
        setMaxLength(maxLength);
        setNullAllowed(nullAllowed);
    }


    // properties ------------------------------------------------------------------------------------------------------

    /**
     * Returns the minimum length.
     * @return the minimum length.
     * @see #minLength
     */
    public int getMinLength() {
        return minLength;
    }

    /**
     * Sets the minimum length.
     * @param minLength the new minimum length
     */
    public void setMinLength(int minLength) {
        if (minLength < 0)
            throw new IllegalArgumentException("minLength may not be less than 0, but was: " + minLength);
        this.minLength = minLength;
    }

    /**
     * Returns the maximum length.
     * @return the maximum length.
     * @see #maxLength
     */
    public Integer getMaxLength() {
        return maxLength;
    }

    /**
     * Sets the maximum length. If set to null, no maximum length check is done.
     * @param maxLength the new minimum length
     */
    public void setMaxLength(Integer maxLength) {
        this.maxLength = maxLength;
    }

    public boolean isNullAllowed() {
        return nullAllowed;
    }

    private void setNullAllowed(boolean nullAllowed) {
        this.nullAllowed = nullAllowed;
    }

    // validator implementation ----------------------------------------------------------------------------------------

    /**
     * @see org.databene.commons.Validator
     * @param text the string to validate
     * @return true if the length restrictions match, otherwise false.
     */
    public boolean valid(String text) {
        if (text == null)
            return nullAllowed;
        return (text.length() >= minLength && (maxLength == null || text.length() <= maxLength));
    }

    // java.lang.Object overrides --------------------------------------------------------------------------------------

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder(getClass().getSimpleName());
        builder.append('[');
        if (minLength > 0)
            builder.append(minLength).append("<=length");
        if (maxLength != null) {
            if (minLength <= 0)
                builder.append("length");
            builder.append("<=").append(maxLength);
        }
        builder.append(']');
        return builder.toString();
    }
}
