/*
 * (c) Copyright 2008-2012 by Volker Bergmann. All rights reserved.
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

package org.databene.commons.comparator;

import java.util.Comparator;

import org.databene.commons.Person;

import org.junit.Test;
import static junit.framework.Assert.*;

/**
 * Tests the {@link ComparatorFactory}.<br/><br/>
 * Created: 16.03.2008 15:13:56
 * @author Volker Bergmann
 */
public class ComparatorFactoryTest {
    
	@Test
    public void testStringCollator() {
        Comparator<String> stringComparator = ComparatorFactory.getComparator(String.class);
        assertNotNull(stringComparator);
        assertEquals(-1, stringComparator.compare("1", "2"));
    }
    
	@Test
    public void testPersonComparator() {
        Comparator<Person> personComparator = ComparatorFactory.getComparator(Person.class);
        assertNotNull(personComparator);
        Person alice = new Person("Alice", 23);
        Person bob   = new Person("Bob",   34);
        assertEquals(-1, personComparator.compare(alice, bob));
    }
    
    public static final class MyComparator implements Comparator<Person> {

        @Override
		public int compare(Person p1, Person p2) {
            return IntComparator.compare(p1.getAge(), p2.getAge());
        }
    }

}
