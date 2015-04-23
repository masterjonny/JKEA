package jkea.simulation;

import static org.junit.Assert.*;
import jkea.simulation.HammingTemplate;

import org.junit.Before;
import org.junit.Test;

public class HammingTemplateTest {

	HammingTemplate testClass;

	@Before
	public void setUp() throws Exception {
		testClass = new HammingTemplate(1.0);
	}

	@Test
	public void adjustVarianceTest() {
		double stdDevSolutions[] = { 1, 1.41421, 1.73205, 2, 2.23607, 2.44949,
				2.64575, 2.82843, 3 };
		double cNormSolutions[] = { 0.398942, 0.282095, 0.230329, 0.199471,
				0.178412, 0.162868, 0.150786, 0.141047, 0.132981 };
		for (int i = 1; i < 10; i++) {
			HammingTemplate newClass = new HammingTemplate(i);
			assertEquals(stdDevSolutions[i - 1], newClass.getStdDeviation(),
					1e-5);
			assertEquals(cNormSolutions[i - 1], newClass.getcNorm(), 1e-5);
		}
	}

	@Test
	public void hammingWeightTest() {
		short solutions[] = { 0, 1, 1, 2, 1, 2, 2, 3, 1, 2, 2, 3, 2, 3, 3, 4,
				1, 2, 2, 3, 2, 3, 3, 4, 2, 3, 3, 4, 3, 4, 4, 5, 1, 2, 2, 3, 2,
				3, 3, 4, 2, 3, 3, 4, 3, 4, 4, 5, 2, 3, 3, 4, 3, 4, 4, 5, 3, 4,
				4, 5, 4, 5, 5, 6, 1, 2, 2, 3, 2, 3, 3, 4, 2, 3, 3, 4, 3, 4, 4,
				5, 2, 3, 3, 4, 3, 4, 4, 5, 3, 4, 4, 5, 4, 5, 5, 6, 2, 3, 3, 4,
				3, 4, 4, 5, 3, 4, 4, 5, 4, 5, 5, 6, 3, 4, 4, 5, 4, 5, 5, 6, 4,
				5, 5, 6, 5, 6, 6, 7, 1, 2, 2, 3, 2, 3, 3, 4, 2, 3, 3, 4, 3, 4,
				4, 5, 2, 3, 3, 4, 3, 4, 4, 5, 3, 4, 4, 5, 4, 5, 5, 6, 2, 3, 3,
				4, 3, 4, 4, 5, 3, 4, 4, 5, 4, 5, 5, 6, 3, 4, 4, 5, 4, 5, 5, 6,
				4, 5, 5, 6, 5, 6, 6, 7, 2, 3, 3, 4, 3, 4, 4, 5, 3, 4, 4, 5, 4,
				5, 5, 6, 3, 4, 4, 5, 4, 5, 5, 6, 4, 5, 5, 6, 5, 6, 6, 7, 3, 4,
				4, 5, 4, 5, 5, 6, 4, 5, 5, 6, 5, 6, 6, 7, 4, 5, 5, 6, 5, 6, 6,
				7, 5, 6, 6, 7, 6, 7, 7, 8 };
		for (short i = 0; i < 256; i++) {
			assertEquals(solutions[i], testClass.hammingWeight(i));
		}
	}

	public void leakDensityTest() {
		double solutions[] = { 0.282095, 0.0297326, 0.0297326, 0.00516675,
				0.0297326, 3.48133e-05, 0.277721, 0.0425899, 0.0425899,
				0.00838646, 0.0425899, 7.25571e-05, 0.265004, 0.0591303,
				0.0591303, 0.0131937, 0.0591303, 0.000146569, 0.245088,
				0.0795685, 0.0795685, 0.0201181, 0.0795685, 0.000286969,
				0.219696, 0.103777, 0.103777, 0.0297326, 0.103777, 0.000544571 };
		int count = 0;
		for (double i = 0; i <= 1; i += 0.25) {
			for(short j = 0; j < 256; j += 25) {
				count ++;
				assertEquals(solutions[count], testClass.leakDensity(i, j), 1e-5);
			}
		}
	}
	
	public void leakTest() {
		double solutions[] = { 2.41421, 4.41421, 7.41421, 5.41421,  6.41421 }; 
		short inputs[] = { 1, 100, 125, 57, 227 };
		for(int i = 0; i < 5; i++) {
			assertEquals(solutions[i], testClass.leak(inputs[i]), 1 + 1e-5);
		}
	}

	@Test(expected = IllegalArgumentException.class)
	public void vairanceLower() {
		new HammingTemplate(0);
	}

	@Test(expected = IllegalArgumentException.class)
	public void varianceLowerTwo() {
		new HammingTemplate(-1);
	}

	@Test(expected = IllegalArgumentException.class)
	public void hammingWeightBoundsLower() {
		testClass.hammingWeight((short) -1);
	}

	@Test(expected = IllegalArgumentException.class)
	public void hammingWeightBoundsUpper() {
		testClass.hammingWeight((short) 256);
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void leakBoundsLower() {
		testClass.leak((short) -1);
	}

	@Test(expected = IllegalArgumentException.class)
	public void leakBoundsUpper() {
		testClass.leak((short) 256);
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void leakDensityBoundsLower() {
		testClass.leakDensity(-0.5, (short)128);
	}

	@Test(expected = IllegalArgumentException.class)
	public void leakDenstiyBoundsUpper() {
		testClass.leakDensity(1.5, (short)128);
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void leakDensityBoundsLowerTwo() {
		testClass.leakDensity(0.5, (short)-1);
	}

	@Test(expected = IllegalArgumentException.class)
	public void leakDenstiyBoundsUpperTwo() {
		testClass.leakDensity(0.5, (short)256);
	}
}
