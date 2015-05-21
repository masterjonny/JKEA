package jkea.core.spi;

import jkea.core.Attack;

public abstract class AttackProvider {

	/**
	 * Constructs an attack provider.
	 */
	public AttackProvider() {
		super();
	}

	/**
	 * Returns the attack with the specified name, or {@code null} if this
	 * provider does not support the problem.
	 *
	 * @param name
	 *            the attack name
	 * @return the attack with the specified name, or {@code null} if this
	 *         provider does not support the attack
	 */
	public abstract Attack getProblem(String name);
}
