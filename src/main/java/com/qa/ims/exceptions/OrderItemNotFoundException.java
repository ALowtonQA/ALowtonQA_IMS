package com.qa.ims.exceptions;

public class OrderItemNotFoundException extends Exception {
	/**
	 * 
	 */
	public OrderItemNotFoundException(Long id) {
		super("Could not find an order item with ID = " + id);
	}
	private static final long serialVersionUID = 2015015826383072761L;
}
