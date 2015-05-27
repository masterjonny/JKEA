package jkea.core.solver;

import java.math.BigDecimal;

import jkea.core.Simulator;
import jkea.core.Solver;

/**
 * Abstract class providing default implementations for several {@link Solver}
 * methods.
 */
public abstract class AbstractSolver implements Solver {

	/**
	 * The attack being processed.
	 */
	protected final Simulator attack;

	/**
	 * {@code true} if the {@link #initialise()} method has been invoked;
	 * {@code false} otherwise.
	 */
	protected boolean initialised;

	/**
	 * The known secret key which the solver is attempting to retrieve.
	 */
	protected short[] key;

	/**
	 * The distinguishing vectors provided to this solver to process
	 */
	protected double[][] scores;

	/**
	 * Constructs an abstract solver for solving the specified attack.
	 *
	 * @param attack
	 *            the attack being processed
	 */
	public AbstractSolver(Simulator attack) {
		super();
		this.attack = attack;
		scores = attack.getVectors();
		key = attack.getKey();
	}

	@Override
	public BigDecimal enumerate() {
		if (!isInitialized()) {
			initialise();
		}

		return runEnumerate();
	}

	@Override
	public Simulator getAttack() {
		return attack;
	}

	/**
	 * Performs any initialisation that is required by this solver.
	 * Implementations should always invoke {@code super.initialize()} to ensure
	 * the hierarchy is initialised correctly.
	 *
	 * @throws SolverInitialisationException
	 *             if the solver has already been initialised
	 */
	protected void initialise() {
		if (initialised) {
			throw new SolverInitialisationException(this,
					"solver already initialized");
		}

		initialised = true;
	}

	/**
	 * Returns {@code true} if the {@link #initialise()} method has been
	 * invoked; {@code false} otherwise.
	 *
	 * @return {@code true} if the {@link #initialise()} method has been
	 *         invoked; {@code false} otherwise
	 */
	public boolean isInitialized() {
		return initialised;
	}

	@Override
	public BigDecimal rank() {
		if (!isInitialized()) {
			initialise();
		}

		return runRank();
	}

	/**
	 * After all wrapping calls prior to the rank operation have been performed,
	 * call the core of the algorithm to perform the enumeration operation.
	 *
	 * @return The rank of the key within the search space
	 * @throws SolverException
	 *             if the solver does not provide and implementation for
	 *             enumeration
	 */
	protected BigDecimal runEnumerate() {
		throw new SolverException(this,
				"This solver does not support this opperation");
	}

	/**
	 * After all wrapping calls prior to the rank operation have been performed,
	 * call the core of the algorithm to perform the ranking operation.
	 *
	 * @return The rank of the key within the search space
	 * @throws SolverException
	 *             if the solver does not provide and implementation for ranking
	 */
	protected BigDecimal runRank() {
		throw new SolverException(this,
				"This solver does not support this opperation");
	}

}
