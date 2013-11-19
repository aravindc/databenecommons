/*
 * (c) Copyright 2013 by Volker Bergmann. All rights reserved.
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

package org.databene.commons;

import java.io.Closeable;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicLong;

/**
 * Generates consecutive sequence values and persists sequence state in a properties file.<br/><br/>
 * Created: 09.11.2013 09:04:51
 * @since 0.5.25
 * @author Volker Bergmann
 */

public class LocalSequenceProvider implements Closeable {
	
	private static Map<String, LocalSequenceProvider> INSTANCES = new HashMap<String, LocalSequenceProvider>();
	
	public static LocalSequenceProvider getInstance(String filename) {
		LocalSequenceProvider result = INSTANCES.get(filename);
		if (result == null) {
			result = new LocalSequenceProvider(filename);
			INSTANCES.put(filename, result);
		}
		return result;
	}
	

	private final String fileName;

	private boolean cached;

	private final Map<String, AtomicLong> counters;

	// Initialization --------------------------------------------------------------------------------------------------

	private LocalSequenceProvider(String fileName) {
		this(fileName, true);
	}

	private LocalSequenceProvider(String fileName, boolean cached) {
		this.fileName = fileName;
		this.cached = cached;
		this.counters = new HashMap<String, AtomicLong>();
		load();
	}

	// Properties ------------------------------------------------------------------------------------------------------

	public boolean isCached() {
		return cached;
	}

	public void setCached(boolean cached) {
		this.cached = cached;
	}
	
	
	// interface -------------------------------------------------------------------------------------------------------

	public long next(String sequenceName) {
		long result = getOrCreateCounter(sequenceName).incrementAndGet();
		if (!cached)
			save();
		return result;
	}

	@Override
	public void close() {
		save();
	}
	
	
	// static methods --------------------------------------------------------------------------------------------------

	public void save() {
		Map<String, String> values = new HashMap<String, String>();
		for (Map.Entry<String, AtomicLong> entry : counters.entrySet())
			values.put(entry.getKey(), String.valueOf(entry.getValue().get()));
		try {
			IOUtil.writeProperties(values, fileName);
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}
	
	
	// private helpers -------------------------------------------------------------------------------------------------

	private void load() {
		if (IOUtil.isURIAvailable(fileName)) {
			try {
				Map<String, String> values = IOUtil.readProperties(fileName);
				for (Map.Entry<String, String> entry : values.entrySet())
					counters.put(entry.getKey(), new AtomicLong(Long.parseLong(entry.getValue())));
			} catch (Exception e) {
				throw new ConfigurationError(e);
			}
		}
	}

	private AtomicLong getOrCreateCounter(String name) {
		AtomicLong counter = counters.get(name);
		if (counter == null) {
			counter = new AtomicLong();
			counters.put(name, counter);
		}
		return counter;
	}

}
