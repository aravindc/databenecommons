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

package org.databene.commons.comparator;

import org.databene.commons.ComparableComparator;
import org.databene.commons.ParseUtil;

import java.util.Comparator;
import java.math.BigInteger;

/**
 * Splits texts into tokens of words and numbers and compares them element-wise.<br/>
 * <br/>
 * Created: 22.05.2007 07:04:10
 * @since 0.1
 * @author Volker Bergmann
 */
public class CompositeTextComparator implements Comparator<String> {

    private ArrayComparator<Object> arrayComparator;

    @SuppressWarnings({ "unchecked", "rawtypes" })
    public CompositeTextComparator() {
        this.arrayComparator = new ArrayComparator<Object>(new ComparatorChain<Object>(
                new ObjectTypeComparator(BigInteger.class, String.class),
                new ComparableComparator()
        ));
    }

    public int compare(String s1, String s2) {
        Object[] s1Parts = ParseUtil.splitNumbers(s1);
        Object[] s2Parts = ParseUtil.splitNumbers(s2);
        return arrayComparator.compare(s1Parts, s2Parts);
    }
    
}
