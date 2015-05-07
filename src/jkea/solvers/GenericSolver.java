package jkea.solvers;

public abstract class GenericSolver implements SolverInterface {

	protected final short[] key;
	protected final double[][] scores;

	GenericSolver(double[][] scores, short[] key) {
		this.scores = scores;
		this.key = key;
	}
	
	GenericSolver(int[][] scores, short[] key) {
		this.scores = new double[scores.length][scores[0].length];
		for(int i = 0; i < scores.length; i++)
			for(int j = 0; j < scores[0].length; j++)
				this.scores[i][j] = (double)scores[i][j];
		this.key = key;
	}

}
