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

package org.databene.commons.accessor;

import org.databene.commons.Accessor;

import java.util.List;
import java.util.ArrayList;

/**
 * Accessor implementation that caches the value it retrieves first 
 * until it is manually invalidated.<br/>
 * <br/>
 * Created: 11.03.2006 17:02:27
 * @author Volker Bergmann
 */
public class AccessorCache<C, V> implements DependentAccessor<C, V> {

    private String name;
    private Accessor<C, V> realAccessor;
    private V cachedValue;
    private boolean valid;

    public AccessorCache(String name, Accessor<C, V> realAccessor) {
        this.realAccessor = realAccessor;
        this.name = name;
        this.valid = false;
    }
    
    // properties ------------------------------------------------------------------------------------------------------

    public String getName() {
        return name;
    }
    
    public boolean isValid() {
        return valid;
    }

    public void invalidate() {
        valid = false;
    }

    // DependentAccessor interface -------------------------------------------------------------------------------------

    public V getValue(C item) {
        if (!valid) {
            cachedValue = realAccessor.getValue(item);
            valid = true;
        }
        return cachedValue;
    }

    @SuppressWarnings({ "unchecked", "rawtypes" })
    public List<? extends Accessor<?,?>> getDependencies() {
        if (realAccessor instanceof DependentAccessor)
            return ((DependentAccessor) realAccessor).getDependencies();
        else
            return new ArrayList();
    }
    
}
