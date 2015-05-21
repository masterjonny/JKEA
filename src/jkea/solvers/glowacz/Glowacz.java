package jkea.solvers.glowacz;

import java.util.ArrayList;

import jkea.solvers.glowacz.data.Histogram;

public class Glowacz {

	private double[][] scores;
	private short[] key;
	private int[] keyLocations;
	private ArrayList<Histogram> hists;

	public Glowacz(double[][] scores, short[] key, int nBins) {
		this.scores = scores;
		this.key = key;
		keyLocations = new int[scores.length];
		hists = new ArrayList<Histogram>(nBins);
		init(nBins);
	}

	public Glowacz(double[][] scores, short[] key) {
		this(scores, key, 5000);
	}

	private void init(int nBins) {
		double min = Double.MAX_VALUE;
		for (int i = 0; i < scores.length; i++)
			for (int j = 0; j < scores[0].length; j++)
				min = Math.min(min, scores[i][j]);

		for (int i = 0; i < scores.length; i++) {
			Histogram h = new Histogram(nBins, min / nBins);
			for (int j = 0; j < scores[0].length; j++) {
				if (j == key[i]) {
					keyLocations[i] = ((scores[i][j] == (min / nBins)) ? h
							.getNBins() - 1
							: (int) (scores[i][j] / (min / nBins)));
				}
				h.add(scores[i][j]);
			}
			hists.add(h);
		}
	}

	public long rank() {
		Histogram curr = hists.get(0);
		int keyPosFinal = keyLocations[0];
		for (int i = 1; i < scores.length; i++) {
			curr = convolve(curr, hists.get(i));
			keyPosFinal += keyLocations[i];
		}
		int rank = 0;
		for (int i = keyPosFinal; i < curr.getNBins(); i++) {
			rank += curr.getBin(i);
		}
		return rank;
	}

	protected Histogram convolve(Histogram sig, Histogram kern) {
		Histogram res = new Histogram(sig.getNBins() + kern.getNBins() - 1,
				sig.getBinSize());

		for (int n = 0; n < res.getNBins(); n++) {
			int kMin = (n >= kern.getNBins() - 1) ? n - (kern.getNBins() - 1)
					: 0;
			int kMax = (n < sig.getNBins() - 1) ? n : sig.getNBins() - 1;

			res.add(0, n);

			for (int k = kMin; k <= kMax; k++)
				res.add(res.getBin(n) + sig.getBin(k) * kern.getBin(n - k), n);

		}
		return res;
	}

}
