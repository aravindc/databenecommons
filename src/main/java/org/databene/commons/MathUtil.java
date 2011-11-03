/*
 * (c) Copyright 2008-2010 by Volker Bergmann. All rights reserved.
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
 * Provides mathematical utility methods.
 * @since 0.4.0
 * @author Volker Bergmann
 */
public class MathUtil {
    
    /**
     * Returns the number of digits needed for displaying the postfix values of a number, but at most 7.
     * @param number
     * @return the number of digits needed for displaying the postfix values of a number, but at most 7
     */
    public static int fractionDigits(double number) {
        double x = fraction(number);
        int n = 0;
        while (x >= 0.0000001 && n < 7) {
            n++;
            x = fraction(x * 10);
        }
        return n;
    }

    private static double fraction(double number) {
        double value = Math.IEEEremainder(Math.abs(number), 1);
        if (value < 0)
            value += 1;
        return value;
    }
    
    public static int prefixDigits(double number) {
        number = Math.abs(number);
        if (number <= 1)
            return 1;
        return 1 + (int) Math.log10(number);
    }

	public static int sumOfDigits(int i) {
		int tmp = i;
		int result = 0;
		while (tmp > 0) {
			result += tmp % 10;
			tmp /= 10;
		}
		return result;
	}
	
	public static int weightedSumOfSumOfDigits(String number, int startIndex, int... weights) {
	    int sum = 0;
	    for (int i = 0; i < weights.length; i++)
	    	sum += MathUtil.sumOfDigits(weights[i] * (number.charAt(startIndex + i) - '0'));
	    return sum;
    }

	public static int weightedSumOfDigits(CharSequence number, int startIndex, int... weights) {
	    int sum = 0;
	    for (int i = 0; i < weights.length; i++)
	    	sum += weights[i] * (number.charAt(startIndex + i) - '0');
	    return sum;
    }

	public static boolean rangeIncludes(long x, long min, long max) {
	    return (min <= x && x <= max);
    }
	
	public static boolean rangeIncludes(double x, double min, double max) {
	    return (min <= x && x <= max);
    }
	
	public static boolean between(long x, long min, long max) {
	    return (min < x && x < max);
    }
	
	public static boolean between(double x, double min, double max) {
	    return (min < x && x < max);
    }

	public static Double sum(double[] addends) {
		double result = 0;
		for (double addend : addends)
			result += addend;
	    return result;
    }
	
    public static int max(int... args) {
        int result = args[0];
        for (int i = 1; i < args.length; i++)
            if (args[i] > result)
                result = args[i];
        return result;
    }
    
}
