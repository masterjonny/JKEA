package jkea.core.solver;

import jkea.core.Solver;

/**
 * An exception that originated from an solver during initialisation.
 */
public class SolverInitialisationException extends SolverException {

	private static final long serialVersionUID = -4341813616427565989L;

	/**
	 * Constructs an solver initialisation exception originating from the
	 * specified solver.
	 *
	 * @param solver
	 *            the solver responsible for this exception
	 */
	public SolverInitialisationException(Solver solver) {
		super(solver);
	}

	/**
	 * Constructs an solver initialisation exception originating from the
	 * specified solver with the given message.
	 *
	 * @param solver
	 *            the solver responsible for this exception
	 * @param message
	 *            the message describing this exception
	 */
	public SolverInitialisationException(Solver solver, String message) {
		super(solver, message);
	}

	/**
	 * Constructs an solver initialisation exception originating from the
	 * specified solver with the given message and cause.
	 *
	 * @param solver
	 *            the solver responsible for this exception
	 * @param message
	 *            the message describing this exception
	 * @param cause
	 *            the cause of this exception
	 */
	public SolverInitialisationException(Solver solver, String message,
			Throwable cause) {
		super(solver, message, cause);
	}

	/**
	 * Constructs an solver initialisation exception originating from the
	 * specified solver with the given cause.
	 *
	 * @param solver
	 *            the solver responsible for this exception
	 * @param cause
	 *            the cause of this exception
	 */
	public SolverInitialisationException(Solver solver, Throwable cause) {
		super(solver, cause);
	}

}
