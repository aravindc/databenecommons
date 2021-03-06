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

import java.awt.Component;
import java.io.File;

/**
 * Platform-independent abstraction of a file chooser facility.
 * Created at 14.12.2008 14:32:22
 * @since 0.4.7
 * @author Volker Bergmann
 */

public interface FileChooser {
	
	void setTitle(String title);
	void setCurrentDirectory(File parentFile);

	File getSelectedFile();
	void setSelectedFile(File file);

	File chooseFile(Component owner);
}
