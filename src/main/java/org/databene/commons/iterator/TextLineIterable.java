/*
 * (c) Copyright 2007-2011 by Volker Bergmann. All rights reserved.
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

package org.databene.commons.iterator;

import org.databene.commons.HeavyweightIterator;
import org.databene.commons.HeavyweightTypedIterable;
import org.databene.commons.IOUtil;
import org.databene.commons.ReaderLineIterator;

import java.io.IOException;

/**
 * Creates Iterator object that iterate through the lines of a text file from the specified uri.<br/>
 * <br/>
 * Created: 27.08.2007 19:13:40
 * @author Volker Bergmann
 */
public class TextLineIterable implements HeavyweightTypedIterable<String> {

    private String uri;

    public TextLineIterable(String uri) {
        this.uri = uri;
    }

    public Class<String> getType() {
        return String.class;
    }

    public HeavyweightIterator<String> iterator() {
        try {
            return new ReaderLineIterator(IOUtil.getReaderForURI(uri));
        } catch (IOException e) {
            throw new RuntimeException("Unable to create an Iterator for URI '" + uri + "'", e);
        }
    }
}
