package jkea.core.solver;

import java.util.ArrayList;

import jkea.core.Attack;

public class Glowacz extends AbstractSolver {

	private class Histogram {

		private final double binSize;
		private final int[] data;

		public Histogram(int nBins, double binSize) {
			data = new int[nBins];
			for (int i = 0; i < nBins; data[i++] = 0)
				;
			this.binSize = binSize;
		}

		public void add(double val) {
			int binID = (int) (val / binSize);
			if (binID == data.length)
				binID--;
			data[binID]++;
		}

		public void add(int val, int binID) {
			data[binID] = val;
		}

		public int getBin(int id) {
			return data[id];
		}

		public double getBinSize() {
			return binSize;
		}

		public int getNBins() {
			return data.length;
		}

	}

	private final ArrayList<Histogram> hists;
	private final short[] key;
	private final int[] keyLocations;
	private final int nBins;

	private final double[][] scores;

	public Glowacz(Attack attack, int nBins) {
		super(attack);
		scores = attack.getVectors();
		key = attack.getKey();
		keyLocations = new int[scores.length];
		this.nBins = nBins;
		hists = new ArrayList<Histogram>(nBins);
	}

	protected Histogram convolve(Histogram sig, Histogram kern) {
		final Histogram res = new Histogram(
				(sig.getNBins() + kern.getNBins()) - 1, sig.getBinSize());

		for (int n = 0; n < res.getNBins(); n++) {
			final int kMin = (n >= (kern.getNBins() - 1)) ? n
					- (kern.getNBins() - 1) : 0;
					final int kMax = (n < (sig.getNBins() - 1)) ? n
							: sig.getNBins() - 1;

					res.add(0, n);

					for (int k = kMin; k <= kMax; k++)
						res.add(res.getBin(n) + (sig.getBin(k) * kern.getBin(n - k)), n);

		}
		return res;
	}

	@Override
	public long enumerate() {
		throw new SolverException(this,
				"This solver does not support this opperation");
	}

	@Override
	protected void initialise() {
		double min = Double.MAX_VALUE;
		for (final double[] score : scores)
			for (int j = 0; j < scores[0].length; j++)
				min = Math.min(min, score[j]);

		for (int i = 0; i < scores.length; i++) {
			final Histogram h = new Histogram(nBins, min / nBins);
			for (int j = 0; j < scores[0].length; j++) {
				if (j == key[i])
					keyLocations[i] = ((scores[i][j] == (min / nBins)) ? h
							.getNBins() - 1
							: (int) (scores[i][j] / (min / nBins)));
				h.add(scores[i][j]);
			}
			hists.add(h);
		}
	}

	@Override
	public long rank() {
		Histogram curr = hists.get(0);
		int keyPosFinal = keyLocations[0];
		for (int i = 1; i < scores.length; i++) {
			curr = convolve(curr, hists.get(i));
			keyPosFinal += keyLocations[i];
		}
		int rank = 0;
		for (int i = keyPosFinal; i < curr.getNBins(); i++)
			rank += curr.getBin(i);
		return rank;
	}

}
