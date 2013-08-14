/*
 * (c) Copyright 2008-2013 by Volker Bergmann. All rights reserved.
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

package org.databene.commons.xml;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.databene.commons.ArrayUtil;
import org.databene.commons.CollectionUtil;
import org.databene.commons.Encodings;
import org.databene.commons.IOUtil;
import org.w3c.dom.DOMImplementation;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.w3c.dom.Text;

import org.junit.Test;
import static junit.framework.Assert.*;

/**
 * Tests the XMLUtil class.<br/><br/>
 * Created: 19.03.2008 09:11:08
 * @author Volker Bergmann
 */
public class XMLUtilTest {
    
    private static final String XML_TEXT = "<?xml version=\"1.0\"?><root att=\"1\"/>";

	@Test
    public void testFormat() {
    	Document document = createDocument();
        String output = XMLUtil.formatShort(createElementWithChildren(document, "ns:test"));
        assertTrue(output.startsWith("<ns:test"));
    }
 
	@Test
    public void testLocalNameString() {
        assertEquals("test", XMLUtil.localName("ns:test"));
    }

	@Test
    public void testLocalNameElement() {
    	Document document = createDocument();
        Element element = createElementWithChildren(document, "ns:test");
        assertEquals("test", XMLUtil.localName(element));
    }

	@Test
    public void testGetChildElements() {
    	Document document = createDocument();
        Element child1 = createElementWithChildren(document, "c1");
        Element child2 = createElementWithChildren(document, "c2");
        Element parent = createElementWithChildren(document, "p", child1, child2);
        Element[] expectedChildren = ArrayUtil.toArray(child1, child2);
        Element[] actualChildren = XMLUtil.getChildElements(parent);
        assertTrue(Arrays.equals(expectedChildren, actualChildren));
    }
    
	@Test
    public void testGetChildElements_empty() {
    	Document document = createDocument();
        assertTrue(Arrays.equals(new Element[0], XMLUtil.getChildElements(document.getDocumentElement())));
    }
    
	@Test
    public void testGetChildElementsByName() {
    	Document document = createDocument();
        Element child1 = createElementWithChildren(document, "c1");
        Element child2 = createElementWithChildren(document, "c2");
        Element parent = createElementWithChildren(document, "p", child1, child2);
        Element[] expectedChildren = ArrayUtil.toArray(child2);
        Element[] actualChildren = XMLUtil.getChildElements(parent, true, "c2");
        assertTrue(Arrays.equals(expectedChildren, actualChildren));
    }

	@Test
    public void testGetChildElementByName() {
    	Document document = createDocument();
        Element child1 = createElementWithChildren(document, "c1");
        Element child2 = createElementWithChildren(document, "c2");
        Element parent = createElementWithChildren(document, "p", child1, child2);
        Element foundChild = XMLUtil.getChildElement(parent, true, true, "c2");
        assertEquals(child2, foundChild);
    }

	@Test
    public void testGetIntegerAttribute() {
    	Document document = createDocument();
        Element element = createElement(document, "test", CollectionUtil.buildMap("value", "1"));
        assertEquals(1, (int) XMLUtil.getIntegerAttribute(element, "value", 2));
    }

	@Test
    public void testGetLongAttribute() {
    	Document document = createDocument();
        Element element = createElement(document, "test", CollectionUtil.buildMap("value", "1"));
        assertEquals(1, (long) XMLUtil.getIntegerAttribute(element, "value", 2));
    }

    @Test
	@SuppressWarnings("unchecked")
    public void testGetAttributes() {
    	Document document = createDocument();
        Element element = createElementWithAttributes(document, "test", "name1", "value1", "name2", "");
        Map<String, String> actualAttributes = XMLUtil.getAttributes(element);
        Map<String, String> expectedAttributes = CollectionUtil.buildMap("name1", "value1", "name2", "");
        assertEquals(expectedAttributes, actualAttributes);
    }

	@Test
    public void testNormalizedAttributeValue() {
    	Document document = createDocument();
        Element element = createElementWithAttributes(document, "test", "name1", "value1", "name2", "");
        assertEquals("value1", XMLUtil.normalizedAttributeValue(element, "name1"));
        assertEquals(null, XMLUtil.normalizedAttributeValue(element, "name2"));
    }

	@Test
    public void testParseUri() throws IOException {
        File file = File.createTempFile("XMLUtilTest", ".xml");
        try {
            FileWriter writer = new FileWriter(file);
            writer.write(XML_TEXT);
            writer.close();
            Document document = XMLUtil.parse(file.getAbsolutePath());
            checkXML(document);
        } finally {
            file.delete();
        }
    }

	@Test
    public void testParseStream() throws IOException {
        ByteArrayInputStream stream = new ByteArrayInputStream(XML_TEXT.getBytes(Charset.forName(Encodings.UTF_8))); 
        checkXML(XMLUtil.parse(stream));
    }

	@Test
    public void testParseString() {
        Document document = XMLUtil.parseString(XML_TEXT);
        checkXML(document);
    }
    
	@Test
    public void testGetText() {
    	Document document = createDocument();
    	Text node = document.createTextNode("my text");
    	assertEquals("my text", XMLUtil.getText(node));
    }

	@Test
	public void testGetWholeText() {
		Element element = XMLUtil.parseStringAsElement("<stmt><!-- xxx -->Al<!-- xyz -->pha<!--z--></stmt>");
		assertEquals("Alpha", XMLUtil.getWholeText(element));
	}
	
	@Test
	public void testCreateDocument() {
		Document document = XMLUtil.createDocument("theRoot");
		assertNotNull(document);
		Element rootElement = document.getDocumentElement();
		assertEquals("theRoot", rootElement.getNodeName());
		assertEquals(0, rootElement.getChildNodes().getLength());
	}

	@Test
	public void testSetProperty_root() {
		Document document = XMLUtil.createDocument();
		XMLUtil.setProperty("theRoot", "rootValue", document);
		assertEquals("rootValue", document.getDocumentElement().getTextContent());
	}

	@Test
	public void testSetProperty_complex() {
		Document document = XMLUtil.createDocument();
		XMLUtil.setProperty("theRoot.aGroup", "groupValue", document);
		Element rootElement = document.getDocumentElement();
		assertEquals("theRoot", rootElement.getNodeName());
		NodeList rootChildren = rootElement.getChildNodes();
		assertEquals(1, rootChildren.getLength());
		Node firstChild = rootChildren.item(0);
		assertEquals("aGroup", firstChild.getNodeName());
		assertEquals("groupValue", firstChild.getTextContent());
	}

	@Test
	public void testParseAsProperties() throws FileNotFoundException, IOException {
		Properties expected = new Properties();
		expected.setProperty("root.topProp", "topValue");
		expected.setProperty("root.group.groupProp", "groupValue");
		expected.setProperty("root.emptyProp", "");
		Properties props = XMLUtil.parseAsProperties(new FileInputStream("src/test/resources/org/databene/commons/xml/properties.xml"));
		assertEquals(expected, props);
	}

	@Test
	public void testSaveAsProperties() throws FileNotFoundException, IOException {
		Properties props = new Properties();
		props.setProperty("root.topProp", "topValue");
		props.setProperty("root.group.groupProp", "groupValue");
		props.setProperty("root.emptyProp", "");
		XMLUtil.saveAsProperties(props, new File("target/testSaveAsProperties-actual.xml"), Encodings.UTF_8);
		String actual = IOUtil.getContentOfURI("target/testSaveAsProperties-actual.xml");
		String expected = IOUtil.getContentOfURI("org/databene/commons/xml/properties.xml");
		assertEquals(expected, actual);
	}

	// private helpers -------------------------------------------------------------------------------------------------

    private static void checkXML(Document document) {
        Element root = document.getDocumentElement();
        assertEquals("root", root.getNodeName());
        assertEquals("1", root.getAttribute("att"));
        assertEquals(1, root.getAttributes().getLength());
    }
    
    @SuppressWarnings("unchecked")
    private static Element createElementWithAttributes(Document document, String name, String... attKeysAndValues) {
        Map<String, String> attMap = CollectionUtil.buildMap((Object[]) attKeysAndValues);
        return createElement(document, name, attMap);
    }
    
    private static Element createElementWithChildren(Document document, String name, Element... children) {
        return createElement(document, name, new HashMap<String, String>(), children);
    }

    private static Element createElement(Document document, String name, Map<String, String> attributes, Element... children) {
		Element element = document.createElement(name);
		for (Map.Entry<String, String> attribute : attributes.entrySet())
			element.setAttribute(attribute.getKey(), attribute.getValue());
		for (Element child : children)
			element.appendChild(child);
		return element;
    }

	private static Document createDocument() {
		try {
			DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
			DocumentBuilder builder = factory.newDocumentBuilder();
			DOMImplementation impl = builder.getDOMImplementation();
			Document document = impl.createDocument(null, "document", null);
			return document;
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
	
}
