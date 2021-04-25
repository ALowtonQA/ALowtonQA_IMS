package com.qa.ims;

import static org.mockito.Mockito.when;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

import com.qa.ims.controller.Action;
import com.qa.ims.controller.CustomerController;
import com.qa.ims.controller.ItemController;
import com.qa.ims.controller.OrderAction;
import com.qa.ims.controller.OrderController;
import com.qa.ims.persistence.domain.Domain;
import com.qa.ims.utils.UI;
import com.qa.ims.utils.Utils;

@RunWith(MockitoJUnitRunner.class)
public class IMSTest {
	
	@Mock
	private CustomerController customers;
	
	@Mock
	private ItemController items;
	
	@Mock
	private OrderController orders;
	
	@Mock
	private UI ui;
	
	@Mock
	private Utils utils;
	
	@InjectMocks
	private IMS ims;
	
	@Test
	public void testIMSSystem() {
		when(ui.selectDomain()).thenReturn(Domain.valueOf("CUSTOMER"), Domain.valueOf("STOP"));
		when(ui.selectAction()).thenReturn(
				Action.valueOf("CREATE"), 
				Action.valueOf("READ"), 
				Action.valueOf("UPDATE"), 
				Action.valueOf("DELETE"), 
				Action.valueOf("RETURN"));
		when(customers.create()).thenReturn(null);
		when(customers.readAll()).thenReturn(null);
		when(customers.update()).thenReturn(null);
		when(customers.delete()).thenReturn(1);
		
		ims.imsSystem();
		
		Mockito.verify(ui, Mockito.times(1)).welcome();
		Mockito.verify(ui, Mockito.times(2)).selectDomain();
		Mockito.verify(ui, Mockito.times(5)).selectAction();
		Mockito.verify(customers, Mockito.times(1)).create();
		Mockito.verify(customers, Mockito.times(1)).readAll();
		Mockito.verify(customers, Mockito.times(1)).update();
		Mockito.verify(customers, Mockito.times(1)).delete();
	}

	@Test
	public void testIMSSystemOrder() {
		when(ui.selectDomain()).thenReturn(Domain.valueOf("ORDER"), Domain.valueOf("STOP"));
		when(ui.selectOrderAction()).thenReturn(
				OrderAction.valueOf("CREATE"), 
				OrderAction.valueOf("READ"), 
				OrderAction.valueOf("UPDATE"), 
				OrderAction.valueOf("DELETE"), 
				OrderAction.valueOf("ADD"), 
				OrderAction.valueOf("ITEMS"),
				OrderAction.valueOf("REMOVE"),
				OrderAction.valueOf("TOTAL"),
				OrderAction.valueOf("RETURN"));
		when(orders.create()).thenReturn(null);
		when(orders.readAll()).thenReturn(null);
		when(orders.update()).thenReturn(null);
		when(orders.delete()).thenReturn(1);
		when(orders.orderItems()).thenReturn(null);
		when(orders.removeItem()).thenReturn(1);
		
		ims.imsSystem();
		
		Mockito.verify(ui, Mockito.times(1)).welcome();
		Mockito.verify(ui, Mockito.times(2)).selectDomain();
		Mockito.verify(ui, Mockito.times(9)).selectOrderAction();	
		Mockito.verify(orders, Mockito.times(1)).create();
		Mockito.verify(orders, Mockito.times(1)).readAll();
		Mockito.verify(orders, Mockito.times(1)).update();
		Mockito.verify(orders, Mockito.times(1)).delete();
		Mockito.verify(orders, Mockito.times(1)).addItem();
		Mockito.verify(orders, Mockito.times(1)).orderItems();
		Mockito.verify(orders, Mockito.times(1)).removeItem();
		Mockito.verify(orders, Mockito.times(1)).orderCost();
	}
}
