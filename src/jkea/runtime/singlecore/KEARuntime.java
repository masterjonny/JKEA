package jkea.runtime.singlecore;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

import jkea.simulation.AttackFramework;
import jkea.solvers.KEASolver;

public class KEARuntime {

	public static void main(String[] args) {

		final int VARIANCE = 2;
		final long NUMBERMESSAGES = 30;
		final int REPLICATE = 1000000;
		
		KEASolver k;
		AttackFramework a;
		
		for (int i = 0; i < REPLICATE; i++) {
			a = new AttackFramework(VARIANCE);
			a.runAttack(NUMBERMESSAGES);

			final long startTime = System.currentTimeMillis();
			k = new KEASolver(a.getPrior(), a.getKey());
			long actual = k.keyRank();
			
			System.out.println("ACTUAL: " + k.keyRank());
			k.solve();
			final long endTime = System.currentTimeMillis();

			if (args.length == 1) {
				try (PrintWriter out = new PrintWriter(new BufferedWriter(
						new FileWriter(args[0], true)))) {
					out.println(VARIANCE + "," + actual + ","
							+ (endTime - startTime));
				} catch (IOException e) {
					// exception handling left as an exercise for the reader
				}
			}
			System.out.println(VARIANCE + "," + actual + ","
					+ (endTime - startTime));
		}
	}

}
