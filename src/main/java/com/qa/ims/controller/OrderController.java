package com.qa.ims.controller;

import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.qa.ims.persistence.dao.OrderDAO;
import com.qa.ims.persistence.domain.Order;
import com.qa.ims.utils.Utils;

/**
 * Takes in Order details for CRUD functionality
 *
 */
public class OrderController implements CrudController<Order> {

	public static final Logger LOGGER = LogManager.getLogger();

	private OrderDAO OrderDAO;
	private Utils utils;

	public OrderController(OrderDAO OrderDAO, Utils utils) {
//		super();
		this.OrderDAO = OrderDAO;
		this.utils = utils;
	}

	/**
	 * Reads all Orders to the logger
	 */
	@Override
	public List<Order> readAll() {
		List<Order> Orders = OrderDAO.readAll();
		for (Order order : Orders) {
			LOGGER.info(order);
		}
		return Orders;
	}

	/**
	 * Creates a Order by taking in user input
	 */
	@Override
	public Order create() {
		LOGGER.info("Please enter a customer id");
		Long customerId = utils.getLong();
		Order Order = OrderDAO.create(new Order(customerId)); // Do something with return?
		LOGGER.info("Order created"); // DO MORE HERE!
		return Order;
	}

	/**
	 * Updates an existing Order by taking in user input
	 */
	@Override
	public Order update() {
		LOGGER.info("Please enter the id of the order you would like to update");
		Long id = utils.getLong();
		LOGGER.info("Please enter a customer id");
		Long customerId = utils.getLong();
		Order Order = OrderDAO.update(new Order(id, customerId)); // Do something with the return? Also mabe adjust date to current here?
		LOGGER.info("Order Updated");
		return Order;
	}

	/**
	 * Deletes an existing Order by the id of the Order
	 * 
	 * @return
	 */
	@Override
	public int delete() {
		LOGGER.info("Please enter the id of the order you would like to delete");
		Long id = utils.getLong();
		return OrderDAO.delete(id);
	}

}
