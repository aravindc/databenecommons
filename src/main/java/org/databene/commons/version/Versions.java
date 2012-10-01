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

package org.databene.commons.version;

import java.text.ParsePosition;

import org.databene.commons.ComparableComparator;
import org.databene.commons.StringUtil;
import org.databene.commons.math.Interval;
import org.databene.commons.math.Intervals;
import org.databene.commons.math.IntervalsParser;

/**
 * {@link Intervals} implementation for {@link VersionNumber}s.<br/><br/>
 * Created: 11.03.2011 10:09:15
 * @since 0.5.8
 * @author Volker Bergmann
 */
public class Versions extends Intervals<VersionNumber> {

	private static final long serialVersionUID = 6258577730893701943L;
	
	private static final ComparableComparator<VersionNumber> VERSION_COMPARATOR = new ComparableComparator<VersionNumber>();

	public static Versions valueOf(String spec) {
		if (StringUtil.isEmpty(spec) || "*".equals(spec.trim()))
			return createUnlimited();
		IntervalsParser<VersionNumber> parser = new IntervalsParser<VersionNumber>(
				new VersionNumberParser(), VERSION_COMPARATOR);
		return (Versions) parser.parseObject(spec, new ParsePosition(0), new Versions());
	}

	public static Versions createUnlimited() {
		Versions result = new Versions();
		result.add(Interval.<VersionNumber>createInfiniteInterval());
		return result;
	}

	public static Versions createSingleVersion(VersionNumber version) {
		Versions result = new Versions();
		result.add(new Interval<VersionNumber>(version, true, version, true, VERSION_COMPARATOR));
		return result;
	}

}
