/*
 * (c) Copyright 2010-2012 by Volker Bergmann. All rights reserved.
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

package org.databene.commons.file;

import java.io.File;
import java.util.regex.Pattern;

/**
 * {@link FileFilter} that can be configured to accepted files and/or folders 
 * based on a regular expression.<br/><br/>
 * Created: 24.02.2010 07:09:52
 * @since 0.5.0
 * @author Volker Bergmann
 */
public class PatternFileFilter implements FileFilter {
	
	private Pattern pattern;
	private boolean acceptingFiles;
	private boolean acceptingFolders;

	public PatternFileFilter(String regex, boolean acceptingFiles, boolean acceptingFolders) {
	    this.pattern = (regex != null ? Pattern.compile(regex) : null);
	    this.acceptingFiles = acceptingFiles;
	    this.acceptingFolders = acceptingFolders;
    }

	@Override
	public boolean accept(File file) {
		if (pattern != null && !pattern.matcher(file.getName()).matches())
	    	return false;
		return (acceptingFiles && file.isFile()) || (acceptingFolders && file.isDirectory());
    }

}
