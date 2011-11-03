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

package org.databene.commons;

import org.junit.Test;
import static junit.framework.Assert.*;

/**
 * Tests the {@link VMInfo} class.<br/><br/>
 * Created: 21.06.2007 08:35:45
 * @author Volker Bergmann
 */
public class VMInfoTest {
	
	@Test
    public void testJavaClassVersion() {
		String value = System.getProperty("java.class.version");
		assertNotNull(value);
		assertEquals(value, VMInfo.getJavaClassVersion());
    }

	@Test
    public void testJavaCompiler() {
        String value = System.getProperty("java.compiler"); // may be null
		assertEquals(value, VMInfo.getJavaCompiler());
    }

	@Test
    public void testJavaHome() {
		String value = System.getProperty("java.home");
		assertNotNull(value);
		assertEquals(value, VMInfo.getJavaHome());
    }

	@Test
    public void testExtDirs() {
		String value = System.getProperty("java.ext.dirs");
		assertNotNull(value);
		assertEquals(value, VMInfo.getExtDirs());
    }

	@Test
    public void testClassPath() {
		String value = System.getProperty("java.class.path");
		assertNotNull(value);
		assertEquals(value, VMInfo.getClassPath());
    }

	@Test
    public void testLibraryPath() {
		String value = System.getProperty("java.library.path");
		assertNotNull(value);
		assertEquals(value, VMInfo.getLibraryPath());
    }

	// vendor ----------------------------------------------------------------------------------------------------------
	
	@Test
    public void testJavaVendor() { 
        String value = System.getProperty("java.vendor");
		assertNotNull(value);
		assertEquals(value, VMInfo.getJavaVendor());
    }

	@Test
    public void testJavaVendorUrl() {
		String value = System.getProperty("java.vendor.url");
		assertNotNull(value);
		assertEquals(value, VMInfo.getJavaVendorUrl());
    }

	// specification ---------------------------------------------------------------------------------------------------
	
	@Test
    public void testJavaSpecificationVersion() {
		String value = System.getProperty("java.specification.version");
		assertNotNull(value);
		assertEquals(value, VMInfo.getJavaSpecificationVersion());
    }

	@Test
    public void testJavaSpecificationVendor() {
		String value = System.getProperty("java.specification.vendor");
		assertNotNull(value);
		assertEquals(value, VMInfo.getJavaSpecificationVendor());
    }

	@Test
    public void testJavaSpecificationName() {
		String value = System.getProperty("java.specification.name");
		assertNotNull(value);
		assertEquals(value, VMInfo.getJavaSpecificationName());
    }

	// VM --------------------------------------------------------------------------------------------------------------
	
	@Test
    public void testJavaVMName() {
		String value = System.getProperty("java.vm.name");
		assertNotNull(value);
		assertEquals(value, VMInfo.getJavaVmName());
    }
	
	@Test
    public void testJavaVersion() {
		String value = System.getProperty("java.vm.version");
		assertNotNull(value);
		assertEquals(value, VMInfo.getJavaVmVersion());
    }

	@Test
    public void testJavaVMVendor() {
		String value = System.getProperty("java.vm.vendor");
		assertNotNull(value);
		assertEquals(value, VMInfo.getJavaVmVendor());
    }

	// VM specification ------------------------------------------------------------------------------------------------

	@Test
    public void testJavaVMSpecificationName() {
		String value = System.getProperty("java.vm.specification.name");
		assertNotNull(value);
		assertEquals(value, VMInfo.getJavaVmSpecificationName());
    }

	@Test
    public void testJavaVMSpecificationVersion() {
		String value = System.getProperty("java.vm.specification.version");
		assertNotNull(value);
		assertEquals(value, VMInfo.getJavaVmSpecificationVersion());
    }

	@Test
    public void testJavaVMSpecificationVendor() {
		String value = System.getProperty("java.vm.specification.vendor");
		assertNotNull(value);
		assertEquals(value, VMInfo.getJavaVmSpecificationVendor());
    }

}
