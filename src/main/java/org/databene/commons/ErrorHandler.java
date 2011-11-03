/*
 * (c) Copyright 2008-2009 by Volker Bergmann. All rights reserved.
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

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Provides for error handling by logging and eventually raising an exception.<br/>
 * <br/>
 * Created at 02.08.2008 13:39:11
 * @since 0.4.5
 * @author Volker Bergmann
 */
public class ErrorHandler {
	
	// attributes ------------------------------------------------------------------------------------------------------

	private Logger logger;
	private Level level;
	private boolean loggingStackTrace;

	// constructors ----------------------------------------------------------------------------------------------------
	
	public ErrorHandler(Class<?> category) {
		this(category.getName());
	}
	
	public ErrorHandler(String category) {
		this(category, Level.fatal);
	}
	
	public ErrorHandler(String category, Level level) {
		this.logger = LoggerFactory.getLogger(category);
		this.level = level;
		this.loggingStackTrace = true;
	}
	
	// interface -------------------------------------------------------------------------------------------------------
	
	public void handleError(String message) {
		switch (level) {
			// yes, this could be more efficient, but it's just for error handling
			// and you're not supposed to have a number of errors that impacts performance
			case trace : logger.trace(message); break;
			case debug : logger.debug(message); break;
			case info  : logger.info( message); break;
			case warn  : logger.warn( message); break;
			case error : logger.error(message); break;
			case fatal : logger.error(message);
						 throw new RuntimeException(message);
			case ignore: // ignore
		}
	}

	public void handleError(String message, Throwable t) {
		if (loggingStackTrace) {
			switch (level) {
				// yes, this could be more efficient, but it's just for error handling
				// and you're not supposed to have a number of errors that impacts performance
				case trace : logger.trace(message); break;
				case debug : logger.debug(message); break;
				case info  : logger.info( message); break;
				case warn  : logger.warn( message); break;
				case error : logger.error(message, t); break;
				case fatal : logger.error(message, t);
							 throw new RuntimeException(t);
				case ignore: // ignore
			}
		} else
			handleError(message + SystemInfo.getLineSeparator() + t.toString());
	}

	// properties ------------------------------------------------------------------------------------------------------
	
	public Level getLevel() {
		return level;
	}

	public boolean isLoggingStackTrace() {
		return loggingStackTrace;
	}

	public void setLoggingStackTrace(boolean loggingStackTrace) {
		this.loggingStackTrace = loggingStackTrace;
	}
	
	// static utilities ------------------------------------------------------------------------------------------------

	private static ErrorHandler defaultInstance = new ErrorHandler(ErrorHandler.class);
	
	public static ErrorHandler getDefault() {
		return defaultInstance;
    }

	private static Level defaultLevel = Level.fatal;
	
	public static Level getDefaultLevel() {
		return defaultLevel;
	}
	
	public static void setDefaultLevel(Level level) {
		defaultLevel = level;
		if (defaultInstance.getLevel() != level)
			defaultInstance = new ErrorHandler(defaultInstance.logger.getName(), level);
	}

}
