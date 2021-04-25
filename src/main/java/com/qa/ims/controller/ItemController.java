package com.qa.ims.controller;

import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import com.qa.ims.persistence.dao.ItemDAO;
import com.qa.ims.persistence.domain.Item;
import com.qa.ims.utils.UI;
import com.qa.ims.utils.Utils;

/**
 * Takes in Item details for CRUD functionality
 */
public class ItemController implements CrudController<Item> {

	public static final Logger LOGGER = LogManager.getLogger();

	private final ItemDAO itemDAO;
	private final Utils utils;
	private final UI ui;

	public ItemController(ItemDAO ItemDAO, Utils utils, UI ui) {
//		super();
		this.itemDAO = ItemDAO;
		this.utils = utils;
		this.ui = ui;
	}

	/**
	 * Reads all items to the logger.
	 * @return A list of items.
	 */
	@Override
	public List<Item> readAll() {
		List<Item> items = itemDAO.readAll();
		ui.fmtHeader("                   Items                    |");
		ui.displayDTOs(items);
		return items;
	}
	
	/**
	 * Creates a Item by taking in user input.
	 * @return An Item object representing the item created.
	 */
	@Override
	public Item create() {
		ui.fmtOutput("         Please enter an item name          |");
		String itemName = utils.getString();
		ui.fmtOutput("           Please enter a price             |");
		double price = utils.getDouble();
		Item item = itemDAO.create(new Item(itemName, price));
		if (item != null)
			ui.fmtOutput("         Item successfully created          |");
		return item;
	}

	/**
	 * Updates an existing Item by taking in user input.
	 * @return An Item object representing the item updated.
	 */
	@Override
	public Item update() {
		ui.fmtOutput("        Display existing items?  Y/N        |");
		if (utils.getYN().equals("y")) readAll();
		ui.fmtOutput("     Please enter an item ID to update      |");
		Long id = utils.getLong();
		if (itemDAO.read(id) != null) {
			ui.fmtOutput("         Please enter an item name          |");
			String itemName = utils.getString();
			ui.fmtOutput("           Please enter a price             |");
			double price = utils.getDouble();
			Item item = itemDAO.update(new Item(id, itemName, price));
			if (item != null) {
				ui.fmtOutput("         Item successfully updated          |");
				ui.displayDTO(item);
			}
			return item;
		}
		return null;
	}

	/**
	 * Deletes an existing item by the id of the item.
	 * @return The number of rows affected by the delete. (Should be 1 or 0).
	 */
	@Override
	public int delete() {
		ui.fmtOutput("        Display existing items?  Y/N        |");
		if (utils.getYN().equals("y")) readAll();
		ui.fmtOutput("     Please enter an item ID to delete      |");
		Long id = utils.getLong();
		int result = itemDAO.delete(id);
		if (result != 0)
			ui.fmtOutput("         Item successfully deleted          |");
		return result;
	}

	/**
	 * Reads an item by id.
	 * @param id of the item. 
	 * @return An Item object representing the item found.
	 */
	public Item read(long id) {
		return itemDAO.read(id);
	}
}
