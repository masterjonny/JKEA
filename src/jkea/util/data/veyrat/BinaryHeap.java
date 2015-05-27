package jkea.util.data.veyrat;

import java.util.ArrayList;

public class BinaryHeap {

	private ArrayList<Outcome> mQueue;

	public BinaryHeap() {
		mQueue = new ArrayList<Outcome>();
		mQueue.add(Outcome.high());
	}

	public boolean empty() {
		return mQueue.size() <= 1;
	}

	public void pop() {
		int hole = 1;
		int succ = 2;
		while ((succ + 1) < mQueue.size()) {
			if (mQueue.get(succ).p() < mQueue.get(succ + 1).p())
				++succ;
			mQueue.set(hole, mQueue.get(succ));
			hole = succ;
			succ <<= 1;
		}
		if (succ < mQueue.size()) {
			mQueue.set(hole, mQueue.get(succ));
			hole = succ;
		}
		int pred = hole >> 1;
		while (mQueue.get(pred).p() < mQueue.get(mQueue.size() - 1).p()) {
			mQueue.set(hole, mQueue.get(pred));
			hole = pred;
			pred >>= 1;
		}
		mQueue.set(hole, mQueue.get(mQueue.size() - 1));
		mQueue.remove(mQueue.get(mQueue.size() - 1));
	}

	public void push(double pP, long pX, long pY) {
		int hole = mQueue.size();
		mQueue.add(null);
		int pred = hole >> 1;
			while (mQueue.get(pred).p() < pP) {
				mQueue.set(hole, mQueue.get(pred));
				hole = pred;
				pred >>= 1;
			}
			mQueue.set(hole, new Outcome(pP, pX, pY));
	}

	public Outcome top() {
		return mQueue.get(1);
	}

}
