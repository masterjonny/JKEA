package jkea.runtime;

import jkea.simulation.AttackFramework;
import jkea.solvers.VeyratSolver;

public class VeyratRuntime {

	public static void main(String[] args) {
		
		final int VARIANCE = 2;
		final long NUMBERMESSAGES = 30;
		
		AttackFramework a = new AttackFramework(VARIANCE);
		a.runAttack(NUMBERMESSAGES);
		
		VeyratSolver v = new VeyratSolver(a.getPrior(), a.getKey());
		long rank = v.solve();
		System.out.println("KEY RANK: " + rank);
	}

}
