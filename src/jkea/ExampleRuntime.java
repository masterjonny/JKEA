package jkea;

public class ExampleRuntime {

	public static void main(String[] args) {

		// THE BARE MINIMUM
		
		new Executor().withSimulator("aeshammingweight").withSolver("glowacz").rank();
		
		// CHOOSE A DISTINGUISHER AND ACCEPT DEFAULTS

		new Executor().withSimulator("aeshammingweight")
				.withDistinguisher("template").withSolver("glowacz").rank();

		// OR CUSTOMISE SOME PARAMETERS

		new Executor().withSimulator("aeshammingweight")
				.withProperty("traces", 30).withProperty("variance", 2.0)
				.withDistinguisher("template").withSolver("glowacz").rank();

		// OR CUSTOMISE ALL THE PARAMETERS

		new Executor().withSimulator("aeshammingweight")
				.withProperty("traces", 50).withProperty("variance", 2.5)
				.withProperty("vectors", 16).withProperty("vectorlength", 256)
				.withDistinguisher("template").withSolver("glowacz")
				.withProperty("bins", 5000).rank();

	}

}
