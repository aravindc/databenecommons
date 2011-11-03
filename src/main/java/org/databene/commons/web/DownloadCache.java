/*
 * (c) Copyright 2010 by Volker Bergmann. All rights reserved.
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

package org.databene.commons.web;

import java.io.File;
import java.io.IOException;
import java.net.URL;

import org.databene.commons.IOUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Provides file download and caches files in the file system.<br/><br/>
 * Created: 15.08.2010 10:07:24
 * @since 0.5.4
 * @author Volker Bergmann
 */
public class DownloadCache {

	private static final String DEFAULT_ROOT_FOLDER = "./cache";

	private static final Logger LOGGER = LoggerFactory.getLogger(DownloadCache.class);
	
	private File rootFolder;

	public DownloadCache() {
		this(new File(DEFAULT_ROOT_FOLDER));
    }
	
	public DownloadCache(File rootFolder) {
		this.rootFolder = rootFolder;
    }
	
	public File get(URL url) throws IOException {
	    File cacheSubDir = new File(rootFolder, url.getHost());
	    String filename = url.getFile();
	    if (filename.endsWith("/"))
	    	filename = filename.substring(0, filename.length() - 1) + ".dir";
		File cacheFile = new File(cacheSubDir, filename);
		if (!cacheFile.exists())
			IOUtil.download(url, cacheFile);
		else
			LOGGER.info("providing {} from cache file {}", url, cacheFile.getAbsolutePath());
		return cacheFile;
	}

}
