package jkea.core;

import java.io.IOException;

/**
 * The framework exception is the parent type of all exceptions specific to the
 * JKEA Framework. Framework exceptions only cover exceptional cases caused by
 * the JKEA Framework itself; user exceptions are still covered by Java's
 * built-in exceptions (i.e., {@link IllegalArgumentException}) or those defined
 * in third-party libraries. Unhandled checked exceptions, such as
 * {@link IOException}, should be wrapped in an appropriate framework exception.
 */
public class JKEAException extends RuntimeException {

	private static final long serialVersionUID = 3068068769472228564L;

	/**
	 * Constructs a new JKEA exception with no message or cause.
	 */
	public JKEAException() {
		super();
	}

	/**
	 * Constructs a new JKEA exception with the specified message.
	 *
	 * @param message
	 *            the message describing this exception
	 */
	public JKEAException(String message) {
		super(message);
	}

	/**
	 * Constructs a new JKEA exception with the specified message and cause.
	 *
	 * @param message
	 *            the message describing this exception
	 * @param cause
	 *            the cause of this exception
	 */
	public JKEAException(String message, Throwable cause) {
		super(message, cause);
	}

	/**
	 * Constructs a new JKEA exception with the specified cause.
	 *
	 * @param cause
	 *            the cause of this exception
	 */
	public JKEAException(Throwable cause) {
		super(cause);
	}

}
