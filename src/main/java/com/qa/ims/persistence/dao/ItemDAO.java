package com.qa.ims.persistence.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import com.qa.ims.exceptions.ItemNotFoundException;
import com.qa.ims.persistence.domain.Item;
import com.qa.ims.utils.UI;

public class ItemDAO implements Dao<Item> {

	public static final Logger LOGGER = LogManager.getLogger();
	private final Connection conn;
	private final UI ui;
	
	public ItemDAO(UI ui, Connection conn) {
		this.conn = conn;
		this.ui = ui;
	}

	@Override
	public Item modelFromResultSet(ResultSet resultSet) throws SQLException {
		Long id = resultSet.getLong("id");
		String itemName = resultSet.getString("item_name");
		double price = resultSet.getDouble("price");
		return new Item(id, itemName, price);
	}

	/**
	 * Reads all Items from the database
	 * 
	 * @return A list of Items
	 */
	@Override
	public List<Item> readAll() {
		try (Statement statement = conn.createStatement();
				ResultSet resultSet = statement.executeQuery("SELECT * FROM items ORDER BY id")) {
			List<Item> Items = new ArrayList<>();
			while (resultSet.next()) {
				Items.add(modelFromResultSet(resultSet));
			}
			return Items;
		} catch (SQLException e) {
			LOGGER.debug(e);
			LOGGER.error(ui.formatError(e.getMessage()));
		}
		return new ArrayList<>();
	}

	public Item readLatest() {
		try (Statement statement = conn.createStatement();
				ResultSet resultSet = statement.executeQuery("SELECT * FROM items ORDER BY id DESC LIMIT 1")) {
			resultSet.next();
			return modelFromResultSet(resultSet);
		} catch (Exception e) {
			LOGGER.debug(e);
			LOGGER.error(ui.formatError(e.getMessage()));
		}
		return null;
	}

	/**
	 * Creates a Item in the database
	 * 
	 * @param Item - takes in a Item object. id will be ignored
	 */
	@Override
	public Item create(Item Item) {
		try (PreparedStatement statement = conn
						.prepareStatement("INSERT INTO items(item_name, price) VALUES (?, ?)")) {
			statement.setString(1, Item.getItemName());
			statement.setDouble(2, Item.getPrice());
			statement.executeUpdate();
			return readLatest();
		} catch (Exception e) {
			LOGGER.debug(e);
			LOGGER.error(ui.formatError(e.getMessage()));
		}
		return null;
	}

	@Override
	public Item read(Long id) {
		try (PreparedStatement statement = conn.prepareStatement("SELECT * FROM items WHERE id = ?")) {
			statement.setLong(1, id);
			try (ResultSet resultSet = statement.executeQuery()) {
				if (!resultSet.isBeforeFirst())   
					throw new ItemNotFoundException(id);
				resultSet.next();
				return modelFromResultSet(resultSet);
			}
		} catch (SQLException e) {
			LOGGER.debug(e);
			LOGGER.error(ui.formatError(e.getMessage()));
		} catch (ItemNotFoundException infe) {
			LOGGER.debug(infe);
			LOGGER.error(ui.formatError("    "+infe.getMessage()+"      |"));
		}
		return null;
	}

	/**
	 * Updates a Item in the database
	 * 
	 * @param Item - takes in a Item object, the id field will be used to
	 *                 update that Item in the database
	 * @return
	 */
	@Override
	public Item update(Item Item) {
		try (PreparedStatement statement = conn
						.prepareStatement("UPDATE items SET item_name = ?, price = ? WHERE id = ?")) {
			statement.setString(1, Item.getItemName());
			statement.setDouble(2, Item.getPrice());
			statement.setLong(3, Item.getId());
			statement.executeUpdate();
			return read(Item.getId());
		} catch (Exception e) {
			LOGGER.debug(e);
			LOGGER.error(ui.formatError(e.getMessage()));
		}
		return null;
	}

	/**
	 * Deletes a Item in the database
	 * 
	 * @param id - id of the Item
	 */
	@Override
	public int delete(long id) {
		try (PreparedStatement statement = conn.prepareStatement("DELETE FROM items WHERE id = ?")) {
			statement.setLong(1, id);
			int result = statement.executeUpdate();
			if (result == 0)
				throw new ItemNotFoundException(id);
			return result;
		} catch (SQLException e) {
			LOGGER.debug(e);
			LOGGER.error(ui.formatError(e.getMessage()));
		} catch (ItemNotFoundException infe) {
			LOGGER.debug(infe);
			LOGGER.error(ui.formatError("    "+infe.getMessage()+"      |"));
		}
		return 0;
	}

}
