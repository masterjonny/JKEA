package jkea.solvers.bristol;

import java.util.TreeSet;

import jkea.solvers.bristol.data.Knapsack;

class Solver {

	private final Integer[] capacitys;
	private final int chunks;
	private final Knapsack container;
	private final short[] key;
	private final int rows;
	private final int[][] scores;

	Solver(int[][] data, short[] key) {
		container = new Knapsack(data);
		scores = data;
		rows = container.getRows();
		chunks = container.getChunks();
		capacitys = calculateCapacitys();
		this.key = key;
	}

	private Integer[] calculateCapacitys() {
		final TreeSet<Integer> capacs = new TreeSet<Integer>();

		for (int k = 0; k < chunks; k++) {
			final int[] depthCurrent = new int[chunks];
			for (int kk = 0; kk < chunks; kk++)
				depthCurrent[kk] = 0;
			for (int i = k; i < chunks; i++)
				for (int j = 0; j < (rows - 1); j++) {
					depthCurrent[i] += 1;
					capacs.add(container.calculateCapacity(depthCurrent));
				}
		}

		return capacs.toArray(new Integer[capacs.size()]);
	}
}
