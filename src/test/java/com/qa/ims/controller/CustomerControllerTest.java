package com.qa.ims.controller;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import com.qa.ims.persistence.dao.CustomerDAO;
import com.qa.ims.persistence.domain.Customer;
import com.qa.ims.utils.UI;
import com.qa.ims.utils.Utils;

@RunWith(MockitoJUnitRunner.class)
public class CustomerControllerTest {

	@Mock
	private UI ui;
	
	@Mock
	private Utils utils;

	@Mock
	private CustomerDAO dao;

	@InjectMocks
	private CustomerController controller;

	@Test
	public void testCreate() {
		final String fName = "barry", lName = "scott";
		final Customer created = new Customer(fName, lName);

		Mockito.when(utils.getString()).thenReturn(fName, lName);
		Mockito.when(dao.create(created)).thenReturn(created);

		assertEquals(created, controller.create());

		Mockito.verify(utils, Mockito.times(2)).getString();
		Mockito.verify(dao, Mockito.times(1)).create(created);
	}

	@Test
	public void testReadAll() {
		List<Customer> customers = new ArrayList<>();
		customers.add(new Customer(1L, "jordan", "harrison"));

		Mockito.when(dao.readAll()).thenReturn(customers);

		assertEquals(customers, controller.readAll());

		Mockito.verify(dao, Mockito.times(1)).readAll();
	}

	@Test
	public void testUpdate() {
		List<Customer> customers = new ArrayList<>();
		Customer updated = new Customer(1L, "chris", "perrins");
		customers.add(updated);

		Mockito.when(utils.getYN()).thenReturn("y");
		Mockito.when(utils.getLong()).thenReturn(1L);
		Mockito.when(utils.getString()).thenReturn(updated.getFirstName(), updated.getSurname());
		Mockito.when(dao.read(1L)).thenReturn(updated);
		Mockito.when(dao.update(updated)).thenReturn(updated);
		
		assertEquals(updated, controller.update());

		Mockito.verify(utils, Mockito.times(1)).getYN();
		Mockito.verify(utils, Mockito.times(1)).getLong();
		Mockito.verify(utils, Mockito.times(2)).getString();
		Mockito.verify(dao, Mockito.times(1)).read(1L);
		Mockito.verify(dao, Mockito.times(1)).update(updated);
	}
	
	@Test
	public void testDelete() {
		final long id = 1L;
		List<Customer> customers = new ArrayList<>();
		customers.add(new Customer(id, "jordan", "harrison"));
		
		Mockito.when(utils.getYN()).thenReturn("y");
		Mockito.when(utils.getLong()).thenReturn(id);
		Mockito.when(dao.delete(id)).thenReturn(1);

		assertEquals(1, controller.delete());
		
		Mockito.verify(utils, Mockito.times(1)).getYN();
		Mockito.verify(utils, Mockito.times(1)).getLong();
		Mockito.verify(dao, Mockito.times(1)).delete(id);
	}
	
	@Test
	public void testRead() {
		final long id = 1L;
		Customer read = new Customer(id, "jordan", "harrison");
		
		Mockito.when(dao.read(id)).thenReturn(read);
		
		assertEquals(read, controller.read(id));
		
		Mockito.verify(dao, Mockito.times(1)).read(id);
	}
}
