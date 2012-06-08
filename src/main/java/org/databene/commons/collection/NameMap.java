/*
 * (c) Copyright 2012 by Volker Bergmann. All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, is permitted under the terms of the
 * GNU General Public License (GPL).
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

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import org.databene.commons.Named;

/**
 * A {@link Map} which offers convenience methods for managing {@link Named} objects
 * in a Map semantics by their <code>name</code> property.<br/><br/>
 * Created: 06.06.2012 20:15:36
 * @since 0.5.16
 * @author Volker Bergmann
 */
public class NameMap<E extends Named> extends HashMap<String, E> {

	private static final long serialVersionUID = -4765030342987297182L;

	public NameMap() {
		super();
	}

	public NameMap(int initialCapacity, float loadFactor) {
		super(initialCapacity, loadFactor);
	}

	public NameMap(int initialCapacity) {
		super(initialCapacity);
	}

	public NameMap(Collection<E> prototype) {
		super(prototype.size());
		for (E item : prototype)
			super.put(item.getName(), item);
	}
	
	public NameMap(E... elements) {
		super(elements.length);
		for (E element : elements)
			super.put(element.getName(), element);
	}
	
	public void put(E item) {
		super.put(item.getName(), item);
	}
	
}
