package jkea.solvers.glowacz;

import static org.junit.Assert.*;
import jkea.solvers.glowacz.Glowacz;
import jkea.solvers.glowacz.data.Histogram;
import jkea.util.FFT;
import jkea.util.data.Complex;

import org.junit.Before;
import org.junit.Test;

public class GlowaczTest {

	Glowacz testClass;
	
	@Before
	public void setUp() throws Exception {
		final double[][] scores = new double[3][3];
		final double[] scores1 = { -7, -1, -9 };
		final double[] scores2 = { -4, -3, -3 };
		final double[] scores3 = { -6, -1, -2 };
		scores[0] = scores1;
		scores[1] = scores2;
		scores[2] = scores3;
		final short[] key = { 2, 0, 0 };
		testClass = new Glowacz(scores, key, 5000);
		
	}
	
	@Test
	public void testRun() {
		long ans = testClass.rank();
		assertEquals(6, ans);
	}

	@Test
	public void testConvolve() {
		Histogram inputA = new Histogram(5, 1);
		Histogram inputB = new Histogram(5, 1);
		
		int[] solutions = {1, 2, 3, 4, 5, 4, 3, 2, 1};
		
		for(int i = 0; i < 5; i++) {
			inputA.add(1, i);
			inputB.add(1, i);
		}
		
		Histogram answer = testClass.convolve(inputA, inputB);
		for(int i = 0; i < 9; i++)
			assertEquals(solutions[i], answer.getBin(i));
	}
	
	@Test
	public void testNewConvolve() {
		Histogram inputA = new Histogram(5, 1);
		Histogram inputB = new Histogram(5, 1);
		
		int[] solutions = {1, 2, 3, 4, 5, 4, 3, 2, 1};
		
		Complex[] x = new Complex[5];
		Complex[] y = new Complex[5];
		
		for(int i = 0; i < 5; i++) {
			inputA.add(1, i);
			inputB.add(1, i);
			x[i] = new Complex(i, 0);
			y[i] = new Complex(i, 0);
		}
		
		Histogram answer = testClass.convolve(inputA, inputB);
		Complex[] c = FFT.convolve(x, y);
		
		for(int i = 0; i < 9; i++)
			assertEquals(solutions[i], answer.getBin(i));
		
		for(int i = 0; i < 9; i++)
			assertEquals(solutions[i], c[i].re(), 1e-5);
	}

}
