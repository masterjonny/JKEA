package jkea.util.data.veyrat;

import java.util.ArrayList;
import java.util.Iterator;

class NodeEnum extends Enum {

	protected BinaryHeap mBoundary;
	protected Enum mEnumerationX;
	protected Enum mEnumerationY;
	private long mX;
	private long mY;

	protected NodeEnum() {
		mBoundary = new BinaryHeap();
		mX = 0;
		mY = 0;
	}

	NodeEnum(ArrayList<PMF> pDistribtuionSet, int pOffset, int pWidth) {
		mBoundary = new BinaryHeap();
		mX = 0;
		mY = 0;
		init(pDistribtuionSet, pOffset, pWidth);
	}

	protected void init(ArrayList<PMF> pDistribtuionSet, int pOffset,
			int pWidth) {
		mWidth = pWidth;
		for (int i = 0; i < mWidth; ++i)
			mSize *= pDistribtuionSet.get(pOffset + i).size();

		int n1 = (int) ((mWidth + 1) / 2);
		int n2 = (int) (mWidth - n1);
		mEnumerationX = (n1 > 1) ? new NodeEnum(pDistribtuionSet, pOffset, n1)
				: new LeafEnum(pDistribtuionSet.get(pOffset));
		mEnumerationY = (n2 > 1) ? new NodeEnum(pDistribtuionSet, pOffset + n1,
				n2) : new LeafEnum(pDistribtuionSet.get(pOffset + n1));

		mEnumerationX.reserve(0);
		mEnumerationY.reserve(0);
		double p = mEnumerationX.getProbability(0)
				+ mEnumerationY.getProbability(0);
		mBoundary.push(p, 0, 0);
	}

	Outcome memorize() {
		Outcome c = mBoundary.top();

		mBufferProbs.add(c.p());

		int n1 = (int) ((mWidth + 1) / 2);
		int n2 = (int) (mWidth - n1);
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
	public boolean next() {
		if (mBoundary.empty())
			return false;

		Outcome c = memorize();
		long x = c.x();
		long y = c.y();
		mX = Math.max(mX, x);
		mY = Math.max(mY, y);
		mBoundary.pop();

		if (mEnumerationX.reserve(x + 1))
			mBoundary.push(
					mEnumerationX.getProbability(x + 1)
					+ mEnumerationY.getProbability(y), x + 1, y);
		else
			mEnumerationY.clear(y);

		if (mEnumerationY.reserve(y + 1))
			mBoundary.push(
					mEnumerationY.getProbability(y + 1)
					+ mEnumerationX.getProbability(x), x, y + 1);
		else
			mEnumerationX.clear(x);

		return true;
	}

}