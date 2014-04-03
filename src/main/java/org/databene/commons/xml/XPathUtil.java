/*
 * (c) Copyright 2014 by Volker Bergmann. All rights reserved.
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
 * TODO Document class.<br/><br/>
 * Created: 28.03.2014 16:55:12
 * @since 0.5.29
 * @author Volker Bergmann
 */

public class XPathUtil {

	public static String queryString(Document document, String expression) throws XPathExpressionException {
		return (String) query(document, expression, XPathConstants.STRING);
	}

	public static List<Element> queryElements(Document document, String expression) throws XPathExpressionException {
		return XMLUtil.toElementList(queryNodes(document, expression));
	}

	public static List<Element> queryElements(Element element, String expression) throws XPathExpressionException {
		return XMLUtil.toElementList((NodeList) query(element, expression, XPathConstants.NODESET));
	}

	public static Element queryElement(Document document, String expression) throws XPathExpressionException {
		return (Element) query(document, expression, XPathConstants.NODE);
	}

	public static NodeList queryNodes(Document document, String expression) throws XPathExpressionException {
		return (NodeList) query(document, expression, XPathConstants.NODESET);
	}

	public static Object query(Document document, String expression, QName returnType) throws XPathExpressionException {
		XPath xpath = XPathFactory.newInstance().newXPath();
		return xpath.evaluate(expression, document, returnType);
	}
	
	public static String queryString(Element element, String expression) throws XPathExpressionException {
		return (String) query(element, expression, XPathConstants.STRING);
	}

	public static Object query(Element element, String expression, QName returnType) throws XPathExpressionException {
		XPath xpath = XPathFactory.newInstance().newXPath();
		return xpath.evaluate(expression, element, returnType);
	}
	
}
