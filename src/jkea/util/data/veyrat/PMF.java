package jkea.util.data.veyrat;

import java.util.ArrayList;
import java.util.Collections;

public class PMF {

	public class PMFOutcome implements Comparable<PMFOutcome> {

		private short mEvent;
		private double mProbability;

		private PMFOutcome(short mEvent, double mProbability) {
			this.mEvent = mEvent;
			this.mProbability = mProbability;
		}

		@Override
		public int compareTo(PMFOutcome o) {
			return (int) Math.round(o.getmProbability() - getmProbability());
		}

		public short getmEvent() {
			return mEvent;
		}

		public double getmProbability() {
			return mProbability;
		}

	}

	private ArrayList<PMFOutcome> mDistribution;

	public PMF(ArrayList<Double> pDistribution) {
		mDistribution = new ArrayList<PMFOutcome>(pDistribution.size());
		short index = 0;
		for (Double val : pDistribution)
			mDistribution.add(new PMFOutcome(index++, val));
		Collections.sort(mDistribution);
	}

	public PMFOutcome get(int i) {
		return mDistribution.get(i);
	}

	public int size() {
		return mDistribution.size();
	}

}