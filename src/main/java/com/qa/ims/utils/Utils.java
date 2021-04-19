package com.qa.ims.utils;

import java.util.Scanner;

public class Utils {
	
	private final Scanner scanner;
	private final UI ui;
	
	public Utils(Scanner scanner, UI ui) {
//		super();
		this.scanner = scanner;
		this.ui = ui;
	}

	public Utils(UI ui) {
		this.scanner = new Scanner(System.in);
		this.ui = ui;
	}

	public Long getLong() {
		String input = null;
		Long longInput = null;
		do {
			try {
				input = getString();
				longInput = Long.parseLong(input);
			} catch (NumberFormatException nfe) {
				ui.fmtOutput("       Error - Input must be a number       |");
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
			ui.fmtOutput("          Error - Input must be Y/N         |");
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
				ui.fmtOutput("       Error - Input must be a number       |");
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
				ui.fmtOutput("       Error - Input must be a number       |");
			}
		} while (doubleInput == null);
		return doubleInput;
	}
}
