package com.qa.ims.controller;

import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.qa.ims.IMS;
import com.qa.ims.exceptions.CustomerNotFoundException;
import com.qa.ims.persistence.dao.CustomerDAO;
import com.qa.ims.persistence.domain.Customer;
import com.qa.ims.utils.Utils;

/**
 * Takes in customer details for CRUD functionality
 *
 */
public class CustomerController implements CrudController<Customer> {

	public static final Logger LOGGER = LogManager.getLogger();

	private final CustomerDAO customerDAO;
	private final Utils utils;

	public CustomerController(CustomerDAO customerDAO, Utils utils) {
//		super();
		this.customerDAO = customerDAO;
		this.utils = utils;
	}

	/**
	 * Reads all customers to the logger
	 */
	@Override
	public List<Customer> readAll() {
		List<Customer> customers = customerDAO.readAll();
		IMS.ui.fmtHeader("                 Customers                  |");
		IMS.ui.displayDTOs(customers);
		return customers;
	}

	/**
	 * Creates a customer by taking in user input
	 */
	@Override
	public Customer create() {
		IMS.ui.fmtOutput("         Please enter a first name          |");
		String firstName = utils.getString();
		IMS.ui.fmtOutput("          Please enter a surname            |");
		String surname = utils.getString();
		Customer customer = customerDAO.create(new Customer(firstName, surname)); 
		if (customer != null)
			IMS.ui.fmtOutput("       Customer successfully created        |");
		return customer;
	}

	/**
	 * Updates an existing customer by taking in user input
	 * @throws CustomerNotFoundException 
	 */
	@Override
	public Customer update() {
		IMS.ui.fmtOutput("      Display existing customers?  Y/N      |");
		if (utils.getYN().equals("y")) readAll();
		IMS.ui.fmtOutput("   Please enter a customer ID to update     |");
		Long id = utils.getLong();
		IMS.ui.fmtOutput("         Please enter a first name          |");
		String firstName = utils.getString();
		IMS.ui.fmtOutput("          Please enter a surname            |");
		String surname = utils.getString();
		Customer customer = customerDAO.update(new Customer(id, firstName, surname));
		if (customer != null) {
			IMS.ui.fmtOutput("       Customer successfully updated        |");
			IMS.ui.displayDTO(customer);
		}
		return customer;
	}

	/**
	 * Deletes an existing customer by the id of the customer
	 * 
	 * @return
	 */
	@Override
	public int delete() {
		IMS.ui.fmtOutput("      Display existing customers?  Y/N      |");
		if (utils.getYN().equals("y")) readAll();
		IMS.ui.fmtOutput("    Please enter a customer ID to delete    |");
		Long id = utils.getLong();
		int result = customerDAO.delete(id);
		if (result != 0)
			IMS.ui.fmtOutput("       Customer successfully deleted        |");
		return result;
	}
	
	public Customer read(long id) {
		return customerDAO.read(id);
	}
}
