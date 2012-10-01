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

package org.databene.commons.bean;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

import org.junit.Test;
import static junit.framework.Assert.*;

/**
 * Tests the {@link ObservableFactory}.<br/>
 * <br/>
 * Created at 17.07.2008 20:33:51
 * @since 0.4.5
 * @author Volker Bergmann
 */
public class ObservableFactoryTest {
	
	@Test
	public void testToString() {
		assertEquals("org.databene.commons.bean.IPerson{age=23, name=Alice}", createAlice().toString());
	}

	@Test
	public void testEquals() {
		assertTrue(createAlice().equals(createAlice()));
	}

	@Test
	public void testHashCode() {
		assertEquals(createAlice().hashCode(), createAlice().hashCode());
	}

	@Test
	public void testEvents() {
		IPerson person = createAlice();
		Listener listener = new Listener();
		person.addPropertyChangeListener("name", listener);
		person.setName("Joe");
		assertEquals("Joe", listener.getName());
	}

	// private helpers -------------------------------------------------------------------------------------------------
	
	private IPerson createAlice() {
		IPerson person = ObservableFactory.create(IPerson.class);
		person.setName("Alice");
		person.setAge(23);
		return person;
	}
	
	public static class Listener implements PropertyChangeListener {
		
		private String name;

		public String getName() {
			return name;
		}

		@Override
		public void propertyChange(PropertyChangeEvent evt) {
			 name = (String) evt.getNewValue();
		}
		
	}
	
}
