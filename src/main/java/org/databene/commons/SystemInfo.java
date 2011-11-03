/*
 * (c) Copyright 2007-2009 by Volker Bergmann. All rights reserved.
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

import java.nio.charset.Charset;

/**
 * Provides the user with the Java system properties related to the Runtime System.<br/>
 * <br/>
 * Created: 06.01.2007 19:10:02
 * @author Volker Bergmann
 */
public final class SystemInfo {

	public static final String USER_LANGUAGE_KEY = "user.language";
	public static final String FILE_ENCODING_KEY = "file.encoding";
	public static final String JAVA_IO_TMPDIR_KEY = "java.io.tmpdir";
	public static final String USER_HOME_KEY = "user.home";
	public static final String USER_NAME_KEY = "user.name";
	public static final String USER_DIR_KEY = "user.dir";
	public static final String FILE_SEPARATOR_KEY = "file.separator";
	public static final String PATH_SEPARATOR_KEY = "path.separator";
	public static final String LINE_SEPARATOR_KEY = "line.separator";
	public static final String OS_VERSION_KEY = "os.version";
	public static final String OS_ARCH_KEY = "os.arch";
	public static final String OS_NAME_KEY = "os.name";

	/**
     * @return the OS name
     * @deprecated use {@link #getOsName()}
     */
	@Deprecated
    public static String osName() {
        return System.getProperty(OS_NAME_KEY);
    }

	/**
     * @return the OS name
     */
    public static String getOsName() {
        return System.getProperty(OS_NAME_KEY);
    }

    /**
     * @return the OS architecture
     * @deprecated use {@link #getOsArchitecture()}
     */
	@Deprecated
    public static String osArchitecture() {
        return System.getProperty(OS_ARCH_KEY);
    }

    /**
     * @return the OS architecture
     */
    public static String getOsArchitecture() {
        return System.getProperty(OS_ARCH_KEY);
    }

    /**
     * @return the OS version
     * @deprecated use {@link #getOsVersion()}
     */
	@Deprecated
    public static String osVersion() {
        return System.getProperty(OS_VERSION_KEY);
    }

    /**
     * @return the OS version
     */
    public static String getOsVersion() {
        return System.getProperty(OS_VERSION_KEY);
    }

    /**
     * @return Line separator ("\n" on UNIX)
     * @deprecated use {@link #getLineSeparator()}
     */
	@Deprecated
    public static String lineSeparator() {
        return System.getProperty(LINE_SEPARATOR_KEY);
    }

    /**
     * @return Line separator ("\n" on UNIX)
     */
    public static String getLineSeparator() {
        return System.getProperty(LINE_SEPARATOR_KEY);
    }

    public static void setLineSeparator(String lineSeparator) {
        System.setProperty(LINE_SEPARATOR_KEY, lineSeparator);
    }

    /**
     * @return Path separator (":" on UNIX)
     * @deprecated use {@link #getPathSeparator()}
     */
	@Deprecated
    public static String pathSeparator() {
        return System.getProperty(PATH_SEPARATOR_KEY);
    }

    /**
     * @return Path separator (":" on UNIX)
     */
    public static String getPathSeparator() {
        return System.getProperty(PATH_SEPARATOR_KEY);
    }

    /**
     * @return File separator ("/" on UNIX)
     * @deprecated use {@link #getFileSeparator()}
     */
	@Deprecated
    public static char fileSeparator() {
        return System.getProperty(FILE_SEPARATOR_KEY).charAt(0);
    }

    /**
     * @return File separator ("/" on UNIX)
     */
    public static char getFileSeparator() {
        return System.getProperty(FILE_SEPARATOR_KEY).charAt(0);
    }

    /**
     * @return the user's current directory
     * @deprecated use {@link #getCurrentDir()}
     */
	@Deprecated
    public static String currentDir() {
        return System.getProperty(USER_DIR_KEY);
    }

    /**
     * @return the user's current directory
     */
    public static String getCurrentDir() {
        return System.getProperty(USER_DIR_KEY);
    }

    /**
     * @return the user's name
     * @deprecated use {@link #getUserName()}
     */
	@Deprecated
    public static String userName() {
        return System.getProperty(USER_NAME_KEY);
    }

    /**
     * @return the user's name
     */
    public static String getUserName() {
        return System.getProperty(USER_NAME_KEY);
    }

    /**
     * @return the user's home directory
     * @deprecated use {@link #getUserHome()}
     */
	@Deprecated
    public static String userHome() {
        return System.getProperty(USER_HOME_KEY);
    }

    /**
     * @return the user's home directory
     */
    public static String getUserHome() {
        return System.getProperty(USER_HOME_KEY);
    }

    /**
     * @return the default temp file path
     * @deprecated use {@link #getTempDir()}
     */
	@Deprecated
    public static String tempDir() {
        return System.getProperty(JAVA_IO_TMPDIR_KEY);
    }

    /**
     * @return the default temp file path
     */
    public static String getTempDir() {
        return System.getProperty(JAVA_IO_TMPDIR_KEY);
    }

    /**
     * @return the file encoding
     * @deprecated use {@link #getFileEncoding()}
     */
	@Deprecated
    public static String fileEncoding() {
        return System.getProperty(FILE_ENCODING_KEY);
    }

    /**
     * @return the file encoding
     */
    public static String getFileEncoding() {
        return System.getProperty(FILE_ENCODING_KEY);
    }
    
    public static void setFileEncoding(String encoding) {
    	System.setProperty(FILE_ENCODING_KEY, encoding);
    }

    /**
     * @return user language
     * @deprecated use {@link #getUserLanguage()}
     */
	@Deprecated
    public static String userLanguage() {
        return System.getProperty(USER_LANGUAGE_KEY);
    }

    /**
     * @return user language
     */
    public static String getUserLanguage() {
        return System.getProperty(USER_LANGUAGE_KEY);
    }

	/**
	 * @return true if the system is a Windows version, else false
	 */
	public static boolean isWindows() {
		return getOsName().toLowerCase().startsWith("win");
	}

	/**
	 * @return true if the system is Mac, else false
	 */
	public static boolean isMacOsx() {
		return getOsName().toLowerCase().startsWith("mac");
	}

	/**
	 * @return true if the system is Linux, else false
	 */
	public static boolean isLinux() {
		return getOsName().toLowerCase().startsWith("linux");
	}

	/**
	 * @return true if the system is Solaris, else false
	 */
	public static boolean isSolaris() {
		return getOsName().toLowerCase().startsWith("sun");
	}

	/**
	 * @return the system's default {@link Charset}
	 */
	/*
	public static Charset charset() {
		return Charset.forName(getFileEncoding());
	}
*/
	/**
	 * @return the system's default {@link Charset}
	 */
	public static Charset getCharset() {
		return Charset.forName(getFileEncoding());
	}

}
