/*
 * (c) Copyright 2011 by Volker Bergmann. All rights reserved.
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

import static org.junit.Assert.*;

import org.databene.commons.version.VersionInfo;
import org.junit.Test;

/**
 * Tests the {@link VersionInfo}.<br/><br/>
 * Created: 23.03.2011 11:17:36
 * @since 0.5.8
 * @author Volker Bergmann
 */
public class VersionInfoTest {

	private static final String LIBRARY = "commons";

	@SuppressWarnings("null")
	@Test
	public void testSelf() {
		VersionInfo version = VersionInfo.getInfo(LIBRARY);
		String versionNumber = version.getVersion();
		assertFalse(versionNumber == null || versionNumber.length() == 0);
		assertFalse(versionNumber.startsWith("${"));
		System.out.println(version);
	}
	
	@Test
	public void testDependencies() {
		VersionInfo version = VersionInfo.getInfo(LIBRARY);
		version.verifyDependencies();
	}
	
}
