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

import java.io.BufferedReader;
import java.io.Reader;
import java.io.IOException;

/**
 * Iterator implementation that returns text lines provided by a reader.<br/><br/>
 * Created: 01.05.2007 08:06:46
 * @since 0.1
 * @author Volker Bergmann
 */
public class ReaderLineIterator implements HeavyweightIterator<String> {

    private BufferedReader reader;
    private boolean skipEmptyLines;
    private String next;

    private int lineCount;

    public ReaderLineIterator(Reader reader) {
    	this(reader, false);
    }
    
    public ReaderLineIterator(Reader reader, boolean skipEmptyLines) {
        if (reader instanceof BufferedReader)
            this.reader = (BufferedReader) reader;
        else
            this.reader = new BufferedReader(reader);
        this.skipEmptyLines = skipEmptyLines;
        this.lineCount = 0;
        fetchNext();
    }

    public void close() {
        if (reader != null) {
            IOUtil.close(reader);
            reader = null;
        }
    }

    public int lineCount() {
        return lineCount;
    }

    // Iterator interface ----------------------------------------------------------------------------------------------

    public boolean hasNext() {
        return reader != null && next != null;
    }

    public String next() {
        String result = next;
        fetchNext();
        return result;
    }

    public void remove() {
        throw new UnsupportedOperationException("Not supported");
    }

    // helpers ---------------------------------------------------------------------------------------------------------

    private void fetchNext() {
        try {
            if (reader != null) {
            	do {
            		next = reader.readLine();
            	} while (skipEmptyLines && next != null && next.trim().length() == 0);
                if (next == null)
                    close();
                lineCount++;
            } else {
                next = null;
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

}
