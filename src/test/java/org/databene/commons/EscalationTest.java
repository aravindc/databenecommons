/*
 * (c) Copyright 2008-2009 by Volker Bergmann. All rights reserved.
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

package org.databene.commons;

import org.junit.Test;
import static junit.framework.Assert.*;

/**
 * Tests the {@link Escalation} class.<br/><br/>
 * Created at 29.04.2008 18:51:28
 * @since 0.2.04
 * @author Volker Bergmann
 */
public class EscalationTest {
	
	@Test    
	public void testEquals() {
		Escalation e1 = new Escalation("mess", "orig", "cause");
		Escalation e2 = new Escalation("other message", "orig", "cause");
		Escalation e3 = new Escalation("mess", "other orig", "cause");
		Escalation e4 = new Escalation("mess", "orig", "other cause");
		Escalation e5 = new Escalation("mess", "orig", null);
		assertTrue(e1.equals(e1));
		assertFalse(e1.equals(null));
		assertFalse(e1.equals("bla"));
		assertFalse(e1.equals(e2));
		assertFalse(e1.equals(e3));
		assertTrue(e1.equals(e4));
		assertTrue(e1.equals(e5));
		assertTrue(e5.equals(e1));
	}
	
}
