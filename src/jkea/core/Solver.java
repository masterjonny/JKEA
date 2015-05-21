package jkea.core;

/**
 * Interface for an key rank/enumeration solver.
 */

public interface Solver {

	/**
	 * Returns the key rank of the key in the search space, whilst enumerating
	 * and rebuilding all keys less likely it
	 *
	 * @return the key rank of the key in the search space
	 */
	public long enumerate();

	/**
	 * Returns the attack being processed by this solver
	 *
	 * @return the attack being processed by this solver
	 */
	public Attack getAttack();

	/**
	 * Returns the key rank of the key in the search space
	 *
	 * @return the key rank of the key in the search space
	 */
	public long rank();

}
