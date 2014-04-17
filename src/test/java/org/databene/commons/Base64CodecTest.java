/*
 * (c) Copyright 2007-2014 by Volker Bergmann. All rights reserved.
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

import java.util.Arrays;

import org.junit.Test;
import static junit.framework.Assert.*;

/**
 * Tests the Base64Codec.
 * @author Volker Bergmann
 * @since 0.2.04
 */
public class Base64CodecTest {
    
    private static final byte[] ALPHABET_BYTES  = "ABCDEFGHIJKLMNOPQRSTUVWXYZ".getBytes();
    private static final String ALPHABET_BASE64 = "QUJDREVGR0hJSktMTU5PUFFSU1RVVldYWVo=";

    private static final byte[] ALL_BYTES;
    private static final String ALL_BASE64 = "gIGCg4SFhoeIiYqLjI2Oj5CRkpOUlZaXmJmam5ydnp+goaKjpKWmp" +
    		"6ipqqusra6vsLGys7S1tre4ubq7vL2+v8DBwsPExcbHyMnKy8zNzs/Q0dLT1NXW19jZ2tvc3d7f4OHi4+Tl5uf" +
    		"o6err7O3u7/Dx8vP09fb3+Pn6+/z9/v8AAQIDBAUGBwgJCgsMDQ4PEBESExQVFhcYGRobHB0eHyAhIiMkJSYnK" +
    		"CkqKywtLi8wMTIzNDU2Nzg5Ojs8PT4/QEFCQ0RFRkdISUpLTE1OT1BRUlNUVVZXWFlaW1xdXl9gYWJjZGVmZ2h" +
    		"pamtsbW5vcHFyc3R1dnd4eXp7fH1+fw==";
    
    static {
        ALL_BYTES = new byte[256];
        for (int i = -128; i < 128; i++)
            ALL_BYTES[128 + i] = (byte)i;
    }
    
    // test methods ----------------------------------------------------------------------------------------------------

	@Test
    public void testEncode() {
        assertEquals(ALPHABET_BASE64, Base64Codec.encode(ALPHABET_BYTES));
        assertEquals(ALL_BASE64, Base64Codec.encode(ALL_BYTES));
    }
    
	@Test
    public void testDecode() {
        assertTrue(Arrays.equals(ALPHABET_BYTES, Base64Codec.decode(ALPHABET_BASE64)));
        assertTrue(Arrays.equals(ALL_BYTES, Base64Codec.decode(ALL_BASE64)));
    }
    
	@Test
    public void testRecode() {
        checkRecodeBytes(ALPHABET_BYTES);
        checkRecodeString(ALPHABET_BASE64);
        checkRecodeBytes(ALL_BYTES);
        checkRecodeString(ALL_BASE64);
        checkRecodeString("");
        checkRecodeBytes(new byte[] { 0 });
        checkRecodeBytes(new byte[] { 1 });
        checkRecodeBytes(new byte[] { 1, 2 });
        checkRecodeBytes(new byte[] { 1, 2, 3 });
        checkRecodeBytes(new byte[] { 1, 2, 3, 4 });
        checkRecodeString("AA==");
        checkRecodeString("ABCD");
    }
    
    // private helpers -------------------------------------------------------------------------------------------------

    private static void checkRecodeString(String code) {
        assertEquals(code, Base64Codec.encode(Base64Codec.decode(code)));
    }

    private static void checkRecodeBytes(byte[] bytes) {
        assertTrue(Arrays.equals(bytes, Base64Codec.decode(Base64Codec.encode(bytes))));
    }
    
}
