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

package org.databene.commons.bean;

import org.databene.commons.ComparableComparator;
import org.databene.commons.comparator.ComparatorFactory;

import java.util.Comparator;

/**
 * Compares JavaBeans by a property.
 * If a beanComparator is provided, this one is used for property comparison,
 * else the ComparatorFactory is queried for a Comparator.<br/>
 * <br/>
 * Created: 06.01.2005 20:04:36
 * @author Volker Bergmann
 * @see ComparatorFactory
 */
public class BeanComparator<E> implements Comparator<E> {

    private Comparator<Object> propertyComparator;
	private PropertyAccessor<E, ?> propertyAccessor;

    // constructor -----------------------------------------------------------------------------------------------------

    @SuppressWarnings({ "unchecked", "rawtypes" })
    public BeanComparator(String propertyName) {
        this.propertyComparator = new ComparableComparator();
        try {
            this.propertyAccessor = PropertyAccessorFactory.getAccessor(propertyName);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public BeanComparator(Class<?> comparedClass, String propertyName) {
        this(comparedClass, propertyName, getComparator(comparedClass, propertyName));
    }

    @SuppressWarnings("unchecked")
    public BeanComparator(Class<?> comparedClass, String propertyName, Comparator<?> comparator) {
        this.propertyComparator = (Comparator<Object>) comparator;
        try {
            this.propertyAccessor = PropertyAccessorFactory.getAccessor(comparedClass, propertyName);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    // interface -------------------------------------------------------------------------------------------------------

    @Override
	public int compare(E o1, E o2) {
        try {
        	Object v1 = propertyAccessor.getValue(o1);
        	Object v2 = propertyAccessor.getValue(o2);
            return propertyComparator.compare(v1, v2);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    // private helpers -------------------------------------------------------------------------------------------------

    @SuppressWarnings({ "unchecked", "rawtypes" })
    private static Comparator getComparator(Class<?> comparedClass, String propertyName) {
        PropertyAccessor propertyAccessor = PropertyAccessorFactory.getAccessor(comparedClass, propertyName);
        Comparator<?> beanComparator = ComparatorFactory.getComparator(propertyAccessor.getValueType());
        if (beanComparator == null)
            throw new IllegalArgumentException("Property '" + comparedClass.getName() + '.' + propertyName + "' " +
                    "is expected to implement Comparable");
        return beanComparator;
    }

}
