package jkea.simulation;

public class SimulateAttackToFile {

	public static void main(String[] args) {
		if (args.length < 2) {
			System.err.println("Please provide at least two parameters:"
					+ " The variance and the number of messages.");
			System.exit(1);
		}

		int variance = 0;
		final int runID = 0;
		long numberMessages = 0;

		try {
			variance = Integer.parseInt(args[0]);
			numberMessages = Long.parseLong(args[1]);
			if (args.length == 3)
				variance = Integer.parseInt(args[2]);
		} catch (final NumberFormatException e) {
			System.err.println("Arguments are invalid values");
			System.exit(1);
		}

		final AttackFramework attack = new AttackFramework(variance);
		attack.runAttack(numberMessages);
		attack.writeToFile("var" + variance + "msg" + numberMessages + "run"
				+ runID);
		System.out.println("Data written to " + System.getProperty("user.dir")
				+ "/var" + variance + "msg" + numberMessages + "run" + runID);
	}
}
