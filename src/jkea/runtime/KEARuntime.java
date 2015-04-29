package jkea.runtime;

import jkea.simulation.AttackFramework;
import jkea.solvers.KEASolver;

public class KEARuntime {

	public static void main(String[] args) {

		final int VARIANCE = 2;
		final long NUMBERMESSAGES = 30;
		final int REPLICATE = 10000;
		
		for(int i = 0; i < REPLICATE; i++) {
			final AttackFramework a = new AttackFramework(VARIANCE);
			a.runAttack(NUMBERMESSAGES);
	
			final KEASolver k = new KEASolver(a.getPrior(), a.getKey());
			final long rank = k.solve();
			System.out.println("KEY RANK: " + rank);
		}
	}
	
}
