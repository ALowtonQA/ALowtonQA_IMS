package com.qa.ims.controller;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class ActionTest {

	@Test
	public void testGetDescription() {
		assertEquals("Create new entities in the database", Action.CREATE.getDescription());
	}
	
	@Test
	public void testGetAction() {
		assertEquals(Action.CREATE, Action.getAction(1));
		assertEquals(Action.READ, Action.getAction(2));
		assertEquals(Action.UPDATE, Action.getAction(3));
		assertEquals(Action.DELETE, Action.getAction(4));
		assertEquals(Action.RETURN, Action.getAction(5));
		assertEquals(null, Action.getAction(6));
	}
}
