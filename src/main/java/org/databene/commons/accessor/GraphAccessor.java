/*
 * (c) Copyright 2007-2014 by Volker Bergmann. All rights reserved.
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

/**
 * Accesses object graphs by splitting a path names into tokens by a dot separator('.').<br/>
 * <br/>
 * Created: 12.06.2007 18:29:19
 * @author Volker Bergmann
 */
@SuppressWarnings({ "unchecked", "rawtypes" })
public class GraphAccessor implements Accessor {

    private Accessor realAccessor;

    public GraphAccessor(String path) {
        int separatorIndex = path.lastIndexOf('.');
        if (separatorIndex < 0)
            realAccessor = new FeatureAccessor(path);
        else {
            realAccessor = new FetchingAccessor(
                    new GraphAccessor(path.substring(0, separatorIndex)),
                    new FeatureAccessor(path.substring(separatorIndex + 1))
            );
        }
    }
    
    
    // Accessor interface implementation -------------------------------------------------------------------------------

    @Override
	public Object getValue(Object o) {
        return realAccessor.getValue(o);
    }
    
    
    // java.lang.Object overrides --------------------------------------------------------------------------------------
    
    @Override
    public String toString() {
        return getClass().getSimpleName() + '[' + realAccessor + ']';
    }
    
    
    // static utility methods ------------------------------------------------------------------------------------------
    
    public static Object getValue(String path, Object o) {
    	return new GraphAccessor(path).getValue(o);
    }
    
}
