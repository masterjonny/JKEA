package jkea.runtime;

import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.DefaultParser;
import org.apache.commons.cli.HelpFormatter;
import org.apache.commons.cli.Option;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;

class Parser {

	private static Options options;

	static {
		options = new Options();
	}

	static CommandLine parseOptions(String[] args) throws ParseException {
		options = new Options();

		Option solver = new Option("s", "solver", true,
				"Choose the solver to use. \n 1: JKEA 2: Veyrat (2013) 3: Glowacz (2015)");
		Option operation = new Option("o", "op", true,
				"Choose the operation to run: \n 1: Key Rank 2: Key Enumeration");
		Option help = new Option("help", "print this message");

		options.addOption(solver);
		options.addOption(help);
		options.addOption(operation);
		CommandLineParser parser = new DefaultParser();
		return parser.parse(options, args);
	}

	static void printHelp() {
		HelpFormatter formatter = new HelpFormatter();
		formatter.printHelp("jkea", options);
	}

}
