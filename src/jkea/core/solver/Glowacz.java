package jkea.core.solver;

import java.util.ArrayList;

import jkea.core.Simulator;

/**
 * An implementation of Glowacz et al (2014) - Simpler and More Efficient Rank
 * Estimation for Side-Channel Security Assessment. This algorithm uses
 * convolution of histograms to quickly approximate the rank of the key within
 * the search space.
 * <p>
 * Accuracy of the ranking is dependent on the number of bins per histogram,
 * although increasing the number of bins will also have a direct impact on
 * runtime. As Java provides no native, fast convolution function, the
 * implementation of this algorithm is slow, therefore the user is recommended
 * to re-implement this in a language such as Matlab for large scale testing.
 */
public class Glowacz extends AbstractSolver {

	/**
	 * A basic histogram implementation used to support this algorithm.
	 */
	private class Histogram {

		/**
		 * The size (range) of each bin within this histogram.
		 */
		private final double binSize;

		/**
		 * Array containing the count of the number of elements stored in each
		 * bin.
		 */
		private final int[] data;

		/**
		 * Constructs a new histogram.
		 *
		 * @param nBins
		 *            number of bins stored in this histogram
		 * @param binSize
		 *            the size of each bin within the histogram
		 */
		public Histogram(int nBins, double binSize) {
			data = new int[nBins];
			for (int i = 0; i < nBins; i++) {
				data[i] = 0;
			}
			this.binSize = binSize;
		}

		/**
		 * Add a value to the histogram.
		 *
		 * @param val
		 *            the value to be added to the histogram
		 */
		public void add(double val) {
			int binID = (int) (val / binSize);
			if (binID == data.length) {
				binID--;
			}
			data[binID]++;
		}

		/**
		 * Get the value contained in a given bin of the histogram.
		 *
		 * @param id
		 *            index of the bin whose value we wish to retrieve
		 * @return value contained in the specified bin
		 */
		public int getBin(int id) {
			return data[id];
		}

		/**
		 * Get the size (range) of each bin in the histogram.
		 *
		 * @return the size of each bin in the histogram
		 */
		public double getBinSize() {
			return binSize;
		}

		/**
		 * Get the number of bins this histogram contains.
		 *
		 * @return the number of bins this histogram contains
		 */
		public int getNBins() {
			return data.length;
		}

		/**
		 * Set a bin in the histogram to a specified value.
		 *
		 * @param val
		 *            value to set the bin to
		 * @param binID
		 *            index of the bin to which the value is to be assigned
		 */
		public void set(int val, int binID) {
			data[binID] = val;
		}

	}

	/**
	 * The set of histograms this algorithm is processing.
	 */
	private final ArrayList<Histogram> hists;

	/**
	 * Indices of the bins which contain the correct key chunks.
	 */
	private final int[] keyLocations;

	/**
	 * The number of bins which each histogram contains.
	 */
	private final int nBins;

	/**
	 * Constructs a new instance of this solver.
	 *
	 * @param attack
	 *            distinguishing vectors and attack data which this solver is to
	 *            process
	 * @param nBins
	 *            the number of bins each histogram is to contain
	 */
	public Glowacz(Simulator attack, int nBins) {
		super(attack);
		keyLocations = new int[scores.length];
		this.nBins = nBins;
		hists = new ArrayList<Histogram>(scores.length);
	}

	/**
	 * Perform a convolution of two histograms.
	 * 
	 * @param sig
	 *            the histogram being used as the signal in the convolution
	 * @param kern
	 *            the histogram being used as the kernel in the convolution
	 * @return the new histogram which is a convolotion of the two inputs
	 */
	protected Histogram convolve(Histogram sig, Histogram kern) {
		final Histogram res = new Histogram(
				(sig.getNBins() + kern.getNBins()) - 1, sig.getBinSize());

		for (int n = 0; n < res.getNBins(); n++) {
			final int kMin = (n >= (kern.getNBins() - 1)) ? n
					- (kern.getNBins() - 1) : 0;
					final int kMax = (n < (sig.getNBins() - 1)) ? n
							: sig.getNBins() - 1;

					res.set(0, n);

					for (int k = kMin; k <= kMax; k++) {
						res.set(res.getBin(n) + (sig.getBin(k) * kern.getBin(n - k)), n);
					}

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
		super.initialise();
		double min = Double.MAX_VALUE;
		for (final double[] score : scores) {
			for (int j = 0; j < scores[0].length; j++) {
				min = Math.min(min, score[j]);
			}
		}

		for (int i = 0; i < scores.length; i++) {
			final Histogram h = new Histogram(nBins, min / nBins);
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

	@Override
	public long runRank() {
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

}
