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

package org.databene.commons.ui.swing;

import javax.swing.*;
import java.awt.*;

/**
 * Provides Swing utilities.<br/>
 * <br/>
 * Created: 23.04.2007 22:41:21
 * @since 0.5.13
 * @author Volker Bergmann
 */
public class SwingUtil {

    public static void repaintLater(final Component component) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                component.repaint();
            }
        });
    }

    public static void center(Component component) {
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        int x = (screenSize.width - component.getWidth()) / 2;
        int y = (screenSize.height - component.getHeight()) / 2;
        component.setLocation(x, y);
    }

    public static void showInFrame(Component component, String title) {
        JFrame frame = new JFrame(title);
        frame.getContentPane().add(component, BorderLayout.CENTER);
        frame.pack();
        center(frame);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
    }

    public static Rectangle fitRectangles(Dimension imageSize, Dimension size) {
        double aspectX = (double) size.width / imageSize.width;
        double aspectY = (double) size.height / imageSize.height;
        double aspect = Math.min(aspectX, aspectY);
        int paintedWidth = (int) (imageSize.width * aspect);
        int paintedHeight = (int) (imageSize.height * aspect);
        int x = (size.width - paintedWidth) / 2;
        int y = (size.height - paintedHeight) / 2;
        return new Rectangle(x, y, paintedWidth, paintedHeight);
    }
    
    public static boolean isLookAndFeelNative() {
    	return UIManager.getSystemLookAndFeelClassName().equals(UIManager.getLookAndFeel().getClass().getName());
    }
}
