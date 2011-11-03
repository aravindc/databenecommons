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

package org.databene.commons.accessor;

import org.databene.commons.Accessor;

import java.util.List;
import java.util.ArrayList;
import java.util.Arrays;

/**
 * DependentAccessor implementation that manages the dependencies in a List.<br/>
 * <br/>
 * Created: 11.03.2006 14:32:52
 * @author Volker Bergmann
 */
public abstract class ManagedAccessor<C, V> implements DependentAccessor<C, V> {

    protected List<? extends Accessor<?, ?>> dependencies;
    
    // constructors ----------------------------------------------------------------------------------------------------

    protected ManagedAccessor() {
        this(new ArrayList<Accessor<?, ?>>());
    }

    @SuppressWarnings("unchecked")
    protected ManagedAccessor(Accessor<?, ?> dependency) {
        this(Arrays.asList(dependency));
    }

    protected ManagedAccessor(List<? extends Accessor<?, ?>> dependencies) {
        this.dependencies = dependencies;
    }
    
    // DependentAccessor interface -------------------------------------------------------------------------------------

    public List<? extends Accessor<?, ?>> getDependencies() {
        return dependencies;
    }

    public void setDependencies(List<? extends Accessor<?, ?>> dependencies) {
        this.dependencies = dependencies;
    }
    
}
