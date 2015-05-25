package jkea.core.spi;

import java.util.Iterator;
import java.util.Properties;
import java.util.ServiceConfigurationError;
import java.util.ServiceLoader;

import jkea.core.Simulator;
import jkea.simulator.StandardSimulators;

/**
 * Factory for simulating side channel attacks. See {@link SimulatorProvider}
 * for details on adding new providers.
 * <p>
 * This class is thread safe.
 */
public class SimulatorFactory {

	/**
	 * The static service loader for loading simulator providers.
	 */
	private static final ServiceLoader<SimulatorProvider> PROVIDERS;

	/**
	 * The default simulator factory.
	 */
	private static SimulatorFactory instance;

	/**
	 * Instantiates the static {@code PROVIDERS} and {@code instance} objects.
	 */
	static {
		PROVIDERS = ServiceLoader.load(SimulatorProvider.class);
		instance = new SimulatorFactory();
	}

	/**
	 * Returns the default simulator factory.
	 *
	 * @return the default simulator factory
	 */
	public static synchronized SimulatorFactory getInstance() {
		return instance;
	}

	/**
	 * Sets the default simulator factory.
	 *
	 * @param instance
	 *            the default simulator factory
	 */
	public static synchronized void setInstance(SimulatorFactory instance) {
		SimulatorFactory.instance = instance;
	}

	/**
	 * Constructs a new simulator factory.
	 */
	public SimulatorFactory() {
		super();
	}

	/**
	 * Searches through all discovered {@code SimulatorProvider} instances,
	 * returning an instance of the simulator with the registered name. This
	 * method must throw an {@link ProviderNotFoundException} if no matching
	 * simulator is found.
	 *
	 * @param name
	 *            the name identifying the simulator
	 * @param properties
	 *            the properties to configure the simulator instance
	 * @return an instance of the simulator with the registered name
	 * @throws ProviderNotFoundException
	 *             if no provider for the simulator is available
	 */
	public synchronized Simulator getSimulator(String name,
			Properties properties) {
		final Iterator<SimulatorProvider> ps = PROVIDERS.iterator();

		if (!ps.hasNext()) {
			final Simulator simulator = instansiateSimulator(
					new StandardSimulators(), name, properties);

			if (simulator != null) {
				return simulator;
			}
		}

		while (ps.hasNext()) {
			final Simulator simulator = instansiateSimulator(ps.next(), name,
					properties);

			if (simulator != null) {
				return simulator;
			}
		}

		throw new ProviderNotFoundException(name);
	}

	/**
	 * Attempts to instantiate the given simulator using the given provider.
	 *
	 * @param provider
	 *            the simulator provider
	 * @param name
	 *            the name identifying the simulator
	 * @param properties
	 *            the properties to configure the simulator instance
	 * @return an instance of the simulator with the registered name; or
	 *         {@code null} if the provider does not implement the simulator
	 */
	private Simulator instansiateSimulator(SimulatorProvider provider,
			String name, Properties properties) {
		try {
			return provider.getSimulator(name, properties);
		} catch (final ServiceConfigurationError e) {
			System.err.println(e.getMessage());
		}

		return null;
	}
}
