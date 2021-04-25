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
 */
public class OrderController implements CrudController<Order> {

	public static final Logger LOGGER = LogManager.getLogger();

	private final CustomerController cController;
	private final ItemController iController;
	private final OrderItemDAO orderItemDAO;
	private final OrderDAO orderDAO;
	private final Utils utils;
	private final UI ui;
	
	public OrderController(OrderDAO oDAO, OrderItemDAO oIDAO, CustomerController cController, ItemController iController, Utils utils, UI ui) {
//		super();
		this.cController = cController;
		this.iController = iController;
		this.orderItemDAO = oIDAO;
		this.orderDAO = oDAO;
		this.utils = utils;
		this.ui = ui;
	}

	/**
	 * Reads all orders to the logger.
	 * @return A list of orders.
	 */
	@Override
	public List<Order> readAll() {
		List<Order> orders = orderDAO.readAll();
		ui.fmtHeader("               Active orders                |");
		ui.displayDTOs(orders);
		return orders;
	}
	
	/**
	 * Creates a Order by taking in user input.
	 * @return An Order object representing the order created.
	 */
	@Override
	public Order create() {
		ui.fmtOutput("      Display existing customers?  Y/N      |");
		if (utils.getYN().equals("y")) cController.readAll();
		ui.fmtOutput("        Please enter a customer id          |");
		Long customerId = utils.getLong();
		if (cController.read(customerId) != null) {
			Order order = orderDAO.create(new Order(customerId));
			if (order != null)
				ui.fmtOutput("        Order successfully created          |");
			return order;
		}
		return null;
	}

	/**
	 * Updates an existing Order by taking in user input.
	 * @return An Order object representing the Order updated.
	 */
	@Override
	public Order update() {
		ui.fmtOutput("        Display existing orders?  Y/N       |");
		if (utils.getYN().equals("y")) readAll();
		ui.fmtOutput("     Please enter an order ID to update     |");
		Long orderId = utils.getLong();
		if (orderDAO.read(orderId) != null) {
			ui.fmtOutput("      Display existing customers?  Y/N      |");
			if (utils.getYN().equals("y")) cController.readAll();
			ui.fmtOutput("         Please enter a customer ID         |");
			Long customerId = utils.getLong();
			if (cController.read(customerId) != null) {
				Order order = orderDAO.update(new Order(orderId, customerId));
				if (order != null) {
					ui.fmtOutput("        Order successfully updated          |");
					ui.displayDTO(order);
					return order;
				}
			}
		}
		return null;
	}

	/**
	 * Deletes an existing Order by the id of the Order
	 * @return The number of rows affected by the delete. (Should be 1 or 0).
	 */
	@Override
	public int delete() {
		ui.fmtOutput("        Display existing orders?  Y/N       |");
		if (utils.getYN().equals("y")) readAll();
		ui.fmtOutput("     Please enter an order ID to delete     |");
		Long id = utils.getLong();
		int result = orderDAO.delete(id);
		if (result != 0)
			ui.fmtOutput("        Order successfully deleted          |");
		return result;
	}
	
	/**
	 * Reads all order items to the logger.
	 * @return A list of order items.
	 */
	public List<OrderItem> readAllOrderItems(Long orderId) {
		List<OrderItem> oItems = orderItemDAO.readAll(orderId);
		ui.fmtHeader("             Items in order #" + orderId + "              |");
		if (oItems.size() > 0) {
			ui.displayDTOs(oItems);
		} else {
			ui.fmtOutput("             No items in order              |");
		}
		return oItems;
	}
	
	/**
	 * Adds an item to an order by taking user input.
	 */
	public void addItem() {
		ui.fmtOutput("        Display existing orders?  Y/N       |");
		if (utils.getYN().equals("y")) readAll();
		ui.fmtOutput("         Please enter an order ID           |");
		Long orderId = utils.getLong();
		if (orderDAO.read(orderId) != null) {
			ui.fmtOutput("        Display existing items?  Y/N        |");
			if (utils.getYN().equals("y")) iController.readAll();
			ui.fmtOutput("          Please enter an item ID           |");
			Long itemId = utils.getLong();
			if (iController.read(itemId) != null) {
				ui.fmtOutput("          Please enter a quantity           |");
				Long quantity = utils.getLong();
				OrderItem orderItem = orderItemDAO.create(new OrderItem(orderId, itemId, quantity));
				if (orderItem != null) {
					ui.fmtOutput("            Item added to order             |");
					readAllOrderItems(orderId);
				}				
			}
		}
	}
	
	/**
	 * Reads all order items to the logger by taking user input (order id).
	 * @return An array containing the number of order items and the order id.
	 */
	public long[] orderItems() {
		ui.fmtOutput("        Display existing orders?  Y/N       |");
		if (utils.getYN().equals("y")) readAll();
		ui.fmtOutput("          Please enter an order ID          |");
		Long orderId = utils.getLong();
		long[] results = new long[2];
		if (orderDAO.read(orderId) != null) {
			results[0] = readAllOrderItems(orderId).size();
			results[1] = orderId;
		}
		return results;
	}
	
	/**
	 * Removes an item from an order by taking user input.
	 * @return The number of rows affected by the delete. (Should be 1 or 0). 
	 */
	public int removeItem() {
		long[] oItems = orderItems();
		long size = oItems[0];
		long orderId = oItems[1];
		int result = 0;
		if (size > 0) {
			ui.fmtOutput("        Please enter an ID to delete        |");
			Long orderItemId = utils.getLong();
			result = orderItemDAO.delete(orderItemId, orderId);
			if (result != 0) {
				ui.fmtOutput("          Item removed from order           |");
				return result;
			}
		}
		return result;
	}
	
	/**
	 * Calculates the cost of the order by taking user input.
	 */
	public void orderCost() {
		ui.fmtOutput("        Display existing orders?  Y/N       |");
		if (utils.getYN().equals("y")) readAll();
		ui.fmtOutput("          Please enter an order ID          |");
		Long orderId = utils.getLong();
		if (orderDAO.read(orderId) != null) {
			Order result = orderDAO.totalCost(orderId);
			if (result != null) {
				ui.displayDTO(result);
			} else {
				ui.fmtOutput("         Order #"+ orderId +" contains no items.        |");
			}
		}
	}
}
