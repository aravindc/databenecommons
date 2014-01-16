/*
 * (c) Copyright 2012 by Volker Bergmann. All rights reserved.
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

package org.databene.commons.xml;

import java.io.File;
import java.io.IOException;

import org.w3c.dom.Document;

/**
 * XML Parser which is able to parse strings, files and content provides from URIs.<br/><br/>
 * Created: 04.06.2012 13:17:53
 * @since 0.5.16
 * @author Volker Bergmann
 */
public class XMLParser {

	private ClassLoader jaxpClassLoader;
	
	public XMLParser() {
		this(null);
	}
	
	public XMLParser(ClassLoader jaxpClassLoader) {
		this.jaxpClassLoader = (jaxpClassLoader != null ? jaxpClassLoader : getClass().getClassLoader());
	}
	
	public Document parse(File file) throws IOException {
		return parse(file.getCanonicalPath());
	}
	
	public Document parse(String uri) throws IOException {
		return XMLUtil.parse(uri, true, null, null, jaxpClassLoader);
	}
	
	public Document parseString(String text) {
		return XMLUtil.parseString(text, null, jaxpClassLoader);
	}
	
}
