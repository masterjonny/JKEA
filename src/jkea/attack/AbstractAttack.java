package jkea.attack;

import jkea.core.Attack;
import jkea.core.Distinguisher;

public abstract class AbstractAttack implements Attack {

	protected Distinguisher distinguisher;

	protected short[] key;

	protected final int numberOfVectors;

	protected final int vectorLength;

	protected double[][] vectors;

	public AbstractAttack(int nVectors, int vectorLength,
			Distinguisher distinguisher) {
		numberOfVectors = nVectors;
		this.vectorLength = vectorLength;
		vectors = new double[nVectors][vectorLength];
		this.distinguisher = distinguisher;
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

}
