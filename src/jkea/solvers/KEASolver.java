package jkea.solvers;

import jkea.solvers.bristol.KEA;

public class KEASolver extends GenericSolver implements SolverInterface {

	KEASolver(double[][] scores, short[] key) {
		super(scores, key);
	}

	@Override
	public long solve() {
		int[][] intScores = new int[scores.length][scores[0].length];
		for(int i = 0; i < scores.length; ++i) {
			for(int j = 0; j < scores[0].length; ++j) {
				intScores[i][j] = (int) Math.abs(scores[i][j]);
			}
		}
		
		KEA k = new KEA(intScores, key);
		
		return 0;
	}

}