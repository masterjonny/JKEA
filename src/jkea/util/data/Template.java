package jkea.util.data;

public interface Template {

	public double leak(short transition);

	double leakDensity(double leak, short transition);
}
