/*
 * Copyright (C) 2004-2014 Volker Bergmann (volker.bergmann@bergmann-it.de).
 * All rights reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * http://www.apache.org/licenses/LICENSE-2.0
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.databene.commons.xml;

import java.util.List;

import javax.xml.namespace.QName;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

/**
 * Provides XPath query functionality on XML documents and elements.
 * Created: 28.03.2014 16:55:12
 * @since 0.5.29
 * @author Volker Bergmann
 */

public class XPathUtil {

	public static List<Element> queryElements(Document document, String expression) throws XPathExpressionException {
		return XMLUtil.toElementList(queryNodes(document, expression));
	}

	public static List<Element> queryElements(Element element, String expression) throws XPathExpressionException {
		return XMLUtil.toElementList((NodeList) query(element, expression, XPathConstants.NODESET));
	}

	public static Element queryElement(Document document, String expression) throws XPathExpressionException {
		return (Element) query(document, expression, XPathConstants.NODE);
	}

	public static String queryElementText(Document document, String elementExpression) throws XPathExpressionException {
		// a direct string query for the content of a missing element would return an empty string, 
		// not allowing to differ an empty element from a missing one, so I need to take a two-step approach:
        Element element = queryElement(document, elementExpression);
        if (element == null)
        	return null;
		return element.getTextContent();
	}

	public static String queryAttribute(Document document, String elementExpression, String attributeName) throws XPathExpressionException {
		// since the result of a string query does not differentiate between empty ("") and not found (null), I need to apply a workaround
		// see http://stackoverflow.com/questions/17390684/jaxp-xpath-1-0-or-2-0-how-to-distinguish-empty-strings-from-non-existent-value
        Element element = queryElement(document, elementExpression);
        if (element == null)
        	return null;
        NodeList nodes = queryNodes(element, "@" + attributeName);
		return (nodes.getLength() == 0 ? null : nodes.item(0).getTextContent());
	}
	
	public static String queryString(Document document, String expression) throws XPathExpressionException {
		return (String) query(document, expression, XPathConstants.STRING);
	}

	public static String queryString(Element element, String expression) throws XPathExpressionException {
		return (String) query(element, expression, XPathConstants.STRING);
	}

	public static NodeList queryNodes(Document document, String expression) throws XPathExpressionException {
		return (NodeList) query(document, expression, XPathConstants.NODESET);
	}

	public static NodeList queryNodes(Element element, String expression) throws XPathExpressionException {
		return (NodeList) query(element, expression, XPathConstants.NODESET);
	}

	public static Object query(Document document, String expression, QName returnType) throws XPathExpressionException {
		XPath xpath = XPathFactory.newInstance().newXPath();
		return xpath.evaluate(expression, document, returnType);
	}
	
	public static Object query(Element element, String expression, QName returnType) throws XPathExpressionException {
		XPath xpath = XPathFactory.newInstance().newXPath();
		return xpath.evaluate(expression, element, returnType);
	}

}
