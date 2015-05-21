package jkea.util.data;

public interface LeakPoint {

	public double leak(short transition);

	double leakDensity(double leak, short transition);
}
