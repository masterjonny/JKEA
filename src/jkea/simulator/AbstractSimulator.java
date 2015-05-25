package jkea.simulator;

import java.util.ArrayList;

import jkea.core.Distinguisher;
import jkea.core.Simulator;
import jkea.util.data.Template;

/**
 * Abstract class for managing the simulation and generation of distinguishing
 * vectors, providing default implementations for {@link Simulator} methods.
 *
 */
public abstract class AbstractSimulator implements Simulator {

	/**
	 * The distinguisher used to process the simulated data and generate
	 * distinguishing vectors.
	 */
	protected Distinguisher distinguisher;

	/**
	 * The known secret key for this set of distinguishing vectors.
	 */
	protected short[] key;

	/**
	 * The number of distinguishing vectors this simulator contains.
	 */
	protected final int numberOfVectors;

	/**
	 * The length of each distinguishing vector in this simulator.
	 */
	protected final int vectorLength;

	/**
	 * The set of distinguishing vectors in this simulator.
	 */
	protected double[][] vectors;

	/**
	 * The number of traces performed to generate this set of distinguishing
	 * vectors.
	 */
	protected int nTraces;

	/**
	 * The amount of noise present in the simulated data.
	 */
	protected double variance;

	/**
	 * The templates used to generate the simulated data.
	 */
	protected ArrayList<Template> leakData;

	/**
	 * Construct a new basic simulator implementation.
	 *
	 * @param numberOfVectors
	 *            the number of distinguishing vectors
	 * @param vectorLength
	 *            the length of each distinguishing vector
	 * @param nTraces
	 *            number of traces to simulate to the generate the data
	 * @param variance
	 *            amount of noise present in the simulated data
	 */
	public AbstractSimulator(int numberOfVectors, int vectorLength,
			int nTraces, double variance) {
		this.nTraces = nTraces;
		this.variance = variance;
		this.numberOfVectors = numberOfVectors;
		this.vectorLength = vectorLength;
		vectors = new double[numberOfVectors][vectorLength];
		leakData = new ArrayList<Template>(numberOfVectors);
	}

	@Override
	public short[] getKey() {
		return key;
	}

	@Override
	public String getName() {
		return getClass().getSimpleName();
	}

	@Override
	public int getNumberOfVectors() {
		return numberOfVectors;
	}

	@Override
	public int getVectorLength() {
		return vectorLength;
	}

	@Override
	public double[][] getVectors() {
		return vectors;
	}

	@Override
	public void setDistinguisher(Distinguisher distinguisher) {
		this.distinguisher = distinguisher;
	}

}
