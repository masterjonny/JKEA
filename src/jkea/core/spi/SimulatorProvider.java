package jkea.core.spi;

import java.util.Properties;

import jkea.core.Simulator;

/**
 * Defines an SPI for instantiation of side channel simulators. Simulators are
 * identified by a unique name. The methods of the provider must return
 * {@code null} if the simulator is not supported by the provider.
 * <p>
 * If the simulator can supply the problem but an error occurred during
 * instantiation, the provider may throw a {@link ProviderNotFoundException}
 * along with the details causing the exception.
 * <p>
 * To provide a custom {@code SimulatorProvider}, first extend this class and
 * implement the abstract method. Next, build a JAR file containing the custom
 * provider. Within the JAR file, create the file
 * {@code META-INF/services/jkea.simulator.SimulatorProvider} containing on a
 * single line the class name of the custom provider. Lastly, add this JAR file
 * to the classpath. Once these steps are completed, the problem(s) are now
 * accessible via the {@link SimulatorFactory#getSimulator(String, Properties)}
 * method.
 * <p>
 * As simulator names are often used in file names, it is best to avoid
 * characters which are not compatible with the file system. It is suggested
 * that names match the following regular expression:
 * {@code ^[a-zA-Z0-9()\-,]+$}.
 */
public abstract class SimulatorProvider {

	/**
	 * The number of traces to simulate.
	 */
	protected int nTraces;
	
	/**
	 * The amount of noise present in the simulated traces.
	 */
	protected double variance;

	
	/**
	 * Constructs an simulator provider.
	 */
	public SimulatorProvider() {
		super();
	}

	/**
	 * Returns the simulator with the specified name, or {@code null} if this
	 * provider does not support the problem.
	 *
	 * @param name
	 *            the simulator name
	 * @param properties
	 *            the properties to be passed to the simulator instance
	 * @return the simulator with the specified name, or {@code null} if this
	 *         provider does not support the simulator
	 */
	public abstract Simulator getSimulator(String name, Properties properties);
}
