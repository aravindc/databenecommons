/*
 * (c) Copyright 2010 by Volker Bergmann. All rights reserved.
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

package org.databene.commons.operation;

import org.databene.commons.Converter;

/**
 * Wraps a data object with a helper object that can be compared independently of the data object.<br/><br/>
 * Created: 26.02.2010 09:17:05
 * @since 0.5.0
 * @author Volker Bergmann
 */
@SuppressWarnings({ "unchecked", "rawtypes" })
public class ComparableWrapper<E> implements Comparable<ComparableWrapper> {

	public final Comparable comparable;
	public final E realObject;
	
	public ComparableWrapper(Comparable comparable, E realObject) {
	    this.comparable = comparable;
	    this.realObject = realObject;
    }

	public int compareTo(ComparableWrapper that) {
	    return this.comparable.compareTo(that.comparable);
    }

    public static <T> ComparableWrapper<T>[] wrapAll(T[] realObjects, Converter<T, ?> comparableBuilder) {
		ComparableWrapper<T>[] result = new ComparableWrapper[realObjects.length];
		for (int i = 0; i < realObjects.length; i++) {
			T realObject = realObjects[i];
			Comparable comparable = (Comparable) comparableBuilder.convert(realObject);
			result[i] = new ComparableWrapper<T>(comparable, realObject);
		}
		return result;
	}
	
}
