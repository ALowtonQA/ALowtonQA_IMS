package com.qa.ims.persistence.domain;

import org.junit.Test;

import nl.jqno.equalsverifier.EqualsVerifier;
import nl.jqno.equalsverifier.Warning;

public class CustomerTest {
	
	@Test
	public void testEquals() {
		EqualsVerifier.forClass(Customer.class)
		.suppress(Warning.NONFINAL_FIELDS)
		.usingGetClass()
		.verify();
	}
}
