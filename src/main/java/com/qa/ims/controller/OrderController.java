package com.qa.ims.controller;

import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.qa.ims.persistence.dao.OrderDAO;
import com.qa.ims.persistence.dao.OrderItemDAO;
import com.qa.ims.persistence.domain.Order;
import com.qa.ims.persistence.domain.OrderItem;
import com.qa.ims.utils.Utils;

/**
 * Takes in Order details for CRUD functionality
 *
 */
public class OrderController implements CrudController<Order> {

	public static final Logger LOGGER = LogManager.getLogger();

	private OrderItemDAO orderItemDAO;
	private OrderDAO orderDAO;
	private Utils utils;

	public OrderController(OrderDAO OrderDAO, OrderItemDAO orderItemDAO, Utils utils) {
//		super();
		this.orderItemDAO = orderItemDAO;
		this.orderDAO = OrderDAO;
		this.utils = utils;
	}

	/**
	 * Reads all Orders to the logger
	 */
	@Override
	public List<Order> readAll() {
		List<Order> Orders = orderDAO.readAll();
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
		Order Order = orderDAO.create(new Order(customerId)); // Do something with return?
		LOGGER.info("Order created"); // DO MORE HERE!
		return Order;
	}

	/**
	 * Updates an existing Order by taking in user input
	 */
	@Override
	public Order update() {
		readAll();
		LOGGER.info("Please enter the id of the order you would like to update");
		Long id = utils.getLong();
		LOGGER.info("Please enter a customer id");
		Long customerId = utils.getLong();
		Order Order = orderDAO.update(new Order(id, customerId)); // Do something with the return? Also mabe adjust date to current here?
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
		readAll();
		LOGGER.info("Please enter the id of the order you would like to delete");
		Long id = utils.getLong();
		return orderDAO.delete(id);
	}
	
	public List<OrderItem> readAllOrderItems(Long orderId) {
		List<OrderItem> items = orderItemDAO.readAll(orderId);
		for (OrderItem item : items) {
			LOGGER.info(item);
		}
		return items;
	}
	
	public List<OrderItem> addItem() {
		readAll();
		LOGGER.info("Please enter an order id");
		Long orderId = utils.getLong();
		LOGGER.info("Please enter an item id"); // Add choice to see items here?
		Long itemId = utils.getLong();
		LOGGER.info("Please enter a quantity");
		Long quantity = utils.getLong();
		orderItemDAO.create(new OrderItem(orderId, itemId, quantity)); // Do something with return?
		LOGGER.info("Item added to order"); // DO MORE HERE!
		return orderItemDAO.readAll(orderId);
	}
	
	public List<OrderItem> orderItems() {
		LOGGER.info("Please enter an order id");
		Long orderId = utils.getLong();
		return readAllOrderItems(orderId);
	}
	
	public int removeItem() {
		readAll();
		LOGGER.info("Please enter an order id");
		Long orderId = utils.getLong();
		readAllOrderItems(orderId);
		LOGGER.info("Please enter the id of the item you would like to delete");
		Long itemId = utils.getLong();
		return orderItemDAO.delete(itemId);
	}
	
	public void orderCost() {
		readAll();
		LOGGER.info("Please enter an order id");
		Long orderId = utils.getLong();
		LOGGER.info(orderDAO.totalCost(orderId));
	}
}
