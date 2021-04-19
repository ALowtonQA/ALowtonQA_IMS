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

	private final CustomerController cController;
	private final ItemController iController;
	private final OrderItemDAO orderItemDAO;
	private final OrderDAO orderDAO;
	private final Utils utils;
	private final UI ui;
	
	public OrderController(OrderDAO oDAO, OrderItemDAO oIDAO, CustomerController cController, ItemController iController, UI ui, Utils utils) {
//		super();
		this.cController = cController;
		this.iController = iController;
		this.orderItemDAO = oIDAO;
		this.orderDAO = oDAO;
		this.ui = ui;
		this.utils = utils;
	}

	/**
	 * Reads all Orders to the logger
	 */
	@Override
	public List<Order> readAll() {
		List<Order> orders = orderDAO.readAll();
		ui.fmtHeader("               Active orders                |");
		ui.displayDTOs(orders);
		return orders;
	}
	
	/**
	 * Creates a Order by taking in user input
	 */
	@Override
	public Order create() {
		ui.fmtOutput("      Display existing customers?  Y/N      |");
		if (utils.getYN().equals("y")) cController.readAll();
		ui.fmtOutput("        Please enter a customer id");
		Long customerId = utils.getLong();
		Order order = orderDAO.create(new Order(customerId));
		ui.fmtOutput("        Order successfully created          |");
		return order;
	}

	/**
	 * Updates an existing Order by taking in user input
	 */
	@Override
	public Order update() {
		ui.fmtOutput("        Display existing orders?  Y/N       |");
		if (utils.getYN().equals("y")) readAll();
		ui.fmtOutput("     Please enter an order ID to update     |");
		Long id = utils.getLong();
		ui.fmtOutput("      Display existing customers?  Y/N      |");
		if (utils.getYN().equals("y")) cController.readAll();
		ui.fmtOutput("         Please enter a customer ID         |");
		Long customerId = utils.getLong();
		Order order = orderDAO.update(new Order(id, customerId));
		ui.fmtOutput("        Order successfully updated          |");
		return order;
	}

	/**
	 * Deletes an existing Order by the id of the Order
	 * 
	 * @return
	 */
	@Override
	public int delete() {
		ui.fmtOutput("        Display existing orders?  Y/N       |");
		if (utils.getYN().equals("y")) readAll();
		ui.fmtOutput("     Please enter an order ID to delete     |");
		Long id = utils.getLong();
		int result = orderDAO.delete(id);
		ui.fmtOutput("        Order successfully deleted          |");
		return result;
	}
	
	public List<OrderItem> readAllOrderItems(Long orderId) {
		List<OrderItem> items = orderItemDAO.readAll(orderId);
		ui.fmtHeader("             Items in order #" + orderId + "              |");
		ui.displayDTOs(items);
		return items;
	}
	
	public List<OrderItem> addItem() {
		ui.fmtOutput("        Display existing orders?  Y/N       |");
		if (utils.getYN().equals("y")) readAll();
		ui.fmtOutput("         Please enter an order ID           |");
		Long orderId = utils.getLong();
		ui.fmtOutput("        Display existing items?  Y/N        |");
		if (utils.getYN().equals("y")) iController.readAll();
		ui.fmtOutput("          Please enter an item ID           |");
		Long itemId = utils.getLong();
		ui.fmtOutput("          Please enter a quantity           |");
		Long quantity = utils.getLong();
		orderItemDAO.create(new OrderItem(orderId, itemId, quantity));
		ui.fmtOutput("            Item added to order             |");
		return orderItemDAO.readAll(orderId);
	}
	
	public List<OrderItem> orderItems() {
		ui.fmtOutput("        Display existing orders?  Y/N       |");
		if (utils.getYN().equals("y")) readAll();
		ui.fmtOutput("          Please enter an order ID          |");
		Long orderId = utils.getLong();
		return readAllOrderItems(orderId);
	}
	
	public int removeItem() {
		orderItems();
		ui.fmtOutput("        Please enter an ID to delete        |");
		Long itemId = utils.getLong();
		int result = orderItemDAO.delete(itemId);
		ui.fmtOutput("          Item removed from order           |");
		return result;
	}
	
	public void orderCost() {
		ui.fmtOutput("        Display existing orders?  Y/N       |");
		if (utils.getYN().equals("y")) readAll();
		ui.fmtOutput("          Please enter an order ID          |");
		Long orderId = utils.getLong();
		ui.displayDTO(orderDAO.totalCost(orderId));
	}
}
