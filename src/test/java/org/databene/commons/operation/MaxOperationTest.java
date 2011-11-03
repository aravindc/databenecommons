/*
 * (c) Copyright 2007-2009 by Volker Bergmann. All rights reserved.
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

import org.databene.commons.comparator.ReverseComparator;
import org.databene.commons.operation.MaxOperation;

import org.junit.Test;
import static junit.framework.Assert.*;

/**
 * Tests the {@link MaxOperation}.<br/>
 * <br/>
 * Created: 25.01.2008 18:03:12
 * @author Volker Bergmann
 */
public class MaxOperationTest {

	@Test
    public void testInteger() {
        MaxOperation<Integer> op = new MaxOperation<Integer>();
        assertEquals(Integer.valueOf(2), op.perform(-1, 0, 1, 2));
    }

	@Test
    public void testString() {
        MaxOperation<String> op = new MaxOperation<String>();
        assertEquals("charly", op.perform("alpha", "bravo", "charly"));
    }

	@Test
    public void testStringDesc() {
        MaxOperation<String> op = new MaxOperation<String>(new ReverseComparator<String>());
        assertEquals("alpha", op.perform("alpha", "bravo", "charly"));
    }

}
