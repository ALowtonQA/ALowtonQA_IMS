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
import com.qa.ims.exceptions.CustomerNotFoundException;
import com.qa.ims.persistence.domain.Customer;
import com.qa.ims.utils.UI;

public class CustomerDAO implements Dao<Customer> {

	public static final Logger LOGGER = LogManager.getLogger();
	private final Connection conn;
	private final UI ui;

	public CustomerDAO(UI ui, Connection conn) {
		this.conn = conn;
		this.ui = ui;
	}

	@Override
	public Customer modelFromResultSet(ResultSet resultSet) throws SQLException {
		Long id = resultSet.getLong("id");
		String firstName = resultSet.getString("first_name");
		String surname = resultSet.getString("surname");
		return new Customer(id, firstName, surname);
	}

	/**
	 * Reads all customers from the database
	 * 
	 * @return A list of customers
	 */
	@Override
	public List<Customer> readAll() {
		try (Statement statement = conn.createStatement();
				ResultSet resultSet = statement.executeQuery("SELECT * FROM customers ORDER BY id")) {
			List<Customer> customers = new ArrayList<>();
			while (resultSet.next()) {
				customers.add(modelFromResultSet(resultSet));
			}
			return customers;
		} catch (SQLException e) {
			LOGGER.debug(e);
			LOGGER.error(ui.formatError(e.getMessage()));
		}
		return new ArrayList<>();
	}

	public Customer readLatest() {
		try (Statement statement = conn.createStatement();
				ResultSet resultSet = statement.executeQuery("SELECT * FROM customers ORDER BY id DESC LIMIT 1")) {
			resultSet.next();
			return modelFromResultSet(resultSet);
		} catch (Exception e) {
			LOGGER.debug(e);
			LOGGER.error(ui.formatError(e.getMessage()));
		}
		return null;
	}

	/**
	 * Creates a customer in the database
	 * 
	 * @param customer - takes in a customer object. id will be ignored
	 */
	@Override
	public Customer create(Customer customer) {
		try (PreparedStatement statement = conn
						.prepareStatement("INSERT INTO customers(first_name, surname) VALUES (?, ?)")) {
			statement.setString(1, customer.getFirstName());
			statement.setString(2, customer.getSurname());
			statement.executeUpdate();
			return readLatest();
		} catch (Exception e) {
			LOGGER.debug(e);
			LOGGER.error(ui.formatError(e.getMessage()));
		}
		return null;
	}

	@Override
	public Customer read(Long id) {
		try (PreparedStatement statement = conn.prepareStatement("SELECT * FROM customers WHERE id = ?")) {
			statement.setLong(1, id);
			try (ResultSet resultSet = statement.executeQuery()) {
				if (!resultSet.isBeforeFirst())   
					throw new CustomerNotFoundException(id);
				resultSet.next();
				return modelFromResultSet(resultSet);
			}
		} catch (SQLException e) {
			LOGGER.debug(e);
			LOGGER.error(ui.formatError(e.getMessage()));
		} catch (CustomerNotFoundException cnfe) {
			LOGGER.debug(cnfe);
			LOGGER.error(ui.formatError("   "+cnfe.getMessage()+"    |"));
		}
		return null;
	}

	/**
	 * Updates a customer in the database
	 * 
	 * @param customer - takes in a customer object, the id field will be used to
	 *                 update that customer in the database
	 * @return
	 * @throws CustomerNotFoundException 
	 */
	@Override
	public Customer update(Customer customer) {
		try (PreparedStatement statement = conn
						.prepareStatement("UPDATE customers SET first_name = ?, surname = ? WHERE id = ?")) {
			statement.setString(1, customer.getFirstName());
			statement.setString(2, customer.getSurname());
			statement.setLong(3, customer.getId());
			statement.executeUpdate();
			return read(customer.getId());
		} catch (SQLException e) {
			LOGGER.debug(e);
			LOGGER.error(ui.formatError(e.getMessage()));
		}
		return null;
	}

	/**
	 * Deletes a customer in the database
	 * 
	 * @param id - id of the customer
	 */
	@Override
	public int delete(long id) {
		try (PreparedStatement statement = conn.prepareStatement("DELETE FROM customers WHERE id = ?")) {
			statement.setLong(1, id);
			int result = statement.executeUpdate();
			if (result == 0)
				throw new CustomerNotFoundException(id);
			return result;
		} catch (SQLException e) {
			LOGGER.debug(e);
			LOGGER.error(ui.formatError(e.getMessage()));
		} catch (CustomerNotFoundException cnfe) {
			LOGGER.debug(cnfe);
			LOGGER.error(ui.formatError("   "+cnfe.getMessage()+"    |"));
		}
		return 0;
	}

}
