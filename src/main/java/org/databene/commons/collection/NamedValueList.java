/*
 * (c) Copyright 2008 by Volker Bergmann. All rights reserved.
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

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.databene.commons.StringUtil;

/**
 * Maintains a list of named objects supporting duplicate names and missing names.<br/><br/>
 * Created at 09.05.2008 20:25:59
 * @since 0.5.4
 * @author Volker Bergmann
 */
public class NamedValueList<E> {
	
	public static final int SENSITIVE = 0;
	public static final int INSENSITIVE = 1;
	public static final int IGNORANT = 2;

	private int caseHandling;
	private List<String> names;
	private List<E> values;
	private Map<String, Integer> indices;

	public static <T> NamedValueList<T> createCaseSensitiveList() {
		return new NamedValueList<T>(SENSITIVE);
	}
	
	public static <T> NamedValueList<T> createCaseInsensitiveList() {
		return new NamedValueList<T>(INSENSITIVE);
	}
	
	public static <T> NamedValueList<T> createCaseIgnorantList() {
		return new NamedValueList<T>(IGNORANT);
	}

	public NamedValueList() {
		this(SENSITIVE);
	}
		
	public NamedValueList(int caseHandling) {
		this.names   = new ArrayList<String>();
		this.values  = new ArrayList<E>();
		this.indices = new HashMap<String, Integer>();
		this.caseHandling = caseHandling;
	}
	
	public int size() {
		return values.size();
	}
	
	public String getName(int index) {
		return names.get(index);
	}
	
	public boolean containsName(String name) {
		if (caseHandling == IGNORANT && name != null)
			name = name.toLowerCase();
		boolean contained = indices.containsKey(name);
		if (contained || caseHandling == IGNORANT || caseHandling == SENSITIVE || name == null)
			return contained;
		for (String nameCandidate : names)
			if (StringUtil.equalsIgnoreCase(nameCandidate, name))
				return true;
		return false;
	}
	
	public E getValue(int index) {
		return values.get(index);
	}

	public void set(String name, E value) {
		if (StringUtil.isEmpty(name))
			add(name, value);
		if (caseHandling == IGNORANT)
			name = name.toLowerCase();
		int index = someIndexOfName(name);
		if (index < 0)
			add(name, value);
		else
			setValue(index, value);
	}
	
	public void add(String name, E value) {
		if (caseHandling == IGNORANT && name != null)
			name = name.toLowerCase();
		names.add(name);
		values.add(value);
		indices.put(name, values.size() - 1);
	}
	
	public void setValue(int index, E value) {
		values.set(index, value);
	}
	
	public E someValueOfName(String name) {
		int index = someIndexOfName(name);
		return (index >= 0 ? getValue(index) : null);
	}
	
	public int someIndexOfName(String name) {
		Integer index;
		if (caseHandling == IGNORANT && name != null)
			name = name.toLowerCase();
		index = indices.get(name);
		if (index != null)
			return index;
		if (caseHandling == IGNORANT || caseHandling == SENSITIVE)
			return -1;
		for (Map.Entry<String, Integer> entry : indices.entrySet())
			if (StringUtil.equalsIgnoreCase(entry.getKey(), name))
				return entry.getValue();
		return -1;
	}
	
	public void clear() {
		names.clear();
		values.clear();
		indices.clear();
	}

	public List<String> names() {
		return Collections.unmodifiableList(names);
	}

	public List<E> values() {
		return Collections.unmodifiableList(values);
	}
	
	@Override
	public String toString() {
		return values.toString();
	}
}
