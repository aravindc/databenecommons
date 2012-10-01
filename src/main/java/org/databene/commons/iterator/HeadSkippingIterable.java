/*
 * (c) Copyright 2011-2012 by Volker Bergmann. All rights reserved.
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

package org.databene.commons.iterator;

import org.databene.commons.HeavyweightIterator;
import org.databene.commons.HeavyweightTypedIterable;

/**
 * {@link Iterable} proxy which skips the first data row.<br/><br/>
 * Created: 19.07.2011 09:04:03
 * @since 0.5.9
 * @author Volker Bergmann
 */
public class HeadSkippingIterable<T> implements HeavyweightTypedIterable<T> {
	
	private HeavyweightTypedIterable<T> source;

	public HeadSkippingIterable(HeavyweightTypedIterable<T> source) {
		this.source = source;
	}

	@Override
	public Class<T> getType() {
		return source.getType();
	}

	@Override
	public HeavyweightIterator<T> iterator() {
		HeavyweightIterator<T> result = source.iterator();
		if (result.hasNext())
			result.next();
		return result;
	}

}
