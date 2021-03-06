package jkea.util.data.veyrat;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;

public class TopEnum extends NodeEnum {

	private ArrayList<PMF> mDistributionSet;
	private ArrayList<Short> mKey;
	private ArrayList<Short> mPerm;

	public TopEnum(double[][] scores) {
		mDistributionSet = new ArrayList<PMF>();
		mPerm = new ArrayList<Short>();
		mKey = new ArrayList<Short>();
		permute(scores);
		for (int i = 0; i < scores.length; i++) {
			ArrayList<Double> t = new ArrayList<Double>(scores[i].length);
			for (int j = 0; j < scores[i].length; j++) {
				t.add(scores[mPerm.get(i)][j]);
			}
			mDistributionSet.add(new PMF(t));
		}
		init(mDistributionSet, 0, scores.length);
		mBufferProbs.ensureCapacity(1);
		mKey.ensureCapacity((int) mWidth);
		for (int i = 0; i < mWidth; i++) {
			mKey.add((short) 0x00);
		}
		mBufferLabel.clear();
	}

	public ArrayList<Short> getKey() {
		return mKey;
	}

	@Override
	Outcome memorize() {
		Outcome o = mBoundary.top();
		if (mBufferProbs.size() == 0) {
			mBufferProbs.add(o.p());
		} else {
			mBufferProbs.set(0, o.p());
		}

		int n1 = (int) ((mWidth + 1) / 2);
		int n2 = (int) (mWidth - n1);
		Iterator<Short> iter1 = mEnumerationX.label(o.x());
		Iterator<Short> iter2 = mPerm.listIterator();
		for (int i = 0; i < n1; ++i) {
			mKey.set(iter2.next(), iter1.next());
		}
		iter1 = mEnumerationY.label(o.y());
		for (int i = 0; i < n2; ++i) {
			mKey.set(iter2.next(), iter1.next());
		}
		++mCount;
		return o;
	}

	void permute(double[][] distributionSet) {
		ArrayList<Double> entropies = new ArrayList<Double>(
				distributionSet.length);
		for (double[] element : distributionSet) {
			double m = element[0];
			for (double element2 : element) {
				m = m > element2 ? m : element2;
			}
			double s = 0;
			for (double element2 : element) {
				s += Math.exp(element2 - m);
			}
			double e = 0;
			for (double element2 : element) {
				double p = Math.exp(element2 - m) / s;
				e -= p * Math.log(p);
			}
			entropies.add(e / Math.log(2));
		}
		int n = distributionSet.length;
		for (int i = 0; i < n; i++) {
			mPerm.add((short) i);
		}

		for (int i = n; i > 1; i >>= 1) {
			for (int j = 0; j < n; j += i) {
				double s1 = 0.0;
				double s2 = 0.0;
				int is1 = j;
				int is2 = (j + i) - 1;
				while (is1 <= is2) {
					double maxEntropy = 0;
					short m = 0;
					for (int k = 0; k < ((is2 + 1) - is1); k++) {
						if (entropies.get(mPerm.get(k + is1)) > maxEntropy) {
							maxEntropy = entropies.get(mPerm.get(k + is1));
							m = mPerm.get(k + is1);
						}
					}
					if (s1 < s2) {
						s1 += entropies.get(m);
						int t = mPerm.indexOf(m);
						Collections.swap(mPerm, is1, t);
						++is1;
					} else {
						s2 += entropies.get(m);
						int t = mPerm.indexOf(m);
						Collections.swap(mPerm, is2, t);
						--is2;
					}
				}
			}
		}
	}

}