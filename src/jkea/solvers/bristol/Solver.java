package jkea.solvers.bristol;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.TreeSet;

import jkea.solvers.bristol.data.Knapsack;

class Solver {

	private Knapsack container;
	private int[][] scores;
	private int rows;
	private int chunks;
	private short[] key;
	private Integer[] capacitys;

	Solver(int[][] data, short[] key) {
		container = new Knapsack(data);
		scores = data;
		rows = container.getRows();
		chunks = container.getChunks();
		capacitys = calculateCapacitys();
		this.key = key;
	}

	private Integer[] calculateCapacitys() {
		int[][] sortedScores = container.getSortedScores();	
		TreeSet<Integer> capacs = new TreeSet<Integer>();
		
		for(int k = 0; k < chunks; k++) {
			int[] depthCurrent = new int[chunks];
			for (int kk = 0; kk < chunks; kk++)
				depthCurrent[kk] = 0;
			for(int i = k; i < chunks; i++) {
				for(int j = 0; j < rows - 1; j++) {
					depthCurrent[i] += 1;
					capacs.add(container.calculateCapacity(depthCurrent));
				}
			}
		}
		
		return capacs.toArray(new Integer[capacs.size()]);
	}
}
