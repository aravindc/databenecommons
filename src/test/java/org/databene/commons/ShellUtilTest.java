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
package org.databene.commons;

import static org.junit.Assert.*;

import java.io.StringWriter;

import org.junit.Test;

/**
 * Tests the {@link ShellUtil}.
 * Created: 22.02.2010 17:09:02
 * @since 0.5.0
 * @author Volker Bergmann
 */
public class ShellUtilTest {

	@Test
	public void test() {
		StringWriter writer = new StringWriter();
		String command = "echo 42";
		if (SystemInfo.isWindows())
			command = "cmd.exe /C " + command;
		ShellUtil.runShellCommand(command, writer, ErrorHandler.getDefault());
		assertEquals("42", writer.toString());
	}
	
}
