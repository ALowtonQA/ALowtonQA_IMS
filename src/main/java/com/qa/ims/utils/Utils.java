package com.qa.ims.utils;

import java.util.Scanner;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class Utils {
	
	private static final Logger LOGGER = LogManager.getLogger();

	private final Scanner scanner;

	public Utils(Scanner scanner) {
		super();
		this.scanner = scanner;
	}

	public Utils() {
		scanner = new Scanner(System.in);
	}

	public Long getLong() {
		String input = null;
		Long longInput = null;
		do {
			try {
				input = getString();
				longInput = Long.parseLong(input);
			} catch (NumberFormatException nfe) {
				LOGGER.info(
						"|=============================================|\n"
						+ "|        Error - Please enter a number        |\n"
						+ "|=============================================|"
					);
			}
		} while (longInput == null);
		return longInput;
	}

	public String getString() {
		System.out.print("| INPUT: ");
		return scanner.nextLine();
	}

	public String getYN() {
		String input = null;
		while (true) {
			input = getString().toLowerCase();
			if (input == "y" || input == "n") {
				return input;
			}
			LOGGER.info(
					"|=============================================|\n"
					+ "|          Error - Input must be Y/N          |\n"
					+ "|=============================================|"
			);
		}
	}
	
	public int getInt() {
		String input = null;
		Integer intInput = null;
		do {
			try {
				input = getString();
				intInput = Integer.parseInt(input);
			} catch (NumberFormatException nfe) {
				LOGGER.info(
					"|=============================================|\n"
					+ "|        Error - Input must be a number       |\n"
					+ "|=============================================|"
				);
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
			} catch (NumberFormatException nfe) {
				LOGGER.info("Error - Please enter a number");
			}
		} while (doubleInput == null);
		return doubleInput;
	}
}
