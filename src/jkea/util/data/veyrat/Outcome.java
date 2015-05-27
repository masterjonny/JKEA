package jkea.util.data.veyrat;

public class Outcome implements Comparable<Outcome> {

	static Outcome high() {
		return new Outcome(Double.MAX_VALUE, 0, 0);
	}

	static Outcome low() {
		return new Outcome(Double.MIN_VALUE, 0, 0);
	}

	private double mP;
	private long mX;
	private long mY;

	Outcome(double pP, long pX, long pY) {
		mP = pP;
		mX = pX;
		mY = pY;
	}

	Outcome(Outcome o) {
		mP = o.mP;
		mX = o.mX;
		mY = o.mY;
	}

	@Override
	public int compareTo(Outcome o) {
		return (int) Math.round(mP - o.mP);
	}

	public double p() {
		return mP;
	}

	public long x() {
		return mX;
	}

	public long y() {
		return mY;
	}

}