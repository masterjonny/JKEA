package jkea.analysis.tools;

import java.io.IOException;
import java.util.Properties;

import jkea.core.Distinguisher;
import jkea.core.JKEAException;
import jkea.core.Simulator;
import jkea.core.Solver;
import jkea.core.spi.DistinguisherFactory;
import jkea.core.spi.SimulatorFactory;
import jkea.core.spi.SolverFactory;
import jkea.util.CommandLineUtility;

import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.Option;
import org.apache.commons.cli.Options;

public class Solve extends CommandLineUtility {

	/**
	 * Starts the command line utility for solving an key rank or enumeration
	 * problem.
	 *
	 * @param args
	 *            the command line arguments
	 * @throws Exception
	 *             if an error occurred
	 */
	public static void main(String[] args) throws Exception {
		new Solve().start(args);
	}

	/**
	 * Constructs the command line utility for solving an key rank or
	 * enumeration problem.
	 */
	public Solve() {
		super();
	}

	@Override
	public Options getOptions() {
		Options options = super.getOptions();

		options.addOption("a", "solver", true, "name");
		options.addOption("s", "simulator", true, "name");
		options.addOption("r", false, "run a ranking operation");
		options.addOption("e", false, "run an enumeration operation");

		options.addOption(Option.builder("properties").required(true)
				.argName("p1=v1;p2=v2;...").valueSeparator(';').build());

		return options;
	}

	@Override
	public void run(CommandLine commandLine) throws IOException {
		Properties properties = new Properties();

		if (commandLine.hasOption("properties")) {
			for (String property : commandLine.getOptionValues("properties")) {
				String[] tokens = property.split("=");

				if (tokens.length == 2) {
					properties.setProperty(tokens[0], tokens[1]);
				} else {
					throw new JKEAException("malformed property argument");
				}
			}
		}

		Simulator simulator = null;
		Distinguisher distinguisher = null;
		Solver solver = null;

		simulator = SimulatorFactory.getInstance().getSimulator(
				commandLine.getOptionValue("simulator"), properties);

		distinguisher = DistinguisherFactory.getInstance().getDistinguisher(
				simulator.getDefaultDistinguisher(), simulator);

		simulator.setDistinguisher(distinguisher);
		simulator.runAttack();

		solver = SolverFactory.getInstance().getSolver(
				commandLine.getOptionValue("solver"), properties, simulator);

		if (commandLine.hasOption("e") || commandLine.hasOption("r")) {
			if (commandLine.hasOption("e")) {
				System.out.println("Starting enumeration");
				System.out.println("Keys enumerated: " + solver.enumerate());
			}
			if (commandLine.hasOption("r")) {
				System.out.println("Starting ranking");
				System.out.println("Estimated key rank: " + solver.rank());
			}
		} else {
			System.out.println("No execution operation defined");
		}
	}

}
