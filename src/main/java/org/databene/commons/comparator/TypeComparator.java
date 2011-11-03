/*
 * (c) Copyright 2010 by Volker Bergmann. All rights reserved.
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

package org.databene.commons.comparator;

import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;

/**
 * Compares classes based on a predefined order.<br/><br/>
 * Created: 17.02.2010 13:07:38
 * @since 0.5.0
 * @author Volker Bergmann
 */
public class TypeComparator implements Comparator<Class<?>> {

    private Map<Class<?>, Integer> indexes;

    public TypeComparator(Class<?> ... orderedClasses) {
        indexes = new HashMap<Class<?>, Integer>();
        int count = 0;
        for (Class<?> type : orderedClasses)
            indexes.put(type, ++count);
    }

	public int compare(Class<?> c1, Class<?> c2) {
		if (c1 == c2)
			return 0;
        int i1 = indexOfClass(c1);
        int i2 = indexOfClass(c2);
        return IntComparator.compare(i1, i2);
    }

    private int indexOfClass(Class<?> type) {
        Integer result = indexes.get(type);
        if (result == null)
        	throw new IllegalArgumentException("Not a supported type: " + type);
		return result;
    }
    
}
