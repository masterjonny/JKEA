package jkea.simulation;

public class SimulateAttackToFile {

	public static void main(String[] args) {
		if (args.length != 2) {
			System.err.println("Please provide two parameters:"
					+ " The variance and the number of messages.");
			System.exit(1);
		}

		int variance = 0;
		long numberMessages = 0;

		try {
			variance = Integer.parseInt(args[0]);
			numberMessages = Long.parseLong(args[1]);
		} catch (NumberFormatException e) {
			System.err.println("Arguments are invalid values");
			System.exit(1);
		}

		AttackFramework attack = new AttackFramework(variance);
		attack.runAttack(numberMessages);
		attack.writeToFile("attack_output.txt");
		System.out.println("Data written to " + System.getProperty("user.dir")
				+ "/attack_output.txt");

	}
}
