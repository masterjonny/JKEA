package runtimes;

import jkea.Executor;
import jkea.core.Simulator;
import jkea.core.Solver;
import jkea.core.spi.SimulatorFactory;
import jkea.core.spi.SolverFactory;
import jkea.util.TypedProperties;

public class ExampleRuntime {

	public static void main(String[] args) {
		
		// !!DO NOT RUN THIS FILE!! 
		// Use the examples here as the starting point for your own needs
		
		//
		// See: StandardSolvers and StandardSimulators for the full list of
		// customisable properties
		//

		// BUILDING AN EXECUTOR
		// ------------------------------------------------------------------ //

		// EXAMPLE 1 - THE BARE MINIMUM

		new Executor().withSimulator("aeshammingweight").withSolver("glowacz")
		.rank();

		// EXAMPLE 2 - CHOOSE A DISTINGUISHER AND ACCEPT DEFAULTS

		new Executor().withSimulator("aeshammingweight")
				.withDistinguisher("template").withSolver("kea").rank();

		// EXAMLPE 3,4 - OR CUSTOMISE SOME PARAMETERS

		new Executor().withSimulator("aeshammingweight")
				.withProperty("traces", 30).withProperty("variance", 2.0)
				.withDistinguisher("template").withSolver("glowacz").rank();

		new Executor().withSimulator("aeshammingweight")
				.withProperty("traces", 40).withProperty("variance", 2.5)
				.withDistinguisher("template").withSolver("kea")
				.withProperty("precision", 8).enumerate();

		// EXAMPLE 5 - OR CUSTOMISE MANY PARAMETERS

		new Executor().withSimulator("aeshammingweight")
				.withProperty("traces", 50).withProperty("variance", 2.5)
				.withProperty("vectors", 16).withProperty("vectorlength", 256)
				.withDistinguisher("template").withSolver("glowacz")
				.withProperty("bins", 5000).rank();

		// Or do it DIY (No warranty provided)
		// ------------------------------------------------------------------ //

		// Configure some parameters...
		TypedProperties myProperties = new TypedProperties();
		myProperties.setDouble("precision", 7.5);
		myProperties.setInt("traces", 30);

		// Run your simulator
		Simulator mySim = SimulatorFactory.getInstance().getSimulator(
				"aeshammingweight", myProperties.getProperties());
		mySim.runAttack();

		// Create your solver
		Solver mySolve = SolverFactory.getInstance().getSolver("kea",
				myProperties.getProperties(), mySim);

		// And solve!
		mySolve.rank();
	}

}
