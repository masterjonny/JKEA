package jkea.solvers.bristol;

import java.util.ArrayList;
import java.util.TreeSet;

import jkea.solvers.bristol.data.Knapsack;
import jkea.solvers.bristol.data.WorkBlock;

public class KEA {

	private final Integer[] capacitys;
	private final int chunks;
	private final Knapsack container;
	private final short[] key;
	private final int rows;
	private final int[][] scores;

	public KEA(int[][] data, short[] key) {
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

	int oneChild(WorkBlock w, int i) {
		final int capacity = w.getCapacity();
		final int fail = w.getFail();
		final int accept = w.getAccept();
		final int wMin = w.getwMin();

		if (i == accept)
			return accept;
		if (i == fail)
			return fail;

		final short row = (short) (i % rows);
		final int offset = ((i - row) % (rows * capacity)) / rows;
		final short col = (short) ((i - row - (offset * rows)) / (rows * capacity));
		if ((offset + scores[col][row]) >= capacity)
			return fail;
		else if (col == (chunks - 1)) {
			if ((offset + scores[col][row]) >= wMin)
				return accept;
			else
				return fail;
		} else
			return (rows * (offset + scores[col][row]))
					+ ((col + 1) * capacity * rows);
	}

	private int pathCount(WorkBlock w, int index) {
		final int length = w.getLength();
		final ArrayList<Integer> localCount = new ArrayList<Integer>(length);
		for (int i = 0; i < length; i++)
			localCount.add(0);
		localCount.set(index, 1);

		for (int i = index - 1; i > -1; i--)
			localCount.set(
					i,
					localCount.get(zeroChild(w, i))
					+ localCount.get(oneChild(w, i)));
		return localCount.get(0);
	}

	void pathCountLoop(WorkBlock w) {
		final int capacity = w.getCapacity();
		final int length = w.getLength();
		final ArrayList<Integer> paths = new ArrayList<Integer>(length);
		for (int i = 0; i < length; i++)
			paths.add(0);
		paths.set(0, 1);

		for (int i = 0; i < chunks; i++)
			for (int j = 0; j < capacity; j++) {
				final int location = (i * rows * capacity) + (j * rows);
				if (paths.get(location) == 1)
					for (int k = 0; k < rows; k++) {
						paths.set(location + k, 1);
						paths.set(oneChild(w, location + k), 1);
					}
			}
		paths.set(length - 1, pathCount(w, length - 1));
		w.setPaths(paths);
	}

	public void run() {

		for (final Integer capacity : capacitys) {

		}
	}

	int zeroChild(WorkBlock w, int i) {
		final int fail = w.getFail();
		if (i == fail)
			return fail;
		final short row = (short) (i % rows);
		if (row == (rows - 1))
			return fail;
		else
			return i + 1;
	}
}
