package com.qa.ims.controller;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * Action is a collection of commands which are used to determine the type of
 * function to apply to an entity.
 *
 */
public enum OrderAction {
	CREATE("Create new orders in the database"), READ("Read all orders from the database"),
	UPDATE("Update orders in the database"), DELETE("Delete an order from the database"),
	ADD("Add an item to an order"), REMOVE("Remove an item from an order"), 
	ITEMS("Display all items in an order"), TOTAL("Display the total cost of an order"), 
	RETURN("Return to domain selection");

	public static final Logger LOGGER = LogManager.getLogger();

	private String description;

	OrderAction(String description) {
		this.description = description;
	}
	
	/**
	 * Describes the action
	 */
	public String getDescription() {
		return this.description;
	}

	public static OrderAction getAction(int i) {
		OrderAction action = null;
		switch (i) {
		case 1:
			return OrderAction.valueOf("CREATE");
		case 2:
			return OrderAction.valueOf("READ");
		case 3:
			return OrderAction.valueOf("UPDATE");
		case 4:
			return OrderAction.valueOf("DELETE");
		case 5:
			return OrderAction.valueOf("ADD");
		case 6:
			return OrderAction.valueOf("REMOVE");
		case 7:
			return OrderAction.valueOf("ITEMS");
		case 8:
			return OrderAction.valueOf("TOTAL");
		case 9:
			return OrderAction.valueOf("RETURN");
		default:
			return action;
		}
	}
	/**
	 * Prints out all possible actions
	 */
//	public static void printActions() {
//		for (OrderAction action : OrderAction.values()) {
//			LOGGER.info(action.getDescription());
//		}
//	}

	/**
	 * Gets an action based on a users input. If user enters a non-specified
	 * enumeration, it will ask for another input.
	 * 
	 * @return Action type
	 */
//	public static OrderAction getAction(Utils utils) {
//		OrderAction action = null;
//		do {
//			try {
//				action = OrderAction.valueOf(utils.getString().toUpperCase());
//			} catch (IllegalArgumentException e) {
//				LOGGER.error("Invalid selection please try again");
//			}
//		} while (action == null);
//		return action;
//	}
}
