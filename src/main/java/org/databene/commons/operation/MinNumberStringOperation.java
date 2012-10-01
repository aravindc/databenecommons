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

package org.databene.commons.operation;

import org.databene.commons.Converter;
import org.databene.commons.Operation;
import org.databene.commons.converter.NumberParser;

/**
 * Returns the maximum value of several number literals.<br/><br/>
 * Created: 08.03.2008 07:18:09
 * @since 0.4.0
 * @author Volker Bergmann
 */
@SuppressWarnings("unchecked")
public class MinNumberStringOperation implements Operation<String, String> {

	@SuppressWarnings("rawtypes")
	private MinOperation<ComparableWrapper> operation;
	
	private Converter<String, ?> parser;

    @SuppressWarnings("rawtypes")
	public MinNumberStringOperation() {
        this.operation = new MinOperation<ComparableWrapper>();
        this.parser = new NumberParser();
    }

	@Override
	public String perform(String... args) {
	    ComparableWrapper<String>[] wrappers = ComparableWrapper.wrapAll(args, parser);
	    ComparableWrapper<String> min = operation.perform(wrappers);
		return min.realObject;
    }
    
}