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
    public void testPrefixDigits() {
        assertEquals(1, MathUtil.prefixDigits(1));
        assertEquals(1, MathUtil.prefixDigits(0));
        assertEquals(1, MathUtil.prefixDigits(-1));
        assertEquals(1, MathUtil.prefixDigits(0.001));
        assertEquals(1, MathUtil.prefixDigits(0.1));
        assertEquals(1, MathUtil.prefixDigits(0.9));
        assertEquals(1, MathUtil.prefixDigits(9.9));
        assertEquals(2, MathUtil.prefixDigits(10));
        assertEquals(2, MathUtil.prefixDigits(-10));
        assertEquals(8, MathUtil.prefixDigits(99999999));
        assertEquals(9, MathUtil.prefixDigits(100000000));
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
