package com.qa.ims.controller;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

import com.qa.ims.persistence.dao.OrderDAO;
import com.qa.ims.persistence.dao.OrderItemDAO;
import com.qa.ims.persistence.domain.Customer;
import com.qa.ims.persistence.domain.Item;
import com.qa.ims.persistence.domain.Order;
import com.qa.ims.persistence.domain.OrderItem;
import com.qa.ims.utils.UI;
import com.qa.ims.utils.Utils;

@RunWith(MockitoJUnitRunner.class)
public class OrderControllerTest {

	@Mock
	private UI ui;
	
	@Mock
	private Utils utils;
	
	@Mock
	private CustomerController cController;
	
	@Mock
	private ItemController iController;
	
	@Mock
	private OrderItemDAO oIDAO;
	
	@Mock
	private OrderDAO oDAO;
	
	@InjectMocks
	private OrderController controller;

	@Test
	public void testSuccessfulCreate() {
		final long id = 1L;
		final Order order = new Order(id);
		final Customer customer = new Customer(id, "jordan", "harrison");
		List<Customer> customers = new ArrayList<>();
		customers.add(customer);
		
		Mockito.when(utils.getYN()).thenReturn("y");
		Mockito.when(cController.readAll()).thenReturn(customers);
		Mockito.when(utils.getLong()).thenReturn(id);
		Mockito.when(cController.read(id)).thenReturn(customer);
		Mockito.when(oDAO.create(order)).thenReturn(order);

		assertEquals(order, controller.create());

		Mockito.verify(utils, Mockito.times(1)).getYN();
		Mockito.verify(cController, Mockito.times(1)).readAll();
		Mockito.verify(utils, Mockito.times(1)).getLong();
		Mockito.verify(cController, Mockito.times(1)).read(id);
		Mockito.verify(oDAO, Mockito.times(1)).create(order);
	}
	
	@Test
	public void testFailedCreate() {
		final long id = 1L;
		final Customer customer = new Customer(id, "jordan", "harrison");
		List<Customer> customers = new ArrayList<>();
		customers.add(customer);
		
		Mockito.when(utils.getYN()).thenReturn("y");
		Mockito.when(cController.readAll()).thenReturn(customers);
		Mockito.when(utils.getLong()).thenReturn(id);
		Mockito.when(cController.read(id)).thenReturn(null);

		assertEquals(null, controller.create());

		Mockito.verify(utils, Mockito.times(1)).getYN();
		Mockito.verify(cController, Mockito.times(1)).readAll();
		Mockito.verify(utils, Mockito.times(1)).getLong();
		Mockito.verify(cController, Mockito.times(1)).read(id);
	}
	
	@Test
	public void testReadAll() {
		List<Order> orders = new ArrayList<>();
		orders.add(new Order(1L, 1L));

		Mockito.when(oDAO.readAll()).thenReturn(orders);

		assertEquals(orders, controller.readAll());

		Mockito.verify(oDAO, Mockito.times(1)).readAll();
	}

	@Test
	public void testSuccessfulUpdate() {
		List<Customer> customers = new ArrayList<>();
		List<Order> orders = new ArrayList<>();
		final Order order = new Order(1L, 1L);
		final Customer customer = new Customer(1L, "jordan", "harrison");
		customers.add(customer);
		orders.add(order);

		Mockito.when(utils.getYN()).thenReturn("y");
		Mockito.when(utils.getLong()).thenReturn(1L);
		Mockito.when(oDAO.read(1L)).thenReturn(order);
		Mockito.when(cController.readAll()).thenReturn(customers);
		Mockito.when(cController.read(1L)).thenReturn(customer);
		Mockito.when(oDAO.update(order)).thenReturn(order);

		assertEquals(order, controller.update());

		Mockito.verify(utils, Mockito.times(2)).getYN();
		Mockito.verify(utils, Mockito.times(2)).getLong();
		Mockito.verify(oDAO, Mockito.times(1)).read(1L);
		Mockito.verify(cController, Mockito.times(1)).readAll();
		Mockito.verify(cController, Mockito.times(1)).read(1L);
		Mockito.verify(oDAO, Mockito.times(1)).update(order);
	}
	
	@Test
	public void testFailedUpdate() {
		List<Customer> customers = new ArrayList<>();
		List<Order> orders = new ArrayList<>();
		final Order order = new Order(1L, 1L);
		customers.add(new Customer(1L, "jordan", "harrison"));
		orders.add(order);

		Mockito.when(utils.getYN()).thenReturn("y");
		Mockito.when(utils.getLong()).thenReturn(1L);
		Mockito.when(oDAO.read(1L)).thenReturn(null);

		assertEquals(null, controller.update());

		Mockito.verify(utils, Mockito.times(1)).getYN();
		Mockito.verify(utils, Mockito.times(1)).getLong();
		Mockito.verify(oDAO, Mockito.times(1)).read(1L);
		Mockito.verify(oDAO, Mockito.times(0)).update(order);
	}

	@Test
	public void testDelete() {
		List<Order> orders = new ArrayList<>();
		final long id = 1L;
		orders.add(new Order(1L));
		
		Mockito.when(utils.getYN()).thenReturn("y");
		Mockito.when(utils.getLong()).thenReturn(id);
		Mockito.when(oDAO.delete(id)).thenReturn(1);

		assertEquals(1, controller.delete());
		
		Mockito.verify(utils, Mockito.times(1)).getYN();
		Mockito.verify(utils, Mockito.times(1)).getLong();
		Mockito.verify(oDAO, Mockito.times(1)).delete(id);
	}
	
	@Test
	public void testAddItem() {
		List<OrderItem> orderItems = new ArrayList<>();
		List<Order> orders = new ArrayList<>();
		List<Item> items = new ArrayList<>();
		final long id = 1L;
		final Order order = new Order(id);
		final Item item = new Item(id, "Titanic", 9.99);
		final OrderItem orderItem = new OrderItem(id, id, 1L);
		orderItems.add(orderItem);
		orders.add(order);
		items.add(item);
		
		Mockito.when(utils.getYN()).thenReturn("y");
		Mockito.when(utils.getLong()).thenReturn(id);
		Mockito.when(oDAO.read(id)).thenReturn(order);
		Mockito.when(iController.readAll()).thenReturn(items);
		Mockito.when(iController.read(id)).thenReturn(item);
		Mockito.when(oIDAO.create(orderItem)).thenReturn(orderItem);
		
		controller.addItem();
		
		Mockito.verify(utils, Mockito.times(2)).getYN();
		Mockito.verify(utils, Mockito.times(3)).getLong();
		Mockito.verify(oDAO, Mockito.times(1)).read(id);
		Mockito.verify(iController, Mockito.times(1)).readAll();
		Mockito.verify(iController, Mockito.times(1)).read(id);
		Mockito.verify(oIDAO, Mockito.times(1)).create(orderItem);
	}
	
	@Test
	public void testorderItems() {
		OrderController spy = Mockito.spy(controller);
		List<OrderItem> orderItems = new ArrayList<>();
		List<Order> orders = new ArrayList<>();
		final long id = 1L;
		final OrderItem orderItem = new OrderItem(id, id, 1L);
		final Order order = new Order(id);
		orderItems.add(orderItem);
		orders.add(order);
		
		Mockito.when(utils.getYN()).thenReturn("y");
		Mockito.when(utils.getLong()).thenReturn(id);
		Mockito.when(oDAO.read(id)).thenReturn(order);
		Mockito.doReturn(orderItems).when(spy).readAllOrderItems(id);
		
		assertArrayEquals(new long[] {orderItems.size(), id}, spy.orderItems());
		
		Mockito.verify(utils, Mockito.times(1)).getYN();
		Mockito.verify(utils, Mockito.times(1)).getLong();
		Mockito.verify(oDAO, Mockito.times(1)).read(id);
	}
	
	@Test
	public void testSuccessfullyRemoveItem() {
		final long[] oItems = {1, 1};
		OrderController spy = Mockito.spy(controller);
		
		Mockito.when(utils.getLong()).thenReturn(1L);
		Mockito.doReturn(oItems).when(spy).orderItems();
		Mockito.when(oIDAO.delete(1L, 1L)).thenReturn(1);
		
		assertEquals(1, spy.removeItem());
		
		Mockito.verify(utils, Mockito.times(1)).getLong();
		Mockito.verify(oIDAO, Mockito.times(1)).delete(1L, 1L);
	}
	
	@Test
	public void testFailedRemoveItem() {
		final long[] oItems = {1, 1};
		OrderController spy = Mockito.spy(controller);
		
		Mockito.when(utils.getLong()).thenReturn(1L);
		Mockito.doReturn(oItems).when(spy).orderItems();
		Mockito.when(oIDAO.delete(1L, 1L)).thenReturn(0);
		
		assertEquals(0, spy.removeItem());
		
		Mockito.verify(utils, Mockito.times(1)).getLong();
		Mockito.verify(oIDAO, Mockito.times(1)).delete(1L, 1L);
	}
	
	@Test
	public void testSuccessfulOrderCost() {
		final long id = 1L;
		List<Order> orders = new ArrayList<>();
		final Order order = new Order(id);
		orders.add(order);
		
		Mockito.when(utils.getYN()).thenReturn("y");
		Mockito.when(utils.getLong()).thenReturn(id);
		Mockito.when(oDAO.read(id)).thenReturn(order);
		Mockito.when(oDAO.totalCost(id)).thenReturn(order);
		
		controller.orderCost();
		
		Mockito.verify(utils, Mockito.times(1)).getYN();
		Mockito.verify(utils, Mockito.times(1)).getLong();
		Mockito.verify(oDAO, Mockito.times(1)).read(id);
	}
	
	@Test
	public void testFailedOrderCost() {
		final long id = 1L;
		List<Order> orders = new ArrayList<>();
		final Order order = new Order(id);
		orders.add(order);
		
		Mockito.when(utils.getYN()).thenReturn("y");
		Mockito.when(utils.getLong()).thenReturn(id);
		Mockito.when(oDAO.read(id)).thenReturn(order);
		Mockito.when(oDAO.totalCost(id)).thenReturn(null);
		
		controller.orderCost();
		
		Mockito.verify(utils, Mockito.times(1)).getYN();
		Mockito.verify(utils, Mockito.times(1)).getLong();
		Mockito.verify(oDAO, Mockito.times(1)).read(id);
	}
}
