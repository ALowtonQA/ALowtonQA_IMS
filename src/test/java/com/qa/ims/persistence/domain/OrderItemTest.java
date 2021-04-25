package com.qa.ims.persistence.domain;

import org.junit.Test;

import nl.jqno.equalsverifier.EqualsVerifier;
import nl.jqno.equalsverifier.Warning;

public class OrderItemTest {
	
	@Test
	public void testEquals() {
		EqualsVerifier.forClass(OrderItem.class)
		.suppress(Warning.NONFINAL_FIELDS)
		.usingGetClass()
		.verify();
	}
}
