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

package org.databene.commons.context;

import org.databene.commons.Context;

import java.util.Hashtable;
import java.util.Map;
import java.util.HashMap;
import java.util.Properties;
import java.util.Set;

/**
 * A thread-safe implementation of Context.<br/>
 * <br/>
 * Created: 06.07.2007 06:30:43
 * @author Volker Bergmann
 */
public class DefaultContext implements Context {

    private Context defaults;

    protected Map<String, Object> map;

    public DefaultContext() {
        this((Context) null);
    }

    public DefaultContext(Context defaults) {
        this.defaults = defaults;
        this.map = new HashMap<String, Object>();
    }

    public DefaultContext(Map<String, ?> map) {
    	this.defaults = null;
        this.map = new HashMap<String, Object>(map);
    }
    
    public DefaultContext(Properties props) {
    	this.defaults = null;
        this.map = new HashMap<String, Object>(props.size());
        for (Map.Entry<?, ?> entry : props.entrySet())
        	this.map.put((String) entry.getKey(), entry.getValue());
    }
    
    public synchronized Object get(String key) {
        Object value = map.get(key);
        if (value == null && defaults != null)
            value = defaults.get(key);
        return value;
    }

    public boolean contains(String key) {
        if (map.containsKey(key))
        	return true;
        return (defaults != null && defaults.contains(key));
    }

    public synchronized void set(String key, Object value) {
        map.put(key, value);
    }
    
    public synchronized Set<Map.Entry<String, Object>> entrySet() {
        return map.entrySet();
    }

    public synchronized <K, V> void setAll(Hashtable<K, V> map) {
        for (Map.Entry<K, V> entry : map.entrySet())
            this.set(String.valueOf(entry.getKey()), entry.getValue());
    }

    public synchronized <K, V> void setAll(Map<K, V> map) {
        for (Map.Entry<K, V> entry : map.entrySet())
            this.set(String.valueOf(entry.getKey()), entry.getValue());
    }

    public void remove(String key) {
		map.remove(key);
	}
	
    public Set<String> keySet() {
        return map.keySet();
    }

    @Override
	public synchronized String toString() {
        return map.toString();
    }

}
