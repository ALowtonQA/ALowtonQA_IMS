package com.qa.ims;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.qa.ims.controller.CustomerController;
import com.qa.ims.controller.ItemController;
import com.qa.ims.controller.OrderController;
import com.qa.ims.persistence.dao.CustomerDAO;
import com.qa.ims.persistence.dao.ItemDAO;
import com.qa.ims.persistence.dao.OrderDAO;
import com.qa.ims.persistence.dao.OrderItemDAO;
import com.qa.ims.utils.DBUtils;
import com.qa.ims.utils.UI;
import com.qa.ims.utils.Utils;

public class Runner {

	public static final Logger LOGGER = LogManager.getLogger();

	public static void main(String[] args) {
		// Utils
		final DBUtils dbu = new DBUtils();
		final Utils utils = new Utils();
		final UI ui = new UI(utils);
		
		// DAOs
		final CustomerDAO custDAO = new CustomerDAO(ui, dbu.getConnection());
		final OrderItemDAO oiDAO = new OrderItemDAO(ui, dbu.getConnection());
		final OrderDAO orderDAO = new OrderDAO(ui, dbu.getConnection());
		final ItemDAO itemDAO = new ItemDAO(ui, dbu.getConnection());
		
		// Controllers
		final CustomerController customers = new CustomerController(custDAO, utils, ui);
		final ItemController items = new ItemController(itemDAO, utils, ui);
		final OrderController orders = new OrderController(orderDAO, oiDAO, customers, items, utils, ui);
		
		// System
		IMS ims = new IMS(dbu, ui, customers, items, orders);
		ims.imsSystem();
		
		// Cleanup
		dbu.closeConnection();
	}
}
