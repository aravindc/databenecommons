/*
 * (c) Copyright 2011-2014 by Volker Bergmann. All rights reserved.
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

package org.databene.commons.math;

import java.text.ParsePosition;
import java.util.Comparator;

import static org.databene.commons.ParseUtil.skipWhiteSpace;
import org.databene.commons.Parser;

/**
 * {@link Parser} implementation for {@link Intervals}.
 * The supported syntax is as follows:
 * <pre>
 * Syntax                   Description
 * *                        Any version
 * 1.1	                    Version 1.1
 * 1.1, 1.2                 Versions 1.1 and 1.2
 * >=1.1                    Version 1.1 and all subsequent ones
 * > 1.0                    All versions after 1.0 
 * < 2.0                    All versions before 2.0
 * <= 2.1                   All versions until 2.1 (inclusive)
 * [1.1.2, 1.1.8]           All versions from 1.1.2 to 1.1.8
 * [1.1.2, 2.0[             All versions from 1.1.2 before 2.0
 * ]1.1.2, 2.0[             All versions after 1.1.2 and before 2.0
 * [1.1, 1.3], [1.5, 1.8]   Versions 1.1 through 1.3 and versions 1.5 to 1.8
 * [1.1, 1.4[,  ]1.4, 1.8]  Versions 1.1 through 1.8, excluding 1.4 
 * </pre>
 * <br/><br/>
 * Created: 10.03.2011 17:37:08
 * @since 0.5.8
 * @author Volker Bergmann
 */
public class IntervalsParser<E> extends Parser<Intervals<E>> {

	private Parser<E> endpointParser;
	private Comparator<E> endpointComparator;
	
	public static <T> Intervals<T> parse(String text, Parser<T> endpointParser, Comparator<T> endpointComparator) {
		return new IntervalsParser<T>(endpointParser, endpointComparator).parseObject(text, new ParsePosition(0));
	}
	
	public IntervalsParser(Parser<E> endpointParser, Comparator<E> endpointComparator) {
		this.endpointParser = endpointParser;
		this.endpointComparator = endpointComparator;
	}

	@Override
	public Intervals<E> parseObject(String text, ParsePosition pos) {
		return parseObject(text, pos, new Intervals<E>());
	}

	public Intervals<E> parseObject(String text, ParsePosition pos, Intervals<E> target) {
		boolean continued;
		do {
			Interval<E> interval = parseRange(text, pos);
			if (interval != null)
				target.add(interval);
			skipWhiteSpace(text, pos);
			int index = pos.getIndex();
			continued = (index < text.length() && text.charAt(index) == ',');
			if (continued)
				advance(pos);
		} while (continued);
		return target;
	}

	private Interval<E> parseRange(String text, ParsePosition pos) {
		skipWhiteSpace(text, pos);
		char c = text.charAt(pos.getIndex());
		if (c == '*') {
			advance(pos);
			return new Interval<E>(null, false, null, false, endpointComparator);
		} else if (c == '>' || c == '<') {
			return parseBound(text, pos);
		} else if (c == '[' || c == ']') {
			return parseInterval(text, pos);
		} else {
			return parseEndpoint(text, pos);
		}
	}

	private Interval<E> parseBound(String text, ParsePosition pos) {
		char comparison = text.charAt(pos.getIndex());
		advance(pos);
		boolean orEqual = (text.charAt(pos.getIndex()) == '=');
		if (orEqual)
			advance(pos);
		skipWhiteSpace(text, pos);
		E endpoint = endpointParser.parseObject(text, pos);
		E min = null;
		E max = null;
		boolean minInclusive = false;
		boolean maxInclusive = false;
		if (comparison == '>') {
			min = endpoint;
			minInclusive = orEqual;
		} else {
			max = endpoint;
			maxInclusive = orEqual;
		}
		return new Interval<E>(min, minInclusive, max, maxInclusive, endpointComparator);
	}

	private Interval<E> parseInterval(String text, ParsePosition pos) {
		return new IntervalParser<E>(endpointParser, endpointComparator).parseObject(text, pos);
	}

	private Interval<E> parseEndpoint(String text, ParsePosition pos) {
		E endpoint = endpointParser.parseObject(text, pos);
		return new Interval<E>(endpoint, true, endpoint, true, endpointComparator);
	}

	private static void advance(ParsePosition pos) {
		pos.setIndex(pos.getIndex() + 1);
	}

}
