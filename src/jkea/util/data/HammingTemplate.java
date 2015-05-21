package jkea.util.data;

import java.util.Random;

public class HammingTemplate implements LeakPoint {

	private double norm;
	private final Random numberStream;
	private double stdDeviation;

	public HammingTemplate(double variance) {
		adjustVariance(variance);
		numberStream = new Random();
	}

	private void adjustVariance(double variance) {
		stdDeviation = Math.sqrt(variance);
		norm = 1. / Math.sqrt(2 * Math.PI * variance);
	}

	double getNorm() {
		return norm;
	}

	double getStdDeviation() {
		return stdDeviation;
	}

	final short hammingWeight(short transition) {
		int x = transition;
		x = (x & 0x55) + ((x >> 1) & 0x55);
		x = (x & 0x33) + ((x >> 2) & 0x33);
		x = (x & 0xf) + (x >> 4);
		return (short) x;
	}

	@Override
	public final double leak(short transition) {
		return hammingWeight(transition)
				+ (stdDeviation * numberStream.nextDouble());
	}

	@Override
	public final double leakDensity(double leak, short transition) {
		final double x = (leak - (hammingWeight(transition) / stdDeviation));
		return Math.exp((-x * x) / 2) * norm;
	}

}
