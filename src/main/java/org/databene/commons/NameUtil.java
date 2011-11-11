/*
 * (c) Copyright 2010-2011 by Volker Bergmann. All rights reserved.
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

package org.databene.commons;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

/**
 * Provides convenience methods for {@link Named} objects.<br/><br/>
 * Created: 12.08.2010 09:21:46
 * @since 0.5.4
 * @author Volker Bergmann
 */
public class NameUtil {

	private NameUtil() { }
	
    public static String[] getNames(Named[] objects) {
    	String[] result = new String[objects.length];
    	for (int i = 0; i < objects.length; i++)
    		result[i] = objects[i].getName();
    	return result;
    }

    public static <T extends Collection<? extends Named>> List<String> getNames(T objects) {
    	List<String> result = new ArrayList<String>(objects.size());
    	for (Named object : objects)
    		result.add(object.getName());
    	return result;
    }

    public static <T extends Named> void orderByName(T[] objects) {
    	Arrays.sort(objects, new NameComparator());
    }

    public static <T extends Named> void orderByName(List<T> objects) {
    	Collections.sort(objects, new NameComparator());
    }

	public static int indexOf(String name, List<? extends Named> objects) {
		for (int i = 0; i < objects.size(); i++)
			if (name.equals(objects.get(i).getName()))
				return i;
		return -1;
	}

	public static int indexOf(String name, Named[] objects) {
		for (int i = 0; i < objects.length; i++)
			if (name.equals(objects[i].getName()))
				return i;
		return -1;
	}

	public static void sort(List<? extends Named> namedObjects) {
		Collections.sort(namedObjects, new NameComparator());
	}

}
