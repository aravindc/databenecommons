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

package org.databene.commons.file;

import java.text.Format;
import java.text.FieldPosition;
import java.text.ParsePosition;
import java.io.File;

/**
 * Formats file names as local names or absolute path.<br/>
 * <br/>
 * Created: 13.05.2007 07:41:51
 * @author Volker Bergmann
 */
public class FilenameFormat extends Format {

	private static final long serialVersionUID = 8865264142496144195L;

	private boolean fullPathUsed;

    public FilenameFormat() {
        this(true);
    }

    public FilenameFormat(boolean fullPathUsed) {
        this.fullPathUsed = fullPathUsed;
    }

    public boolean isFullPathUsed() {
        return fullPathUsed;
    }

    public void setFullPathUsed(boolean fullPathUsed) {
        this.fullPathUsed = fullPathUsed;
    }

    @Override
    public StringBuffer format(Object fileObject, StringBuffer toAppendTo, FieldPosition pos) {
        File file = (File) fileObject;
        String filename;
        if (fullPathUsed)
            filename = file.getAbsolutePath();
        else
            filename = file.getName();
        return toAppendTo.append(filename);
    }

    @Override
    public Object parseObject(String filename, ParsePosition pos) {
        return new File(filename);
    }
}
