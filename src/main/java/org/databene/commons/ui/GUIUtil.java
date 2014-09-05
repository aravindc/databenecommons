/*
 * (c) Copyright 2010-2014 by Volker Bergmann. All rights reserved.
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

import java.awt.AWTException;
import java.awt.Rectangle;
import java.awt.Robot;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import org.databene.commons.SystemInfo;
import org.databene.commons.ui.FileChooser;
import org.databene.commons.ui.FileOperation;
import org.databene.commons.ui.FileTypeSupport;
import org.databene.commons.ui.awt.AwtFileChooser;
import org.databene.commons.ui.swing.SwingFileChooser;

/**
 * Provides GUI utility methods.<br/><br/>
 * Created: 01.12.2010 13:55:23
 * @since 0.2.4
 * @author Volker Bergmann
 */
public class GUIUtil {

	public static FileChooser createFileChooser(
			File selectedFile, FileTypeSupport supportedTypes, FileOperation operation) {
		FileChooser chooser;
        if (SystemInfo.isMacOsx())
        	chooser = new AwtFileChooser(null, operation, supportedTypes);
        else
	        chooser = new SwingFileChooser(supportedTypes, operation);
        if (selectedFile != null && selectedFile.exists()) {
        	if (selectedFile.isDirectory())
        		chooser.setCurrentDirectory(selectedFile);
        	else
        		chooser.setSelectedFile(selectedFile);
        }
        return chooser;
	}
	
	public static void takeScreenshot(String fileName, String formatName) throws IOException, AWTException {
		Rectangle screenBounds = new Rectangle(Toolkit.getDefaultToolkit().getScreenSize());
		BufferedImage image = new Robot().createScreenCapture(screenBounds);
		ImageIO.write(image, formatName, new File(fileName));
	}
	
}
