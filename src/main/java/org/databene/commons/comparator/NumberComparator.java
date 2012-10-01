/*
 * (c) Copyright 2007-2012 by Volker Bergmann. All rights reserved.
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

package org.databene.commons.comparator;

import java.util.Comparator;
import java.math.BigInteger;
import java.math.BigDecimal;

/**
 * Generic comparator for Number objects of different type.<br/>
 * <br/>
 * Created: 09.10.2006 19:46:22
 * @since 0.1
 * @author Volker Bergmann
 */
public class NumberComparator<E extends Number> implements Comparator<E> {

    @Override
	public int compare(E n1, E n2) {
        return compareNumbers(n1, n2);
    }

	public static <T extends Number> int compareNumbers(T n1, T n2) {
	    if (n1 == null || n2 == null)
            throw new IllegalArgumentException("comparing null value");
        if (n1 instanceof Integer)
            return ((Integer) n1).compareTo(n2.intValue());
        else if (n1 instanceof Long)
            return ((Long) n1).compareTo(n2.longValue());
        else if (n1 instanceof Short)
            return ((Short) n1).compareTo(n2.shortValue());
        else if (n1 instanceof Byte)
            return ((Byte) n1).compareTo(n2.byteValue());
        else if (n1 instanceof Float)
            return ((Float) n1).compareTo(n2.floatValue());
        else if (n1 instanceof Double)
            return ((Double) n1).compareTo(n2.doubleValue());
        else if (n1 instanceof BigInteger)
            return ((BigInteger) n1).compareTo((BigInteger) n2);
        else if (n1 instanceof BigDecimal)
            return ((BigDecimal) n1).compareTo((BigDecimal) n2);
        else
            throw new UnsupportedOperationException("Unsupported Number type: " + n1.getClass());
    }
}
