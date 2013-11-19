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

package org.databene.commons.tag;

import java.util.Set;

import org.databene.commons.Tagged;

/**
 * Parent class which provides tag support by inheritance.<br/><br/>
 * Created: 14.11.2013 07:04:54
 * @since 0.5.25
 * @author Volker Bergmann
 */

public class AbstractTagged implements Tagged {
	
	protected TagSupport tags;
	
	protected AbstractTagged() {
		this.tags = new TagSupport();
	}

	@Override
	public Set<String> getTags() {
		return this.tags.getTags();
	}

	@Override
	public boolean hasTag(String tag) {
		return this.tags.hasTag(tag);
	}

	@Override
	public void addTag(String tag) {
		this.tags.addTag(tag);
	}

	@Override
	public void removeTag(String tag) {
		this.tags.removeTag(tag);
	}

}
