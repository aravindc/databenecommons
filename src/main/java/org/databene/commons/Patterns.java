/*
 * (c) Copyright 2009 by Volker Bergmann. All rights reserved.
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

/**
 * Assembles default patterns for all databene formats and {@link Converter}s.<br/>
 * <br/>
 * Created at 01.10.2009 12:36:09
 * @since 0.5.0
 * @author Volker Bergmann
 */

public interface Patterns {

	public static final String DEFAULT_NULL_STRING = "";

	public static final String DEFAULT_DATE_PATTERN = "yyyy-MM-dd";

	// time patterns ---------------------------------------------------------------------------------------------------
	
	public static final String DEFAULT_TIME_MILLIS_PATTERN = "HH:mm:ss.SSS";

	public static final String DEFAULT_TIME_SECONDS_PATTERN = "HH:mm:ss";

	public static final String DEFAULT_TIME_MINUTES_PATTERN = "HH:mm";

	public static final String DEFAULT_TIME_PATTERN = DEFAULT_TIME_SECONDS_PATTERN;

	// datetime patterns -----------------------------------------------------------------------------------------------
	
	public static final String DEFAULT_DATETIME_MINUTES_PATTERN = "yyyy-MM-dd'T'HH:mm";
    
	public static final String DEFAULT_DATETIME_SECONDS_PATTERN = "yyyy-MM-dd'T'HH:mm:ss";
    
	public static final String DEFAULT_DATETIME_MILLIS_PATTERN = "yyyy-MM-dd'T'HH:mm:ss.SSS";
    
	public static final String DEFAULT_DATETIME_MICROS_PATTERN = "yyyy-MM-dd'T'HH:mm:ss.SSSSSS";
    
	public static final String DEFAULT_DATETIME_NANOS_PATTERN = "yyyy-MM-dd'T'HH:mm:ss.SSSSSSSSS";
    
	public static final String DEFAULT_DATETIME_PATTERN = DEFAULT_DATETIME_SECONDS_PATTERN;

	// timestamp patterns ----------------------------------------------------------------------------------------------
	
	public static final String DEFAULT_TIMESTAMP_PATTERN = DEFAULT_DATETIME_NANOS_PATTERN;

}
