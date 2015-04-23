package jkea.simulation;

import java.lang.Math;
import java.lang.IllegalArgumentException;

public class HammingTemplate {

	private double stdDeviation;
	private double cNorm;

	public HammingTemplate(double variance) {
		if (variance <= 0) {
			throw new IllegalArgumentException("Variance must be positive");
		}
		adjustVariance(variance);
	}

	public double getStdDeviation() {
		return stdDeviation;
	}

	public double getcNorm() {
		return cNorm;
	}

	private void adjustVariance(double variance) {
		stdDeviation = Math.sqrt(variance);
		cNorm = 1. / Math.sqrt(2 * Math.PI * variance);
	}
	
	public double leakDensity(double leak, short transition) {
		double x = (leak - hammingWeight(transition) / stdDeviation);
		return Math.exp(-x * x / 2) * cNorm;
	}

	public short hammingWeight(short transition) {
		if ((transition < 0) || (transition > 255)) {
			throw new IllegalArgumentException(
					"Transition must be an unsigned char");
		}
		int x = transition;
		x = (x & 0x55) + ((x >> 1) & 0x55);
		x = (x & 0x33) + ((x >> 2) & 0x33);
		x = (x & 0xf) + (x >> 4);
		return (short) x;
	}

}
