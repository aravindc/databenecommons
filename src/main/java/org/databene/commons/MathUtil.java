/*
 * Copyright (C) 2004-2014 Volker Bergmann (volker.bergmann@bergmann-it.de).
 * All rights reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * http://www.apache.org/licenses/LICENSE-2.0
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
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
    
    public static boolean isIntegralValue(double number) {
    	return (Math.IEEEremainder(Math.abs(number), 1) == 0);
    }

    private static double fraction(double number) {
        double value = Math.IEEEremainder(Math.abs(number), 1);
        if (value < 0)
            value += 1;
        return value;
    }
    
    public static int prefixDigitCount(double number) {
        return nonNegativeDigitCount((long) Math.abs(number));
    }

    public static int digitCount(long number) {
        return nonNegativeDigitCount(Math.abs(number));
    }

	private static int nonNegativeDigitCount(long number) {
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
    
    public static double max(double... args) {
        double result = args[0];
        for (int i = 1; i < args.length; i++)
            if (args[i] > result)
                result = args[i];
        return result;
    }
    
    public static double min(double... args) {
        double result = args[0];
        for (int i = 1; i < args.length; i++)
            if (args[i] < result)
                result = args[i];
        return result;
    }
    
}
