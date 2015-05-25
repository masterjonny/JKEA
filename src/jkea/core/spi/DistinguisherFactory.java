package jkea.core.spi;

import jkea.core.Distinguisher;
import jkea.core.Simulator;
import jkea.distinguisher.TemplateAttack;

/**
 * Factory for creating distinguisher instances. The table below shows supported
 * distinguishers, and names used in
 * {@link #getDistinguisher(String, Simulator)}.
 * <p>
 * <table width="100%" border="1" cellpadding="3" cellspacing="0">
 * <tr class="TableHeadingColor">
 * <th width="50%" align="left">Operator</th>
 * <th width="50%" align="left">Name</th>
 * </tr>
 * <tr>
 * <td>{@link TemplateAttack}</td>
 * <td>{@code template}</td>
 * </tr>
 * </table>
 */
public class DistinguisherFactory {

	/**
	 * The default distinguisher factory.
	 */
	private static DistinguisherFactory instance;

	/**
	 * Instantiates the static {@code instance} object.
	 */
	static {
		instance = new DistinguisherFactory();
	}

	/**
	 * Returns the default distinguisher factory.
	 *
	 * @return the default distinguisher factory
	 */
	public static synchronized DistinguisherFactory getInstance() {
		return instance;
	}

	/**
	 * Sets the default distinguisher factory.
	 *
	 * @param instance
	 *            the default distinguisher factory
	 */
	public static synchronized void setInstance(DistinguisherFactory instance) {
		DistinguisherFactory.instance = instance;
	}

	/**
	 * Constructs a new distinguisher factory.
	 */
	public DistinguisherFactory() {
		super();
	}

	/**
	 * Returns an instance of the distinguisher with the specified name. This
	 * method must throw an {@link ProviderNotFoundException} if no suitable
	 * distinguisher is found. If {@code name} is null, the factory should
	 * return a default distinguisher appropriate for the simulation.
	 *
	 * @param name
	 *            the name identifying the distinguisher
	 * @param simulator
	 *            the simulation being performed
	 * @return an instance of the distinguisher with the specified name
	 * @throws ProviderNotFoundException
	 *             if no provider for the distinguisher is available
	 */
	public Distinguisher getDistinguisher(String name, Simulator simulator) {
		if (name == null) {
			return getDistinguisher(simulator.getDefaultDistinguisher(),
					simulator);
		}

		if (name.equalsIgnoreCase("template")) {
			return new TemplateAttack();
		} else {
			throw new ProviderNotFoundException(name);
		}
	}
}