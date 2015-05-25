package jkea.core;

import jkea.core.solver.SolverException;

/**
 * Interface for an key rank/enumeration solver. Solvers defined through this
 * interface are expected to be provided with the output of a simulated or real
 * attack in the form of distinguishing vectors, and be able to provide the
 * secret keys rank in the search space or enumerate all keys until the secret
 * key is located. If a solver does not support an operation it should throw a
 * {@link SolverException}.
 */
public interface Solver {

	/**
	 * Returns the key rank of the key in the search space, whilst enumerating
	 * and rebuilding all keys less likely than it.
	 *
	 * @return the key rank of the key in the search space
	 */
	public long enumerate();

	/**
	 * Returns the attack being processed by this solver.
	 *
	 * @return the attack being processed by this solver
	 */
	public Simulator getAttack();

	/**
	 * Returns the key rank of the key in the search space.
	 *
	 * @return the key rank of the key in the search space
	 */
	public long rank();

}
