package jkea.solvers.veyrat;

import jkea.solvers.veyrat.data.PMF;

class LeafEnum extends Enum {

	LeafEnum(final PMF pPmf) {
		mWidth = 1;
		mCount = pPmf.size();
		mSize *= mCount;
		final double p = pPmf.get(0).getmProbability();
		for (int i = 0; i < pPmf.size(); ++i) {
			mBufferProbs.add(pPmf.get(i).getmProbability() - p);
			mBufferLabel.add(pPmf.get(i).getmEvent());
			mDominance.add(false);
		}
	}

	@Override
	boolean next() {
		return true;
	}
}
