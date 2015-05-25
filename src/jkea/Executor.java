package jkea;

import java.util.Properties;

import jkea.core.Distinguisher;
import jkea.core.Simulator;
import jkea.core.Solver;
import jkea.core.spi.DistinguisherFactory;
import jkea.core.spi.SimulatorFactory;
import jkea.core.spi.SolverFactory;
import jkea.util.TypedProperties;

public class Executor {

	private String solverName;

	private String distinguisherName;
	
	private String simulatorName;
	
	private TypedProperties properties;

	private SolverFactory solverFactory;
	
	private DistinguisherFactory distinguisherFactory;
	
	private SimulatorFactory simulatorFactory;

	public Executor() {
		super();

		properties = new TypedProperties();
	}

	public Executor usingSolverFactory(SolverFactory solverFactory) {
		this.solverFactory = solverFactory;

		return this;
	}

	public Executor usingDistinguisherFactory(DistinguisherFactory distinguisherFactory) {
		this.distinguisherFactory = distinguisherFactory;

		return this;
	}	
	
	public Executor usingSimulatorFactory(SimulatorFactory simulatorFactory) {
		this.simulatorFactory = simulatorFactory;

		return this;
	}	
	

	public Executor withSimulator(String simulatorName) {
		this.simulatorName = simulatorName;
		
		return this;
	}
	
	public Executor withSolver(String solverName) {
		this.solverName = solverName;
		
		return this;
	}
	
	public Executor withDistinguisher(String distinguisherName) {
		this.distinguisherName = distinguisherName;
		
		return this;
	}
	
	/**
	 * Sets a property.
	 * 
	 * @param key the property key
	 * @param value the property value
	 * @return a reference to this executor
	 */
	public Executor withProperty(String key, String value) {
		properties.setString(key, value);
		
		return this;
	}
	
	/**
	 * Sets a {@code float} property.
	 * 
	 * @param key the property key
	 * @param value the property value
	 * @return a reference to this executor
	 */
	public Executor withProperty(String key, float value) {
		properties.setFloat(key, value);
		
		return this;
	}
	
	/**
	 * Sets a {@code double} property.
	 * 
	 * @param key the property key
	 * @param value the property value
	 * @return a reference to this executor
	 */
	public Executor withProperty(String key, double value) {
		properties.setDouble(key, value);
		
		return this;
	}
	
	/**
	 * Sets a {@code byte} property.
	 * 
	 * @param key the property key
	 * @param value the property value
	 * @return a reference to this executor
	 */
	public Executor withProperty(String key, byte value) {
		properties.setByte(key, value);
		
		return this;
	}
	
	/**
	 * Sets a {@code short} property.
	 * 
	 * @param key the property key
	 * @param value the property value
	 * @return a reference to this executor
	 */
	public Executor withProperty(String key, short value) {
		properties.setShort(key, value);
		
		return this;
	}
	
	/**
	 * Sets an {@code int} property.
	 * 
	 * @param key the property key
	 * @param value the property value
	 * @return a reference to this executor
	 */
	public Executor withProperty(String key, int value) {
		properties.setInt(key, value);
		
		return this;
	}
	
	/**
	 * Sets a {@code long} property.
	 * 
	 * @param key the property key
	 * @param value the property value
	 * @return a reference to this executor
	 */
	public Executor withProperty(String key, long value) {
		properties.setLong(key, value);
		
		return this;
	}
	
	/**
	 * Sets a {@code boolean} property.
	 * 
	 * @param key the property key
	 * @param value the property value
	 * @return a reference to this executor
	 */
	public Executor withProperty(String key, boolean value) {
		properties.setBoolean(key, value);
		
		return this;
	}
	
	/**
	 * Sets a {@code String} array property.
	 * 
	 * @param key the property key
	 * @param values the property value
	 * @return a reference to this executor
	 */
	public Executor withProperty(String key, String[] values) {
		properties.setStringArray(key, values);
		
		return this;
	}
	
	/**
	 * Sets a {@code float} array property.
	 * 
	 * @param key the property key
	 * @param values the property value
	 * @return a reference to this executor
	 */
	public Executor withProperty(String key, float[] values) {
		properties.setFloatArray(key, values);
		
		return this;
	}
	
	/**
	 * Sets a {@code double} array property.
	 * 
	 * @param key the property key
	 * @param values the property value
	 * @return a reference to this executor
	 */
	public Executor withProperty(String key, double[] values) {
		properties.setDoubleArray(key, values);
		
		return this;
	}
	
	/**
	 * Sets a {@code byte} array property.
	 * 
	 * @param key the property key
	 * @param values the property value
	 * @return a reference to this executor
	 */
	public Executor withProperty(String key, byte[] values) {
		properties.setByteArray(key, values);
		
		return this;
	}
	
	/**
	 * Sets a {@code short} array property.
	 * 
	 * @param key the property key
	 * @param values the property value
	 * @return a reference to this executor
	 */
	public Executor withProperty(String key, short[] values) {
		properties.setShortArray(key, values);
		
		return this;
	}
	
	/**
	 * Sets an {@code int} array property.
	 * 
	 * @param key the property key
	 * @param values the property value
	 * @return a reference to this executor
	 */
	public Executor withProperty(String key, int[] values) {
		properties.setIntArray(key, values);
		
		return this;
	}
	
	/**
	 * Sets a {@code long} array property.
	 * 
	 * @param key the property key
	 * @param values the property value
	 * @return a reference to this executor
	 */
	public Executor withProperty(String key, long[] values) {
		properties.setLongArray(key, values);
		
		return this;
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
	 * Sets all properties.  This will clear any existing properties.
	 * 
	 * @param properties the properties
	 * @return a reference to this executor
	 */
	public Executor withProperties(Properties properties) {
		this.properties.clear();
		this.properties.addAll(properties);
		
		return this;
	}
	
	public long enumerate() {
		return run(1);
	}
	
	public long rank() {
		return run(0);
	}
	
	protected long run(int executionMode) {
		if(solverName == null) {
			throw new IllegalArgumentException("no solver specified");
		}
		
		if (simulatorName == null) {
			throw new IllegalArgumentException("no simulator specified");
		}
		
		Simulator simulator = null;
		Distinguisher distinguisher = null;
		Solver solver = null;

		if(simulatorFactory == null) {
			simulator = SimulatorFactory.getInstance().getSimulator(simulatorName, properties.getProperties());
		} else {
			simulator = simulatorFactory.getSimulator(simulatorName, properties.getProperties());
		}
		
		if(distinguisherFactory == null) {
			distinguisher = DistinguisherFactory.getInstance().getDistinguisher(distinguisherName, simulator);
		} else {
			distinguisher = distinguisherFactory.getDistinguisher(distinguisherName, simulator);
		}
		
		simulator.setDistinguisher(distinguisher);
		simulator.runAttack();
		
		if(solverFactory == null) {
			solver = SolverFactory.getInstance().getSolver(solverName, properties.getProperties(), simulator);
		} else {
			solver = solverFactory.getSolver(solverName, properties.getProperties(), simulator);
		}
		
		
		if(executionMode == 1) {
			return solver.enumerate();
		} else {
			return solver.rank();
		}
	}

}
