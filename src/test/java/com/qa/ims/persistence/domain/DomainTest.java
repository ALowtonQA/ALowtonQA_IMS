package com.qa.ims.persistence.domain;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class DomainTest {

	@Test
	public void testGetDescription() {
		assertEquals("Customer Menu - Customer Info", Domain.CUSTOMER.getDescription());
	}
	
	@Test
	public void testGetDomain() {
		assertEquals(Domain.CUSTOMER, Domain.getDomain(1));
		assertEquals(Domain.ITEM, Domain.getDomain(2));
		assertEquals(Domain.ORDER, Domain.getDomain(3));
		assertEquals(Domain.STOP, Domain.getDomain(4));
		assertEquals(null, Domain.getDomain(5));
	}
}
