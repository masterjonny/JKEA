package jkea;

import java.math.BigDecimal;
import java.util.Properties;

import jkea.core.Distinguisher;
import jkea.core.Simulator;
import jkea.core.Solver;
import jkea.core.spi.DistinguisherFactory;
import jkea.core.spi.SimulatorFactory;
import jkea.core.spi.SolverFactory;
import jkea.util.TypedProperties;

/**
 * Configures and executes solvers while hiding the underlying boilerplate code
 * needed to setup and safely execute an solver.
 *
 * The simulator and solver must be specified prior to {@link #rank()} or
 * {@link #enumerate()}.
 */
public class Executor {

	/**
	 * The solver name.
	 */
	private String solverName;

	/**
	 * The distinguisher name.
	 */
	private String distinguisherName;

	/**
	 * The simulator name.
	 */
	private String simulatorName;

	/**
	 * The properties passed to both the simulator and solvers.
	 */
	private final TypedProperties properties;

	/**
	 * The solver factory for creating solver instances, or {@code null} if the
	 * default solver factory is to be used.
	 */
	private SolverFactory solverFactory;

	/**
	 * The distinguisher factory for creating distinguisher instances, or
	 * {@code null} if the default distinguisher factory is to be used.
	 */
	private DistinguisherFactory distinguisherFactory;

	/**
	 * The simulator factory for creating simulator instances, or {@code null}
	 * if the default simulator factory is to be used.
	 */
	private SimulatorFactory simulatorFactory;

	/**
	 * Create a new executor with default settings.
	 */
	public Executor() {
		super();

		properties = new TypedProperties();
	}

	/**
	 * Clears the properties.
	 *
	 * @return a reference to this executor
	 */
	public Executor clearProperties() {
		properties.clear();

		return this;
	}

	/**
	 * Enumerate keys until the correct key is located.
	 *
	 * @return the number of keys enumerated before the correct key was located
	 */
	public BigDecimal enumerate() {
		return run(1);
	}

	/**
	 * Estimate the rank of the key within the search space.
	 *
	 * @return the estimated rank of the key within the search space
	 */
	public BigDecimal rank() {
		return run(0);
	}

	/**
	 * Ensure the user has set the required parameters, initialise the
	 * simulator, distinguisher and solver and begin execution.
	 *
	 * @param executionMode
	 *            the execution mode to run in. {@code 0} for ranking of the key
	 *            and {@code 1} for enumeration
	 * @return the estiamted rank of the key or the number of keys enumerated
	 *         depending on execution mode
	 */
	protected BigDecimal run(int executionMode) {
		if (solverName == null) {
			throw new IllegalArgumentException("no solver specified");
		}

		if (simulatorName == null) {
			throw new IllegalArgumentException("no simulator specified");
		}

		Simulator simulator = null;
		Distinguisher distinguisher = null;
		Solver solver = null;

		if (simulatorFactory == null) {
			simulator = SimulatorFactory.getInstance().getSimulator(
					simulatorName, properties.getProperties());
		} else {
			simulator = simulatorFactory.getSimulator(simulatorName,
					properties.getProperties());
		}

		if (distinguisherName == null) {
			distinguisherName = simulator.getDefaultDistinguisher();
		}

		if (distinguisherFactory == null) {
			distinguisher = DistinguisherFactory.getInstance()
					.getDistinguisher(distinguisherName, simulator);
		} else {
			distinguisher = distinguisherFactory.getDistinguisher(
					distinguisherName, simulator);
		}

		simulator.setDistinguisher(distinguisher);
		simulator.runAttack();

		if (solverFactory == null) {
			solver = SolverFactory.getInstance().getSolver(solverName,
					properties.getProperties(), simulator);
		} else {
			solver = solverFactory.getSolver(solverName,
					properties.getProperties(), simulator);
		}

		if (executionMode == 1) {
			return solver.enumerate();
		} else {
			return solver.rank();
		}
	}

	/**
	 * Sets the distinguisher factory used by this executor.
	 *
	 * @param distinguisherFactory
	 *            the distinguisher factory
	 * @return a reference to this executor
	 */
	public Executor usingDistinguisherFactory(
			DistinguisherFactory distinguisherFactory) {
		this.distinguisherFactory = distinguisherFactory;

		return this;
	}

	/**
	 * Sets the simulator factory used by this executor.
	 *
	 * @param simulatorFactory
	 *            the simulator factory
	 * @return a reference to this executor
	 */
	public Executor usingSimulatorFactory(SimulatorFactory simulatorFactory) {
		this.simulatorFactory = simulatorFactory;

		return this;
	}

	/**
	 * Sets the solver factory used by this executor,
	 *
	 * @param solverFactory
	 *            the solver factory
	 * @return a reference to this executor
	 */
	public Executor usingSolverFactory(SolverFactory solverFactory) {
		this.solverFactory = solverFactory;

		return this;
	}

	public Executor withDistinguisher(String distinguisherName) {
		this.distinguisherName = distinguisherName;

		return this;
	}

	/**
	 * Sets all properties. This will clear any existing properties.
	 *
	 * @param properties
	 *            the properties
	 * @return a reference to this executor
	 */
	public Executor withProperties(Properties properties) {
		this.properties.clear();
		this.properties.addAll(properties);

		return this;
	}

	/**
	 * Sets a {@code boolean} property.
	 *
	 * @param key
	 *            the property key
	 * @param value
	 *            the property value
	 * @return a reference to this executor
	 */
	public Executor withProperty(String key, boolean value) {
		properties.setBoolean(key, value);

		return this;
	}

	/**
	 * Sets a {@code byte} property.
	 *
	 * @param key
	 *            the property key
	 * @param value
	 *            the property value
	 * @return a reference to this executor
	 */
	public Executor withProperty(String key, byte value) {
		properties.setByte(key, value);

		return this;
	}

	/**
	 * Sets a {@code byte} array property.
	 *
	 * @param key
	 *            the property key
	 * @param values
	 *            the property value
	 * @return a reference to this executor
	 */
	public Executor withProperty(String key, byte[] values) {
		properties.setByteArray(key, values);

		return this;
	}

	/**
	 * Sets a {@code double} property.
	 *
	 * @param key
	 *            the property key
	 * @param value
	 *            the property value
	 * @return a reference to this executor
	 */
	public Executor withProperty(String key, double value) {
		properties.setDouble(key, value);

		return this;
	}

	/**
	 * Sets a {@code double} array property.
	 *
	 * @param key
	 *            the property key
	 * @param values
	 *            the property value
	 * @return a reference to this executor
	 */
	public Executor withProperty(String key, double[] values) {
		properties.setDoubleArray(key, values);

		return this;
	}

	/**
	 * Sets a {@code float} property.
	 *
	 * @param key
	 *            the property key
	 * @param value
	 *            the property value
	 * @return a reference to this executor
	 */
	public Executor withProperty(String key, float value) {
		properties.setFloat(key, value);

		return this;
	}

	/**
	 * Sets a {@code float} array property.
	 *
	 * @param key
	 *            the property key
	 * @param values
	 *            the property value
	 * @return a reference to this executor
	 */
	public Executor withProperty(String key, float[] values) {
		properties.setFloatArray(key, values);

		return this;
	}

	/**
	 * Sets an {@code int} property.
	 *
	 * @param key
	 *            the property key
	 * @param value
	 *            the property value
	 * @return a reference to this executor
	 */
	public Executor withProperty(String key, int value) {
		properties.setInt(key, value);

		return this;
	}

	/**
	 * Sets an {@code int} array property.
	 *
	 * @param key
	 *            the property key
	 * @param values
	 *            the property value
	 * @return a reference to this executor
	 */
	public Executor withProperty(String key, int[] values) {
		properties.setIntArray(key, values);

		return this;
	}

	/**
	 * Sets a {@code long} property.
	 *
	 * @param key
	 *            the property key
	 * @param value
	 *            the property value
	 * @return a reference to this executor
	 */
	public Executor withProperty(String key, long value) {
		properties.setLong(key, value);

		return this;
	}

	/**
	 * Sets a {@code long} array property.
	 *
	 * @param key
	 *            the property key
	 * @param values
	 *            the property value
	 * @return a reference to this executor
	 */
	public Executor withProperty(String key, long[] values) {
		properties.setLongArray(key, values);

		return this;
	}

	/**
	 * Sets a {@code short} property.
	 *
	 * @param key
	 *            the property key
	 * @param value
	 *            the property value
	 * @return a reference to this executor
	 */
	public Executor withProperty(String key, short value) {
		properties.setShort(key, value);

		return this;
	}

	/**
	 * Sets a {@code short} array property.
	 *
	 * @param key
	 *            the property key
	 * @param values
	 *            the property value
	 * @return a reference to this executor
	 */
	public Executor withProperty(String key, short[] values) {
		properties.setShortArray(key, values);

		return this;
	}

	/**
	 * Sets a property.
	 *
	 * @param key
	 *            the property key
	 * @param value
	 *            the property value
	 * @return a reference to this executor
	 */
	public Executor withProperty(String key, String value) {
		properties.setString(key, value);

		return this;
	}

	/**
	 * Sets a {@code String} array property.
	 *
	 * @param key
	 *            the property key
	 * @param values
	 *            the property value
	 * @return a reference to this executor
	 */
	public Executor withProperty(String key, String[] values) {
		properties.setStringArray(key, values);

		return this;
	}

	/**
	 * The name of the simulator to use.
	 *
	 * @param simulatorName
	 *            the simulator name
	 * @return a reference to this executor
	 */
	public Executor withSimulator(String simulatorName) {
		this.simulatorName = simulatorName;

		return this;
	}

	/**
	 * The name of the solver to use.
	 *
	 * @param solverName
	 *            the solver name
	 * @return a reference to this executor
	 */
	public Executor withSolver(String solverName) {
		this.solverName = solverName;

		return this;
	}

}
