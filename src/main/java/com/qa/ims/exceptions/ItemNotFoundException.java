package com.qa.ims.exceptions;

public class ItemNotFoundException extends Exception {
	/**
	 * 
	 */
	public ItemNotFoundException(Long id) {
		super("Could not find an item with ID = " + id);
	}
	private static final long serialVersionUID = 5620718840139344556L;
}
