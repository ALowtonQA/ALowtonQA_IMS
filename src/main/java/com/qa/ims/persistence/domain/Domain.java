package com.qa.ims.persistence.domain;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public enum Domain {

	CUSTOMER("Customer Menu - Customer Info"), ITEM("Item Menu - Individual Items"), ORDER("Order Menu - Item Purchases"),
	STOP("Exit the application");

	public static final Logger LOGGER = LogManager.getLogger();

	private String description;

	private Domain(String description) {
		this.description = description;
	}

	public String getDescription() {
		return this.description;
	}

	public static Domain getDomain(int i) {
		Domain domain = null;
		switch (i) {
		case 1:
			return Domain.valueOf("CUSTOMER");
		case 2:
			return Domain.valueOf("ITEM");
		case 3:
			return Domain.valueOf("ORDER");
		case 4:
			return Domain.valueOf("STOP");
		default:
			return domain;
		}
	}
//	public static void printDomains() {
//	for (Domain domain : Domain.values()) {
//	LOGGER.info(domain.getDescription());
//}
//}

//public static Domain getDomain(Utils utils) {
//Domain domain;
//while (true) {
//	try {
//		domain = Domain.valueOf(utils.getString().toUpperCase());
//		break;
//	} catch (IllegalArgumentException e) {
//		LOGGER.error("Invalid selection please try again\n");
//	}
//}
//return domain;
//}
}
