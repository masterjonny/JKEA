package jkea.solvers.bristol;

import java.util.ArrayList;

import jkea.solvers.bristol.data.KeyLeaf;
import jkea.solvers.bristol.data.WorkBlock;

public class ParallelKEA extends KEA {

	private class ParallelBuild extends Thread {

		private boolean found;
		private final int ind;
		private final short[] realKey;
		private volatile boolean running = true;

		public ParallelBuild(int i, short[] realKey) {
			found = false;
			ind = i;
			this.realKey = realKey;
		}

		private boolean buildKeys(ArrayList<KeyLeaf> keys,
				ArrayList<Short> initKey, int shiftToo, int keyLength) {
			if (running) {
				for (int i = shiftToo; i < (keyLength - 1); i++)
					initKey.set(i, initKey.get(i + 1));
				boolean flag = false;
				for (final KeyLeaf keysLocal : keys)
					if (keysLocal.hasList()) {
						final ArrayList<Short> newSoFar = new ArrayList<Short>(
								initKey);
						newSoFar.set(realKey.length - 1,
								keysLocal.getKeyChunk());
						flag = flag
								| buildKeys(keysLocal.getKeyList(), newSoFar,
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
			} else
				return false;
		}

		public boolean isFound() {
			return found;
		}

		@Override
		public void run() {
			final WorkBlock w = new WorkBlock();
			w.setCapacity(capacitys[ind]);
			if (ind == 0)
				w.setwMin(0);
			else
				w.setwMin(capacitys[ind - 1]);

			w.setLength(((rows * capacitys[ind]) * chunks) + 2);
			w.setFail(w.getLength() - 2);
			w.setAccept(w.getLength() - 1);
			ArrayList<KeyLeaf> keyList;
			if (running) {
				pathCountLoop(w);
				keyList = pathEnumerate(w);
			} else {
				return;
			}
			final ArrayList<Short> initKey = new ArrayList<Short>(chunks);
			for (int j = 0; j < chunks; j++)
				initKey.add((short) 0);
			found = buildKeys(keyList, initKey, realKey.length - 1,
					realKey.length);

		}

		public void terminate() {
			running = false;
		}

	}

	public ParallelKEA(int[][] data, short[] key) {
		super(data, key);
	}

	@Override
	public long runEnumerate() {
		return runEnumerate(2);
	}

	public long runEnumerate(int nProcessors) {
		boolean found = false;
		final ArrayList<ParallelBuild> pool = new ArrayList<ParallelBuild>(
				capacitys.length);
		for (final Integer c : capacitys)
			pool.add(null);

		int nRun = 0;
		int nRunning = 0;

		while ((nRun < capacitys.length) && !found) {
			if (nRunning < nProcessors) {
				pool.set(nRun, new ParallelBuild(nRun, key));
				pool.get(nRun).start();
				nRun++;
			}

			nRunning = 0;
			for (int i = 0; i < nRun; i++)
				if (pool.get(i).isAlive())
					nRunning++;

			try {
				Thread.sleep(100);
			} catch (final InterruptedException e) {
				e.printStackTrace();
			}
			
			found = false;
			for (int i = 0; i < nRun; i++)
				if (!pool.get(i).isAlive())
					found = found ^ pool.get(i).isFound();
			if (found)
				for (int i = 0; i < nRun; i++)
					pool.get(i).terminate();
		}

		for (int i = 0; i < nRun; i++) {
			try {
				pool.get(i).join();
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
	/*	while (nRunning > 0) {
			nRunning = 0;
			for (int i = 0; i < nRun; i++)
				if (pool.get(i).isAlive())
					nRunning++;
			try {
				Thread.sleep(10);
			} catch (final InterruptedException e) {
				e.printStackTrace();
			}
		} */

		return keyRank();
	}

}
