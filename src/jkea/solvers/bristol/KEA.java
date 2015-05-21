package jkea.solvers.bristol;

import java.util.ArrayList;
import java.util.TreeSet;

import jkea.solvers.bristol.data.KeyLeaf;
import jkea.solvers.bristol.data.Knapsack;
import jkea.solvers.bristol.data.WorkBlock;

public class KEA {

	protected final Integer[] capacitys;
	protected final int chunks;
	private final Knapsack container;
	protected final short[] key;
	protected final int rows;
	private final int[][] scores;
	private int graphWidth;

	public int getGraphWidth() {
		return graphWidth;
	}

	public KEA(int[][] data, short[] key) {
		container = new Knapsack(data);
		scores = data;
		rows = container.getRows();
		chunks = container.getChunks();
		capacitys = calculateCapacitys();
		this.key = key;
	}

	public boolean buildKeys(ArrayList<KeyLeaf> keys, ArrayList<Short> initKey,
			short[] realKey, int shiftToo, int keyLength) {
		for (int i = shiftToo; i < (keyLength - 1); i++)
			initKey.set(i, initKey.get(i + 1));
		boolean flag = false;
		for (final KeyLeaf keysLocal : keys)
			if (keysLocal.hasList()) {
				final ArrayList<Short> newSoFar = new ArrayList<Short>(initKey);
				newSoFar.set(realKey.length - 1, keysLocal.getKeyChunk());
				flag = flag
						| buildKeys(keysLocal.getKeyList(), newSoFar, realKey,
								shiftToo - 1, keyLength);
			} else {
				initKey.set(realKey.length - 1, keysLocal.getKeyChunk());
				for (int j = 0, jj = keyLength; j < jj; j++) {
					if (initKey.get(j) != realKey[j])
						break;
					if (j == (jj - 1))
						flag = true;
				}
			}
		return flag;
	}

	private Integer[] calculateCapacitys() {
		container.sortScore();
		final TreeSet<Integer> capacs = new TreeSet<Integer>();

		for (int k = 0; k < chunks; k++) {
			final int[] depthCurrent = new int[chunks];
			for (int kk = 0; kk < chunks; kk++)
				depthCurrent[kk] = 0;
			for (int i = k; i < chunks; i++)
				for (int j = 0; j < (rows - 1); j++) {
					depthCurrent[i] += 1;
					capacs.add(container
							.calculateCapacityWithoutSort(depthCurrent));
				}
		}
		container.sortKeyChunk();
		return capacs.toArray(new Integer[capacs.size()]);
	}

	public long keyRank() {
		final WorkBlock w = new WorkBlock();
		graphWidth = container.calculateCapacityWithoutSort(key);
		w.setCapacity(graphWidth);
		w.setLength(((rows * w.getCapacity()) * chunks) + 2);
		w.setFail(w.getLength() - 2);
		w.setAccept(w.getLength() - 1);
		pathCountLoop(w);
		return w.getPaths().get(w.getAccept());
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

		final int row = i % rows;
		final int offset = ((i - row) % (rows * capacity)) / rows;
		final int col = (i - row - (offset * rows)) / (rows * capacity);
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

	private long pathCount(WorkBlock w, int index) {
		final int length = w.getLength();
		final ArrayList<Long> localCount = new ArrayList<Long>(length);
		for (int i = 0; i < length; i++)
			localCount.add((long) 0);
		localCount.set(index, (long) 1);

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
		final ArrayList<Long> paths = new ArrayList<Long>(length);
		for (int i = 0; i < length; i++)
			paths.add((long) 0);
		paths.set(0, (long) 1);

		for (int i = 0; i < chunks; i++)
			for (int j = 0; j < capacity; j++) {
				final int location = (i * rows * capacity) + (j * rows);
				if (paths.get(location) == 1)
					for (int k = 0; k < rows; k++) {
						paths.set(location + k, (long) 1);
						paths.set(oneChild(w, location + k), (long) 1);
					}
			}
		paths.set(length - 1, (long) pathCount(w, length - 1));
		w.setPaths(paths);
	}

	ArrayList<KeyLeaf> pathEnumerate(WorkBlock w) {
		final int capacity = w.getCapacity();
		final int lengthCapacity = rows * capacity;
		final int length = w.getLength();
		final ArrayList<Long> paths = w.getPaths();

		int offset;
		int row;
		int col;
		int zeroChild;
		int oneChild;
		int iMod;

		final ArrayList<ArrayList<KeyLeaf>> keyList = new ArrayList<ArrayList<KeyLeaf>>(
				lengthCapacity);
		final ArrayList<ArrayList<KeyLeaf>> oldKeyList = new ArrayList<ArrayList<KeyLeaf>>(
				capacity);

		for (int i = 0; i < lengthCapacity; i++)
			keyList.add(new ArrayList<KeyLeaf>());
		for (int i = 0; i < capacity; i++)
			oldKeyList.add(new ArrayList<KeyLeaf>());

		for (int i = length - 3; i > -1; i--) {
			row = i % rows;
			offset = ((i - row) % (rows * capacity)) / rows;
			col = (i - row - (offset * rows)) / (rows * capacity);
			if (paths.get(i) != 0) {
				iMod = i % lengthCapacity;
				zeroChild = zeroChild(w, i);
				oneChild = oneChild(w, i);
				keyList.set(iMod, new ArrayList<KeyLeaf>());
				if (zeroChild != w.getFail()) {
					keyList.set(iMod, keyList.get(iMod + 1));
					keyList.set(iMod + 1, new ArrayList<KeyLeaf>());
				}
				if (oneChild == w.getAccept())
					keyList.get(iMod).add(new KeyLeaf((short) row));
				else if ((oneChild != w.getFail())
						&& (oldKeyList.get(offset + scores[col][row]).size() != 0))
					keyList.get(iMod).add(
							new KeyLeaf((short) row, oldKeyList.get(offset
									+ scores[col][row])));

			}
			if ((row == 0) && (offset == 0))
				for (int j = 0; j < capacity; j++)
					oldKeyList.set(j, keyList.get(rows * j));
		}
		return keyList.get(0);
	}

	public long runEnumerate() {

		long total = 0;
		boolean flag = false;

		for (int i = 0; i < capacitys.length; i++) {
			final WorkBlock w = new WorkBlock();
			w.setCapacity(capacitys[i]);
			if (i == 0)
				w.setwMin(container.calcualteMinCapacity());
			else
				w.setwMin(capacitys[i - 1]);
			w.setLength(((rows * capacitys[i]) * chunks) + 2);
			w.setFail(w.getLength() - 2);
			w.setAccept(w.getLength() - 1);
			pathCountLoop(w);
			total += w.getPaths().get(w.getAccept());
			final ArrayList<KeyLeaf> keyList = pathEnumerate(w);
			final ArrayList<Short> initKey = new ArrayList<Short>(chunks);
			for (int j = 0; j < chunks; j++)
				initKey.add((short) 0);
			flag = buildKeys(keyList, initKey, key, chunks - 1, chunks);
			if (flag == true)
				break;
		}
		return total;
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
