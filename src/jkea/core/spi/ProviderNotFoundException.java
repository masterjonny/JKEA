package jkea.core.spi;

import java.text.MessageFormat;

import jkea.core.JKEAException;

/**
 * Exception indicating a provider was not found.
 */
public class ProviderNotFoundException extends JKEAException {

	private static final long serialVersionUID = 7885715232193445329L;

	/**
	 * Constructs an exception indicating the specified provider was not found.
	 *
	 * @param name
	 *            the provider name
	 */
	public ProviderNotFoundException(String name) {
		this(name, null);
	}

	/**
	 * Constructs an exception indicating the specified provider was not found.
	 *
	 * @param name
	 *            the provider name
	 * @param cause
	 *            the cause of this exception
	 */
	public ProviderNotFoundException(String name, Throwable cause) {
		super(MessageFormat.format("no provider for {0}", name), cause);
	}

}
