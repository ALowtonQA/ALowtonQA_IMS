package com.qa.ims.utils;

import java.util.Scanner;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class Utils {
	
	public static final Logger LOGGER = LogManager.getLogger();
	private final Scanner scanner;
	
	public Utils(Scanner scanner) {
//		super();
		this.scanner = scanner;
	}

	public Utils() {
//		super();
		this.scanner = new Scanner(System.in);
	}
	
	public Long getLong() {
		String input = null;
		Long longInput = null;
		do {
			try {
				input = getString();
				longInput = Long.parseLong(input);
				if (longInput <= 0) {
					fmtOutput("      Error - Input can't be 0 or less      |");
					longInput = null;
					continue;
				}
			} catch (NumberFormatException nfe) {
				fmtOutput("       Error - Input must be a number       |");
			}
		} while (longInput == null);
		return longInput;
	}

	public String getString() {
		String result = null;
		do {
			System.out.print("| INPUT: ");
			result = scanner.nextLine().strip();
			if (!(result.length() > 0)) {
				fmtOutput("       Error - Please enter a value         |");
			}
		} while (!(result.length() > 0));
		return result;
	}

	public String getYN() {
		String input = null;
		while (true) {
			input = getString().toLowerCase();
			if (input.equals("y") || input.equals("n")) {
				return input;
			}
			fmtOutput("          Error - Input must be Y/N         |");
		}
	}
	
	public int getInt() {
		String input = null;
		Integer intInput = null;
		do {
			try {
				input = getString();
				intInput = Integer.parseInt(input);
				if (intInput <= 0) {
					fmtOutput("      Error - Input can't be 0 or less      |");
					intInput = null;
					continue;
				}
			} catch (NumberFormatException nfe) {
				fmtOutput("       Error - Input must be a number       |");
			}
		} while (intInput == null);
		return intInput;
	}
	
	public Double getDouble() {
		String input = null;
		Double doubleInput = null;
		do {
			try {
				input = getString();
				doubleInput = Double.parseDouble(input);
				if (doubleInput <= 0) {
					fmtOutput("      Error - Input can't be 0 or less      |");
					doubleInput = null;
					continue;
				}
			} catch (NumberFormatException nfe) {
				fmtOutput("       Error - Input must be a number       |");
			}
		} while (doubleInput == null);
		return doubleInput;
	}
	
	public void fmtOutput(String output) {
		LOGGER.info(
			"|=============================================|"
			+"\n| "+output
			+"\n|=============================================|"
		);
	}
}
