package com.qa.ims.exceptions;

public class OrderNotFoundException extends Exception {
	/**
	 * 
	 */
	public OrderNotFoundException(Long id) {
		super("Could not find an order with ID = " + id);
	}
	private static final long serialVersionUID = -893187539056911193L;
}
