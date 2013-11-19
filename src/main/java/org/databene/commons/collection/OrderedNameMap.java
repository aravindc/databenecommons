/*
 * (c) Copyright 2008-2012 by Volker Bergmann. All rights reserved.
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

package org.databene.commons.collection;

import java.util.List;
import java.util.Map;

import org.databene.commons.OrderedMap;

/**
 * A map that assigns names to Objects and keeps entries 
 * in the order in which they were inserted.<br/><br/>
 * Created at 14.04.2008 09:49:34
 * @since 0.5.2
 * @author Volker Bergmann
 */
public class OrderedNameMap<E> extends MapProxy<OrderedMap<String, E>, String, E> {
	
	/** caseSupport setting which respects capitalization */
	private static final int CASE_SENSITIVE   = 0;
	
	/** caseSupport setting which preserves capitalization for stored entries but  */
	private static final int CASE_INSENSITIVE = 1;
	private static final int CASE_IGNORANT    = 2;
	
	private int caseSupport;
	
	// constructors + factory methods ----------------------------------------------------------------------------------
	
    public OrderedNameMap() {
		this(CASE_SENSITIVE);
	}
    
    public OrderedNameMap(int caseSupport) {
    	super(OrderedNameMap.<E>createRealMap(caseSupport));
		this.caseSupport = caseSupport;
	}

    public OrderedNameMap(OrderedNameMap<E> that) {
    	super(OrderedNameMap.<E>createRealMap(that.caseSupport));
		this.caseSupport = that.caseSupport;
		putAll(that);
	}

    private static <T> OrderedMap<String, T> createRealMap(int caseSupport) {
		switch (caseSupport) {
			case CASE_SENSITIVE:   return new CaseSensitiveOrderedNameMap<T>();
			case CASE_INSENSITIVE: return new CaseInsensitiveOrderedNameMap<T>();
			case CASE_IGNORANT:    return new CaseIgnorantOrderedNameMap<T>();
			default: throw new IllegalArgumentException("Illegal caseSupport setting: " + caseSupport);
		}
	}

	public static <T> OrderedNameMap<T> createCaseSensitiveMap() {
    	return new OrderedNameMap<T>(CASE_SENSITIVE);
    }

    public static <T> OrderedNameMap<T> createCaseInsensitiveMap() {
    	return new OrderedNameMap<T>(CASE_INSENSITIVE);
    }

    public static <T> OrderedNameMap<T> createCaseIgnorantMap() {
    	return new OrderedNameMap<T>(CASE_IGNORANT);
    }
    
	public E valueAt(int index) {
		return realMap.valueAt(index);
    }

	public int indexOfValue(E value) {
		return realMap.indexOfValue(value);
    }

	public Map.Entry<String, E> getEntry(String key) {
		return realMap.getEntry(key);
    }
	
    public boolean equalsIgnoreOrder(Map<String, E> that) {
		return realMap.equalsIgnoreOrder(that);
    }
    
	@Override
	public List<E> values() {
		return realMap.values();
	}
	
}
