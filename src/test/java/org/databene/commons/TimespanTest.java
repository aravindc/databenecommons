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

package org.databene.commons;

import org.junit.Test;
import static junit.framework.Assert.*;

import java.util.Date;

import org.databene.commons.Timespan;
import org.databene.commons.TimeUtil;

/**
 * Tests the {@link Timespan} class.<br/><br/>
 * Created: 17.02.2005 21:29:24
 * @author Volker Bergmann
 */
public class TimespanTest {

	@Test
    public void testDuration() {
        Date now = new Date();
        Date tomorrow = new Date(now.getTime() + Period.DAY.getMillis());
        Timespan timespan = new Timespan(now, tomorrow);
        assertEquals((Long)Period.DAY.getMillis(), timespan.duration());
    }

	@Test
    public void testContains() {
        Date d1 = TimeUtil.date(2005, 0, 1);
        Date d2 = TimeUtil.date(2005, 0, 2);
        Date d3 = TimeUtil.date(2005, 0, 3);
        Date d4 = TimeUtil.date(2005, 0, 4);

        Timespan l  = new Timespan(d1, d4);
        Timespan m1 = new Timespan(d1, d3);
        Timespan m2 = new Timespan(d2, d4);
        Timespan s2 = new Timespan(d2, d3);

        assertTrue(l.contains(l));
        assertTrue(l.contains(m1));
        assertTrue(l.contains(m2));
        assertTrue(l.contains(s2));
        assertFalse(m1.contains(l));
        assertFalse(m2.contains(l));
        assertFalse(s2.contains(l));
    }

	@Test
    public void testOverlaps() {
        Date d1 = TimeUtil.date(2005, 0, 1);
        Date d2 = TimeUtil.date(2005, 0, 2);
        Date d3 = TimeUtil.date(2005, 0, 3);
        Date d4 = TimeUtil.date(2005, 0, 4);

        Timespan l  = new Timespan(d1, d4);
        Timespan m1 = new Timespan(d1, d3);
        Timespan m2 = new Timespan(d2, d4);
        Timespan s1 = new Timespan(d1, d2);
        Timespan s2 = new Timespan(d2, d3);
        Timespan s3 = new Timespan(d3, d4);

        assertTrue(l.overlaps(l));
        assertTrue(l.overlaps(m1));
        assertTrue(l.overlaps(m2));
        assertTrue(l.overlaps(s2));
        assertTrue(m1.overlaps(l));
        assertTrue(m2.overlaps(l));
        assertTrue(s2.overlaps(l));
        assertTrue(m1.overlaps(m2));
        assertTrue(m2.overlaps(m1));
        assertFalse(s1.overlaps(s2));
        assertFalse(s2.overlaps(s1));
        assertFalse(s1.overlaps(s3));
        assertFalse(s3.overlaps(s1));
    }
    
	@Test
    public void testUnite() {
        Date d1 = TimeUtil.date(2005, 0, 1);
        Date d2 = TimeUtil.date(2005, 0, 2);
        Date d3 = TimeUtil.date(2005, 0, 3);
        Date d4 = TimeUtil.date(2005, 0, 4);
        Timespan t12 = new Timespan(d1, d2);
        Timespan t34 = new Timespan(d3, d4);
        Timespan t14 = new Timespan(d1, d4);
        assertEquals(t14, Timespan.unite(t14, t14));
        assertEquals(t14, Timespan.unite(t12, t34));
        Date now = new Date();
        Timespan future = new Timespan(now, null);
        Timespan past = new Timespan(null, now);
        Timespan always = new Timespan(null, null);
        assertEquals(always, Timespan.unite(past, future));
    }
    
	@Test
    public void testEquals() {
    	Timespan always = new Timespan(null, null);
    	assertFalse(always.equals(null));
    	assertFalse(always.equals(""));
    	assertTrue(always.equals(always));
    	Date now = new Date();
    	Timespan future = new Timespan(now, null);
    	assertFalse(always.equals(future));
    	assertFalse(future.equals(always));
    }
	
}

