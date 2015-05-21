package jkea.runtime;

import jkea.solvers.SolverInterface;

import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.ParseException;

public class Runtime {

	public static void main(String[] args) {

		SolverInterface solver;
		
		
		try {
			final CommandLine line = Parser.parseOptions(args);
			if (line.hasOption("help")) {
				Parser.printHelp();
				System.exit(0);
			}
			
			if (line.hasOption("solver")) {
				if(line.getOptionValue("solver").compareTo("1") == 0) {
			
				} else if(line.getOptionValue("solver").compareTo("2") == 0) {
					
				} else if(line.getOptionValue("solver").compareTo("3") == 0) {
					
				} else {
					System.err.println("Unrecognized solver!");	
					System.exit(-1);
				}
			} else {
				
			}
			
			if (line.hasOption("operation")) {
				if(line.getOptionValue("operation").compareTo("1") == 0) {
					
				} else if(line.getOptionValue("operation").compareTo("1") == 0) {
					
				} else {
					System.err.println("Unrecognized operation!");	
					System.exit(-1);
				}
			}
			

		} catch (final ParseException e) {
			System.err.println("Arguemt parsing failed!  Error: "
					+ e.getMessage());
		}
	}

}
