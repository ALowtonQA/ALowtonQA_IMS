package com.qa.ims.controller;

import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import com.qa.ims.persistence.dao.CustomerDAO;
import com.qa.ims.persistence.domain.Customer;
import com.qa.ims.utils.UI;
import com.qa.ims.utils.Utils;


/**
 * Takes in customer details for CRUD functionality
 */
public class CustomerController implements CrudController<Customer> {

	public static final Logger LOGGER = LogManager.getLogger();

	private final CustomerDAO customerDAO;
	private final Utils utils;
	private final UI ui;

	public CustomerController(CustomerDAO customerDAO, Utils utils, UI ui) {
//		super();
		this.customerDAO = customerDAO;
		this.utils = utils;
		this.ui = ui;
	}

	/**
	 * Reads all customers to the logger.
	 * @return A list of customers.
	 */
	@Override
	public List<Customer> readAll() {
		List<Customer> customers = customerDAO.readAll();
		ui.fmtHeader("                 Customers                  |");
		ui.displayDTOs(customers);
		return customers;
	}

	/**
	 * Creates a customer by taking in user input.
	 * @return A Customer object representing the customer created.
	 */
	@Override
	public Customer create() {
		ui.fmtOutput("         Please enter a first name          |");
		String firstName = utils.getString();
		ui.fmtOutput("          Please enter a surname            |");
		String surname = utils.getString();
		Customer customer = customerDAO.create(new Customer(firstName, surname)); 
		if (customer != null)
			ui.fmtOutput("       Customer successfully created        |");
		return customer;
	}

	/**
	 * Updates an existing customer by taking in user input.
	 * @return A Customer object representing the customer updated.
	 */
	@Override
	public Customer update() {
		ui.fmtOutput("      Display existing customers?  Y/N      |");
		if (utils.getYN().equals("y")) readAll();
		ui.fmtOutput("   Please enter a customer ID to update     |");
		Long id = utils.getLong();
		if (customerDAO.read(id) != null) {
			ui.fmtOutput("         Please enter a first name          |");
			String firstName = utils.getString();
			ui.fmtOutput("          Please enter a surname            |");
			String surname = utils.getString();
			Customer customer = customerDAO.update(new Customer(id, firstName, surname));
			if (customer != null) {
				ui.fmtOutput("       Customer successfully updated        |");
				ui.displayDTO(customer);
			}
			return customer;
		}
		return null;
	}

	/**
	 * Deletes an existing customer by the id of the customer.
	 * @return The number of rows affected by the delete. (Should be 1 or 0).
	 */
	@Override
	public int delete() {
		ui.fmtOutput("      Display existing customers?  Y/N      |");
		if (utils.getYN().equals("y")) readAll();
		ui.fmtOutput("    Please enter a customer ID to delete    |");
		Long id = utils.getLong();
		int result = customerDAO.delete(id);
		if (result != 0)
			ui.fmtOutput("       Customer successfully deleted        |");
		return result;
	}
	
	/**
	 * Reads a customer by id.
	 * @param id of the customer. 
	 * @return A Customer object representing the customer found.
	 */
	public Customer read(long id) {
		return customerDAO.read(id);
	}
}
