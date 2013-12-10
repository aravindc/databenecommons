/*
 * (c) Copyright 2013 by Volker Bergmann. All rights reserved.
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

package org.databene.commons.tag;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.databene.commons.Tagged;

/**
 * Provides tag-related utility methods.<br/><br/>
 * Created: 15.11.2013 06:58:15
 * @since 0.5.25
 * @author Volker Bergmann
 */

public class TagUtil {

	public static void addTag(String tag, List<? extends Tagged> taggeds) {
		for (Tagged tagged : taggeds)
			tagged.addTag(tag);
	}

	public static void removeTag(String tag, List<? extends Tagged> taggeds) {
		for (Tagged tagged : taggeds)
			tagged.removeTag(tag);
	}

	public static int frequency(String tag, List<? extends Tagged> taggeds) {
		int n = 0;
		for (Tagged tagged : taggeds)
			if (tagged != null && tagged.hasTag(tag))
				n++;
		return n;
	}

	public static <T extends Tagged> List<T> getElementsWithTag(String tag, Collection<T> elements, boolean ignoreCase, boolean partialMatch) {
		List<T> result = new ArrayList<T>();
		for (T element : elements)
			if (hasTag(tag, element, ignoreCase, partialMatch))
				result.add(element);
		return result;
	}
	
	public static boolean hasTag(String tag, Tagged tagged, boolean ignoreCase, boolean partialMatch) {
		for (String candidate : tagged.getTags()) {
			if (ignoreCase) {
				candidate = candidate.toLowerCase();
				tag = tag.toLowerCase();
			}
			if (candidate.equals(tag) || (partialMatch && candidate.startsWith(tag)))
				return true;
		}
		return false;
	}
}
