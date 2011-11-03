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

package org.databene.commons.condition;

import org.databene.commons.ComparableComparator;
import org.databene.commons.Condition;

import java.util.Comparator;

/**
 * Condition implementation that compares an arbitrary number of arguments 
 * with one of different available operators.<br/>
 * <br/>
 * Created: 06.03.2006 17:49:06
 * @since 0.1
 * @author Volker Bergmann
 */
public class ComparationCondition<E> implements Condition<E[]> {

    public static final int EQUAL = 0;
    public static final int NOT_EQUAL = 1;
    public static final int GREATER_OR_EQUAL = 2;
    public static final int GREATER = 3;
    public static final int LESS_OR_EQUAL = 4;
    public static final int LESS = 5;

    private int operator;
    private Comparator<E> comparator;

    public ComparationCondition() {
        this(EQUAL);
    }

    @SuppressWarnings({ "unchecked", "rawtypes" })
    public ComparationCondition(int operator) {
        this(operator, new ComparableComparator());
    }

    public ComparationCondition(int operator, Comparator<E> comparator) {
        this.operator = operator;
        this.comparator = comparator;
    }

    public int getOperator() {
        return operator;
    }

    public void setOperator(int operator) {
        this.operator = operator;
    }

    public Comparator<E> getComparator() {
        return comparator;
    }

    public void setComparator(Comparator<E> comparator) {
        this.comparator = comparator;
    }

    public boolean evaluate(E[] arguments) {
        if (arguments.length != 2)
            throw new IllegalArgumentException("Comparation only supported for two arguments, found: "
                    + arguments.length);
        int comparation = comparator.compare(arguments[0], arguments[1]);
        switch (operator) {
            case EQUAL            : return comparation == 0;
            case NOT_EQUAL        : return comparation != 0;
            case GREATER_OR_EQUAL : return comparation >= 0;
            case GREATER          : return comparation == 1;
            case LESS_OR_EQUAL    : return comparation <= 0;
            case LESS             : return comparation == -1;
            default               : throw new IllegalStateException("Operator no supported: " + operator);
        }
    }
}
