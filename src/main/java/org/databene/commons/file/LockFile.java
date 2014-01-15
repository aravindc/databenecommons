/*
 * (c) Copyright 2013 by Volker Bergmann. All rights reserved.
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
import java.io.IOException;

/**
 * <p>Utility for preventing the start of more than one process of a given application.
 * The lock is acquired calling {@link #acquireLock(String)} with a file name. 
 * The method creates the file and registers a shutdown hook that deletes the file 
 * when the process finishes. When a second acquireLock is tried while the file exists 
 * (no matter if by this or another process), a {@link LockAlreadyAcquiredException} 
 * is thrown.</p>
 * It can be used like this:
 * <pre>
 * 		try {
 *			LockFile.acquireLock("MyApp.lock");
 *		} catch (LockFile.LockAlreadyAcquiredException e) {
 *			JOptionPane.showMessageDialog(null, "MyApp is already running", "Error", JOptionPane.ERROR_MESSAGE);
 *			System.exit(-1);
 *		}
 * </pre>
 * <br/><br/>
 * Created: 29.11.2013 08:16:23
 * @since 0.5.25
 * @author Volker Bergmann
 */

public class LockFile {

	public static void acquireLock(String fileName) throws LockAlreadyAcquiredException {
		final File lockFile = new File(fileName);
		if (lockFile.exists()) {
			throw new LockAlreadyAcquiredException(fileName);
		} else {
			try {
				File parent = lockFile.getParentFile();
				parent.mkdirs();
				lockFile.createNewFile();
			} catch (IOException e) {
				throw new RuntimeException(e);
			}
			Runtime.getRuntime().addShutdownHook(new Thread() {
				@Override
				public void run() {
					lockFile.delete();
				}
			});
		}
	}
	
	public static class LockAlreadyAcquiredException extends Exception {

		private static final long serialVersionUID = 1L;

		public LockAlreadyAcquiredException(String fileName) {
			super("Lock file already acquired: " + fileName);
		}
	}
	
}
