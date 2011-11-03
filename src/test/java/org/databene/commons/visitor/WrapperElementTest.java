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

package org.databene.commons.visitor;

import java.util.ArrayList;
import java.util.Collection;

import org.databene.commons.Element;
import org.databene.commons.Visitor;

import org.junit.Test;
import static junit.framework.Assert.*;

/**
 * Tests the {@link WrapperElement} class.<br/><br/>
 * Created at 02.05.2008 12:29:30
 * @since 0.4.3
 * @author Volker Bergmann
 */
public class WrapperElementTest {
	
	@Test
	public void testEquals() {
		IntWrapper nullWrapper = new IntWrapper(null);
		IntWrapper oneWrapper = new IntWrapper(1);
		assertFalse(nullWrapper.equals(null));
		assertFalse(nullWrapper.equals(""));
		assertFalse(nullWrapper.equals(oneWrapper));
		assertTrue(nullWrapper.equals(nullWrapper));
		assertTrue(nullWrapper.equals(new IntWrapper(null)));
		assertTrue(oneWrapper.equals(new IntWrapper(1)));
		assertFalse(oneWrapper.equals(nullWrapper));
		assertFalse(nullWrapper.equals(oneWrapper));
	}
	
	public static class IntWrapper extends WrapperElement<Integer> {

		protected IntWrapper(Integer wrappedInt) {
			super(wrappedInt);
		}

		@Override
        protected Collection<Element<Integer>> getChildren(
				Visitor<Integer> visitor) {
			return new ArrayList<Element<Integer>>();
		}
	}

}
