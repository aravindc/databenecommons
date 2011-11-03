/*
 * (c) Copyright 2011 by Volker Bergmann. All rights reserved.
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

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Class that monitors resources as a collection of {@link MonitoredResource}s.
 * Resources are registered and unregistered with {@link #register(Object)} and
 * {@link #unregister(Object)} and in the end you can assert that all resources 
 * have been unregistered by calling {@link #assertNoRegistrations(boolean)}.<br/>
 * <br/>
 * Created: 14.04.2011 17:16:20
 * @since 0.5.8
 * @author Volker Bergmann
 */
public class ResourceMonitor {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(ResourceMonitor.class);
	
	private List<MonitoredResource> registrations;
	
	public ResourceMonitor() {
		registrations = new ArrayList<MonitoredResource>();
	}

	public void register(Object object) {
		Throwable t = new Throwable();
		registrations.add(new MonitoredResource(object, t.getStackTrace()));
	}
	
	public void unregister(Object object) {
		for (int i = registrations.size() - 1; i >= 0; i--) {
			MonitoredResource candidate = registrations.get(i);
			if (candidate.resource == object) {
				registrations.remove(i);
				return;
			}
		}
		throw new IllegalStateException("Object '" + object + "' was not registered");
	}
	
	public List<MonitoredResource> getRegistrations() {
		return registrations;
	}
	
	public void reset() {
		this.registrations.clear();
	}
	
	public boolean assertNoRegistrations(boolean critical) {
		if (registrations.size() == 0)
			return true;
		String message = "There are resources which have not been unregistered:";
		LOGGER.warn(message);
		logRegistrations();
		if (critical)
			throw new IllegalStateException(message);
		return false;
	}

	private void logRegistrations() {
		for (MonitoredResource resource : registrations) {
			LOGGER.warn(resource.toString());
		}
	}

}
