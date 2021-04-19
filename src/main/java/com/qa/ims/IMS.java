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
import com.qa.ims.utils.UI;
import com.qa.ims.utils.Utils;

public class IMS {

	public static final Logger LOGGER = LogManager.getLogger();

	private final CustomerController customers;
	private final OrderController orders;
	private final ItemController items;
	private final Utils utils;
	private final UI ui;

	public IMS() {
		this.ui = new UI();
		this.utils = new Utils();
		final CustomerDAO custDAO = new CustomerDAO();
		final OrderItemDAO oiDAO = new OrderItemDAO();
		final OrderDAO orderDAO = new OrderDAO();
		final ItemDAO itemDAO = new ItemDAO();
		this.customers = new CustomerController(custDAO, ui, utils);
		this.orders = new OrderController(orderDAO, oiDAO, ui, utils);
		this.items = new ItemController(itemDAO, ui, utils);
	}

	public void imsSystem() {
		ui.welcome();
		DBUtils.connect();	
		Domain domain = null;
		do {
			domain = ui.selectDomain(utils);
			domainAction(domain);
		} while (domain != Domain.STOP);
		ui.exit();
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

			if (domain == Domain.ORDER) {
				OrderAction action = ui.selectOrderAction(utils);
				if (action == OrderAction.RETURN) {
					changeDomain = true;
				} else {
					doOrderAction(this.orders, action);
				}
			} else {
				Action action = ui.selectAction(utils, domain.name());	
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
