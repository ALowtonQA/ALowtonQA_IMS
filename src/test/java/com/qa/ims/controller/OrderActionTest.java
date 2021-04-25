package com.qa.ims.controller;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class OrderActionTest {

	@Test
	public void testGetDescription() {
		assertEquals("Create new orders in the database", OrderAction.CREATE.getDescription());
	}
	
	@Test
	public void testGetOrderAction() {
		assertEquals(OrderAction.CREATE, OrderAction.getAction(1));
		assertEquals(OrderAction.READ, OrderAction.getAction(2));
		assertEquals(OrderAction.UPDATE, OrderAction.getAction(3));
		assertEquals(OrderAction.DELETE, OrderAction.getAction(4));
		assertEquals(OrderAction.ADD, OrderAction.getAction(5));
		assertEquals(OrderAction.REMOVE, OrderAction.getAction(6));
		assertEquals(OrderAction.ITEMS, OrderAction.getAction(7));
		assertEquals(OrderAction.TOTAL, OrderAction.getAction(8));
		assertEquals(OrderAction.RETURN, OrderAction.getAction(9));
		assertEquals(null, OrderAction.getAction(10));
	}
}
