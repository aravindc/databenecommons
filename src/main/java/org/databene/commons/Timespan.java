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

import java.util.Date;
import java.text.SimpleDateFormat;

/**
 * Represents a timespan between two dates.
 * Created: 17.02.2005 20:59:22
 */
public class Timespan {

    private static SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yyyy HH:mm:ss");

    public Date startDate;
    public Date endDate;

    public Timespan(Date startDate, Date endDate) {
        this.startDate = startDate;
        this.endDate = endDate;
    }

    public boolean overlaps(Timespan that) {
        return (intersection(this, that) != null);
    }

    public Long duration() {
        if (endDate != null && startDate != null)
            return new Long(endDate.getTime() - startDate.getTime());
        else
            return null;
    }

    public boolean contains(Timespan that) {
        return (!this.startDate.after(that.startDate) && !this.endDate.before(that.endDate));
    }

    public boolean contains(Date date) {
        return (this.startDate.before(date) && this.endDate.after(date));
    }

    public static Timespan intersection(Timespan span1, Timespan span2) {
        Date startDate = span1.startDate;
        if (span1.startDate == null || (span2.startDate != null && span1.startDate.before(span2.startDate)))
            startDate = span2.startDate;
        Date endDate = span1.endDate;
        if (span1.endDate == null || (span2.endDate != null && span1.endDate.after(span2.endDate)))
            endDate = span2.endDate;
        if (startDate != null && endDate != null && !endDate.after(startDate))
            return null;
        return new Timespan(startDate, endDate);
    }

    public static Timespan unite(Timespan span1, Timespan span2) {
        Date startDate = span1.startDate;
        if (startDate != null) {
            Date date2 = span2.startDate;
            if (date2 == null)
                startDate = date2;
            else if (date2.before(startDate))
                startDate = date2;
        }
        Date endDate = span1.endDate;
        if (endDate != null) {
            Date date2 = span2.endDate;
            if (date2 == null)
                endDate = date2;
            else if (date2.after(endDate))
                endDate = date2;
        }
        return new Timespan(startDate, endDate);
    }

    @Override
    public String toString() {
        if (startDate != null)
            if (endDate != null)
                return sdf.format(startDate) + " - " + sdf.format(endDate);
            else
                return "since " + sdf.format(startDate);
        else
            if (endDate != null)
                return "until " + sdf.format(endDate);
            else
                return "ever";
    }

	@Override
	public int hashCode() {
		int result = ((endDate == null) ? 0 : endDate.hashCode());
		return result * 31 + ((startDate == null) ? 0 : startDate.hashCode());
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null || getClass() != obj.getClass())
			return false;
		final Timespan that = (Timespan) obj;
		if (endDate == null) {
			if (that.endDate != null)
				return false;
		} else if (!endDate.equals(that.endDate))
			return false;
		if (startDate == null) {
			if (that.startDate != null)
				return false;
		} else 
			return (startDate.equals(that.startDate));
		return true;
	}
    
    
}
