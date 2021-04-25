package com.qa.ims;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.qa.ims.controller.Action;
import com.qa.ims.controller.CrudController;
import com.qa.ims.controller.CustomerController;
import com.qa.ims.controller.ItemController;
import com.qa.ims.controller.OrderAction;
import com.qa.ims.controller.OrderController;
import com.qa.ims.persistence.domain.Domain;
import com.qa.ims.utils.DBUtils;
import com.qa.ims.utils.UI;

public class IMS {

	public static final Logger LOGGER = LogManager.getLogger();
	
	private final CustomerController customers;
	private final OrderController orders;
	private final ItemController items;
	private final DBUtils dbu;
	private final UI ui;

	public IMS(DBUtils dbu, UI ui, CustomerController customers, ItemController items, OrderController orders) {
		this.dbu = dbu;
		this.ui = ui;
		this.customers = customers;
		this.items = items;
		this.orders = orders;
	}

	/**
	 * Setup and initial domain selection for the program.
	 * 
	 */
	public void imsSystem() {
		ui.welcome();
		if (ui.initDB())
			dbu.init("src/main/resources/sql-schema.sql", "src/main/resources/sql-data.sql");
		Domain domain = null;
		do {
			domain = ui.selectDomain();
			domainAction(domain);
		} while (domain != Domain.STOP);
		ui.exit();
	}

	/**
	 * Present the user with options to choose from 
	 * depending on the domain they have chosen.
	 * 
	 * @param domain - Takes in a domain.
	 */
	private void domainAction(Domain domain) {
		boolean changeDomain = false;
		do {
			CrudController<?> active = null;
			switch (domain) {
				case CUSTOMER:
					active = this.customers;
					break;
				case ITEM:
					active = this.items;
					break;
				case ORDER:
					active = this.orders;
					break;
				case STOP:
					return;
				default:
					break;
			}

			if (domain == Domain.ORDER) {
				OrderAction action = ui.selectOrderAction();
				if (action == OrderAction.RETURN) {
					changeDomain = true;
				} else {
					doOrderAction(this.orders, action);
				}
			} else {
				Action action = ui.selectAction();	
				if (action == Action.RETURN) {
					changeDomain = true;
				} else {
					doAction(active, action);
				}
			}
		} while (!changeDomain);
	}
	
	/**
	 * Performs the action the user has chosen on the domain
	 * that they are currently in.
	 * 
	 * @param crudController - Takes in a CrudController object.
	 * @param action - An action to perform.
	 */
	public void doAction(CrudController<?> crudController, Action action) {
		switch (action) {
		case CREATE:
			crudController.create();
			break;
		case READ:
			crudController.readAll();
			break;
		case UPDATE:
			crudController.update();
			break;
		case DELETE:
			crudController.delete();
			break;
		case RETURN:
			break;
		default:
			break;
		}
	}

	/**
	 * Performs the action the user has chosen on the order
	 * domain..
	 * 
	 * @param OrderController - Takes in an OrderController object.
	 * @param action - An action to perform.
	 */
	public void doOrderAction(OrderController oc, OrderAction action) {
		switch (action) {
		case CREATE:
			oc.create();
			break;
		case READ:
			oc.readAll();
			break;
		case UPDATE:
			oc.update();
			break;
		case DELETE:
			oc.delete();
			break;
		case ADD:
			oc.addItem();
			break;
		case ITEMS:
			oc.orderItems();
			break;
		case REMOVE:
			oc.removeItem();
			break;
		case TOTAL:
			oc.orderCost();
			break;
		case RETURN:
			break;
		default:
			break;
		}
	}
}
