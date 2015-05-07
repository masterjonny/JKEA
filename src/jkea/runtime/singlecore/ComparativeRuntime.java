package jkea.runtime.singlecore;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

import jkea.simulation.AttackFramework;
import jkea.solvers.KEASolver;
import jkea.solvers.VeyratSolver;

public class ComparativeRuntime {

	public static void main(String[] args) {

		final int VARIANCE = 2;
		final long NUMBERMESSAGES = 30;
		final int REPLICATE = 1000000;

		System.out.println("VARAINCE,KEYDEPTH,MILLISECS");

		VeyratSolver v;
		AttackFramework a;
		KEASolver k;
		
		for (int i = 0; i < REPLICATE; i++) {
			a = new AttackFramework(VARIANCE);
			a.runAttack(NUMBERMESSAGES);

			final double[][] priorScores = a.getPrior();
			
			int max = 0;
			
			final int[][] intScores = new int[priorScores.length][priorScores[0].length];
			for (int j = 0; j < priorScores.length; ++j)
				for (int kk = 0; kk < priorScores[0].length; ++kk) {
					intScores[j][kk] = (int) Math.abs(priorScores[j][kk]);
					max = Math.max(intScores[j][kk], max);
				}
			
			for (int j = 0; j < priorScores.length; ++j)
				for (int kk = 0; kk < priorScores[0].length; ++kk) {
					intScores[j][kk] = max - intScores[j][kk];
				}
			
			
			k = new KEASolver(a.getPrior(), a.getKey());
			long actual = k.keyRank();
			
			System.out.println("ACTUAL: " + k.keyRank());
			long kStartTime = System.currentTimeMillis();
			long kRank = k.solve();
			long kEndTime = System.currentTimeMillis();
		
			System.out.println("KEA: " + VARIANCE + "," + kRank + ","
					+ (kEndTime - kStartTime));
								
			v = new VeyratSolver(intScores, a.getKey());
			long vStartTime = System.currentTimeMillis();
			long vRank = v.solve();
			long vEndTime = System.currentTimeMillis();

			if (args.length == 1) {
				try (PrintWriter out = new PrintWriter(new BufferedWriter(
						new FileWriter(args[0] + "veyart.csv", true)))) {
					out.println(VARIANCE + "," + actual + ","
							+ (vEndTime - vStartTime));
				} catch (IOException e) {
					e.printStackTrace();
				}
				try (PrintWriter out = new PrintWriter(new BufferedWriter(
						new FileWriter(args[0] + "kea.csv", true)))) {
					out.println(VARIANCE + "," + actual + ","
							+ (kEndTime - kStartTime));
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			System.out.println("VEYRAT: " + VARIANCE + "," + vRank + ","
					+ (vEndTime - vStartTime));
		}
	}

}
