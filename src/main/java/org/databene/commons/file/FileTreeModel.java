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

package org.databene.commons.file;

import org.databene.commons.ArrayUtil;
import org.databene.commons.Element;
import org.databene.commons.TreeModel;
import org.databene.commons.Visitor;

import java.io.File;
import java.util.Arrays;
import java.util.Comparator;

/**
 * databene TreeModel implementation for files.<br/>
 * <br/>
 * Created: 08.05.2007 17:55:54
 * @author Volker Bergmann
 */
public class FileTreeModel implements TreeModel<File>, Element<File> {

    private File root;
    private Comparator<File> fileComparator;

    public FileTreeModel(File root) {
        this(root, new FilenameComparator());
    }

    public FileTreeModel(File root, Comparator<File> fileComparator) {
        if (!root.exists())
            throw new IllegalArgumentException("File/Directory not found: " + root);
        this.root = root;
        this.fileComparator = fileComparator;
    }

    @Override
	public File getRoot() {
        return root;
    }

    @Override
	public File getParent(File child) {
        return child.getParentFile();
    }

    @Override
	public File getChild(File parent, int index) {
        return listFiles(parent)[index];
    }

    @Override
	public int getChildCount(File parent) {
        if (parent.isFile())
            return 0;
        else
            return listFiles(parent).length;
    }

    @Override
	public boolean isLeaf(File node) {
        return (node).isFile();
    }

    @Override
	public int getIndexOfChild(File parent, File child) {
        File[] files = listFiles(parent);
        return ArrayUtil.indexOf(child, files);
    }

    private File[] listFiles(File parent) {
        File[] files = parent.listFiles();
        if (files != null)
        	Arrays.sort(files, fileComparator);
        else
        	files = new File[0];
        return files;
    }

	@Override
	public void accept(Visitor<File> visitor) {
		accept(visitor, root);
	}

	private void accept(Visitor<File> visitor, File file) {
		visitor.visit(file);
		if (file.isDirectory()) {
			for (File child : file.listFiles())
				accept(visitor, child);
		}
	}

}
