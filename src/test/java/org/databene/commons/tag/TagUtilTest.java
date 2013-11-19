/*
 * (c) Copyright 2013 by Volker Bergmann. All rights reserved.
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

package org.databene.commons.tag;

import static org.junit.Assert.*;

import org.junit.Test;

/**
 * Tests the TagUtil class.<br/><br/>
 * Created: 16.11.2013 07:16:00
 * @since 0.5.25
 * @author Volker Bergmann
 */

public class TagUtilTest {
	
	@Test
	public void testHasTag() {
		TaggedBean bean = new TaggedBean("Alpha", "Beta");
		
		// test perfect match
		assertTrue(TagUtil.hasTag( "Alpha", bean, false, false));
		assertTrue(TagUtil.hasTag( "Beta",  bean, false, false));
		assertFalse(TagUtil.hasTag("Gamma", bean, false, false));
		
		// test partial match
		assertTrue( TagUtil.hasTag("Alp", bean, false, true));
		assertTrue( TagUtil.hasTag("Bet", bean, false, true));
		assertFalse(TagUtil.hasTag("Gam", bean, false, true));
		
		// test ignoreCase match
		assertTrue(TagUtil.hasTag("alpha", bean, true, false));
		assertTrue(TagUtil.hasTag("beta", bean, true, false));
		assertFalse(TagUtil.hasTag("gamma", bean, true, false));
		
		// test partial ignoreCase match
		assertTrue(TagUtil.hasTag("alp", bean, true, true));
		assertTrue(TagUtil.hasTag("bet", bean, true, true));
		assertFalse(TagUtil.hasTag("gam", bean, true, true));
	}
	
	static class TaggedBean extends AbstractTagged {

		public TaggedBean(String... tags) {
			for (String tag : tags)
				addTag(tag);
		}
		
	}
	
}
