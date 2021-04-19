package com.qa.ims.controller;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * Action is a collection of commands which are used to determine the type of
 * function to apply to an entity.
 *
 */
public enum Action {
	CREATE("Create new entities in the database"), READ("Read entities in the database"),
	UPDATE("Update entities in the database"), DELETE("Delete entities from the database"),
	RETURN("Return to domain selection");

	public static final Logger LOGGER = LogManager.getLogger();

	private String description;

	Action(String description) {
		this.description = description;
	}

	/**
	 * Describes the action
	 */
	public String getDescription() {
		return this.description;
	}

	public static Action getAction(int i) {
		Action action = null;
		switch (i) {
		case 1:
			return Action.valueOf("CREATE");
		case 2:
			return Action.valueOf("READ");
		case 3:
			return Action.valueOf("UPDATE");
		case 4:
			return Action.valueOf("DELETE");
		case 5:
			return Action.valueOf("RETURN");
		default:
			return action;
		}
	}
	/**
	 * Prints out all possible actions
	 */
//	public static void printActions() {
//		for (Action action : Action.values()) {
//			LOGGER.info(action.getDescription());
//		}
//	}

	/**
	 * Gets an action based on a users input. If user enters a non-specified
	 * enumeration, it will ask for another input.
	 * 
	 * @return Action type
	 */
//	public static Action getAction(Utils utils) {
//		Action action = null;
//		do {
//			try {
//				action = Action.valueOf(utils.getString().toUpperCase());
//			} catch (IllegalArgumentException e) {
//				LOGGER.error("Invalid selection please try again");
//			}
//		} while (action == null);
//		return action;
//	}
}
