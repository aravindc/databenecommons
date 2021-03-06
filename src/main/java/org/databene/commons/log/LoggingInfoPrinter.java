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
package org.databene.commons.log;

import org.databene.commons.ui.InfoPrinter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * {@link InfoPrinter} implementation that writes text to a logger category.
 * Created: 01.08.2010 17:13:04
 * @since 0.5.3
 * @author Volker Bergmann
 */
public class LoggingInfoPrinter extends InfoPrinter {
	
    private final Logger logger;
	
	public LoggingInfoPrinter(Class<?> clazz) {
		this.logger = LoggerFactory.getLogger(clazz);
    }

	public LoggingInfoPrinter(String category) {
		this.logger = LoggerFactory.getLogger(category);
    }

	@Override
    public void printLines(Object owner, String... infoLines) {
        for (String info : infoLines)
        	logger.info(info);
    }
	
}
