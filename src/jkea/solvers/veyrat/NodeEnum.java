package jkea.solvers.veyrat;

import java.util.ArrayList;
import java.util.Iterator;

import jkea.solvers.veyrat.data.BinaryHeap;
import jkea.solvers.veyrat.data.Outcome;
import jkea.solvers.veyrat.data.PMF;

class NodeEnum extends Enum {

	private final BinaryHeap mBoundary;
	private Enum mEnumerationX;
	private Enum mEnumerationY;
	private long mX;
	private long mY;

	NodeEnum(final ArrayList<PMF> pDistribtuionSet, int pOffset, int pWidth) {
		mBoundary = new BinaryHeap();
		mX = 0;
		mY = 0;
		init(pDistribtuionSet, pOffset, pWidth);
	}

	private void init(final ArrayList<PMF> pDistribtuionSet, int pOffset,
			int pWidth) {
		mWidth = pWidth;
		for (int i = 0; i < mWidth; ++i)
			mSize *= pDistribtuionSet.get(pOffset + i).size();

		final int n1 = (int) ((mWidth + 1) / 2);
		final int n2 = (int) (mWidth - n1);
		mEnumerationX = (n1 > 1) ? new NodeEnum(pDistribtuionSet, pOffset, n1)
				: new LeafEnum(pDistribtuionSet.get(pOffset));
		mEnumerationY = (n2 > 1) ? new NodeEnum(pDistribtuionSet, pOffset + n1,
				n2) : new LeafEnum(pDistribtuionSet.get(pOffset + n1));

		mEnumerationX.reserve(0);
		mEnumerationY.reserve(0);
		final double p = mEnumerationX.getProbability(0)
				+ mEnumerationY.getProbability(0);
		mBoundary.push(p, 0, 0);
	}

	final Outcome memorize() {
		final Outcome c = mBoundary.top();

		mBufferProbs.add(c.p());

		final int n1 = (int) ((mWidth + 1) / 2);
		final int n2 = (int) (mWidth - n1);
		Iterator<Short> iter = mEnumerationX.label(c.x());
		for (int i = 0; i < n1; ++i)
			mBufferLabel.add(iter.next());
		iter = mEnumerationY.label(c.y());
		for (int i = 0; i < n2; i++)
			mBufferLabel.add(iter.next());
		++mCount;
		return c;
	}

	@Override
	boolean next() {
		if (mBoundary.empty() == false)
			return false;

		final Outcome c = memorize();
		final long x = c.x();
		final long y = c.y();
		mX = mX > x ? mX : x;
		mY = mY > y ? mY : y;
		mBoundary.pop();

		if (mEnumerationX.reserve(x + 1)) {
			double p = mEnumerationX.getProbability(x + 1);
			p += mEnumerationY.getProbability(y);
			mBoundary.push(p, x + 1, y);
		} else
			mEnumerationY.clear(y);

		if (mEnumerationY.reserve(y + 1)) {
			double p = mEnumerationY.getProbability(y + 1);
			p += mEnumerationX.getProbability(x);
			mBoundary.push(p, x, y + 1);
		} else
			mEnumerationX.clear(x);

		return true;
	}

}
