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

package org.databene.commons.ui;

import org.databene.commons.SystemInfo;

/**
 * {@link InfoPrinter} implementation that prints into a buffer 
 * and provides the received input as String in {@link #toString()}.<br/><br/>
 * Created: 17.03.2013 18:06:28
 * @since 0.5.23
 * @author Volker Bergmann
 */
public class BufferedInfoPrinter extends InfoPrinter {
	
	private StringBuilder buffer;
	
	public BufferedInfoPrinter() {
		this.buffer = new StringBuilder();
	}

	@Override
	public void printLines(Object owner, String... lines) {
		for (String line : lines)
			buffer.append(line).append(SystemInfo.getLineSeparator());
	}
	
	public void clear() {
		buffer.delete(0, buffer.length());
	}
	
	@Override
	public String toString() {
		return buffer.toString();
	}
	
}
