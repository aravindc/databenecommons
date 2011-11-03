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

package org.databene.commons.bean;

import org.databene.commons.ConfigurationError;
import org.junit.Test;

import static junit.framework.Assert.*;

/**
 * Tests the {@link ClassCache}.<br/>
 * <br/>
 * Created at 15.11.2008 17:16:09
 * @since 0.4.6
 * @author Volker Bergmann
 */
public class ClassCacheTest {

	@Test
	public void testFqName() {
		ClassCache cache = new ClassCache();
		assertEquals(String.class, cache.forName("java.lang.String"));
	}

	@Test
	public void testDefaultPackage() {
		ClassCache cache = new ClassCache();
		assertEquals(String.class, cache.forName("String"));
	}

	@Test
	public void testUndefinedPackage() {
		try {
			ClassCache cache = new ClassCache();
			assertEquals(String.class, cache.forName("ClassCache"));
			fail("ConfigurationError expected");
		} catch (ConfigurationError e) {
			// that's the desired behavior
		}
	}

	@Test
	public void testCustomPackage1() {
		ClassCache cache = new ClassCache();
		cache.importPackage("org.databene.commons.bean");
		assertEquals(ClassCache.class, cache.forName("ClassCache"));
	}

	@Test
	public void testCustomPackage2() {
		ClassCache cache = new ClassCache();
		cache.importClass("org.databene.commons.bean.*");
		assertEquals(ClassCache.class, cache.forName("ClassCache"));
	}

	@Test
	public void testUndefinedImportClass() {
		try {
			ClassCache cache = new ClassCache();
			cache.importClass("org.databene.commons.bean");
		} catch (ConfigurationError e) {
			// that's the desired behavior
		}
	}

	@Test
	public void testCustomClass() {
		ClassCache cache = new ClassCache();
		cache.importClass("org.databene.commons.bean.ClassCache");
		assertEquals(ClassCache.class, cache.forName("ClassCache"));
	}

}
