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

package org.databene.commons;

import java.util.HashSet;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Logs escalations to a logger.
 * @since 0.2.04
 * @author Volker Bergmann
 */
public class LoggerEscalator implements Escalator {
    
    private Set<Escalation> escalations;
    
    public LoggerEscalator() {
        this.escalations = new HashSet<Escalation>();
    }
    
    @Override
	public void escalate(String message, Object originator, Object cause) {
        // determine logger by the originator
        Class<?> category = null;
        if (originator != null)
            if (originator instanceof Class)
                category = (Class<?>) originator;
            else
                category = originator.getClass();
        else
            originator = this.getClass();
        Logger logger = LoggerFactory.getLogger(category);
        // create escalation
        Escalation escalation = new Escalation(message, originator, cause);
        // if the escalation is new, send it
        if (!escalations.contains(escalation)) {
            escalations.add(escalation);
            if (cause instanceof Throwable)
                logger.warn(escalation.toString(), (Throwable) cause);
            else
                logger.warn(escalation.toString());
        }
    }
}
