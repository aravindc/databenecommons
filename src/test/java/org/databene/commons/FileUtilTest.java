/*
 * (c) Copyright 2009-2010 by Volker Bergmann. All rights reserved.
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

import java.io.File;
import java.io.IOException;
import java.util.List;

import org.junit.Test;
import static junit.framework.Assert.*;

/**
 * Tests the {@link FileUtil} class.<br/>
 * <br/>
 * Created at 10.03.2009 18:21:54
 * @since 0.5.8
 * @author Volker Bergmann
 */

public class FileUtilTest {
	
	protected File ROOT_DIR = new File("target" + File.separator + "filetest");
	protected File SUB_DIR = new File(ROOT_DIR, "sub");
	protected File ROOT_DIR_FILE = new File(ROOT_DIR, "fr.txt");
	protected File SUB_DIR_FILE = new File(SUB_DIR, "fs.txt");

	@Test
	public void testCopyFile() throws Exception {
		File rootDir = new File("target");
		File srcFile = new File(rootDir, getClass().getSimpleName() + ".original");
		File targetFile = new File(rootDir, getClass().getSimpleName() + ".copy");
		try {
	        IOUtil.writeTextFile(srcFile.getAbsolutePath(), "0123456789");
	        assertTrue(srcFile.exists());
	        
	        // create file no matter the preconditions
	        FileUtil.copy(srcFile, targetFile, true);
	        assertTrue(targetFile.exists());
	        assertTrue(targetFile.length() >= 10);
	        
	        // check that copy fails if file exists and overwrite=false
	        try {
	        	FileUtil.copy(srcFile, targetFile, false);
	        	fail("Exception expected if target file exists");
	        } catch (ConfigurationError e) {
	        	// exception is expected here
	        }
	        
	        // check that copy succeeds if file exists and overwrite=true
        	FileUtil.copy(srcFile, targetFile, true);

        } finally {
        	// remove the used files
        	FileUtil.deleteIfExists(targetFile);
        	FileUtil.deleteIfExists(srcFile);
        }
	}
	
	@Test
	public void testListFiles() throws Exception {
		createTestFolders();
		try {
			check(null, true, false, false, ROOT_DIR_FILE); // non-recursive, only files, w/o pattern
			check("fr.*", true, false, false, ROOT_DIR_FILE); // non-recursive, only files,  w/ pattern
			check("x.*", true, false, false); // non-recursive, only files, w/ pattern
			check(null, false, true, false, SUB_DIR); // non-recursive, only folders, w/o pattern
			check(null, true, true, false, SUB_DIR, ROOT_DIR_FILE); // non-recursive, files and folders, w/o pattern
			check(null, true, true, true, SUB_DIR, ROOT_DIR_FILE, SUB_DIR_FILE); // recursive, files and folders, w/o pattern
			check(null, false, true, true, SUB_DIR); // recursive, only folders, w/o pattern
			check("f.*", true, true, true, ROOT_DIR_FILE, SUB_DIR_FILE); // recursive, files and folders, "f.*" pattern
			check("s.*", false, true, true, SUB_DIR); // recursive, only folders, "s.*" pattern
        } finally {
        	// remove the used files
        	removeTestFolders();
        }
	}
	
	@Test
	public void testLocalFilename() {
		assertEquals(null, FileUtil.localFilename(null));
		assertEquals("", FileUtil.localFilename(""));
		assertEquals("x", FileUtil.localFilename("x"));
		assertEquals("x", FileUtil.localFilename("/x"));
		assertEquals("x", FileUtil.localFilename("y/z/x"));
		assertEquals("x", FileUtil.localFilename("y" + File.separator + "z" + File.separator + "x"));
	}
	
	@Test
	public void testRelativePath() {
		assertEquals(".",    FileUtil.relativePath(new File("a/b/index.html"), new File("a/b"), '/'));
		assertEquals("b",    FileUtil.relativePath(new File("a/index.html"),   new File("a/b"), '/'));
		assertEquals("../c", FileUtil.relativePath(new File("a/b/index.html"), new File("a/c"), '/'));
	}
	
	@Test
	public void testFileOfLimitedPathLength_valid() throws Exception {
		File root = new File(SystemInfo.isWindows() ? "C:\\" : "/");
		File normResult = FileUtil.fileOfLimitedPathLength(
				root, "testx", ".xml", root.getCanonicalPath().length() + 10, false);
		assertEquals(root.getCanonicalPath() + "testx.xml", normResult.getCanonicalPath());
		File cutResult = FileUtil.fileOfLimitedPathLength(
				root, "testx", ".xml", root.getCanonicalPath().length() + 9, false);
		assertEquals(root.getCanonicalPath() + "test.xml", cutResult.getCanonicalPath());
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void testFileOfLimitedPathLength_invalid() throws Exception {
		File root = new File((SystemInfo.isWindows() ? "C:\\" : "/") + "01234567890123456789");
		FileUtil.fileOfLimitedPathLength(root, "test", ".xml", 10, false);
	}
	
	@Test
	public void testNewFile() {
		// file relative to working dir
		assertEquals(new File(SystemInfo.getCurrentDir(), "x").getAbsoluteFile(), FileUtil.newFile("x").getAbsoluteFile());
		if (!SystemInfo.isWindows()) {
			// file in user home
			assertEquals(new File(SystemInfo.getUserHome(), "x").getAbsolutePath(), FileUtil.newFile("~/x").getAbsolutePath());
			// file in other user's home
			File otherUsersHome = new File(new File(SystemInfo.getUserHome()).getParentFile(), "qawsed");
			assertEquals(new File(otherUsersHome, "x").getAbsolutePath(), FileUtil.newFile("~qawsed/x").getAbsolutePath());
		}
	}
	
	// test helpers ----------------------------------------------------------------------------------------------------

	private void check(String regex, boolean acceptingFiles, boolean acceptingFolders, boolean recursive, 
			File... expectedResult) {
        List<File> actual = FileUtil.listFiles(ROOT_DIR, regex, recursive, acceptingFiles, acceptingFolders);
        List<File> expected = CollectionUtil.toList(expectedResult);
		assertTrue("Expected " + expected + ", but was " + actual, CollectionUtil.equalsIgnoreOrder(expected, actual));
    }

	protected void createTestFolders() throws IOException {
		FileUtil.ensureDirectoryExists(ROOT_DIR);
		FileUtil.ensureDirectoryExists(SUB_DIR);
	    IOUtil.writeTextFile(ROOT_DIR_FILE.getAbsolutePath(), "rfc");
		IOUtil.writeTextFile(SUB_DIR_FILE.getAbsolutePath(), "sfc");
    }

	protected void removeTestFolders() {
	    FileUtil.deleteIfExists(SUB_DIR_FILE);
	    FileUtil.deleteIfExists(SUB_DIR);
	    FileUtil.deleteIfExists(ROOT_DIR_FILE);
	    FileUtil.deleteIfExists(ROOT_DIR);
    }

}
