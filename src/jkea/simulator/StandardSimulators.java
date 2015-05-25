package jkea.simulator;

import java.util.Properties;

import jkea.core.JKEAException;
import jkea.core.Simulator;
import jkea.core.spi.ProviderNotFoundException;
import jkea.core.spi.SimulatorProvider;
import jkea.util.TypedProperties;

/**
 * A provider of standard simulators. The following table contains all available
 * simulators and the customisable properties. All simulators support the
 * properties {@code traces} and {@code variance} to customise the number of
 * traces and level of variance respectively.
 * <p>
 * <table width="100%" border="1" cellpadding="3" cellspacing="0">
 * <tr class="TableHeadingColor">
 * <th width="50%" align="left">Name</th>
 * <th width="50%" align="left">Properties</th>
 * </tr>
 * <tr>
 * <td>AESHammingWeight</td>
 * <td>{@code vectors}, {@code vector length}</td>
 * </tr>
 * </table>
 */
public class StandardSimulators extends SimulatorProvider {

	/**
	 * Constructs a simulator provider.
	 */
	public StandardSimulators() {
		super();
	}

	@Override
	public Simulator getSimulator(String name, Properties properties) {
		final TypedProperties typedProperties = new TypedProperties(properties);

		nTraces = typedProperties.getInt("traces", 30);
		variance = typedProperties.getDouble("variance", 2.0);

		try {
			if (name.equalsIgnoreCase("aeshammingweight")) {
				return newAESHammingWeight(typedProperties);
			} else {
				return null;
			}
		} catch (final JKEAException e) {
			throw new ProviderNotFoundException(name, e);
		}
	}

	/**
	 * Creates a new instance of {@link AESHammingWeight}.
	 *
	 * @param properties
	 *            customisable properties to be passed to the simulator
	 * @return a new instance of {@code AESHammingWeigth}
	 */
	private Simulator newAESHammingWeight(TypedProperties properties) {
		int nVectors = properties.getInt("vectors", 16);
		int vectorLength = properties.getInt("vectorlength", 256);

		return new AESHammingWeight(nTraces, variance, nVectors, vectorLength);
	}

}
