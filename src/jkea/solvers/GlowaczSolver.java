package jkea.solvers;

import jkea.solvers.glowacz.Glowacz;

public class GlowaczSolver extends GenericSolver implements SolverInterface {

	public GlowaczSolver(double[][] scores, short[] key) {
		super(scores, key);
	}
	
	GlowaczSolver(int[][] scores, short[] key) {
		super(scores, key);
	}

	@Override
	public long enumerate() {
		throw new UnsupportedOperationException("This solver does not support enumeration");
	}

	@Override
	public long enumerateParallel(int nProcessors) {
		throw new UnsupportedOperationException("This solver does not support parallel enumeration");
	}

	@Override
	public long rank() {
		Glowacz fs = new Glowacz(scores, key, 50000);
		return fs.rank();
	}

}
