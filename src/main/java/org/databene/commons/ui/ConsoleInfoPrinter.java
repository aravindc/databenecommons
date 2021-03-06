/*
 * Copyright (C) 2004-2015 Volker Bergmann (volker.bergmann@bergmann-it.de).
 * All rights reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * http://www.apache.org/licenses/LICENSE-2.0
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.databene.commons.ui;

import java.io.IOException;

import org.databene.commons.IOUtil;
import org.databene.commons.ReaderLineIterator;

/**
 * {@link InfoPrinter} implementation that prints info to the console.
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
