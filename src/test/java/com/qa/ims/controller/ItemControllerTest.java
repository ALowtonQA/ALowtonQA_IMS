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
import com.qa.ims.persistence.dao.ItemDAO;
import com.qa.ims.persistence.domain.Item;
import com.qa.ims.utils.UI;
import com.qa.ims.utils.Utils;

@RunWith(MockitoJUnitRunner.class)
public class ItemControllerTest {

	@Mock
	private UI ui;
	
	@Mock
	private Utils utils;

	@Mock
	private ItemDAO dao;

	@InjectMocks
	private ItemController controller;

	@Test
	public void testCreate() {	
		final String iName = "Titanic";
		final double price = 9.99;
		final Item created = new Item(iName, price);

		Mockito.when(utils.getString()).thenReturn(iName);
		Mockito.when(utils.getDouble()).thenReturn(price);
		Mockito.when(dao.create(created)).thenReturn(created);

		assertEquals(created, controller.create());

		Mockito.verify(utils, Mockito.times(1)).getString();
		Mockito.verify(utils, Mockito.times(1)).getDouble();
		Mockito.verify(dao, Mockito.times(1)).create(created);
	}

	@Test
	public void testReadAll() {
		List<Item> items = new ArrayList<>();
		items.add(new Item(1L, "Titanic", 9.99));

		Mockito.when(dao.readAll()).thenReturn(items);

		assertEquals(items, controller.readAll());

		Mockito.verify(dao, Mockito.times(1)).readAll();
	}

	@Test
	public void testUpdate() {
		Item updated = new Item(1L, "Titanic", 9.99);

		Mockito.when(utils.getYN()).thenReturn("y");
		Mockito.when(utils.getLong()).thenReturn(1L);
		Mockito.when(utils.getString()).thenReturn(updated.getItemName());
		Mockito.when(utils.getDouble()).thenReturn(updated.getPrice());
		Mockito.when(dao.read(1L)).thenReturn(updated);
		Mockito.when(dao.update(updated)).thenReturn(updated);

		assertEquals(updated, controller.update());

		Mockito.verify(utils, Mockito.times(1)).getYN();
		Mockito.verify(utils, Mockito.times(1)).getLong();
		Mockito.verify(utils, Mockito.times(1)).getString();
		Mockito.verify(utils, Mockito.times(1)).getDouble();
		Mockito.verify(dao, Mockito.times(1)).read(1L);
		Mockito.verify(dao, Mockito.times(1)).update(updated);
	}

	@Test
	public void testDelete() {
		final long id = 1L;

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
		Item read = new Item(id, "Titanic", 9.99);
		
		Mockito.when(dao.read(id)).thenReturn(read);
		
		assertEquals(read, controller.read(id));
		
		Mockito.verify(dao, Mockito.times(1)).read(id);
	}
}
