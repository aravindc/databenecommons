/*
 * (c) Copyright 2011 by Volker Bergmann. All rights reserved.
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

import org.databene.commons.ParseException;
import static org.databene.commons.ParseUtil.skipWhiteSpace;
import org.databene.commons.Parser;

/**
 * Parses an {@link Interval} generically using an endpoint parser and an endpoint comparator.
 * The endpoint parser has to be able to parse the interval endpoint values.<br/><br/>
 * Created: 10.03.2011 15:33:01
 * @since 0.5.8
 * @author Volker Bergmann
 */
public class IntervalParser<E> extends Parser<Interval<E>> {

	private Parser<E> endpointParser;
	private Comparator<E> endpointComparator;

	public static <T> Interval<T> parse(String text, Parser<T> endpointParser, Comparator<T> endpointComparator) {
		return new IntervalParser<T>(endpointParser, endpointComparator).parseObject(text, new ParsePosition(0));
	}
	
	public IntervalParser(Parser<E> endPointParser, Comparator<E> endpointComparator) {
		this.endpointParser = endPointParser;
		this.endpointComparator = endpointComparator;
	}

	@Override
	public Interval<E> parseObject(String text, ParsePosition pos) throws ParseException {
		skipWhiteSpace(text, pos);

		// parse left bracket
		boolean minInclusive;
		char c = text.charAt(pos.getIndex());
		switch (c) {
			case '[' : minInclusive = true; break;
			case ']' : minInclusive = false; break;
			default  : throw new ParseException("Expected '[' or ']', found: " + c, text);
		}
		pos.setIndex(pos.getIndex() + 1);
		skipWhiteSpace(text, pos);
		
		// parse lower endpoint
		E min = endpointParser.parseObject(text, pos);
		skipWhiteSpace(text, pos);

		// parse comma
		c = text.charAt(pos.getIndex());
		if (c != ',')
			throw new ParseException("Expected ',', found '" + c + "'", text);
		pos.setIndex(pos.getIndex() + 1);
		skipWhiteSpace(text, pos);
		
		// parse upper endpoint
		E max = endpointParser.parseObject(text, pos);
		skipWhiteSpace(text, pos);
		
		// parse right bracket
		boolean maxInclusive;
		c = text.charAt(pos.getIndex());
		switch (c) {
			case ']' : maxInclusive = true; break;
			case '[' : maxInclusive = false; break;
			default  : throw new ParseException("Expected '[' or ']', found: " + c, text);
		}
		pos.setIndex(pos.getIndex() + 1);
		skipWhiteSpace(text, pos);
		
		return new Interval<E>(min, minInclusive, max, maxInclusive, endpointComparator);
	}

}
