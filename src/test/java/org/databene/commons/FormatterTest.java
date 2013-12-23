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

import static org.junit.Assert.*;

import java.util.Locale;

import org.junit.Test;

/**
 * Tests the Formatter.<br/><br/>
 * Created: 23.12.2013 09:07:11
 * @since 0.5.25
 * @author Volker Bergmann
 */

public class FormatterTest {
	
	@Test
	public void test_DE() {
		LocaleUtil.runInLocale(Locale.GERMAN, new Runnable() {
			@Override
			public void run() {
				assertEquals("morgen", Formatter.formatDaysFromNow(TimeUtil.tomorrow()));
				assertEquals("gestern", Formatter.formatDaysFromNow(TimeUtil.yesterday()));
				assertEquals("heute", Formatter.formatDaysFromNow(TimeUtil.today()));
				assertEquals("übermorgen", Formatter.formatDaysFromNow(TimeUtil.addDays(TimeUtil.today(), 2)));
				assertEquals("vorgestern", Formatter.formatDaysFromNow(TimeUtil.addDays(TimeUtil.today(), -2)));
				assertEquals("vor 3 Tagen", Formatter.formatDaysFromNow(TimeUtil.addDays(TimeUtil.today(), -3)));
				assertEquals("in 3 Tagen", Formatter.formatDaysFromNow(TimeUtil.addDays(TimeUtil.today(), 3)));
			}
		});
	}
	
	@Test
	public void test_EN() {
		LocaleUtil.runInLocale(Locale.ENGLISH, new Runnable() {
			@Override
			public void run() {
				assertEquals("tomorrow", Formatter.formatDaysFromNow(TimeUtil.tomorrow()));
				assertEquals("yesterday", Formatter.formatDaysFromNow(TimeUtil.yesterday()));
				assertEquals("today", Formatter.formatDaysFromNow(TimeUtil.today()));
				assertEquals("the day after tomorrow", Formatter.formatDaysFromNow(TimeUtil.addDays(TimeUtil.today(), 2)));
				assertEquals("the day before yesterday", Formatter.formatDaysFromNow(TimeUtil.addDays(TimeUtil.today(), -2)));
				assertEquals("3 days ago", Formatter.formatDaysFromNow(TimeUtil.addDays(TimeUtil.today(), -3)));
				assertEquals("in 3 days", Formatter.formatDaysFromNow(TimeUtil.addDays(TimeUtil.today(), 3)));
			}
		});
	}
	
}
