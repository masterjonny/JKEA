package jkea.solvers;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

public class VeyratSolverTest {

	VeyratSolver testClass;
	
	@Before
	public void setUp() throws Exception {
		double[][] scores = new double[3][3];
		double[] scores1 = { 7, 1, 9 };
		double[] scores2 = { 4, 3, 3 };
		double[] scores3 = { 6, 1, 2 };
		scores[0] = scores1;
		scores[1] = scores2;
		scores[2] = scores3;
		short[] key = {1, 0, 2};
		testClass = new VeyratSolver(scores, key);
		
	}

	@Test
	public void solveTest() {
		testClass.solve();
	}
	
}
