package jkea.core;

/**
 * Interface for defining attack types. All methods must be thread safe.
 */
public interface Attack {

	/**
	 * Returns the secret key on which this attack data is based
	 *
	 * @return the secret key on which this attack data is based
	 */
	public short[] getKey();

	/**
	 * Returns the user-friendly name for this attack.
	 *
	 * @return the user-friendly name for this attack
	 */
	public String getName();

	/**
	 * Returns the number of distinguishing vectors for this attack.
	 *
	 * @return the number of distinguishing vectors for this attack.
	 */
	public int getNumberOfVectors();

	/**
	 * Returns the length of the distinguishing vectors in this attack.
	 *
	 * @return the length of the distinguishing vectors in this attack.
	 */
	public int getVectorLength();

	/**
	 * Returns all distinguishing vectors used in this attack.
	 *
	 * @return all distinguishing vectors used in this attack.
	 */
	public double[][] getVectors();

}
