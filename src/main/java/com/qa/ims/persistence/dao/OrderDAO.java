package com.qa.ims.persistence.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import com.qa.ims.exceptions.OrderNotFoundException;
import com.qa.ims.persistence.domain.Order;
import com.qa.ims.utils.UI;

public class OrderDAO implements Dao<Order> {

	public static final Logger LOGGER = LogManager.getLogger();
	private final Connection conn;
	private final UI ui;
	
	public OrderDAO(UI ui, Connection conn) {
		this.conn = conn;
		this.ui = ui;
	}

	@Override
	public Order modelFromResultSet(ResultSet resultSet) throws SQLException {
		Long id = resultSet.getLong("id");
		String customerName = resultSet.getString("customer");
		Timestamp timestamp = resultSet.getTimestamp("date");
		Date date = new Date(timestamp.getTime());
		return new Order(id, customerName, date);
	}

	public Order modelTotalCost(ResultSet resultSet) throws SQLException {
		Long id = resultSet.getLong("id");
		String customerName = resultSet.getString("customer");
		Timestamp timestamp = resultSet.getTimestamp("date");
		Date date = new Date(timestamp.getTime());
		double total = resultSet.getDouble("total_price");
		return new Order(id, customerName, date, total);
	}	
	
	/**
	 * Reads all orders from the database
	 * 
	 * @return A list of orders
	 */
	@Override
	public List<Order> readAll() {
		try (Statement statement = conn.createStatement();
				ResultSet resultSet = statement.executeQuery(
					"SELECT o.id, CONCAT(c.first_name, ' ', c.surname) AS customer, `date`"
							+ "FROM orders AS o"
							+ "	INNER JOIN customers AS c"
							+ " ON o.cust_id = c.id"
							+ " ORDER BY id")) {
			List<Order> orders = new ArrayList<>();
			while (resultSet.next()) {
				orders.add(modelFromResultSet(resultSet));
			}
			return orders;
		} catch (SQLException e) {
			LOGGER.debug(e);
			LOGGER.error(ui.formatError(e.getMessage()));
		}
		return new ArrayList<>();
	}

	public Order readLatest() {
		try (Statement statement = conn.createStatement();
				ResultSet resultSet = statement.executeQuery(
					"SELECT o.id, CONCAT(c.first_name, ' ', c.surname) AS customer, `date`"
							+ "FROM orders AS o"
							+ "	INNER JOIN customers AS c"
							+ " ON o.cust_id = c.id"
							+ " ORDER BY id DESC LIMIT 1")) {
			resultSet.next();
			return modelFromResultSet(resultSet);
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
	public Order create(Order order) {
		try (PreparedStatement statement = conn
						.prepareStatement("INSERT INTO orders(cust_id) VALUES (?)")) {
			statement.setLong(1, order.getCustomerId());
			statement.executeUpdate();
			return readLatest();
		} catch (SQLException e) {
			LOGGER.debug(e);
			LOGGER.error(ui.formatError(e.getMessage()));
		}
		return null;
	}

	@Override
	public Order read(Long id) {
		try (PreparedStatement statement = conn.prepareStatement(
				"SELECT o.id, CONCAT(c.first_name, ' ', c.surname) AS customer, `date`"
						+ "FROM orders AS o"
						+ "	INNER JOIN customers AS c"
						+ " ON o.cust_id = c.id"
						+ " WHERE o.id = ?")) {
			statement.setLong(1, id);
			try (ResultSet resultSet = statement.executeQuery()) {
				if (!resultSet.isBeforeFirst())   
					throw new OrderNotFoundException(id);
				resultSet.next();
				return modelFromResultSet(resultSet);
			}
		} catch (SQLException e) {
			LOGGER.debug(e);
			LOGGER.error(ui.formatError(e.getMessage()));
		} catch (OrderNotFoundException infe) {
			LOGGER.debug(infe);
			LOGGER.error(ui.formatError("    "+infe.getMessage()+"     |"));
		}
		return null;
	}

	/**
	 * Updates a order in the database
	 * 
	 * @param order - takes in a order object, the id field will be used to
	 *                 update that order in the database
	 * @return
	 */
	@Override
	public Order update(Order order) {
		try (PreparedStatement statement = conn
						.prepareStatement("UPDATE orders SET cust_id = ? WHERE id = ?")) {
			statement.setLong(1, order.getCustomerId());
			statement.setLong(2, order.getId());
			statement.executeUpdate();
			return read(order.getId());
		} catch (SQLException e) {
			LOGGER.debug(e);
			LOGGER.error(ui.formatError(e.getMessage()));
		}
		return null;
	}

	/**
	 * Deletes a order in the database
	 * 
	 * @param id - id of the order
	 */
	@Override
	public int delete(long id) {
		try (PreparedStatement statement = conn.prepareStatement("DELETE FROM orders WHERE id = ?")) {
			statement.setLong(1, id);
			int result = statement.executeUpdate();
			if (result == 0)
				throw new OrderNotFoundException(id);
			return result;
		} catch (SQLException e) {
			LOGGER.debug(e);
			LOGGER.error(ui.formatError(e.getMessage()));
		} catch (OrderNotFoundException infe) {
			LOGGER.debug(infe);
			LOGGER.error(ui.formatError("    "+infe.getMessage()+"     |"));
		}
		return 0;
	}
	
	public Order totalCost(Long id) {
		try (PreparedStatement statement = conn.prepareStatement(
						"SELECT o.id, CONCAT(c.first_name, ' ', c.surname) AS customer, `date`, SUM(i.price * oi.quantity) as total_price"
						+ " FROM orders as o"
						+ "  INNER JOIN customers as c"
						+ "  ON o.cust_id = c.id"
						+ "	 INNER JOIN order_items as oi"
						+ "	 ON o.id = oi.order_id"
						+ "	 INNER JOIN items as i"
						+ "	 ON oi.item_id = i.id"
						+ " WHERE o.id = ?"
						+ " HAVING total_price IS NOT NULL")) {
			statement.setLong(1, id);
			try (ResultSet resultSet = statement.executeQuery()) {
				if (!resultSet.isBeforeFirst())
					return null;
				resultSet.next();
				return modelTotalCost(resultSet);
			}
		} catch (Exception e) {
			LOGGER.debug(e);
			LOGGER.error(ui.formatError(e.getMessage()));
		}
		return null;
	}
}
