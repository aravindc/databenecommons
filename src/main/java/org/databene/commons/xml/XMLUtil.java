/*
 * (c) Copyright 2007-2014 by Volker Bergmann. All rights reserved.
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
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import javax.xml.XMLConstants;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactoryConfigurationError;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.sax.SAXTransformerFactory;
import javax.xml.transform.stream.StreamResult;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;

import org.databene.commons.ArrayBuilder;
import org.databene.commons.BeanUtil;
import org.databene.commons.ConfigurationError;
import org.databene.commons.Converter;
import org.databene.commons.Encodings;
import org.databene.commons.ErrorHandler;
import org.databene.commons.Filter;
import org.databene.commons.IOUtil;
import org.databene.commons.Level;
import org.databene.commons.ParseUtil;
import org.databene.commons.StringUtil;
import org.databene.commons.SyntaxError;
import org.databene.commons.SystemInfo;
import org.databene.commons.Visitor;
import org.databene.commons.converter.NoOpConverter;
import org.databene.commons.converter.String2DateConverter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Attr;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.w3c.dom.Text;
import org.xml.sax.EntityResolver;
import org.xml.sax.SAXException;
import org.xml.sax.SAXParseException;

/**
 * Provides XML Utility methods.<br/><br/>
 * Created: 25.08.2007 22:09:26
 * @author Volker Bergmann
 */
public class XMLUtil {

	private static final String DOCUMENT_BUILDER_FACTORY_IMPL = "org.apache.xerces.jaxp.DocumentBuilderFactoryImpl";
	private static final ErrorHandler DEFAULT_ERROR_HANDLER = new ErrorHandler(XMLUtil.class.getSimpleName(), Level.error);
	private static final Logger LOGGER = LoggerFactory.getLogger(XMLUtil.class);
	
    private XMLUtil() {}

    public static String format(Element element) {
    	ByteArrayOutputStream buffer = new ByteArrayOutputStream();
		String encoding = Encodings.UTF_8;
		SimpleXMLWriter out = new SimpleXMLWriter(buffer, encoding, false);
		format(element, out);
		out.close();
		try {
			return new String(buffer.toByteArray(), encoding);
		} catch (UnsupportedEncodingException e) {
			throw new RuntimeException(e);
		}
    }
    
	public static String formatShort(Element element) {
        StringBuilder builder = new StringBuilder();
        builder.append('<').append(element.getNodeName());
        NamedNodeMap attributes = element.getAttributes();
        for (int i = 0; i < attributes.getLength(); i++) {
            Attr attribute = (Attr) attributes.item(i);
            builder.append(' ').append(attribute.getName()).append("=\"").append(attribute.getValue()).append('"');
        }
        builder.append("...");
        return builder.toString();
    }

    public static String localName(Element element) {
        return localName(element.getNodeName());
    }

    public static String localName(String elementName) {
        if (elementName == null)
            return null;
        int sep = elementName.indexOf(':');
        if (sep < 0)
        	return elementName;
        return elementName.substring(sep + 1);
    }

    public static Element[] getChildElements(Element parent) {
        NodeList childNodes = parent.getChildNodes();
        return toElementArray(childNodes);
    }

	public static Element[] toElementArray(NodeList nodeList) {
		if (nodeList == null)
        	return new Element[0];
        int n = nodeList.getLength();
        ArrayBuilder<Element> builder = new ArrayBuilder<Element>(Element.class, n);
        for (int i = 0; i < n; i++) {
            Node item = nodeList.item(i);
            if (item instanceof Element)
                builder.add((Element) item);
        }
        return builder.toArray();
	}
    
	public static List<Element> toElementList(NodeList nodeList) {
		List<Element> list = new ArrayList<Element>(nodeList != null ? nodeList.getLength() : 0);
		if (nodeList == null)
        	return list;
        int n = nodeList.getLength();
        for (int i = 0; i < n; i++) {
            Node item = nodeList.item(i);
            if (item instanceof Element)
                list.add((Element) item);
        }
        return list;
	}
    
    public static Element[] getChildElements(Element parent, boolean namespaceAware, String name) {
        ArrayBuilder<Element> builder = new ArrayBuilder<Element>(Element.class);
        NodeList childNodes = parent.getChildNodes();
        if (childNodes == null)
        	return new Element[0];
        int n = childNodes.getLength();
        for (int i = 0; i < n; i++) {
            Node item = childNodes.item(i);
            if (item instanceof Element && hasName(name, namespaceAware, item))
            	builder.add((Element) item);
        }
        return builder.toArray();
    }

    public static Element getChildElementAtPath(Element parent, String path, boolean namespaceAware, boolean required) {
        Element[] elements = getChildElementsAtPath(parent, path, namespaceAware);
		return assertSingleSearchResult(elements, required, path);
    }

    public static Element[] getChildElementsAtPath(Element parent, String path, boolean namespaceAware) {
        ArrayBuilder<Element> builder = new ArrayBuilder<Element>(Element.class);
        getChildElementsAtPath(parent, namespaceAware, path.split("/"), 0, builder);
        return builder.toArray();
    }

    private static void getChildElementsAtPath(Element parent, boolean namespaceAware, String[] pathComponents, int pathIndex, ArrayBuilder<Element> result) {
        NodeList childNodes = parent.getChildNodes();
        if (childNodes != null) {
        	String pathComponentName = pathComponents[pathIndex];
	        int n = childNodes.getLength();
	        for (int i = 0; i < n; i++) {
	            Node item = childNodes.item(i);
	            if (item instanceof Element) {
	            	Element element = (Element) item;
		            if (pathIndex < pathComponents.length - 1)
		            	getChildElementsAtPath(element, namespaceAware, pathComponents, pathIndex + 1, result);
		            else if (hasName(pathComponentName, namespaceAware, item))
	            		result.add(element);
	            }
	        }
        }
    }

	public static boolean hasName(String name, boolean namespaceAware, Node item) {
		String fqName = item.getNodeName();
		if (namespaceAware)
		    return fqName.equals(name);
		else
		    return name.equals(StringUtil.lastToken(fqName, ':'));
	}

    public static Element getChildElement(Element parent, boolean namespaceAware, boolean required, String name) {
        Element[] elements = getChildElements(parent, namespaceAware, name);
        return assertSingleSearchResult(elements, required, name);
    }

    private static Element assertSingleSearchResult(Element[] elements, boolean required, String searchTerm) {
        if (required && elements.length == 0)
            throw new IllegalArgumentException("No element found in search: " + searchTerm);
        if (elements.length > 1)
            throw new IllegalArgumentException("More that one element found in search: " + searchTerm);
        return (elements.length > 0 ? elements[0] : null);
    }

    public static String getText(Node node) {
        if (node == null)
            return null;
        if (node instanceof Text)
            return node.getNodeValue();
        NodeList children = node.getChildNodes();
        for (int i = 0; i < children.getLength(); i++)
            if (children.item(i) instanceof Text)
                return children.item(i).getNodeValue();
        return null;
    }

    public static Integer getIntegerAttribute(Element element, String name, Integer defaultValue) {
        if (LOGGER.isDebugEnabled())
            LOGGER.debug("getIntegerAttribute(" + element.getNodeName() + ", " + name + ')');
        String stringValue = element.getAttribute(name);
        if (StringUtil.isEmpty(stringValue))
            return defaultValue;
        return Integer.parseInt(stringValue);
    }

    public static Long getLongAttribute(Element element, String name, long defaultValue) {
        if (LOGGER.isDebugEnabled())
            LOGGER.debug("getLongAttribute(" + element.getNodeName() + ", " + name + ')');
        String stringValue = element.getAttribute(name);
        if (StringUtil.isEmpty(stringValue))
            return defaultValue;
        return Long.parseLong(stringValue);
    }

    public static Map<String, String> getAttributes(Element element) {
        NamedNodeMap attributes = element.getAttributes();
        Map<String, String> result = new HashMap<String, String>();
        int n = attributes.getLength();
        for (int i = 0; i < n; i++) {
            Attr attribute = (Attr) attributes.item(i);
            result.put(attribute.getName(), attribute.getValue());
        }
        return result;
    }

    public static PrintWriter createXMLFile(String uri, String encoding) 
        throws FileNotFoundException, UnsupportedEncodingException {
        PrintWriter printer = IOUtil.getPrinterForURI(uri, encoding);
        printer.println("<?xml version=\"1.0\" encoding=\"" + encoding + "\"?>");
        return printer;
    }

    public static String normalizedAttributeValue(Element element, String attributeName) {
        String value = element.getAttribute(attributeName);
        if (StringUtil.isEmpty(value))
            value = null;
        return value;
    }

    // XML operations --------------------------------------------------------------------------------------------------

    public static Document parse(String uri) throws IOException {
        return parse(uri, true, null, null, null);
    }

    public static Document parse(String uri, boolean namespaceAware, EntityResolver resolver, String schemaUri, ClassLoader classLoader) 
    		throws IOException {
        InputStream stream = null;
        try {
            stream = IOUtil.getInputStreamForURI(uri);
            return parse(stream, namespaceAware, resolver, schemaUri, classLoader, DEFAULT_ERROR_HANDLER);
        } catch (ConfigurationError e) {
        	throw new ConfigurationError("Error parsing " + uri, e);
        } finally {
            IOUtil.close(stream);
        }
    }

    public static Document parseString(String text) {
        return parseString(text, null, null);
    }
        
	public static Element parseStringAsElement(String xml) {
		return XMLUtil.parseString(xml).getDocumentElement();
	}
	
    public static Document parseString(String text, EntityResolver resolver, ClassLoader classLoader) {
        if (LOGGER.isDebugEnabled())
            LOGGER.debug(text);
        try {
        	String encoding = getEncoding(text);
	        return parse(new ByteArrayInputStream(text.getBytes(encoding)), true, resolver, null, classLoader, DEFAULT_ERROR_HANDLER);
        } catch (IOException e) {
        	throw new RuntimeException("Unexpected error", e);
        }
    }

	public static Document parse(InputStream stream) throws IOException {
        return parse(stream, null, null, DEFAULT_ERROR_HANDLER);
    }

    /**
     * @param resolver an {@link EntityResolver} implementation or null, in the latter case, no validation is applied
     */
    public static Document parse(InputStream stream, EntityResolver resolver, String schemaUri, ErrorHandler errorHandler) throws IOException {
        return parse(stream, true, resolver, schemaUri, null, errorHandler);
    }

	public static Document parse(InputStream stream, boolean namespaceAware, EntityResolver resolver,
			String schemaUri, ClassLoader classLoader, ErrorHandler errorHandler)
				throws IOException {
		try {
			DocumentBuilderFactory factory = createDocumentBuilderFactory(classLoader);
            factory.setNamespaceAware(namespaceAware);
            if (schemaUri != null)
            	activateXmlSchemaValidation(factory, schemaUri);
            DocumentBuilder builder = factory.newDocumentBuilder();
            if (resolver != null)
            	builder.setEntityResolver(resolver);
            if (errorHandler == null)
            	errorHandler = new ErrorHandler("XMLUtil");
            builder.setErrorHandler(createSaxErrorHandler(errorHandler));
            return builder.parse(stream);
        } catch (ParserConfigurationException e) {
            throw new ConfigurationError(e);
        } catch (SAXParseException e) {
            throw new ConfigurationError("Error in line " + e.getLineNumber() + " column " + e.getColumnNumber(), e);
        } catch (SAXException e) {
            throw new ConfigurationError(e);
        }
	}

	public static DocumentBuilderFactory createDocumentBuilderFactory(ClassLoader classLoader) {
		if (classLoader == null)
			classLoader = Thread.currentThread().getContextClassLoader();
		return DocumentBuilderFactory.newInstance(DOCUMENT_BUILDER_FACTORY_IMPL, classLoader);
	}

    public static NamespaceAlias namespaceAlias(Document document, String namespaceUri) {
        Map<String, String> attributes = XMLUtil.getAttributes(document.getDocumentElement());
        for (Map.Entry<String, String> entry : attributes.entrySet()) {
            String namespaceName = entry.getValue();
            if (namespaceUri.equals(namespaceName)) {
                String def = entry.getKey();
                String alias = (def.contains(":") ? StringUtil.lastToken(def, ':') : "");
				return new NamespaceAlias(alias, namespaceName);
            }
        }
        return new NamespaceAlias("", namespaceUri);
    }

    public static Map<String, String> getNamespaces(Document document) {
    	Map<String, String> namespaces = new HashMap<String, String>();
        Map<String, String> attributes = XMLUtil.getAttributes(document.getDocumentElement());
        for (Map.Entry<String, String> entry : attributes.entrySet()) {
            String attributeName = entry.getKey();
            if (attributeName.startsWith("xmlns")) {
                String alias = (attributeName.contains(":") ? StringUtil.lastToken(attributeName, ':') : "");
				namespaces.put(alias, entry.getValue());
            }
        }
        return namespaces;
    }

    public static String getTargetNamespace(Document xsdDocument) {
    	return xsdDocument.getDocumentElement().getAttribute("targetNamespace");
    }

	public static Boolean getBooleanAttribute(Element element, String attributeName, boolean required) {
		String stringValue = element.getAttribute(attributeName);
		if (StringUtil.isEmpty(stringValue) && required)
			throw new SyntaxError("Missing attribute '" + attributeName + "'", format(element));
		return ParseUtil.parseBoolean(stringValue);
	}

	public static boolean getBooleanAttributeWithDefault(Element element, String attributeName, boolean defaultValue) {
		String stringValue = element.getAttribute(attributeName);
		return (StringUtil.isEmpty(stringValue) ? defaultValue : Boolean.parseBoolean(stringValue));
	}

	public static double getDoubleAttribute(Element element, String name) {
		return Double.parseDouble(element.getAttribute(name));
	}

	public static Date getDateAttribute(Element element, String name) {
		return new String2DateConverter<Date>().convert(element.getAttribute(name));
	}

	public static void mapAttributesToProperties(Element element, Object bean, boolean unescape) {
		mapAttributesToProperties(element, bean, unescape, new NoOpConverter<String>());
	}
	
	public static void mapAttributesToProperties(Element element, Object bean, boolean unescape, Converter<String, String> nameNormalizer) {
		for (Map.Entry<String, String> attribute : getAttributes(element).entrySet()) {
			String name = StringUtil.lastToken(attribute.getKey(), ':');
			name = nameNormalizer.convert(name);
			String value = attribute.getValue();
			if (unescape)
				value = StringUtil.unescape(value);
			Class<?> type = bean.getClass();
			if (BeanUtil.hasProperty(type, name))
				BeanUtil.setPropertyValue(bean, name, value, true, true);
		}
	}
	
	public static void visit(Node element, Visitor<Node> visitor) {
	    visitor.visit(element);
	    NodeList childNodes = element.getChildNodes();
	    for (int i = 0; i < childNodes.getLength(); i++)
	    	visit(childNodes.item(i), visitor);
    }

	public static Element findElementByAttribute(String attributeName, String attributeValue, Element root) {
		if (attributeValue.equals(root.getAttribute(attributeName)))
			return root;
		else
			for (Element child : XMLUtil.getChildElements(root)) {
				Element candidate = findElementByAttribute(attributeName, attributeValue, child);
				if (candidate != null)
					return candidate;
			}
		return null;
    }

	public static Element findFirstAccepted(Filter<Element> filter, Element element) {
		if (filter.accept(element))
			return element;
		else
			for (Element child : XMLUtil.getChildElements(element)) {
				Element candidate = findFirstAccepted(filter, child);
				if (candidate != null)
					return candidate;
			}
		return null;
    }

	public static List<Element> findElementsByName(String name, boolean caseSensitive, Element root) {
	    return findElementsByName(name, caseSensitive, root, new ArrayList<Element>());
    }

	public static String getAttribute(Element element, String attributeName, boolean required) {
		String value = StringUtil.emptyToNull(element.getAttribute(attributeName));
		if (value == null && required)
			throw new IllegalArgumentException("Element '" + element.getNodeName() + "'" +
					" is missing the required attribute '" + attributeName + "'");
	    return value;
    }

	public static String getWholeText(Element element) {
	    StringBuilder builder = new StringBuilder();
	    NodeList nodeList = element.getChildNodes();
	    for (int i = 0; i < nodeList.getLength(); i++) {
	    	Node node = nodeList.item(i);
	    	if (node instanceof Text)
	    		builder.append(((Text) node).getWholeText());
	    	else if (node instanceof Element)
	    		builder.append(getWholeText((Element) node));
	    }
	    return builder.toString();
    }

	public static String formatText(String text) {
		return text.replace("&", "&amp;").replace("<", "&lt;").replace(">", "&gt;");
	}

	// private helpers -------------------------------------------------------------------------------------------------

    private static void format(Element element, SimpleXMLWriter out) {
		String name = element.getNodeName();
		Map<String, String> attributes = XMLUtil.getAttributes(element);
		try {
			out.startElement(name, attributes);
			NodeList childNodes = element.getChildNodes();
			for (int i = 0; i < childNodes.getLength(); i++) {
				Node child = childNodes.item(i);
				if (child instanceof Element)
					format((Element) child, out);
				else if (child instanceof Text) {
					String text = child.getTextContent();
					if (!StringUtil.isEmpty(text))
						out.characters(text.toCharArray(), 0, text.length());
				}
			}
			out.endElement(name);
		} catch (SAXException e) {
			throw new RuntimeException(e);
		}
	}

	private static List<Element> findElementsByName(String name, boolean caseSensitive, Element root, List<Element> result) {
		if (root.getNodeName().equals(name))
			result.add(root);
		else
			for (Element child : getChildElements(root))
				findElementsByName(name, caseSensitive, child, result);
	    return result;
    }

	private static Schema activateXmlSchemaValidation(DocumentBuilderFactory factory, String schemaUrl) {
		try {
			SchemaFactory schemaFactory = SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);
			Schema schema = schemaFactory.newSchema(new URL(schemaUrl));
			factory.setSchema(schema);
			return schema;
		} catch (Exception e) {
			// some XML parsers may not support attributes in general or especially XML Schema 
			LOGGER.error("Error activating schema validation, possibly you are offline or behind a proxy?", e.getMessage());
			return null;
		}
	}

    private static String getEncoding(String text) {
		if (text.startsWith("<?xml")) {
			int qm2i = text.indexOf('?', 5);
			int ei = text.indexOf("encoding");
			if (ei > 0 && ei < qm2i) {
				int dq = text.indexOf('"', ei);
				int sq = text.indexOf('\'', ei);
				int q1 = (dq > 0 ? (sq > 0 ? dq : Math.min(sq, dq)) : sq);
				dq = text.indexOf('"', q1 + 1);
				sq = text.indexOf('\'', q1 + 1);
				int q2 = (dq > 0 ? (sq > 0 ? dq : Math.min(sq, dq)) : sq);
				if (q1 > 0 && q2 > 0)
					return text.substring(q1 + 1, q2);
			}
		}
		return SystemInfo.getFileEncoding();
	}

	private static org.xml.sax.ErrorHandler createSaxErrorHandler(
			final ErrorHandler errorHandler) {
		return new org.xml.sax.ErrorHandler() {

			@Override
			public void error(SAXParseException e) {
				errorHandler.handleError(e.getMessage(), e);
			}

			@Override
			public void fatalError(SAXParseException e) {
				errorHandler.handleError(e.getMessage(), e);
			}

			@Override
			public void warning(SAXParseException e) {
				errorHandler.handleError(e.getMessage(), e);
			}
			
		};
	}

	@SuppressWarnings("null")
	public static void saveAsProperties(Properties properties, File file, String encoding) throws FileNotFoundException {
		if (properties.size() == 0)
			throw new IllegalArgumentException("Cannot save empty Properties");
		Document document = null;
		for (Map.Entry<Object, Object> entry : properties.entrySet()) {
			String key = (String) entry.getKey();
			String value = (String) entry.getValue();
			String[] prefixAndRemainingPath = StringUtil.splitOnFirstSeparator(key, '.');
			if (document == null)
				document = createDocument(prefixAndRemainingPath[0]);
			String rootElementName = document.getDocumentElement().getNodeName();
			if (!key.startsWith(rootElementName + '.'))
				throw new SyntaxError("Required prefix '" + rootElementName + "' not present in key", key);
			setProperty(prefixAndRemainingPath[1], value, document.getDocumentElement(), document);
		}
		document.setXmlStandalone(true); // needed to omit standalone="yes/no" in the XML header
		saveDocument(document, file, encoding);
	}
	
	public static void saveDocument(Document document, File file, String encoding) throws FileNotFoundException {
		FileOutputStream stream = new FileOutputStream(file);
        saveDocument(document, encoding, stream);
	}

	public static void saveDocument(Document document, String encoding, OutputStream out)
			throws TransformerFactoryConfigurationError {
		try {
			Transformer transformer = createTransformer(encoding);
			transformer.transform(new DOMSource(document), new StreamResult(out));
		} catch (TransformerException e) {
			throw new ConfigurationError(e);
		} finally {
			IOUtil.close(out);
		}
	}

	public static Document createDocument(String rootElementName) {
		Document document = createDocument();
		Element rootElement = document.createElement(rootElementName);
		document.appendChild(rootElement);
		return document;
	}
	
	public static Document createDocument() {
		try {
			DocumentBuilder documentBuilder = createDocumentBuilderFactory(null).newDocumentBuilder();
			return documentBuilder.newDocument();
		} catch (ParserConfigurationException e) {
			throw new RuntimeException(e);
		}
	}
	
	public static void setProperty(String key, String value, Document document) {
		String[] prefixAndRemainingPath = StringUtil.splitOnFirstSeparator(key, '.');
		Element rootElement = document.getDocumentElement();
		if (rootElement == null) {
			rootElement = document.createElement(prefixAndRemainingPath[0]);
			document.appendChild(rootElement);
		} else if (!key.equals(rootElement.getNodeName()))
			throw new IllegalArgumentException("Cannot set a property '" + key + "' on a document with root <" + rootElement.getNodeName() + ">");
		setProperty(prefixAndRemainingPath[1], value, rootElement, document);
	}
	
	public static void setProperty(String key, String value, Element element, Document document) {
		if (!StringUtil.isEmpty(key)) {
			String[] prefixAndRemainingPath = StringUtil.splitOnFirstSeparator(key, '.');
			String childName = prefixAndRemainingPath[0];
			Element child = getChildElement(element, false, false, childName);
			if (child == null) {
				child = document.createElement(childName);
				element.appendChild(child);
			}
			setProperty(prefixAndRemainingPath[1], value, child, document);
		} else {
			element.setTextContent(value);
		}
	}
	
	public static String resolveEntities(String xmlText) {
		while (xmlText.contains("&#")) {
			int st = xmlText.indexOf("&#");
			int en = xmlText.indexOf(";", st + 1);
			if (st >= 0 && en > 0) {
				int c = Integer.parseInt(xmlText.substring(st + 2, en));
				xmlText = xmlText.substring(0, st) + (char) c + xmlText.substring(en + 1);
			} else {
				break;
			}
		}
		return xmlText;
	}
	

	
	// private helpers -------------------------------------------------------------------------------------------------

	private static Transformer createTransformer(String encoding) {
		try {
			SAXTransformerFactory tf = (SAXTransformerFactory) SAXTransformerFactory.newInstance();
			Transformer transformer = tf.newTransformer();
			transformer.setOutputProperty(OutputKeys.STANDALONE, "yes");
			transformer.setOutputProperty(OutputKeys.ENCODING, encoding);
			transformer.setOutputProperty(OutputKeys.INDENT, "yes");
			transformer.setOutputProperty("{http://xml.apache.org/xslt}" + "indent-amount", "2");
			return transformer;
		} catch (TransformerConfigurationException e) {
			throw new ConfigurationError("Error creating Transformer", e);
		} catch (TransformerFactoryConfigurationError e) {
			throw new ConfigurationError("Error creating Transformer", e);
		}
	}

}
