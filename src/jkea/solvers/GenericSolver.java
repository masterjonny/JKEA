package jkea.solvers;

public abstract class GenericSolver implements SolverInterface {

	protected final short[] key;
	protected final double[][] scores;

	GenericSolver(double[][] scores, short[] key) {
		this.scores = scores;
		this.key = key;
	}

}
