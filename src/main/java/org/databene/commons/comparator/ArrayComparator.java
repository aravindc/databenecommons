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

package org.databene.commons.comparator;

import org.databene.commons.ComparableComparator;

import java.util.Comparator;

/**
 * Compares two arrays by the first <min-length> array elements with a Comparator.<br/>
 * <br/>
 * Created: 22.05.2007 07:07:17
 * @since 0.1
 * @author Volker Bergmann
 */
public class ArrayComparator<E> implements Comparator<E[]> {

    private Comparator<E> elementComparator;

    @SuppressWarnings({ "unchecked", "rawtypes" })
    public ArrayComparator() {
        this(new ComparableComparator());
    }

    public ArrayComparator(Comparator<E> elementComparator) {
        this.elementComparator = elementComparator;
    }

    public int compare(E[] array1, E[] array2) {
    	if (array1 == null)
    		return (array2 == null ? 0 : -1);
    	if (array2 == null)
    		return 1;
        // iterate through the elements and compara them one by one
        int minLength = Math.min(array1.length, array2.length);
        for (int i = 0; i < minLength; i++) {
            int elementComparison = elementComparator.compare(array1[i], array2[i]);
            // if element #i differs then return the difference
            if (elementComparison != 0)
                return elementComparison;
        }
        // All elements from 0 to minLength are equals - return the longer array as greater
        if (array1.length < array2.length)
            return -1;
        else if (array1.length > array2.length)
            return 1;
        else // the arrays have equal size and equal elements
            return 0;
    }
    
}
