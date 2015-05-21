package jkea.core.solver;

import java.util.Properties;

import jkea.core.Attack;
import jkea.core.JKEAException;
import jkea.core.Solver;
import jkea.core.spi.ProviderNotFoundException;
import jkea.core.spi.SolverProvider;
import jkea.util.TypedProperties;

public class StandardSolvers extends SolverProvider {

	/**
	 * Constructs the standard solver provider.
	 */
	public StandardSolvers() {
		super();
	}

	@Override
	public Solver getSolver(String name, Properties properties, Attack attack) {
		final TypedProperties typedProperties = new TypedProperties(properties);

		try {
			if (name.equalsIgnoreCase("glowacz"))
				return newGlowacz(typedProperties, attack);
			else
				return null;
		} catch (final JKEAException e) {
			throw new ProviderNotFoundException(name, e);
		}
	}

	private Solver newGlowacz(TypedProperties properties, Attack attack) {
		int nBins = 50000;
		if (properties.contains("nBins"))
			nBins = (int) properties.getDouble("nBins", 50000);
		return new Glowacz(attack, nBins);
	}

}
