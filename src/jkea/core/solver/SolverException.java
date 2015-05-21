package jkea.core.solver;

import jkea.core.JKEAException;
import jkea.core.Solver;

/**
 * An exception that originated from an solver.
 */
public class SolverException extends JKEAException {

	private static final long serialVersionUID = 582065896367014652L;

	/**
	 * The solver responsible for this exception.
	 */
	private final Solver solver;

	/**
	 * Constructs an solver exception originating from the specified solver.
	 *
	 * @param solver
	 *            the solver responsible for this exception
	 */
	public SolverException(Solver solver) {
		this(solver, null, null);
	}

	/**
	 * Constructs an solver exception originating from the specified solver with
	 * the given message.
	 *
	 * @param solver
	 *            the solver responsible for this exception
	 * @param message
	 *            the message describing this exception
	 */
	public SolverException(Solver solver, String message) {
		this(solver, message, null);
	}

	/**
	 * Constructs an solver exception originating from the specified solver with
	 * the given message and cause.
	 *
	 * @param solver
	 *            the solver responsible for this exception
	 * @param message
	 *            the message describing this exception
	 * @param cause
	 *            the cause of this exception
	 */
	public SolverException(Solver solver, String message, Throwable cause) {
		super(message, cause);
		this.solver = solver;
	}

	/**
	 * Constructs an solver exception originating from the specified solver with
	 * the given cause.
	 *
	 * @param solver
	 *            the solver responsible for this exception
	 * @param cause
	 *            the cause of this exception
	 */
	public SolverException(Solver solver, Throwable cause) {
		this(solver, cause.getMessage(), cause);
	}

	/**
	 * Returns the solver responsible for this exception.
	 *
	 * @return the solver responsible for this exception
	 */
	public Solver getsolver() {
		return solver;
	}

}
