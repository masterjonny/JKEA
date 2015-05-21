package jkea.core.spi;

import java.nio.file.ProviderNotFoundException;
import java.util.Properties;

import jkea.core.Attack;
import jkea.core.Solver;

/**
 * Defines an SPI for solver. Solvers are solvers by a unique name and may be
 * given optional {@link Properties}. The methods of the provider must return
 * {@code null} if the solver is not supported by the provider.
 * <p>
 * If the provider can supply the solver but an error occurred during
 * instantiation, the provider may throw a {@link ProviderNotFoundException}
 * along with the details causing the exception.
 * <p>
 * To provide a custom {@code SolverProiver}, first extend this class and
 * implement the abstract method. Next, build a JAR file containing the custom
 * provider. Within the JAR file, create the file
 * {@code META-INF/services/jkea.solver.SolverProvider} containing on a single
 * line the class name of the custom provider. Lastly, add this JAR file to the
 * classpath. Once these steps are completed, the solvers(s) are now accessible
 * via the {@link SolverFactory#getSolver} methods.
 * <p>
 * As solver names are often used in file names, it is best to avoid characters
 * which are not compatible with the file system. It is suggested that names
 * match the following regular expression: {@code ^[a-zA-Z0-9()\-,]+$}.
 */
public abstract class SolverProvider {

	/**
	 * Constructs an solver provider.
	 */
	public SolverProvider() {
		super();
	}

	/**
	 * Returns the solver with the specified name, or {@code null} if this
	 * provider does not support the solver. An optional set of properties may
	 * be provided to further define the solver; however, the provider is
	 * expected to supply default properties if none are provided.
	 *
	 * @param name
	 *            the solver name
	 * @param properties
	 *            optional properties for the solver
	 * @param attack
	 *            the attack traces
	 * @return the solver with the specified name, or {@code null} if this
	 *         provider does not support the solver
	 */
	public abstract Solver getSolver(String name, Properties properties,
			Attack attack);

}
