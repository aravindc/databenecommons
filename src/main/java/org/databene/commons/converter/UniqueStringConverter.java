/*
 * (c) Copyright 2008-2012 by Volker Bergmann. All rights reserved.
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

package org.databene.commons.converter;

import java.util.HashSet;
import java.util.Set;

import org.databene.commons.ConversionException;

/**
 * Assures uniqueness for all processed Strings by appending unique numbers to recurring instances.<br/>
 * <br/>
 * Created: 24.06.2008 19:41:08
 * @since 0.4.4
 * @author Volker Bergmann
 */
public class UniqueStringConverter extends AbstractConverter<String, String> {
	
	private static final int MAX_TRIES = 10000;
	private Set<String> usedStrings;

	public UniqueStringConverter() {
		super(String.class, String.class);
		usedStrings = new HashSet<String>();
	}

	@Override
	public synchronized String convert(String sourceValue) throws ConversionException {
		String resultValue = sourceValue;
		if (usedStrings.contains(sourceValue)) {
			boolean ok = false;
			for (int i = 0; !ok && i < MAX_TRIES; i++) {
				resultValue = sourceValue + i;
				if (!usedStrings.contains(resultValue)) {
					ok = true;
				}
			}
			if (!ok)
				throw new UnsupportedOperationException("not more than " + MAX_TRIES + " identical Strings can be made unique");
		}
		usedStrings.add(resultValue);
		return resultValue;
	}

	@Override
	public boolean isParallelizable() {
	    return false;
    }

	@Override
	public boolean isThreadSafe() {
	    return true;
    }

}
