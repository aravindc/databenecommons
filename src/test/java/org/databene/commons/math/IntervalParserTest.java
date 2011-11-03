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

import static org.junit.Assert.*;

import java.text.ParsePosition;

import org.databene.commons.ComparableComparator;
import org.databene.commons.comparator.IntComparator;
import org.junit.Test;

/**
 * Tests the {@link IntervalParser}.<br/><br/>
 * Created: 10.03.2011 16:04:54
 * @since 0.5.8
 * @author Volker Bergmann
 */
public class IntervalParserTest {
	
	@Test
	public void testClosedInterval() {
		Interval<Integer> parsedInterval = parseInterval("[1,2]");
		assertEquals(new Interval<Integer>(1, true, 2, true, new ComparableComparator<Integer>()), parsedInterval);
	}

	@Test
	public void testOpenInterval() {
		Interval<Integer> parsedInterval = parseInterval("]1,2[");
		assertEquals(new Interval<Integer>(1, false, 2, false, new ComparableComparator<Integer>()), parsedInterval);
	}

	@Test
	public void testWhitespace() {
		Interval<Integer> parsedInterval = parseInterval(" [ 1 ,	2 ] ");
		assertEquals(new Interval<Integer>(1, true, 2, true, new ComparableComparator<Integer>()), parsedInterval);
	}

	// helpers ---------------------------------------------------------------------------------------------------------
	
	private Interval<Integer> parseInterval(String text) {
		IntervalParser<Integer> parser = new IntervalParser<Integer>(new IntParser(), new IntComparator());
		return parser.parseObject(text, new ParsePosition(0));
	}

}
