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

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;
import java.util.zip.ZipOutputStream;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Provides ZIP-related convenience methods.<br/><br/>
 * Created: 20.10.2011 15:19:07
 * @since 0.5.10
 * @author Volker Bergmann
 */
public class ZipUtil {

	private static final int BUFFER_SIZE = 2048;
	
	private static final Logger LOGGER = LoggerFactory.getLogger(ZipUtil.class);

	public static void compress(File source, File zipFile) throws IOException {
		ZipOutputStream out = new ZipOutputStream(new BufferedOutputStream(new FileOutputStream(zipFile)));
		out.setMethod(ZipOutputStream.DEFLATED);
		try {
			addFileOrDirectory(source, source, out);
			out.close();
		} catch (IOException e) {
			throw new RuntimeException("Zipping the report failed");
		}
	}
	
	public static void printContent(File zipFile) {
		ZipInputStream in = null;
		try {
			in = new ZipInputStream(new BufferedInputStream(new FileInputStream(zipFile)));
			ZipEntry entry;
			while ((entry = in.getNextEntry()) != null)
				System.out.println(entry.getName());
		} catch (IOException e) {
			LOGGER.error("Error listing archive content of file " + zipFile, e);
		} finally {
			IOUtil.close(in);
		}
	}
	
	// private helpers -------------------------------------------------------------------------------------------------

	private static void addFileOrDirectory(File source, File root, ZipOutputStream out) throws IOException {
		if (source.isFile())
			addFile(source, root, out);
		else if (source.isDirectory())
			addDirectory(source, root, out);
	}

	private static void addDirectory(File source, File root, ZipOutputStream out) throws IOException {
		for (File file : source.listFiles())
			addFileOrDirectory(file, root, out);
	}

	private static void addFile(File source, File root, ZipOutputStream out) throws IOException {
		byte[] buffer = new byte[BUFFER_SIZE];
		InputStream in = new BufferedInputStream(new FileInputStream(source));
		ZipEntry entry = new ZipEntry(FileUtil.relativePath(root, source));
		out.putNextEntry(entry);
		int count;
		while ((count = in.read(buffer, 0, BUFFER_SIZE)) != -1)
			out.write(buffer, 0, count);
		in.close();
	}
	
}
