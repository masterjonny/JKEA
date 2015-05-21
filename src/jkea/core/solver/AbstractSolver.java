package jkea.core.solver;

import jkea.core.Attack;
import jkea.core.Solver;

/**
 * Abstract class providing default implementations for several {@link Solver}
 * methods.
 */

public abstract class AbstractSolver implements Solver {

	/**
	 * The attack being processed.
	 */
	protected final Attack attack;

	/**
	 * {@code true} if the {@link #initialise()} method has been invoked;
	 * {@code false} otherwise.
	 */
	protected boolean initialised;

	/**
	 * Constructs an abstract solver for solving the specified attack.
	 *
	 * @param problem
	 *            the attack being processed
	 */
	public AbstractSolver(Attack attack) {
		super();
		this.attack = attack;
	}

	@Override
	public Attack getAttack() {
		return attack;
	}

	/**
	 * Performs any initialisation that is required by this solver.
	 * Implementations should always invoke {@code super.initialize()} to ensure
	 * the hierarchy is initialised correctly.
	 *
	 * @throws SolverInitializationException
	 *             if the solver has already been initialised
	 */
	protected void initialise() {
		if (initialised)
			throw new SolverInitialisationException(this,
					"solver already initialized");

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
}
