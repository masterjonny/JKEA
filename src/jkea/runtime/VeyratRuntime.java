package jkea.runtime;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

import jkea.simulation.AttackFramework;
import jkea.solvers.VeyratSolver;

public class VeyratRuntime {

	public static void main(String[] args) {

		final int VARIANCE = 2;
		final long NUMBERMESSAGES = 30;
		final boolean EXPERIMENT_OUTPUT = true;
		final int REPLICATE = 100000;

		for (int i = 0; i < REPLICATE; i++) {
			final AttackFramework a = new AttackFramework(VARIANCE);
			a.runAttack(NUMBERMESSAGES);

			final long startTime = System.currentTimeMillis();
			final VeyratSolver v = new VeyratSolver(a.getPrior(), a.getKey());
			final long rank = v.solve();
			final long endTime = System.currentTimeMillis();

			if (EXPERIMENT_OUTPUT) {
				try (PrintWriter out = new PrintWriter(new BufferedWriter(
						new FileWriter(args[0], true)))) {
					out.println(VARIANCE + "," + rank + ","
							+ (endTime - startTime));
				} catch (IOException e) {
					// exception handling left as an exercise for the reader
				}
			} else {
				System.out.println("KEY RANK: " + rank);
			}
		}
	}

}
