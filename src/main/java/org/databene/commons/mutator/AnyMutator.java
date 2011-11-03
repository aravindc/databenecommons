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

package org.databene.commons.mutator;

import java.util.Map;

import org.databene.commons.BeanUtil;
import org.databene.commons.Composite;
import org.databene.commons.Context;
import org.databene.commons.Escalator;
import org.databene.commons.LoggerEscalator;
import org.databene.commons.Mutator;
import org.databene.commons.UpdateFailedException;
import org.databene.commons.accessor.FeatureAccessor;

/**
 * Mutator implementation for graphs of any object types.<br/><br/>
 * Created: 31.01.2008 20:15:11
 * @since 0.3.0
 * @author Volker Bergmann
 */
public class AnyMutator implements Mutator {
    
    private String path;
    private boolean strict;
    
    public AnyMutator(String path) {
        this(path, true);
    }

    public AnyMutator(String path, boolean strict) {
        this.path = path;
        this.strict = strict;
    }

    public void setValue(Object target, Object value) throws UpdateFailedException {
        setValue(target, path, value, strict);
    }
    
    public static <C, V> void setValue(C target, String path, V value) {
        setValue(target, path, value, true);
    }
    
    public static <C, V> void setValue(C target, String path, V value, boolean strict) {
        int sep = path.indexOf('.');
        if (sep < 0)
            setLocal(target, path, value, strict);
        else {
            String localName = path.substring(0, sep);
            Object subTarget = FeatureAccessor.getValue(target, localName);
            if (subTarget == null)
                throw new IllegalStateException("No feature '" + localName + "' found in " + target);
            String remainingName = path.substring(sep + 1);
            setValue(subTarget, remainingName, value, strict);
        }
    }
    
    @SuppressWarnings({ "unchecked", "rawtypes" })
    private static <C, V> void setLocal(C target, String featureName, V value, boolean strict) {
    	if (BeanUtil.hasProperty(target.getClass(), featureName))
            BeanUtil.setPropertyValue(target, featureName, value, false);
    	else if (target instanceof Context)
            ((Context) target).set(featureName, value);
        else if (target instanceof Map)
            ((Map) target).put(featureName, value);
        else if (target instanceof Composite)
            ((Composite) target).setComponent(featureName, value);
        else {
            String message = "No feature '" + featureName + "' found in " + target;
            if (strict)
                throw new UnsupportedOperationException(message);
            else
                escalator.escalate(message, AnyMutator.class, null);
        }
    }
    
    private static Escalator escalator = new LoggerEscalator();
}
