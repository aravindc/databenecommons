/*
 * (c) Copyright 2013 by Volker Bergmann. All rights reserved.
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

package org.databene.commons.context;

import java.util.HashSet;
import java.util.Set;
import java.util.Stack;
import java.util.Map.Entry;

import org.databene.commons.Assert;
import org.databene.commons.Context;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Combines several contexts to a {@link Stack}, querying recursively in the {@link #get(String)} method 
 * until an entry is found or the stack is completely iterated.<br/><br/>
 * Created: 09.01.2013 13:06:13
 * @since 0.5.21
 * @author Volker Bergmann
 */
public class SimpleContextStack implements ContextStack {

    private static final Logger logger = LoggerFactory.getLogger(ContextStack.class);

    protected Stack<Context> contexts;
    
    public SimpleContextStack(Context ... contexts) {
        this.contexts = new Stack<Context>();
        for (Context c : contexts) 
            this.contexts.push(c);
    }

    @Override
	public Object get(String key) {
        for (int i = contexts.size() - 1; i >= 0; i--) {
            Object result = contexts.get(i).get(key);
            if (result != null)
                return result;
        }
        return null;
    }

	@Override
	public boolean contains(String key) {
        for (int i = contexts.size() - 1; i >= 0; i--) {
            Context c = contexts.get(i);
            if (c.contains(key))
            	return true;
        }
        return false;
    }

    @Override
	public Set<String> keySet() {
        Set<String> keySet = new HashSet<String>();
        for (int i = contexts.size() - 1; i >= 0; i--) {
            Context c = contexts.get(i);
            keySet.addAll(c.keySet());
        }
        return keySet;
    }

	@Override
	public Set<Entry<String, Object>> entrySet() {
		Set<Entry<String, Object>> entrySet = new HashSet<Entry<String, Object>>();
        for (int i = 0; i < contexts.size(); i++) {
            Context c = contexts.get(i);
            entrySet.addAll(c.entrySet());
        }
        return entrySet;
    }

	@Override
	public void remove(String key) {
        if (contexts.size() > 0)
            contexts.peek().remove(key);
    }

    @Override
	public void set(String key, Object value) {
    	Assert.notNull(key, "key");
        if (contexts.size() > 0)
            contexts.peek().set(key, value);
        else
            logger.warn("ContextStack is empty, ignoring element: " + key);
    }

    @Override
	public void push(Context context) {
   		this.contexts.push(context);
    }
    
    @Override
	public Context pop() {
   		return this.contexts.pop();
    }

}
