package com.qa.ims.utils;

import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.qa.ims.controller.Action;
import com.qa.ims.controller.OrderAction;
import com.qa.ims.persistence.domain.Domain;


public class UI {
	
	public static final Logger LOGGER = LogManager.getLogger();
	private final Utils utils;

	public UI(Utils utils) {
		this.utils = utils;
	}
	
	public void welcome() {
		LOGGER.info(
			"|=============================================|" +
			"\n| Welcome to the Inventory Management System  |"
		);
	}
	
	public boolean initDB() {
		fmtOutput("       Set up a fresh database? Y/N         |");
		if (utils.getYN().equals("y")) {
			return true;
		}
		return false;
	}
	
	public Domain selectDomain() {
		Domain[] dNames = Domain.values();
		LOGGER.info(
			"|=============================================|\n"
			+ "|        Please make a selection (1-4)        |\n" 
			+ "|=============================================|"   
			+ "\n| " + dNames[0].getDescription() + "           | 1 |"
			+ "\n| " + dNames[1].getDescription() + "            | 2 |"
			+ "\n| " + dNames[2].getDescription() + "             | 3 |"
			+ "\n| " + dNames[3].getDescription() + "                    | 4 |\n"
			+ "|=============================================|"
		);

		while (true) {
			int input = utils.getInt();
			if (input < 5) {
				return Domain.getDomain(input);
			}
			fmtOutput(" Error - Please enter a valid number (1-4)  |");
		}
	}
	
	public OrderAction selectOrderAction() {
		OrderAction[] oANames = OrderAction.values();
		LOGGER.info(
			"|=============================================|"	
			+"\n|    What would you like to do with order?    |"	
			+"\n|=============================================|"
			+"\n|        Please make a selection (1-9)        |" 
			+ "\n|=============================================|"
			+ "\n|=============== ORDER ACTIONS ===============|"
			+ "\n|=============================================|"
			+ "\n| " + oANames[0].getDescription() + "       | 1 |"
			+ "\n| " + oANames[1].getDescription() + "       | 2 |"
			+ "\n| " + oANames[2].getDescription() + "           | 3 |"
			+ "\n| " + oANames[3].getDescription() + "       | 4 |"
			+ "\n|=============================================|"
			+ "\n|============ ORDER ITEM ACTIONS =============|"
			+ "\n|=============================================|"
			+ "\n| " + oANames[4].getDescription() + "                 | 5 |"
			+ "\n| " + oANames[5].getDescription() + "            | 6 |"
			+ "\n| " + oANames[6].getDescription() + "           | 7 |"
			+ "\n| " + oANames[7].getDescription() + "      | 8 |"
			+ "\n| " + oANames[8].getDescription() + "              | 9 |\n"
			+ "|=============================================|"
		);
		
		while (true) {
			int input = utils.getInt();
			if (input < 10) {
				return OrderAction.getAction(input);
			}
			fmtOutput(" Error - Please enter a valid number (1-9)  |");
		}
	}
	
	public Action selectAction() {
		Action[] aNames = Action.values();
		LOGGER.info(
			 "|=============================================|"	
			+"\n|  What would you like to do in this domain?  |"	
			+"\n|=============================================|"
			+"\n|        Please make a selection (1-5)        |" 
			+ "\n|=============================================|"   
			+ "\n| " + aNames[0].getDescription() + "     | 1 |"
			+ "\n| " + aNames[1].getDescription() + "           | 2 |"
			+ "\n| " + aNames[2].getDescription() + "         | 3 |"
			+ "\n| " + aNames[3].getDescription() + "       | 4 |"
			+ "\n| " + aNames[4].getDescription() + "              | 5 |\n"
			+ "|=============================================|"
		);
		
		while (true) {
			int input = utils.getInt();
			if (input < 6) {
				return Action.getAction(input);
			}
			fmtOutput(" Error - Please enter a valid number (1-5)  |");
		}
	}
	
	public void fmtOutput(String output) {
		LOGGER.info(
			"|=============================================|"
			+"\n| "+output
			+"\n|=============================================|"
		);
	}
	
	public void fmtHeader(String output) {
		LOGGER.info(
			"|=============================================|"
			+"\n| "+output
		);
	}
	
	public void displayDTO(Object result) {
		LOGGER.info(
				"|=============================================|\n"
				+ result
			);
	}
	
	public void displayDTOs(List<?> results) {
		for (Object result : results) {
			LOGGER.info(
				"|=============================================|\n"
				+ result
			);
		}
	}

	public String formatError(String e) {
		return new String (
				"|=============================================|\n"
				+"|====================ERROR====================|\n"
				+"|=============================================|\n"
				+"| "+e
			);
	}
	
	public void exit() {
		LOGGER.info(
				"|=============================================|"
				+"\n|              Application Closed             |"
				+"\n|=============================================|"
			);
	}
}
