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

package org.databene.commons.converter;

import java.text.Format;

import org.databene.commons.Converter;

/**
 * Parent class for {@link Converter}s that use a {@link java.text.Format} instance for 
 * parsing Strings or formatting other objects.<br/><br/>
 * Created: 26.02.2010 14:52:25
 * @since 0.5.0
 * @author Volker Bergmann
 * @param <S>
 */
public abstract class FormatBasedConverter<S, T> extends AbstractConverter<S, T> implements Cloneable {

	/** The java.text.Format object used for conversion */
    protected Format format;
	private boolean threadSafe;

	public FormatBasedConverter(Class<S> sourceType, Class<T> targetType, Format format, boolean threadSafe) {
		super(sourceType, targetType);
		this.format = format;
		this.threadSafe = threadSafe;
	}

	public boolean isParallelizable() {
        return true;
    }

	public boolean isThreadSafe() {
        return threadSafe;
    }

	@SuppressWarnings({ "unchecked", "rawtypes" })
    @Override
    public Object clone() {
        try {
        	FormatBasedConverter copy = (FormatBasedConverter) super.clone();
        	copy.format = (Format) format.clone();
    		return copy;
        } catch (CloneNotSupportedException e) {
        	throw new RuntimeException(e);
        }
    }

}