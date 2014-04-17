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

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.xml.namespace.NamespaceContext;
import javax.xml.namespace.QName;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;

import org.databene.commons.CollectionUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

/**
 * Provides XPath queries on XML documents that make use of namespaces.<br/><br/>
 * Created: 23.03.2014 17:07:01
 * @since 0.5.29
 * @author Volker Bergmann
 */

public class XPathSupport {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(XPathSupport.class);
	
	private static final XPathFactory FACTORY = XPathFactory.newInstance();
	
	private Document document;
	private SimpleNamespaceContext context;

	public XPathSupport(Document document, String... prefixes) {
		this.document = document;
		this.context = scanNamespaces(document, (prefixes != null ? CollectionUtil.toSet(prefixes) : null));
	}
	
	public String queryString(String expression) throws XPathExpressionException {
		return (String) query(expression, XPathConstants.STRING);
	}

	public List<Element> queryElements(String expression) throws XPathExpressionException {
		return XMLUtil.toElementList((NodeList) query(expression, XPathConstants.NODESET));
	}
	
	public Object query(String expression, QName returnType) throws XPathExpressionException {
		XPath xpath = FACTORY.newXPath();
		xpath.setNamespaceContext(context);
		return xpath.evaluate(expression, document, returnType);
	}
	
	
	// private helpers -------------------------------------------------------------------------------------------------
	
	private static SimpleNamespaceContext scanNamespaces(Document document, Set<String> prefixes) {
		SimpleNamespaceContext context = new SimpleNamespaceContext();
		scanNamespaces(document.getDocumentElement(), prefixes, context); 
		return context;
	}

	private static void scanNamespaces(Element element, Set<String> prefixes, SimpleNamespaceContext context) {
		NodeList children = element.getChildNodes();
		for (int i = 0; i < children.getLength() && (prefixes == null || prefixes.size() > 0); i++) {
			Node childNode = children.item(i);
			String nsUri = childNode.getNamespaceURI();
			String prefix = childNode.getPrefix();
			LOGGER.debug("prefix {} refers NS {}", prefix, nsUri);
			if (prefixes != null)
				prefixes.remove(prefix);
			context.add(prefix, nsUri);
			if (childNode instanceof Element)
				scanNamespaces((Element) childNode, prefixes, context);
		}
	}
	
	public static class SimpleNamespaceContext implements NamespaceContext {
		
		private Map<String, String> map;
		
		public SimpleNamespaceContext() {
			this.map = new HashMap<String, String>();
		}
		
		public void add(String prefix, String uri) {
			map.put(prefix, uri);
		}

		@Override
		public String getNamespaceURI(String prefix) {
			return map.get(prefix);
		}

		@Override
		public String getPrefix(String uri) {
			throw new UnsupportedOperationException(); // TODO implement
		}

		@Override
		public Iterator<String> getPrefixes(String uri) {
			throw new UnsupportedOperationException(); // TODO implement
		}

	}

}
