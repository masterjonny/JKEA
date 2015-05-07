package jkea.solvers;

import jkea.solvers.bristol.KEA;

public class KEASolver extends GenericSolver implements SolverInterface {
	
	public KEASolver(double[][] scores, short[] key) {
		super(scores, key);
	}
	
	public KEASolver(int[][] scores, short[] key) {
		super(scores, key);
	}

	private int[][] keaInit() {
		final int[][] intScores = new int[scores.length][scores[0].length];
		for (int i = 0; i < scores.length; ++i)
			for (int j = 0; j < scores[0].length; ++j)
				intScores[i][j] = (int) Math.abs(scores[i][j]);
		return intScores;
	}
	
	@Override
	public long solve() {	
		final KEA k = new KEA(keaInit(), key);
		return k.runEnumerate();
	}
	
	public long keyRank() {
		final KEA k = new KEA(keaInit(), key);
		return k.keyRank();
	}

}
