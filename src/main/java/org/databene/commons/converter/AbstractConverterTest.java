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

import org.databene.commons.Assert;
import org.databene.commons.BeanUtil;
import org.databene.commons.Converter;

/**
 * Parent class for {@link Converter} test classes.<br/><br/>
 * Created: 26.02.2010 16:29:48
 * @since 0.5.0
 * @author Volker Bergmann
 */
@SuppressWarnings("rawtypes")
public abstract class AbstractConverterTest {
	
	private Class<? extends Converter> converterClass;

    public AbstractConverterTest(Class<? extends Converter> converterClass) {
		this.converterClass = converterClass;
	}
	
	public void verifyParallelizable() {
		Converter<?, ?> converter;
        try {
	        converter = BeanUtil.newInstance(converterClass);
        } catch (Exception e) {
	        return; // if there is no default constructor, we can't test
        }
	    checkParallelizable(converter);
	}

	private void checkParallelizable(Converter<?, ?> converter) {
	    if (converter.isParallelizable()) {
	    	Assert.isTrue(converter instanceof Cloneable, "Parallelizable converters must implement " + Cloneable.class);
	    	Assert.isTrue(BeanUtil.findMethod(converterClass, "clone", (Class[]) null) != null, 
	    			"Parallelizable converters must have a public clone() method");
	    }
    }
	
	// TODO v0.5.x add further common test features, e.g. multithreaded tests for predefined samples

}
