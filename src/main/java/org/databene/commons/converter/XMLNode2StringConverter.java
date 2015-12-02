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

package org.databene.commons.converter;

import org.databene.commons.ConversionException;
import org.databene.commons.SystemInfo;
import org.databene.commons.converter.ToStringConverter;
import org.databene.commons.converter.UnsafeConverter;
import org.databene.commons.xml.XMLUtil;
import org.w3c.dom.CDATASection;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Text;

/**
 * Converts content elements of org.w3c.Document to strings.<br/><br/>
 * Created: 20.11.2015 18:00:16
 * @since 1.0.6
 * @author Volker Bergmann
 */

public class XMLNode2StringConverter extends UnsafeConverter<Object, String> {
	
	private static final String LF = SystemInfo.getLineSeparator();
	private static final XMLNode2StringConverter DEFAULT_INSTANCE = new XMLNode2StringConverter();

	public XMLNode2StringConverter() {
		super(Object.class, String.class);
	}

	@Override
	public String convert(Object node) throws ConversionException {
		if (node instanceof CDATASection)
			return "<![CDATA[" + ((CDATASection) node).getTextContent() + "]]>";
		else if (node instanceof Text) 
			return "'" + ((Text) node).getTextContent() + "'";
		else if (node instanceof Element)
			return LF + XMLUtil.format((Element) node).trim() + LF;
		else if (node instanceof Document)
			return XMLUtil.format((Document) node);
		else if (node instanceof String)
			return "'" + node + "'";
		else
			return ToStringConverter.convert(node, "");
	}

	public static String format(Object node) throws ConversionException {
		return DEFAULT_INSTANCE.convert(node);
	}

}
