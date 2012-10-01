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

package org.databene.commons.context;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.Map.Entry;

import org.databene.commons.CollectionUtil;
import org.databene.commons.Context;

/**
 * {@link Context} implementation which is case insensitive regarding key Strings.<br/>
 * <br/>
 * Created at 19.11.2008 08:55:05
 * @since 0.4.6
 * @author Volker Bergmann
 */

public class CaseInsensitiveContext implements Context {
	
	private boolean capsPreserved;
	private Map<String, Object> map;

	public CaseInsensitiveContext(boolean capsPreserved) {
		this.capsPreserved = capsPreserved;
		map = new HashMap<String, Object>();
	}
	
	// Context interface implementation --------------------------------------------------------------------------------

	@Override
	public synchronized void set(String key, Object value) {
		map.put(transformKey(key), value);
	}
	
    @Override
	public synchronized Object get(String key) {
        return CollectionUtil.getCaseInsensitive(transformKey(key), map);
    }

    @Override
	public boolean contains(String key) {
        return CollectionUtil.containsCaseInsensitive(transformKey(key), map);
    }

	@Override
	public Set<String> keySet() {
		return map.keySet();
	}

	@Override
	public Set<Entry<String, Object>> entrySet() {
		return map.entrySet();
	}

	@Override
	public void remove(String key) {
		map.remove(transformKey(key));
	}
	
	// private helpers -------------------------------------------------------------------------------------------------

	private String transformKey(String key) {
		if (!capsPreserved)
			key = key.toUpperCase();
		return key;
	}
	
	// java.lang.Object overrides --------------------------------------------------------------------------------------
	
	@Override
	public String toString() {
		return getClass().getSimpleName() + map;
	}

}
