/*
 * (c) Copyright 2007-2011 by Volker Bergmann. All rights reserved.
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

package org.databene.commons.converter;

import org.databene.commons.ConversionException;

import java.text.*;
import java.util.Locale;

/**
 * Formats a number as a String.<br/>
 * <br/>
 * Created: 10.09.2007 07:35:18
 * @author Volker Bergmann
 */
public class Number2StringConverter extends ThreadSafeConverter<Number, String> {

    private int minimumFractionDigits;
    private int maximumFractionDigits;
    boolean groupingUsed;

    public Number2StringConverter(int minimumFractionDigits, int maximumFractionDigits, boolean groupingUsed) {
    	super(Number.class, String.class);
        this.minimumFractionDigits = minimumFractionDigits;
        this.maximumFractionDigits = maximumFractionDigits;
        this.groupingUsed = groupingUsed;
    }

    public String convert(Number sourceValue) throws ConversionException {
        return convert(sourceValue, minimumFractionDigits, maximumFractionDigits, groupingUsed);
    }

    public static String convert(Number sourceValue, int minimumFractionDigits, int maximumFractionDigits, boolean groupingUsed) {
        NumberFormat format = NumberFormat.getInstance(Locale.US);
        format.setMinimumFractionDigits(minimumFractionDigits);
        format.setMaximumFractionDigits(maximumFractionDigits);
        format.setGroupingUsed(groupingUsed);
        return format.format(sourceValue);
    }
    
}
