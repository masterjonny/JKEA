package jkea.simulator;

import java.util.ArrayList;

import jkea.util.cipher.AES;
import jkea.util.data.template.HammingTemplate;
import jkea.util.data.template.Template;

/**
 * Implementation of a simulator that creates an AES device and leaks hamming
 * weights.
 *
 */
public class AESHammingWeight extends AbstractSimulator {

	/**
	 * Implementation of an AES based device.
	 */
	private class AESDevice extends AbstractDevice {

		/**
		 * Construct a new AES device.
		 *
		 * @param templates
		 *            hamming weights used in this device
		 * @param blockLength
		 *            the length of the AES cipher block
		 * @param keyRange
		 *            the maximum key value for chunks of this AES device
		 */
		AESDevice(ArrayList<Template> templates, int blockLength, int keyRange) {
			super(templates, new AES(blockLength, keyRange));
		}
	}

	/**
	 * The AES device from which the simulated data is generated.
	 */
	AESDevice device;

	/**
	 * Alternate constructor that calls
	 * {@link AESHammingWeight#AESHammingWeight(int, double, int, int)}
	 * defaulting to a 256 bit key.
	 *
	 * @param nTraces
	 *            number of traces to simulate
	 * @param variance
	 *            the amount of noise present in the simulated data
	 */
	AESHammingWeight(int nTraces, double variance) {
		this(nTraces, variance, 16, 256);
	}

	/**
	 * Constructs a new AES device that leaks hamming weights
	 *
	 * @param nTraces
	 *            number of traces to simulate
	 * @param variance
	 *            the amount of noise present in the simulated data
	 * @param nVectors
	 *            the number of distinguishing vectors
	 * @param vectorLength
	 *            the length of each distinguishing vector
	 */
	public AESHammingWeight(int nTraces, double variance, int nVectors,
			int vectorLength) {
		super(nVectors, vectorLength, nTraces, variance);
		for (int i = 0; i < nVectors; i++) {
			leakData.add(new HammingTemplate(variance));
		}

		device = new AESDevice(leakData, nVectors, vectorLength);
		key = device.getKey();
	}

	@Override
	public String getDefaultDistinguisher() {
		return "template";
	}

	@Override
	public void runAdditionalTrace(int nTraces) {
		final AES c = new AES(numberOfVectors, vectorLength);
		for (long i = 0; i < nTraces; i++) {
			device.newPlain();
			short[] plain = device.getPlain();
			double[] trace = device.getTrace();
			vectors = distinguisher.updatePrior(vectors, plain, trace, c,
					leakData);
		}
	}

	@Override
	public void runAttack() {
		for (int i = 0; i < numberOfVectors; i++) {
			for (int j = 0; j < vectorLength; j++) {
				vectors[i][j] = 1. / vectorLength;
			}
		}

		runAdditionalTrace(nTraces);
	}

}
