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

package org.databene.commons.operation;

import org.databene.commons.ComparableComparator;
import org.databene.commons.Operation;

import java.util.Comparator;

/**
 * Returns the minimum of two values. If a Comparator is provided, that one is used,
 * else it is assumed that E implements Comparable.<br/>
 * <br/>
 * Created: 03.08.2007 07:40:14
 * @author Volker Bergmann
 */
public class MaxOperation<E> implements Operation<E, E> {

    private Comparator<E> comparator;

    @SuppressWarnings({ "unchecked", "rawtypes" })
    public MaxOperation() {
        this(new ComparableComparator());
    }

    public MaxOperation(Comparator<E> comparator) {
        this.comparator = comparator;
    }

    public E perform(E... args) {
        if (args.length == 0)
            return null;
        E result = args[0];
        for (int i = 1; i < args.length; i++)
            if (comparator.compare(result, args[i]) < 0)
                result = args[i];
        return result;
    }
}
