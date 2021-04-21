package com.qa.ims.exceptions;

public class CustomerNotFoundException extends Exception {
	/**
	 * 
	 */
	public CustomerNotFoundException(Long id) {
		super("Could not find a customer with ID = " + id);
	}
	
	private static final long serialVersionUID = 4430093146627824002L;
}
