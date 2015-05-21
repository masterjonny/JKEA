package jkea.solvers;

import java.util.ArrayList;

import jkea.solvers.veyrat.TopEnum;

public class VeyratSolver extends GenericSolver implements SolverInterface {

	public VeyratSolver(double[][] scores, short[] key) {
		super(scores, key);
	}
	
	public VeyratSolver(int[][] scores, short[] key) {
		super(scores, key);
	}

	@Override
	public long enumerate() {
		final TopEnum e = new TopEnum(scores);
		boolean found = false;
		long rank = 0;
		while (!found) {
			e.next();
			final ArrayList<Short> next = e.getKey();
			found = true;
			for (int i = 0; i < next.size(); i++)
				if (next.get(i) != key[i]) {
					found = false;
					break;
				}
			rank++;
		}
		return rank;

	}

	@Override
	public long enumerateParallel(int nProcessors) {
		throw new UnsupportedOperationException("This solver does not support parallel enumeration");
	}

	@Override
	public long rank() {
		throw new UnsupportedOperationException("This solver does not support key ranking");
	}
}
