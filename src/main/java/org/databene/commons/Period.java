/*
 * (c) Copyright 2007 by Volker Bergmann. All rights reserved.
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

import java.util.*;

/**
 * Defines duration constants on millisecond base.
 * Created: 09.05.2007 19:22:27
 */
public class Period implements Comparable<Period> {

    private static SortedSet<Period> instances = new TreeSet<Period>();

    public static final Period MILLISECOND = new Period(1L, "ms");
    public static final Period SECOND = new Period(1000L, "s");
    public static final Period MINUTE = new Period(60 * 1000L, "m");
    public static final Period HOUR = new Period(60 * 60 * 1000L, "h");
    public static final Period DAY = new Period(24 * 60 * 60 * 1000L, "d");
    public static final Period WEEK = new Period(7 * 24 * 60 * 60 * 1000L, "w");
    public static final Period MONTH = new Period(30L * DAY.millis, "M");
    public static final Period QUARTER = new Period(3L * MONTH.millis, "M");
    public static final Period YEAR = new Period(365L * DAY.millis, "y");

    private long millis;
    private String name;

    private Period(long millis, String name) {
        this.millis = millis;
        this.name = name;
        instances.add(this);
    }

    public long getMillis() {
        return millis;
    }

    public String getName() {
        return name;
    }

    public static List<Period> getInstances() {
        return new ArrayList<Period>(instances);
    }

    // java.lang.Object overrides --------------------------------------------------------------------------------------

    @Override
    public String toString() {
        return name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;
        return millis == ((Period) o).millis;
    }

    @Override
    public int hashCode() {
        return (int) (millis ^ (millis >>> 32));
    }

    // Comparable interface --------------------------------------------------------------------------------------------

    public int compareTo(Period that) {
        if (this.millis > that.millis)
            return 1;
        else if (this.millis < that.millis)
            return -1;
        else
            return 0;
    }

    public static Period minInstance() {
        return instances.first();
    }

    public static Period maxInstance() {
        return instances.last();
    }
    
}
