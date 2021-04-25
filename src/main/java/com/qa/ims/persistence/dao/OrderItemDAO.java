package com.qa.ims.persistence.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import com.qa.ims.exceptions.OrderItemNotFoundException;
import com.qa.ims.persistence.domain.OrderItem;
import com.qa.ims.utils.UI;

public class OrderItemDAO implements Dao<OrderItem> {

	public static final Logger LOGGER = LogManager.getLogger();
	private final Connection conn;
	private final UI ui;
	
	public OrderItemDAO(UI ui, Connection conn) {
		this.conn = conn;
		this.ui = ui;
	}

	@Override
	public OrderItem modelFromResultSet(ResultSet resultSet) throws SQLException {
		Long id = resultSet.getLong("id");
		Long orderId = resultSet.getLong("order_id");
		String itemName = resultSet.getString("item_name");
		Long quantity = resultSet.getLong("quantity");
		return new OrderItem(id, orderId, itemName, quantity);
	}

	public OrderItem modelForSpecificOrder(ResultSet resultSet) throws SQLException {
		Long id = resultSet.getLong("id");
		String itemName = resultSet.getString("item_name");
		Long quantity = resultSet.getLong("quantity");
		return new OrderItem(id, itemName, quantity);
	}
	
	/**
	 * Reads all order items from the database
	 * 
	 * @return A list of order_items
	 */
	@Override
	public List<OrderItem> readAll() {
		try (Statement statement = conn.createStatement();
				ResultSet resultSet = statement.executeQuery(
						"SELECT oi.id, i.item_name as item_name, quantity"
							+ " FROM order_items AS oi"
							+ "	INNER JOIN items AS i"
							+ " ON oi.item_id = i.id"
							+ " ORDER BY id")) {
			List<OrderItem> order_items = new ArrayList<>();
			while (resultSet.next()) {
				order_items.add(modelFromResultSet(resultSet));
			}
			return order_items;
		} catch (SQLException e) {
			LOGGER.debug(e);
			LOGGER.error(ui.formatError(e.getMessage()));
		}
		return new ArrayList<>();
	}
	/**
	 * Reads all order items from the database based on a specific order Id.
	 * 
	 * @param orderId - The id of the order to display the associated order items.
	 * 
	 * @return A list of order items.
	 */
	public List<OrderItem> readAll(Long orderId) {
		try (PreparedStatement statement = conn.prepareStatement(
					"SELECT oi.id, i.item_name as item_name, quantity"
						+" FROM order_items AS oi"
						+" INNER JOIN items AS i"
						+" ON oi.item_id = i.id"
						+" WHERE oi.order_id = ?"
						+" ORDER BY id")) {
			statement.setLong(1, orderId);
			try(ResultSet resultSet = statement.executeQuery()) {
				List<OrderItem> orderItems = new ArrayList<>();
				while (resultSet.next()) {
					orderItems.add(modelForSpecificOrder(resultSet));
				}
				return orderItems;
			}
		} catch (SQLException e) {
			LOGGER.debug(e);
			LOGGER.error(ui.formatError(e.getMessage()));
		}
		return new ArrayList<>();
	}
	
	public OrderItem readLatest() {
		try (Statement statement = conn.createStatement();
				ResultSet resultSet = statement.executeQuery(
						"SELECT oi.id, i.item_name as item_name, quantity"
							+" FROM order_items AS oi"
							+" INNER JOIN items AS i"
							+" ON oi.item_id = i.id"
							+" ORDER BY oi.id DESC LIMIT 1")) {
			resultSet.next();
			return modelForSpecificOrder(resultSet);
		} catch (Exception e) {
			LOGGER.debug(e);
			LOGGER.error(ui.formatError(e.getMessage()));
		}
		return null;
	}

	/**
	 * Creates a order in the database
	 * 
	 * @param order - takes in a order object. id will be ignored
	 */
	@Override
	public OrderItem create(OrderItem order) {
		try (PreparedStatement statement = conn
				.prepareStatement("INSERT INTO order_items(order_id, item_id, quantity) VALUES (?, ?, ?)")) {
			statement.setLong(1, order.getOrderId());
			statement.setLong(2, order.getItemId());
			statement.setLong(3, order.getQuantity());
			statement.executeUpdate();
			return readLatest();
		} catch (SQLException e) {
			LOGGER.debug(e);
			LOGGER.error(ui.formatError(e.getMessage()));
		}
		return null;
	}

	/**
	 * Deletes an order item in the database
	 * 
	 * @param id - id of the order
	 * @param orderId - id of the order
	 */
	
	public int delete(long id, long orderId) {
		try (PreparedStatement statement = conn.prepareStatement("DELETE FROM order_items WHERE id = ? AND order_id = ?")) {
			statement.setLong(1, id);
			statement.setLong(2, orderId);
			int result = statement.executeUpdate();
			if (result == 0)
				throw new OrderItemNotFoundException(id);
			return result;
		} catch (SQLException e) {
			LOGGER.debug(e);
			LOGGER.error(ui.formatError(e.getMessage()));
		} catch (OrderItemNotFoundException oinfe) {
			LOGGER.debug(oinfe);
			LOGGER.error(ui.formatError(" "+oinfe.getMessage()+"   |"));
		}
		return 0;
	}

	/**
	 * Updates an order item in the database
	 * 
	 * @param order - takes in an order item object, the id field will be used to
	 *                 update that order in the database.
	 * @return
	 */
	@Override
	public OrderItem update(OrderItem order) {
//		try (PreparedStatement statement = conn
//						.prepareStatement("UPDATE order_items SET order_id = ?, item_id = ?, quantity = ? WHERE id = ?")) {
//			statement.setLong(1, order.getOrderId());
//			statement.setLong(2, order.getItemId());
//			statement.setLong(3, order.getQuantity());
//			statement.setLong(4, order.getId());
//			statement.executeUpdate();
//			return read(order.getId());
//		} catch (Exception e) {
//			LOGGER.debug(e);
//			LOGGER.error(e.getMessage());
//		}
		return null;
	}
	
	@Override
	public OrderItem read(Long id) {
//		try (PreparedStatement statement = conn.prepareStatement(
//						"SELECT oi.id, oi.order_id, i.item_name as item_name, quantity"
//								+" FROM order_items AS oi"
//								+" INNER JOIN items AS i"
//								+" ON oi.item_id = i.id"
//								+" WHERE oi.order_id = ?")) {
//			statement.setLong(1, id);
//			try (ResultSet resultSet = statement.executeQuery()) {
//				resultSet.next();
//				return modelFromResultSet(resultSet);
//			}
//		} catch (Exception e) {
//			LOGGER.debug(e);
//			LOGGER.error(e.getMessage());
//		}
		return null;
	}
	
	@Override
	public int delete(long id) {
		return 0;
	}
}
