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

package org.databene.commons.io;

import java.io.IOException;
import java.io.PushbackReader;
import java.io.Reader;
import java.nio.CharBuffer;

/**
 * TODO Document class.<br/><br/>
 * Created: 19.03.2013 21:13:02
 * @since TODO version
 * @author Volker Bergmann
 */
public class OffsetPushbackReader extends PushbackReader {
	
	int offset;

	public OffsetPushbackReader(Reader in) {
		this(in, 1);
	}

	public OffsetPushbackReader(Reader in, int pushBackBufferSize) {
		super(in, pushBackBufferSize);
		this.offset = 0;
	}

	@Override
	public int read() throws IOException {
		offset++;
		return super.read();
	}
	
	@Override
	public int read(char[] charBuffer) throws IOException {
		int count = super.read(charBuffer);
		offset += count;
		return count;
	}
	
	@Override
	public int read(CharBuffer charVuffer) throws IOException {
		int count = super.read(charVuffer);
		offset += count;
		return count;
	}
	
	@Override
	public void unread(int c) throws IOException {
		offset--;
		super.unread(c);
	}
	
	@Override
	public void unread(char[] charBuffer) throws IOException {
		offset -= charBuffer.length;
		super.unread(charBuffer);
	}
	
	@Override
	public void unread(char[] charBuffer, int off, int len) throws IOException {
		offset -= len;
		super.unread(charBuffer, off, len);
	}
	
	public int getOffset() {
		return offset;
	}
	
}
