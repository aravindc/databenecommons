/*
 * (c) Copyright 2007 by Volker Bergmann. All rights reserved.
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

/**
 * Codes/Decodes Base64 strings.
 * @author Volker Bergmann
 * @since 0.2.04
 */
public class Base64Codec {

    private static final char[] DIGITS  = createDigits();
    private static final byte[] INDICES = createIndices();

    private Base64Codec() {}
    
    // interface ----------------------------------------------------------------------------------------

    public static String encode(byte[] source) {
        int srcLength = source.length;
        int srcCursor = 0;
        int unpaddedLength = (srcLength * 4 + 2) / 3;
        int paddedLength = ((srcLength + 2) / 3) * 4;
        char[] buffer = new char[paddedLength];
        int bufferCursor = 0;
        while (srcCursor < srcLength) {
            int d0 = source[srcCursor++] & 0xff;
            int d1 = srcCursor < srcLength ? source[srcCursor++] & 0xff : 0;
            int d2 = srcCursor < srcLength ? source[srcCursor++] & 0xff : 0;
            buffer[bufferCursor++] = DIGITS[d0 >>> 2];
            buffer[bufferCursor++] = DIGITS[((d0 & 0x3) << 4) | (d1 >>> 4)];
            buffer[bufferCursor] = bufferCursor++ < unpaddedLength ? DIGITS[((d1 & 0xf) << 2) | (d2 >>> 6)] : '=';
            buffer[bufferCursor] = bufferCursor++ < unpaddedLength ? DIGITS[d2 & 0x3F] : '=';
        }
        return new String(buffer);
    }

    public static byte[] decode(String code) {
        if (code == null)
            throw new IllegalArgumentException("code is null");
        char[] codeBuffer = code.toCharArray();
        int codeLength = codeBuffer.length;
        if (codeLength % 4 != 0)
            throw new IllegalArgumentException("Length of Base64 encoded input string is not a multiple of 4.");
        while (codeLength > 0 && codeBuffer[codeLength - 1] == '=')
            codeLength--;
        for (int i = 0; i < codeLength; i++)
            if (!isBase64Char(codeBuffer[i]))
                throw new IllegalArgumentException("Not a base64 code: " + code);
        int resultLength = (codeLength * 3) / 4;
        byte[] result = new byte[resultLength];
        int codeCursor = 0;
        int resultCursor = 0;
        while (codeCursor < codeLength) {
            int b0 = INDICES[codeBuffer[codeCursor++]];
            int b1 = INDICES[codeBuffer[codeCursor++]];
            int b2 = (codeCursor < codeLength ? INDICES[codeBuffer[codeCursor++]] : 0);
            int b3 = (codeCursor < codeLength ? INDICES[codeBuffer[codeCursor++]] : 0);
            result[resultCursor++] = (byte) ((b0 << 2) | (b1 >>> 4));
            if (resultCursor < resultLength)
                result[resultCursor++] = (byte) (((b1 & 0xf) << 4) | (b2 >>> 2));
            if (resultCursor < resultLength)
                result[resultCursor++] = (byte) (((b2 & 3) << 6) | b3);
        }
        return result;
    }
    
    // private helpers -------------------------------------------------------------------------------------
    
    public static boolean isBase64Char(char c) {
        return ('A' <= c && c <= 'Z') || ('a' <= c && c <= 'z') || ('0' <= c && c <= '9') || c == '+' || c =='/';
    }

    private static char[] createDigits() {
        char[] digits = new char[64];
        int i = 0;
        for (char c = 'A'; c <= 'Z'; c++)
            digits[i++] = c;
        for (char c = 'a'; c <= 'z'; c++)
            digits[i++] = c;
        for (char c = '0'; c <= '9'; c++)
            digits[i++] = c;
        digits[i++] = '+';
        digits[i++] = '/';
        return digits;
    }

    private static byte[] createIndices() {
        byte[] indices = new byte[128];
        Arrays.fill(indices, 0, indices.length, (byte) -1);
        for (int i = 0; i < 64; i++)
            indices[DIGITS[i]] = (byte) i;
        return indices;
    }
}