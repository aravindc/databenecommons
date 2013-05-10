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

package org.databene.commons.ui;

import java.io.IOException;

import org.databene.commons.IOUtil;
import org.databene.commons.ReaderLineIterator;

/**
 * {@link InfoPrinter} implementation that prints info to the console.<br/>
 * <br/>
 * Created at 21.12.2008 11:34:25
 * @since 0.4.7
 * @author Volker Bergmann
 */

public class ConsoleInfoPrinter extends InfoPrinter {

	@Override
	public void printLines(Object owner, String... helpLines) {
		printHelp(helpLines);
	}

	public static void printHelp(String... helpLines) {
		for (String helpLine : helpLines)
			System.out.println(helpLine);
	}
	
	public static void printFile(String uri) throws IOException {
		ReaderLineIterator iterator = null;
		try {
			iterator = new ReaderLineIterator(IOUtil.getReaderForURI(uri));
			while (iterator.hasNext())
				System.out.println(iterator.next());
		} finally {
			IOUtil.close(iterator);
		}
	}
	
}
