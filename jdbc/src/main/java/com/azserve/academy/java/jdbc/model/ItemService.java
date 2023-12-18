package com.azserve.academy.java.jdbc.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ItemService {

	private final Connection connection;

	public ItemService(final Connection connection) {
		this.connection = connection;
	}

	/**
	 * Insert new item and return the id
	 *
	 * @param code
	 * @param description
	 * @return
	 * @throws SQLException
	 */
	public int addItem(final String code, final String description) throws SQLException {
		try (PreparedStatement statement = this.connection.prepareStatement("insert into item (code, description) values (?,?) returning id")) {
			statement.setString(1, code);
			statement.setString(2, description);
			try (ResultSet rs = statement.executeQuery()) {
				rs.next();
				return rs.getInt(1);
			}
		}
	}

	/**
	 * Find the item with the given id, null if missing
	 *
	 * @param id
	 * @return
	 * @throws SQLException
	 */
	public Item getItem(final int id) throws SQLException {
		try (PreparedStatement statement = this.connection.prepareStatement("select id, code, description from item where id = ?")) {
			statement.setInt(1, id);
			try (ResultSet rs = statement.executeQuery()) {
				if (rs.next()) {
					return new Item(rs.getInt("id"), rs.getString("code"), rs.getString("description"));
				}
				return null;
			}
		}
	}

	/**
	 * Find the item with the given code, null if missing
	 *
	 * @param code
	 * @return
	 * @throws SQLException
	 */
	public Item getItem(final String code) throws SQLException {
		try (PreparedStatement statement = this.connection.prepareStatement("select id, code, description from item where code = ?")) {
			statement.setString(1, code);
			try (ResultSet rs = statement.executeQuery()) {
				if (rs.next()) {
					return new Item(rs.getInt("id"), rs.getString("code"), rs.getString("description"));
				}
				return null;
			}
		}
	}

	/**
	 * Search all items with the given description
	 *
	 * @param description
	 * @return
	 * @throws SQLException
	 */
	public List<Item> searchItem(final String description) throws SQLException {
		try (PreparedStatement statement = this.connection.prepareStatement("select id, code, description from item where description ilike ?")) {
			statement.setString(1, description);
			final List<Item> result = new ArrayList<>();
			try (ResultSet rs = statement.executeQuery()) {
				while (rs.next()) {
					result.add(new Item(rs.getInt("id"), rs.getString("code"), rs.getString("description")));
				}
			}
			return result;
		}
	}

}
