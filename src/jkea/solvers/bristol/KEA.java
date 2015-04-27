package jkea.solvers.bristol;

import java.util.ArrayList;
import java.util.Collections;
import java.util.TreeSet;

import jkea.solvers.bristol.data.KeyLeaf;
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

	public int buildKeys(ArrayList<KeyLeaf> keys, ArrayList<Short> soFar,
			short[] realKey) {
		Collections.rotate(soFar, -1);
		final int length = keys.size();
		int flag = 0;
		for (int i = 0; i < length; i++) {
			final KeyLeaf keysLocal = keys.get(i);
			if (keysLocal.hasList()) {
				final ArrayList<Short> newSoFar = new ArrayList<Short>(soFar);
				newSoFar.set(realKey.length - 1, keysLocal.getKeyChunk());
				flag = flag
						^ buildKeys(keysLocal.getKeyList(), newSoFar, realKey);
			} else {
				soFar.set(realKey.length - 1, keysLocal.getKeyChunk());
				flag = 1;
				for (int j = 0; j < soFar.size(); j++)
					if (soFar.get(j) != realKey[j]) {
						flag = 0;
						break;
					}
			}
		}
		return flag;
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

	ArrayList<KeyLeaf> pathEnumerate(WorkBlock w) {
		final int capacity = w.getCapacity();
		final int lengthCapacity = rows * capacity;
		final int length = w.getLength();
		final ArrayList<Integer> paths = w.getPaths();

		final ArrayList<ArrayList<KeyLeaf>> keyList = new ArrayList<ArrayList<KeyLeaf>>(
				lengthCapacity);
		final ArrayList<ArrayList<KeyLeaf>> oldKeyList = new ArrayList<ArrayList<KeyLeaf>>(
				capacity);

		for (int i = 0; i < lengthCapacity; i++)
			keyList.add(new ArrayList<KeyLeaf>());
		for (int i = 0; i < capacity; i++)
			oldKeyList.add(new ArrayList<KeyLeaf>());

		for (int i = length - 3; i > -1; i--) {
			short row = (short) (i % rows);
			final int offset = ((i - row) % (rows * capacity)) / rows;
			final int col = (i - row - (offset * rows)) / (rows * capacity);
			if (paths.get(i) != 0) {
				final int iMod = i % lengthCapacity;
				final int zeroChild = zeroChild(w, i);
				final int oneChild = oneChild(w, i);
				row = (short) (i % rows);
				keyList.set(iMod, new ArrayList<KeyLeaf>());
				if (zeroChild != w.getFail()) {
					keyList.set(iMod, keyList.get(iMod + 1));
					keyList.set(iMod + 1, new ArrayList<KeyLeaf>());
				}
				if (oneChild == w.getAccept())
					keyList.get(iMod).add(new KeyLeaf(row));
				else if ((oneChild != w.getFail())
						&& (oldKeyList.get(offset + scores[col][row]).size() != 0))
					keyList.get(iMod).add(
							new KeyLeaf(row, oldKeyList.get(offset
									+ scores[col][row])));
			}
			if ((row == 0) && (offset == 0))
				for (int j = 0; j < capacity; j++)
					oldKeyList.set(j, keyList.get(rows * j));
		}
		return keyList.get(0);
	}

	public long run() {

		long total = 0;
		int flag = 0;

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
			flag = buildKeys(keyList, initKey, key);
			if (flag == 1)
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
