package jkea.solvers.veyrat;

import java.util.ArrayList;
import java.util.Iterator;

class Enum {

	protected ArrayList<Short> mBufferLabel;
	protected ArrayList<Double> mBufferProbs;
	protected long mCount;
	protected ArrayList<Boolean> mDominance;
	protected double mSize;
	protected long mWidth;

	Enum() {
		mDominance = new ArrayList<Boolean>();
		mBufferProbs = new ArrayList<Double>();
		mBufferLabel = new ArrayList<Short>();
		mSize = 1.0;
		mWidth = 0;
		mCount = 0;
	}

	void clear(long pIndex) {
		mDominance.set((int) pIndex, false);
	}

	final long count() {
		return mCount;
	}

	final double getProbability(long pIndex) {
		return mBufferProbs.get((int) pIndex);
	}

	final Iterator<Short> label(long pIndex) {
		return mBufferLabel.listIterator((int) (pIndex * mWidth));
	}

	public boolean next() {
		return false;
	}

	boolean reserve(long pIndex) {
		while (pIndex >= mCount) {
			mDominance.add(false);
			next();
		}
		if (mDominance.get((int) pIndex) || (pIndex >= mSize))
			return false;
		mDominance.set((int) pIndex, true);
		return true;
	}
}
