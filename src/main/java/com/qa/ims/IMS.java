package com.qa.ims;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.qa.ims.controller.Action;
import com.qa.ims.controller.CrudController;
import com.qa.ims.controller.CustomerController;
import com.qa.ims.controller.ItemController;
import com.qa.ims.controller.OrderAction;
import com.qa.ims.controller.OrderController;
import com.qa.ims.persistence.dao.CustomerDAO;
import com.qa.ims.persistence.dao.ItemDAO;
import com.qa.ims.persistence.dao.OrderDAO;
import com.qa.ims.persistence.dao.OrderItemDAO;
import com.qa.ims.persistence.domain.Domain;
import com.qa.ims.utils.DBUtils;
import com.qa.ims.utils.Utils;

public class IMS {

	public static final Logger LOGGER = LogManager.getLogger();

	private final CustomerController customers;
	private final OrderController orders;
	private final ItemController items;
	private final Utils utils;

	public IMS() {
		this.utils = new Utils();
		final CustomerDAO custDAO = new CustomerDAO();
		final OrderItemDAO oiDAO = new OrderItemDAO();
		final OrderDAO orderDAO = new OrderDAO();
		final ItemDAO itemDAO = new ItemDAO();
		this.customers = new CustomerController(custDAO, utils);
		this.orders = new OrderController(orderDAO, oiDAO, utils);
		this.items = new ItemController(itemDAO, utils);
	}

	public void imsSystem() {
		LOGGER.info("Welcome to the Inventory Management System!");
		DBUtils.connect();
		
		Domain domain = null;
		do {
			LOGGER.info("Which entity would you like to use?");
			Domain.printDomains();

			domain = Domain.getDomain(utils);

			domainAction(domain);

		} while (domain != Domain.STOP);
	}

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
			
			String curr = domain.name();
			
			LOGGER.info("What would you like to do with " + curr.toLowerCase() + ":");
			
			if (curr == "ORDER") {
				OrderAction.printActions();
				OrderAction action = OrderAction.getAction(utils);
				
				if (action == OrderAction.RETURN) {
					changeDomain = true;
				} else {
					doOrderAction(this.orders, action);
				}
			} else {
				Action.printActions();
				Action action = Action.getAction(utils);
				
				if (action == Action.RETURN) {
					changeDomain = true;
				} else {
					doAction(active, action);
				}
			}
		} while (!changeDomain);
	}

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
