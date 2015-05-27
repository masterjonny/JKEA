package jkea.core.spi;

import java.util.Iterator;
import java.util.Properties;
import java.util.ServiceConfigurationError;
import java.util.ServiceLoader;

import jkea.core.Simulator;
import jkea.core.Solver;
import jkea.core.solver.StandardSolvers;

/**
 * Factory for creating solver instances. See {@link SolverProvider} for details
 * on adding new providers.
 * <p>
 * This class is thread safe.
 */
public class SolverFactory {

	/**
	 * The default solver factory.
	 */
	private static SolverFactory instance;

	/**
	 * The static service loader for loading solver providers.
	 */
	private static final ServiceLoader<SolverProvider> PROVIDERS;

	/**
	 * Instantiates the static {@code PROVIDERS} and {@code instance} objects.
	 */
	static {
		PROVIDERS = ServiceLoader.load(SolverProvider.class);
		instance = new SolverFactory();
	}

	/**
	 * Returns the default solver factory.
	 *
	 * @return the default solver factory
	 */
	public static synchronized SolverFactory getInstance() {
		return instance;
	}

	/**
	 * Sets the default solver factory.
	 *
	 * @param instance
	 *            the default solver factory
	 */
	public static synchronized void setInstance(SolverFactory instance) {
		SolverFactory.instance = instance;
	}

	/**
	 * Constructs a new solver factory.
	 */
	public SolverFactory() {
		super();
	}

	/**
	 * Searches through all discovered {@code SolverProvider} instances,
	 * returning an instance of the solver with the registered name. The solver
	 * is initialised using implementation-specific properties. This method must
	 * throw an {@link ProviderNotFoundException} if no suitable solver is
	 * found.
	 *
	 * @param name
	 *            the name identifying the solver
	 * @param properties
	 *            the implementation-specific properties
	 * @param attack
	 *            the attack data to process
	 * @return an instance of the solver with the registered name
	 * @throws ProviderNotFoundException
	 *             if no provider for the solver is available
	 */
	public synchronized Solver getSolver(String name, Properties properties,
			Simulator attack) {
		Iterator<SolverProvider> ps = PROVIDERS.iterator();

		if (!ps.hasNext()) {
			Solver solver = instansiateSolver(new StandardSolvers(), name,
					properties, attack);

			if (solver != null) {
				return solver;
			}
		}

		while (ps.hasNext()) {
			Solver solver = instansiateSolver(ps.next(), name, properties,
					attack);

			if (solver != null) {
				return solver;
			}
		}

		throw new ProviderNotFoundException(name);
	}

	/**
	 * Attempts to instantiate the given solver using the given provider.
	 *
	 * @param provider
	 *            the solver provider
	 * @param name
	 *            the name identifying the solver
	 * @param properties
	 *            the implementation-specific properties
	 * @param attack
	 *            the attack data to process
	 * @return an instance of the solver with the registered name; or
	 *         {@code null} if the provider does not implement the solver
	 */
	private Solver instansiateSolver(SolverProvider provider, String name,
			Properties properties, Simulator attack) {
		try {
			return provider.getSolver(name, properties, attack);
		} catch (ServiceConfigurationError e) {
			System.err.println(e.getMessage());
		}

		return null;
	}

}
