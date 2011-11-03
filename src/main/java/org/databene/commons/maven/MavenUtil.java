/*
 * (c) Copyright 2008, 2009 by Volker Bergmann. All rights reserved.
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

package org.databene.commons.maven;

import java.io.File;

import org.databene.commons.ArrayBuilder;
import org.databene.commons.ErrorHandler;
import org.databene.commons.ShellUtil;
import org.databene.commons.SystemInfo;

/**
 * Provides utility methods for Maven invocation and repository access.<br/>
 * <br/>
 * Created at 17.12.2008 10:32:32
 * @since 0.4.7
 * @author Volker Bergmann
 */

public class MavenUtil {

	public static void invoke(String goal, File folder, boolean online) {
		ArrayBuilder<String> cmdBuilder = new ArrayBuilder<String>(String.class);
		
		// maven invocation
		if (SystemInfo.isWindows())
			cmdBuilder.add("mvn.bat");
		else
			cmdBuilder.add("mvn");
		
		// offline parameter?
		if (!online)
			cmdBuilder.add("-o");
		
		// goal
		cmdBuilder.add(goal);
		
		// run
		ShellUtil.runShellCommand(cmdBuilder.toArray(), null, folder, new ErrorHandler(MavenUtil.class));
	}
	
}
