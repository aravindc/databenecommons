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
import org.databene.commons.NullSafeComparator;

import java.util.Map;

/**
 * Accesses Values from a Map.<br/>
 * <br/>
 * Created: 11.03.2006 12:39:07
 * @author Volker Bergmann
 */
public class MapAccessor<C extends Map<K, V>, K, V> implements Accessor<C, V> {

    /**
     * the key of the object to look up; null is supported.
     */
    private K key;

    public MapAccessor(K key) {
        this.key = key;
    }
    
    // Accessor interface ----------------------------------------------------------------------------------------------

    public V getValue(C item) {
        return item.get(key);
    }
    
    // java.lang.Object overrides --------------------------------------------------------------------------------------

    @SuppressWarnings("rawtypes")
    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;
        final MapAccessor that = (MapAccessor) o;
        return NullSafeComparator.equals(this.key, that.key);
    }

    @Override
    public int hashCode() {
        return (key != null ? key.hashCode() : 0);
    }
}
