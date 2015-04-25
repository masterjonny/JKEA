package jkea.solvers.veyrat.data;

public class Outcome implements Comparable<Outcome> {

	static Outcome high() {
		return new Outcome(Double.MAX_VALUE, 0, 0);
	}

	static Outcome low() {
		return new Outcome(Double.MIN_VALUE, 0, 0);
	}

	private final double mP;
	private final long mX;
	private final long mY;

	Outcome(double pP, long pX, long pY) {
		mP = pP;
		mX = pX;
		mY = pY;
	}

	Outcome(final Outcome o) {
		mP = o.mP;
		mX = o.mX;
		mY = o.mY;
	}

	@Override
	public int compareTo(Outcome o) {
		return (int) Math.round(mP - o.mP);
	}

	public final double p() {
		return mP;
	}

	public final long x() {
		return mX;
	}

	public final long y() {
		return mY;
	}

}
