/*
 * (c) Copyright 2011-2014 by Volker Bergmann. All rights reserved.
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

package org.databene.commons.debug;

import org.databene.commons.SystemInfo;

/**
 * Wrapper class for resources that are monitored.
 * It stores the monitored object itself and its allocation stack trace.<br/><br/>
 * Created: 14.04.2011 17:22:56
 * @since 0.5.8
 * @author Volker Bergmann
 */
public class MonitoredResource {

	final Object resource;
	final StackTraceElement[] registrationTrace;

	public MonitoredResource(Object resource, StackTraceElement[] registrationTrace) {
		this.resource = resource;
		this.registrationTrace = registrationTrace;
	}

	private static String toString(StackTraceElement[] trace) {
		StringBuilder builder = new StringBuilder();
		boolean first = true;
		for (StackTraceElement element : trace) {
			appendTraceElement(element, first, builder);
			first = false;
		}
		return builder.toString();
	}
	
	private static void appendTraceElement(StackTraceElement element, boolean first, StringBuilder builder) {
		builder.append(SystemInfo.getLineSeparator());
		builder.append("\t");
		if (!first)
			builder.append("at ");
		builder.append(element.getClassName()).append('.').append(element.getMethodName());
		if (element.getLineNumber() > 0)
			builder.append(" (Line ").append(element.getLineNumber()).append(")");
	}

	@Override
	public String toString() {
		return "Monitored Object: " + resource.toString() + SystemInfo.getLineSeparator() + 
		"Registration stack:" +  
		toString(registrationTrace);
	}

}
