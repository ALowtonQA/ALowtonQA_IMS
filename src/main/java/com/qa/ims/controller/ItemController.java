package com.qa.ims.controller;

import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.qa.ims.persistence.dao.ItemDAO;
import com.qa.ims.persistence.domain.Item;
import com.qa.ims.utils.Utils;

/**
 * Takes in Item details for CRUD functionality
 *
 */
public class ItemController implements CrudController<Item> {

	public static final Logger LOGGER = LogManager.getLogger();

	private ItemDAO itemDAO;
	private Utils utils;

	public ItemController(ItemDAO ItemDAO, Utils utils) {
//		super();
		this.itemDAO = ItemDAO;
		this.utils = utils;
	}

	/**
	 * Reads all Items to the logger
	 */
	@Override
	public List<Item> readAll() {
		List<Item> Items = itemDAO.readAll();
		for (Item item : Items) {
			LOGGER.info(item);
		}
		return Items;
	}

	/**
	 * Creates a Item by taking in user input
	 */
	@Override
	public Item create() {
		LOGGER.info("Please enter an item name");
		String ItemName = utils.getString();
		LOGGER.info("Please enter a price");
		double price = utils.getDouble();
		Item Item = itemDAO.create(new Item(ItemName, price)); // Do something with return?
		LOGGER.info("Item created"); // DO MORE HERE!
		return Item;
	}

	/**
	 * Updates an existing Item by taking in user input
	 */
	@Override
	public Item update() {
		LOGGER.info("Please enter the id of the item you would like to update");
		Long id = utils.getLong();
		LOGGER.info("Please enter an item name");
		String ItemName = utils.getString();
		LOGGER.info("Please enter a price");
		double price = utils.getDouble();
		Item Item = itemDAO.update(new Item(id, ItemName, price)); // Do something with the return?
		LOGGER.info("Item Updated");
		return Item;
	}

	/**
	 * Deletes an existing Item by the id of the Item
	 * 
	 * @return
	 */
	@Override
	public int delete() {
		LOGGER.info("Please enter the id of the item you would like to delete");
		Long id = utils.getLong();
		return itemDAO.delete(id);
	}

}
