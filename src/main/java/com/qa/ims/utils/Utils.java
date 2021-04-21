package com.qa.ims.utils;

import java.util.Scanner;

import com.qa.ims.IMS;

public class Utils {
	
	private final Scanner scanner;
	
	public Utils(Scanner scanner) {
//		super();
		this.scanner = scanner;
	}

	public Utils() {
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
					IMS.ui.fmtOutput("      Error - Input can't be 0 or less      |");
					longInput = null;
					continue;
				}
			} catch (NumberFormatException nfe) {
				IMS.ui.fmtOutput("       Error - Input must be a number       |");
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
			if (input.equals("y") || input.equals("n")) {
				return input;
			}
			IMS.ui.fmtOutput("          Error - Input must be Y/N         |");
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
					IMS.ui.fmtOutput("      Error - Input can't be 0 or less      |");
					intInput = null;
					continue;
				}
			} catch (NumberFormatException nfe) {
				IMS.ui.fmtOutput("       Error - Input must be a number       |");
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
					IMS.ui.fmtOutput("      Error - Input can't be 0 or less      |");
					doubleInput = null;
					continue;
				}
			} catch (NumberFormatException nfe) {
				IMS.ui.fmtOutput("       Error - Input must be a number       |");
			}
		} while (doubleInput == null);
		return doubleInput;
	}
}
