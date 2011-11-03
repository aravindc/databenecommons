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

import java.io.File;
import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Tests the {@link SystemInfo} class.<br/><br/>
 * Created: 21.06.2007 08:35:00
 * @author Volker Bergmann
 */
public class SystemInfoTest {

    private static final Logger logger = LoggerFactory.getLogger(SystemInfoTest.class);

	@Test
    public void testVersion() {
        assertNotNull(SystemInfo.getOsVersion());
    }

	@Test
    public void testOSArchitecture() {
        assertNotNull(SystemInfo.getOsArchitecture());
    }

	@Test
    public void testOSName() {
        assertNotNull(SystemInfo.getOsName());
    }

	@Test
    public void testLineSeparator() {
        assertNotNull(SystemInfo.getLineSeparator());
    }

	@Test
    public void testPathSeparator() {
        assertNotNull(SystemInfo.getPathSeparator());
    }

	@Test
    public void testFileSeparator() {
        char fileSeparator = SystemInfo.getFileSeparator();
        assertEquals(File.separatorChar, fileSeparator);
    }

	@Test
    public void testCurrentDir() throws IOException {
        String currentDir = SystemInfo.getCurrentDir();
        assertNotNull(currentDir);
        assertEquals(new File(".").getCanonicalPath(), currentDir);
    }

	@Test
    public void testUserName() {
        assertNotNull(SystemInfo.getUserName());
    }

	@Test
    public void testUserHome() {
        File userHome = new File(SystemInfo.getUserHome());
        assertTrue(userHome.exists());
    }

	@Test
    public void testTempDir() {
        String tempDir = SystemInfo.getTempDir();
        assertNotNull(tempDir);
        assertTrue(new File(tempDir).exists());
    }

	@Test
    public void testUserLanguage() {
        String userLanguage = SystemInfo.getUserLanguage();
        logger.debug("user language: " + userLanguage);
        assertNotNull(userLanguage);
    }

}
