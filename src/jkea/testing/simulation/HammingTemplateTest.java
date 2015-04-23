package jkea.testing.simulation;

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
		for (int i = 1; i < 10; ++i) {
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
}
