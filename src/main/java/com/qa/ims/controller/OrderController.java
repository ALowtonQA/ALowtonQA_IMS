package com.qa.ims.controller;

import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.qa.ims.persistence.dao.OrderDAO;
import com.qa.ims.persistence.dao.OrderItemDAO;
import com.qa.ims.persistence.domain.Order;
import com.qa.ims.persistence.domain.OrderItem;
import com.qa.ims.utils.UI;
import com.qa.ims.utils.Utils;

/**
 * Takes in Order details for CRUD functionality
 *
 */
public class OrderController implements CrudController<Order> {

	public static final Logger LOGGER = LogManager.getLogger();

	private final OrderItemDAO orderItemDAO;
	private final OrderDAO orderDAO;
	private final Utils utils;
	private final UI ui;
	
	public OrderController(OrderDAO OrderDAO, OrderItemDAO orderItemDAO, UI ui, Utils utils) {
//		super();
		this.orderItemDAO = orderItemDAO;
		this.orderDAO = OrderDAO;
		this.ui = ui;
		this.utils = utils;
	}

	/**
	 * Reads all Orders to the logger
	 */
	@Override
	public List<Order> readAll() {
		List<Order> orders = orderDAO.readAll();
		ui.fmtOutput("               Active orders");
		ui.displayDTOs(orders);
		return orders;
	}
	
	/**
	 * Creates a Order by taking in user input
	 */
	@Override
	public Order create() {
		// some option here
		// customer controller action here (read)
		ui.fmtOutput("        Please enter a customer id");
		Long customerId = utils.getLong();
		Order order = orderDAO.create(new Order(customerId)); // Do something with return?
		ui.fmtOutput("              Order created"); // DO MORE HERE!
		return order;
	}

	/**
	 * Updates an existing Order by taking in user input
	 */
	@Override
	public Order update() {
		readAll();
		ui.fmtOutput("    Please enter an order ID to update");
		Long id = utils.getLong();
		ui.fmtOutput("        Please enter a customer id");
		Long customerId = utils.getLong();
		Order order = orderDAO.update(new Order(id, customerId)); // Maybe adjust date to current here?
		ui.fmtOutput("               Order Updated");
		return order;
	}

	/**
	 * Deletes an existing Order by the id of the Order
	 * 
	 * @return
	 */
	@Override
	public int delete() {
		readAll();
		ui.fmtOutput("    Please enter an order ID to delete");
		Long id = utils.getLong();
		int result = orderDAO.delete(id);
		ui.fmtOutput("        Order successfully deleted.");
		return result;
	}
	
	public List<OrderItem> readAllOrderItems(Long orderId) {
		List<OrderItem> items = orderItemDAO.readAll(orderId);
		ui.fmtOutput("            Items in order #" + orderId);
		ui.displayDTOs(items);
		return items;
	}
	
	public List<OrderItem> addItem() {
		readAll();
		ui.fmtOutput("         Please enter an order id");
		Long orderId = utils.getLong();
		ui.fmtOutput("          Please enter an item id"); // Add choice to see items here?
		Long itemId = utils.getLong();
		ui.fmtOutput("          Please enter a quantity");
		Long quantity = utils.getLong();
		orderItemDAO.create(new OrderItem(orderId, itemId, quantity)); // Do something with return?
		ui.fmtOutput("           Item added to order"); // DO MORE HERE!
		return orderItemDAO.readAll(orderId);
	}
	
	public List<OrderItem> orderItems() {
		readAll();
		ui.fmtOutput("            Please enter an order id");
		Long orderId = utils.getLong();
		return readAllOrderItems(orderId);
	}
	
	public int removeItem() {
		readAll();
		ui.fmtOutput("         Please enter an order id");
		Long orderId = utils.getLong();
		readAllOrderItems(orderId);
		ui.fmtOutput("      Please enter an ID to delete");
		Long itemId = utils.getLong();
		int result = orderItemDAO.delete(itemId);
		ui.fmtOutput("        Item removed from order");
		return result;
	}
	
	public void orderCost() {
		readAll();
		ui.fmtOutput("            Please enter an order id");
		Long orderId = utils.getLong();
		ui.displayDTO(orderDAO.totalCost(orderId));
	}
}
