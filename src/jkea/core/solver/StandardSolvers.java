package jkea.core.solver;

import java.util.Properties;

import jkea.core.JKEAException;
import jkea.core.Simulator;
import jkea.core.Solver;
import jkea.core.spi.ProviderNotFoundException;
import jkea.core.spi.SolverProvider;
import jkea.util.TypedProperties;

/**
 * A provider of standard solvers. The following table contains all available
 * algorithms and the customisable properties.
 * <p>
 * <table width="100%" border="1" cellpadding="3" cellspacing="0">
 * <tr class="TableHeadingColor">
 * <th width="10%" align="left">Name</th>
 * <th width="20%" align="left">Ranking</th>
 * <th width="20%" align="left">Enumeration</th>
 * <th width="50%" align="left">Properties</th>
 * </tr>
 * <tr>
 * <td>Glowacz (2014)</td>
 * <td>Yes</td>
 * <td>No</td>
 * <td>{@code bins}</td>
 * </tr>
 * </table>
 */
public class StandardSolvers extends SolverProvider {

	/**
	 * Constructs the standard solver provider.
	 */
	public StandardSolvers() {
		super();
	}

	@Override
	public Solver getSolver(String name, Properties properties, Simulator attack) {
		TypedProperties typedProperties = new TypedProperties(properties);

		try {
			if (name.equalsIgnoreCase("glowacz")) {
				return newGlowacz(typedProperties, attack);
			} else if (name.equalsIgnoreCase("kea")) {
				return newKEA(typedProperties, attack);
			} else {
				return null;
			}
		} catch (JKEAException e) {
			throw new ProviderNotFoundException(name, e);
		}
	}

	/**
	 * Returns a new {@link Glowacz} instance.
	 *
	 * @param properties
	 *            the properties for customising the {@code Glowacz} instance
	 * @param attack
	 *            the attack
	 * @return a new {@code Glowacz} instance
	 */
	private Solver newGlowacz(TypedProperties properties, Simulator attack) {
		int nBins = (int) properties.getDouble("bins", 5000);
		return new Glowacz(attack, nBins);
	}

	private Solver newKEA(TypedProperties properties, Simulator attack) {
		double precision = properties.getDouble("precision", 7.5);
		return new KEA(attack, precision);
	}

}
