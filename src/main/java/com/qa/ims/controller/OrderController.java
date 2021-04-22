package com.qa.ims.controller;

import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.qa.ims.IMS;
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

	private final CustomerController cController;
	private final ItemController iController;
	private final OrderItemDAO orderItemDAO;
	private final OrderDAO orderDAO;
	private final Utils utils;
	
	public OrderController(OrderDAO oDAO, OrderItemDAO oIDAO, CustomerController cController, ItemController iController, Utils utils) {
//		super();
		this.cController = cController;
		this.iController = iController;
		this.orderItemDAO = oIDAO;
		this.orderDAO = oDAO;
		this.utils = utils;
	}

	/**
	 * Reads all Orders to the logger
	 */
	@Override
	public List<Order> readAll() {
		List<Order> orders = orderDAO.readAll();
		IMS.ui.fmtHeader("               Active orders                |");
		IMS.ui.displayDTOs(orders);
		return orders;
	}
	
	/**
	 * Creates a Order by taking in user input
	 */
	@Override
	public Order create() {
		IMS.ui.fmtOutput("      Display existing customers?  Y/N      |");
		if (utils.getYN().equals("y")) cController.readAll();
		IMS.ui.fmtOutput("        Please enter a customer id");
		Long customerId = utils.getLong();
		if (cController.read(customerId) != null) {
			Order order = orderDAO.create(new Order(customerId));
			if (order != null)
				IMS.ui.fmtOutput("        Order successfully created          |");
			return order;
		}
		return null;
	}

	/**
	 * Updates an existing Order by taking in user input
	 */
	@Override
	public Order update() {
		IMS.ui.fmtOutput("        Display existing orders?  Y/N       |");
		if (utils.getYN().equals("y")) readAll();
		IMS.ui.fmtOutput("     Please enter an order ID to update     |");
		Long orderId = utils.getLong();
		if (orderDAO.read(orderId) != null) {
			IMS.ui.fmtOutput("      Display existing customers?  Y/N      |");
			if (utils.getYN().equals("y")) cController.readAll();
			IMS.ui.fmtOutput("         Please enter a customer ID         |");
			Long customerId = utils.getLong();
			if (cController.read(customerId) != null) {
				Order order = orderDAO.update(new Order(orderId, customerId));
				if (order != null) {
					IMS.ui.fmtOutput("        Order successfully updated          |");
					IMS.ui.displayDTO(order);
					return order;
				}
			}
		}
		return null;
	}

	/**
	 * Deletes an existing Order by the id of the Order
	 * 
	 * @return
	 */
	@Override
	public int delete() {
		IMS.ui.fmtOutput("        Display existing orders?  Y/N       |");
		if (utils.getYN().equals("y")) readAll();
		IMS.ui.fmtOutput("     Please enter an order ID to delete     |");
		Long id = utils.getLong();
		int result = orderDAO.delete(id);
		if (result != 0)
			IMS.ui.fmtOutput("        Order successfully deleted          |");
		return result;
	}
	
	public List<OrderItem> readAllOrderItems(Long orderId) {
		List<OrderItem> oItems = orderItemDAO.readAll(orderId);
		IMS.ui.fmtHeader("             Items in order #" + orderId + "              |");
		if (oItems.size() > 0) {
			IMS.ui.displayDTOs(oItems);
		} else {
			IMS.ui.fmtOutput("             No items in order              |");
		}
		return oItems;
	}
	
	public void addItem() {
		IMS.ui.fmtOutput("        Display existing orders?  Y/N       |");
		if (utils.getYN().equals("y")) readAll();
		IMS.ui.fmtOutput("         Please enter an order ID           |");
		Long orderId = utils.getLong();
		if (orderDAO.read(orderId) != null) {
			IMS.ui.fmtOutput("        Display existing items?  Y/N        |");
			if (utils.getYN().equals("y")) iController.readAll();
			IMS.ui.fmtOutput("          Please enter an item ID           |");
			Long itemId = utils.getLong();
			if (iController.read(itemId) != null) {
				IMS.ui.fmtOutput("          Please enter a quantity           |");
				Long quantity = utils.getLong();
				OrderItem orderItem = orderItemDAO.create(new OrderItem(orderId, itemId, quantity));
				if (orderItem != null) {
					IMS.ui.fmtOutput("            Item added to order             |");
					readAllOrderItems(orderId);
				}				
			}
		}
	}
	
	public long[] orderItems() {
		IMS.ui.fmtOutput("        Display existing orders?  Y/N       |");
		if (utils.getYN().equals("y")) readAll();
		IMS.ui.fmtOutput("          Please enter an order ID          |");
		Long orderId = utils.getLong();
		long[] results = new long[2];
		if (orderDAO.read(orderId) != null) {
			results[0] = readAllOrderItems(orderId).size();
			results[1] = orderId;
		}
		return results;
	}
	
	public void removeItem() {
		long[] oItems = orderItems();
		long size = oItems[0];
		long orderId = oItems[1];
		int result = 0;
		if (size > 0) {
			IMS.ui.fmtOutput("        Please enter an ID to delete        |");
			Long orderItemId = utils.getLong();
			result = orderItemDAO.delete(orderItemId, orderId);
			if (result != 0)
				IMS.ui.fmtOutput("          Item removed from order           |");
		}
	}
	
	public void orderCost() {
		IMS.ui.fmtOutput("        Display existing orders?  Y/N       |");
		if (utils.getYN().equals("y")) readAll();
		IMS.ui.fmtOutput("          Please enter an order ID          |");
		Long orderId = utils.getLong();
		if (orderDAO.read(orderId) != null) {
			Order result = orderDAO.totalCost(orderId);
			if (result != null) {
				IMS.ui.displayDTO(result);
			} else {
				IMS.ui.fmtOutput("         Order #"+ orderId +" contains no items.        |");
			}
		}
	}
}
