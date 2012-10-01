/*
 * (c) Copyright 2007-2012 by Volker Bergmann. All rights reserved.
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

package org.databene.commons.mutator;

import org.databene.commons.ConversionException;
import org.databene.commons.Converter;
import org.databene.commons.Mutator;
import org.databene.commons.UpdateFailedException;

/**
 * Converts its input by a Converter object and forwards the result to another Mutator.<br/>
 * <br/>
 * Created: 12.05.2005 19:08:30
 */
@SuppressWarnings("unchecked")
public class ConvertingMutator extends MutatorWrapper {

    @SuppressWarnings("rawtypes")
	private Converter converter;

    @SuppressWarnings("rawtypes")
	public ConvertingMutator(Mutator realMutator, Converter converter) {
        super(realMutator);
        this.converter = converter;
    }

    @Override
	public void setValue(Object target, Object value) throws UpdateFailedException {
        try {
            Object convertedValue = converter.convert(value);
            realMutator.setValue(target, convertedValue);
        } catch (ConversionException e) {
            throw new UpdateFailedException(e);
        }
    }

}
