package jkea.solvers;

import jkea.solvers.bristol.KEA;
import jkea.solvers.bristol.ParallelKEA;

public class KEASolver extends GenericSolver implements SolverInterface {

	private int capacity;
	private double precisionFinal;
	private double setPrecision;

	public KEASolver(double[][] scores, short[] key) {
		super(scores, key);
	}

	public KEASolver(int[][] scores, short[] key) {
		super(scores, key);
	}

	public int getCapacity() {
		return capacity;
	}

	public double getPrecision() {
		return precisionFinal;
	}
	
	public void setPrecision(double setPrecision) {
		this.setPrecision = setPrecision;
	}

	private int[][] keaInit() {
		int globalMax = 0;
		final int[][] intScores = new int[scores.length][scores[0].length];
		for (int i = 0; i < scores.length; ++i)
			for (int j = 0; j < scores[0].length; ++j) {
				intScores[i][j] = (int) Math.abs(scores[i][j] * Math.pow(2, setPrecision));
				globalMax = Math.max(globalMax, intScores[i][j]);
			}
		precisionFinal = Math.log(globalMax) / Math.log(2);
		return intScores;

	}

	public long rank() {
		final KEA k = new KEA(keaInit(), key);
		final long result = k.keyRank();
		capacity = k.getGraphWidth();
		return result;
	}

	@Override
	public long enumerate() {
		final KEA k = new KEA(keaInit(), key);
		return k.runEnumerate();
	}

	@Override
	public long enumerateParallel(int nProcessors) {
		final ParallelKEA k = new ParallelKEA(keaInit(), key);
		return k.runEnumerate(nProcessors);
	}
	

}
