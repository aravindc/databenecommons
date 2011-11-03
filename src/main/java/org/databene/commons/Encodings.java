/*
 * (c) Copyright 2009 by Volker Bergmann. All rights reserved.
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

package org.databene.commons;

/**
 * Provides names for frequently used encodings.<br/><br/>
 * Created: 16.12.2009 08:38:45
 * @since 0.5.0
 * @author Volker Bergmann
 */
public interface Encodings {
	
	public static final String ASCII = "US-ASCII";

	public static final String UTF_8    = "UTF-8";
	public static final String UTF_16   = "UTF-16";
	public static final String UTF_16BE = "UTF-16BE";
	public static final String UTF_16LE = "UTF-16LE";
	public static final String UTF_32   = "UTF-32";
	public static final String UTF_32BE = "UTF-32BE";
	public static final String UTF_32LE = "UTF-32LE";

	public static final String ISO_2022_CN = "ISO-2022-CN";
	public static final String ISO_2022_JP = "ISO-2022-JP";
	public static final String ISO_2022_JP2 = "ISO-2022-JP-2";
	public static final String ISO_2022_KR = "ISO-2022-KR";
	public static final String ISO_8859_1  = "ISO-8859-1";
	public static final String ISO_8859_13 = "ISO-8859-13";
	public static final String ISO_8859_15 = "ISO-8859-15";
	public static final String ISO_8859_2  = "ISO-8859-2";
	public static final String ISO_8859_3  = "ISO-8859-3";
	public static final String ISO_8859_4  = "ISO-8859-4";
	public static final String ISO_8859_5  = "ISO-8859-5";
	public static final String ISO_8859_6  = "ISO-8859-6";
	public static final String ISO_8859_7  = "ISO-8859-7";
	public static final String ISO_8859_8  = "ISO-8859-8";
	public static final String ISO_8859_9  = "ISO-8859-9";
	
	public static final String MAC_ROMAN   = "MacRoman";
	public static final String WIN_1252    = "windows-1252";

}
