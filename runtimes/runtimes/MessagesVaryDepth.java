package runtimes;

import java.math.BigDecimal;

import jkea.core.Distinguisher;
import jkea.core.Simulator;
import jkea.core.Solver;
import jkea.core.spi.DistinguisherFactory;
import jkea.core.spi.SimulatorFactory;
import jkea.core.spi.SolverFactory;
import jkea.util.TypedProperties;

public class MessagesVaryDepth {

	public static void main(String[] args) {

		// EXAMPLE RUNTIME FOR MAN DARTIN
		//
		// How does running additional traces effect the key rank?

		int START = 1;
		int STEP = 1;
		int STOP = 100;

		BigDecimal[] results = new BigDecimal[STOP];

		TypedProperties myProperties = new TypedProperties();

		myProperties.setInt("traces", START);
		myProperties.setDouble("variance", 2.0);
		myProperties.setDouble("precision", 8);

		Simulator mySim = SimulatorFactory.getInstance().getSimulator(
				"aeshammingweight", myProperties.getProperties());
		Distinguisher myDis = DistinguisherFactory.getInstance()
				.getDistinguisher("template", mySim);

		mySim.setDistinguisher(myDis);
		mySim.runAttack();

		int c = 0;
		for (int i = START; i < STOP; i += STEP, c += 1) {
			Solver mySolve = SolverFactory.getInstance().getSolver("kea",
					myProperties.getProperties(), mySim);
			results[c] = mySolve.rank();
			mySim.runAdditionalTrace(STEP);
			System.out.println(results[c]);
		}

	}

}
