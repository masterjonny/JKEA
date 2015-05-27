package jkea.core.solver;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.TreeSet;

import jkea.core.Simulator;
import jkea.util.data.kea.KeyLeaf;
import jkea.util.data.kea.Knapsack;
import jkea.util.data.kea.WorkBlock;

public class KEA extends AbstractSolver {

	protected Integer[] capacitys;
	protected int chunks;
	private Knapsack container;
	protected short[] key;
	protected int rows;
	private int[][] intScores;
	private int graphWidth;
	private double precision;

	public KEA(Simulator simulator, double precision) {
		super(simulator);

		rows = attack.getVectorLength();
		chunks = attack.getNumberOfVectors();
		key = attack.getKey();
		this.precision = precision;
	}

	public boolean buildKeys(ArrayList<KeyLeaf> keys, ArrayList<Short> initKey,
			short[] realKey, int shiftToo, int keyLength) {
		for (int i = shiftToo; i < (keyLength - 1); i++) {
			initKey.set(i, initKey.get(i + 1));
		}
		boolean flag = false;
		for (KeyLeaf keysLocal : keys) {
			if (keysLocal.hasList()) {
				ArrayList<Short> newSoFar = new ArrayList<Short>(initKey);
				newSoFar.set(realKey.length - 1, keysLocal.getKeyChunk());
				flag = flag
						| buildKeys(keysLocal.getKeyList(), newSoFar, realKey,
								shiftToo - 1, keyLength);
			} else {
				initKey.set(realKey.length - 1, keysLocal.getKeyChunk());
				for (int j = 0, jj = keyLength; j < jj; j++) {
					if (initKey.get(j) != realKey[j]) {
						break;
					}
					if (j == (jj - 1)) {
						flag = true;
					}
				}
			}
		}
		return flag;
	}

	private Integer[] calculateCapacitys() {
		container.sortScore();
		TreeSet<Integer> capacs = new TreeSet<Integer>();

		for (int k = 0; k < chunks; k++) {
			int[] depthCurrent = new int[chunks];
			for (int kk = 0; kk < chunks; kk++) {
				depthCurrent[kk] = 0;
			}
			for (int i = k; i < chunks; i++) {
				for (int j = 0; j < (rows - 1); j++) {
					depthCurrent[i] += 1;
					capacs.add(container
							.calculateCapacityWithoutSort(depthCurrent));
				}
			}
		}
		container.sortKeyChunk();
		return capacs.toArray(new Integer[capacs.size()]);
	}

	public int getGraphWidth() {
		return graphWidth;
	}

	@Override
	public void initialise() {
		int globalMax = 0;
		intScores = new int[scores.length][scores[0].length];

		for (int i = 0; i < scores.length; ++i) {
			for (int j = 0; j < scores[0].length; ++j) {
				intScores[i][j] = (int) Math.abs(scores[i][j]);
				globalMax = Math.max(globalMax, intScores[i][j]);
			}
		}

		double alpha = Math.log(globalMax) / Math.log(2);

		for (int i = 0; i < scores.length; ++i) {
			for (int j = 0; j < scores[0].length; ++j) {
				intScores[i][j] = (int) Math.abs(scores[i][j]
						* Math.pow(2, precision - alpha));
			}
		}

		container = new Knapsack(intScores);
		capacitys = calculateCapacitys();
	}

	int oneChild(WorkBlock w, int i) {
		int capacity = w.getCapacity();
		int fail = w.getFail();
		int accept = w.getAccept();
		int wMin = w.getwMin();

		if (i == accept) {
			return accept;
		}
		if (i == fail) {
			return fail;
		}

		int row = i % rows;
		int offset = ((i - row) % (rows * capacity)) / rows;
		int col = (i - row - (offset * rows)) / (rows * capacity);
		if ((offset + intScores[col][row]) >= capacity) {
			return fail;
		} else if (col == (chunks - 1)) {
			if ((offset + intScores[col][row]) >= wMin) {
				return accept;
			} else {
				return fail;
			}
		} else {
			return (rows * (offset + intScores[col][row]))
					+ ((col + 1) * capacity * rows);
		}
	}

	private BigDecimal pathCount(WorkBlock w, int index) {
		int length = w.getLength();
		ArrayList<BigDecimal> localCount = new ArrayList<BigDecimal>(length);
		for (int i = 0; i < length; i++) {
			localCount.add(new BigDecimal(0));
		}
		localCount.set(index, new BigDecimal(1));

		for (int i = index - 1; i > -1; i--) {
			localCount.set(
					i,
					localCount.get(zeroChild(w, i)).add(
					localCount.get(oneChild(w, i))));
		}
		
		return localCount.get(0);
	}

	void pathCountLoop(WorkBlock w) {
		int capacity = w.getCapacity();
		int length = w.getLength();
		ArrayList<BigDecimal> paths = new ArrayList<BigDecimal>(length);
		for (int i = 0; i < length; i++) {
			paths.add(new BigDecimal(0));
		}
		paths.set(0, new BigDecimal(1));

		BigDecimal one = new BigDecimal(1);
		
		for (int i = 0; i < chunks; i++) {
			for (int j = 0; j < capacity; j++) {
				int location = (i * rows * capacity) + (j * rows);
				if (paths.get(location).equals(one)) {
					for (int k = 0; k < rows; k++) {
						paths.set(location + k, new BigDecimal(1));
						paths.set(oneChild(w, location + k), new BigDecimal(1));
					}
				}
			}
		}
		w.setPathCount(pathCount(w, length - 1));
		w.setPaths(paths);
	}

	ArrayList<KeyLeaf> pathEnumerate(WorkBlock w) {
		int capacity = w.getCapacity();
		int lengthCapacity = rows * capacity;
		int length = w.getLength();
		ArrayList<BigDecimal> paths = w.getPaths();

		int offset;
		int row;
		int col;
		int zeroChild;
		int oneChild;
		int iMod;

		ArrayList<ArrayList<KeyLeaf>> keyList = new ArrayList<ArrayList<KeyLeaf>>(
				lengthCapacity);
		ArrayList<ArrayList<KeyLeaf>> oldKeyList = new ArrayList<ArrayList<KeyLeaf>>(
				capacity);

		for (int i = 0; i < lengthCapacity; i++) {
			keyList.add(new ArrayList<KeyLeaf>());
		}
		for (int i = 0; i < capacity; i++) {
			oldKeyList.add(new ArrayList<KeyLeaf>());
		}

		BigDecimal zero = new BigDecimal(0);
		
		for (int i = length - 3; i > -1; i--) {
			row = i % rows;
			offset = ((i - row) % (rows * capacity)) / rows;
			col = (i - row - (offset * rows)) / (rows * capacity);
			if (!paths.get(i).equals(zero)) {
				iMod = i % lengthCapacity;
				zeroChild = zeroChild(w, i);
				oneChild = oneChild(w, i);
				keyList.set(iMod, new ArrayList<KeyLeaf>());
				if (zeroChild != w.getFail()) {
					keyList.set(iMod, keyList.get(iMod + 1));
					keyList.set(iMod + 1, new ArrayList<KeyLeaf>());
				}
				if (oneChild == w.getAccept()) {
					keyList.get(iMod).add(new KeyLeaf((short) row));
				} else if ((oneChild != w.getFail())
						&& (oldKeyList.get(offset + intScores[col][row]).size() != 0)) {
					keyList.get(iMod).add(
							new KeyLeaf((short) row, oldKeyList.get(offset
									+ intScores[col][row])));
				}

			}
			if ((row == 0) && (offset == 0)) {
				for (int j = 0; j < capacity; j++) {
					oldKeyList.set(j, keyList.get(rows * j));
				}
			}
		}
		return keyList.get(0);
	}

	@Override
	public BigDecimal runEnumerate() {

		BigDecimal total = new BigDecimal(0);
		boolean flag = false;

		for (int i = 0; i < capacitys.length; i++) {
			WorkBlock w = new WorkBlock();
			w.setCapacity(capacitys[i]);
			if (i == 0) {
				w.setwMin(container.calcualteMinCapacity());
			} else {
				w.setwMin(capacitys[i - 1]);
			}
			w.setLength(((rows * capacitys[i]) * chunks) + 2);
			w.setFail(w.getLength() - 2);
			w.setAccept(w.getLength() - 1);
			pathCountLoop(w);
			total.add(w.getPathCount());
			ArrayList<KeyLeaf> keyList = pathEnumerate(w);
			ArrayList<Short> initKey = new ArrayList<Short>(chunks);
			for (int j = 0; j < chunks; j++) {
				initKey.add((short) 0);
			}
			flag = buildKeys(keyList, initKey, key, chunks - 1, chunks);
			if (flag == true) {
				break;
			}
		}
		return total;
	}

	@Override
	public BigDecimal runRank() {
		WorkBlock w = new WorkBlock();
		graphWidth = container.calculateCapacityWithoutSort(key);
		w.setCapacity(graphWidth);
		w.setLength(((rows * w.getCapacity()) * chunks) + 2);
		w.setFail(w.getLength() - 2);
		w.setAccept(w.getLength() - 1);
		pathCountLoop(w);
		return w.getPathCount();
	}

	int zeroChild(WorkBlock w, int i) {
		int fail = w.getFail();
		if (i == fail) {
			return fail;
		}
		short row = (short) (i % rows);
		if (row == (rows - 1)) {
			return fail;
		} else {
			return i + 1;
		}
	}

}
