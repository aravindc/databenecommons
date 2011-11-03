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

import org.databene.commons.Element;
import org.databene.commons.Visitor;
import org.databene.commons.visitor.WrapperElement;

import java.util.Collection;
import java.util.ArrayList;
import java.util.List;
import java.io.File;

/**
 * Element implementation (of the Visitor Design Pattern) for a File.<br/>
 * <br/>
 * Created: 04.02.2007 08:10:05
 * @author Volker Bergmann
 */
public class FileElement extends WrapperElement<File> {

    public FileElement(File file) {
        super(file);
    }

    @Override
    protected Collection<Element<File>> getChildren(Visitor<File> visitor) {
        if (wrappedObject.isFile())
            return new ArrayList<Element<File>>();
        File[] content = wrappedObject.listFiles();
        if (content == null)
        	content = new File[0];
        List<Element<File>> children = new ArrayList<Element<File>>(content.length);
        for (File file : content)
            children.add(new FileElement(file));
        return children;
    }


}
