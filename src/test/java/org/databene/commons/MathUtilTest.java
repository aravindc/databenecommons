package org.databene.commons;

import org.junit.Test;
import static junit.framework.Assert.*;

/**
 * Tests the {@link MathUtil} class.<br/>
 * <br/>
 * @author Volker Bergmann
 */
public class MathUtilTest {

	@Test
    public void testDigitCount() {
        assertEquals(1, MathUtil.digitCount(1));
        assertEquals(1, MathUtil.digitCount(0));
        assertEquals(1, MathUtil.digitCount(-1));
        assertEquals(2, MathUtil.digitCount(10));
        assertEquals(2, MathUtil.digitCount(-10));
        assertEquals(6, MathUtil.digitCount(999999));
        assertEquals(7, MathUtil.digitCount(1000000));
        assertEquals(8, MathUtil.digitCount(99999999));
        assertEquals(9, MathUtil.digitCount(100000000));
    }

	@Test
    public void testPrefixDigitCount() {
        assertEquals(1, MathUtil.prefixDigitCount(1));
        assertEquals(1, MathUtil.prefixDigitCount(0));
        assertEquals(1, MathUtil.prefixDigitCount(-1));
        assertEquals(1, MathUtil.prefixDigitCount(0.001));
        assertEquals(1, MathUtil.prefixDigitCount(0.1));
        assertEquals(1, MathUtil.prefixDigitCount(0.9));
        assertEquals(1, MathUtil.prefixDigitCount(9.9));
        assertEquals(2, MathUtil.prefixDigitCount(10));
        assertEquals(2, MathUtil.prefixDigitCount(-10));
        assertEquals(6, MathUtil.prefixDigitCount(999999));
        assertEquals(7, MathUtil.prefixDigitCount(1000000));
        assertEquals(8, MathUtil.prefixDigitCount(99999999));
        assertEquals(9, MathUtil.prefixDigitCount(100000000));
    }

	@Test
    public void testFractionDigits() {
        assertEquals(0, MathUtil.fractionDigits(0));
        assertEquals(0, MathUtil.fractionDigits(1));
        assertEquals(0, MathUtil.fractionDigits(-1));
        assertEquals(1, MathUtil.fractionDigits(0.5));
        assertEquals(1, MathUtil.fractionDigits(0.1));
        assertEquals(1, MathUtil.fractionDigits(-0.1));
        assertEquals(1, MathUtil.fractionDigits(0.9));
        assertEquals(7, MathUtil.fractionDigits(0.9999999));
        assertEquals(7, MathUtil.fractionDigits(0.0000001));
        assertEquals(7, MathUtil.fractionDigits(0.0000009));
    }
    
	@Test
    public void testSumOfDigits() {
    	assertEquals(0, MathUtil.sumOfDigits(0));
    	assertEquals(1, MathUtil.sumOfDigits(1));
    	assertEquals(1, MathUtil.sumOfDigits(10));
    	assertEquals(6, MathUtil.sumOfDigits(123));
    }
	
}
