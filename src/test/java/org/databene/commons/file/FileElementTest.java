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

package org.databene.commons.file;

import java.io.File;
import java.util.Arrays;

import org.junit.Test;
import static junit.framework.Assert.*;
import org.databene.commons.FileUtil;
import org.databene.commons.Visitor;
import org.databene.commons.file.FileElement;

/**
 * Created: 04.02.2007 08:20:57
 * @author Volker Bergmann
 */
public class FileElementTest {

	@Test
    public void test() {
        File root = new File("target/classes/test");
        File alpha = new File(root, "alpha");
        FileUtil.ensureDirectoryExists(alpha);
        File beta = new File(alpha, "beta");
        FileUtil.ensureDirectoryExists(beta);
        CheckVisitor visitor = new CheckVisitor(root, alpha, beta);
        new FileElement(root).accept(visitor);
        assertTrue(visitor.allFound());
    }

    class CheckVisitor implements Visitor<File> {

        private File[] expectedFiles;
        private boolean[] filesFound;

        public CheckVisitor(File ... expectedFiles) {
            this.expectedFiles = expectedFiles;
            Arrays.sort(this.expectedFiles);
            this.filesFound = new boolean[expectedFiles.length];
        }

        public void visit(File file) {
            int index = Arrays.binarySearch(expectedFiles, file);
            if (index >= 0)
                filesFound[index] = true;
        }

        public boolean allFound() {
            for (boolean b : filesFound)
                if (!b)
                    return false;
            return true;
        }
    }
    
}
