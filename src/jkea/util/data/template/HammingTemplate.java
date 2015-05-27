package jkea.util.data.template;

import java.util.Random;

import jkea.core.PRNG;

/**
 * An implementation of simulated leakage where the device leaks hamming
 * templates
 */
public class HammingTemplate implements Template {

	/**
	 * The normalised variance.
	 */
	private double norm;

	/**
	 * Internal source of randomness.
	 */
	private Random numberStream;

	/**
	 * The standard deviation of the variance.
	 */
	private double stdDeviation;

	/**
	 * For a given variance level (noise) create a new hamming template.
	 *
	 * @param variance
	 *            the amount of variance present in this hamming template
	 */
	public HammingTemplate(double variance) {
		adjustVariance(variance);
		numberStream = PRNG.getRandom();
	}

	/**
	 * Adjust the level of variance in this hamming template.
	 *
	 * @param variance
	 *            the amount of variance to set in this hamming template
	 */
	private void adjustVariance(double variance) {
		stdDeviation = Math.sqrt(variance);
		norm = 1. / Math.sqrt(2 * Math.PI * variance);
	}

	/**
	 * Get the normalised variance.
	 *
	 * @return the normalised variance
	 */
	double getNorm() {
		return norm;
	}

	/**
	 * Get the standard deviation of the variance.
	 *
	 * @return the standard deviation of the variance
	 */
	double getStdDeviation() {
		return stdDeviation;
	}

	/**
	 * For a given transition calculate the hamming weight.
	 *
	 * @param transition
	 *            the transition to calculate the hamming weight for
	 * @return the hamming weight for the provided transition
	 */
	short hammingWeight(short transition) {
		int x = transition;
		x = (x & 0x55) + ((x >> 1) & 0x55);
		x = (x & 0x33) + ((x >> 2) & 0x33);
		x = (x & 0xf) + (x >> 4);
		return (short) x;
	}

	@Override
	public double leak(short transition) {
		return hammingWeight(transition)
				+ (stdDeviation * numberStream.nextDouble());
	}

	@Override
	public double leakDensity(double leak, short transition) {
		double x = (leak - (hammingWeight(transition) / stdDeviation));
		return Math.exp((-x * x) / 2) * norm;
	}

}
