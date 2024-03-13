package com.azserve.academy.java.jdbc.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

public class MovementService {

	private final Connection connection;

	public MovementService(final Connection connection) {
		this.connection = connection;
	}

	public int addMovement(final int item_id, final int amount, final Timestamp date) throws SQLException {
		try (PreparedStatement statement = this.connection.prepareStatement("insert into movement (  item_id,   amount,   date) values (?,?,?) returning id")) {
			statement.setInt(1, item_id);
			statement.setInt(2, amount);
			statement.setTimestamp(3, date);
			try (ResultSet rs = statement.executeQuery()) {
				rs.next();
				return rs.getInt(1);
			}
		}
	}

	public int inventoryID(final int id_item) throws SQLException {
		try (PreparedStatement statement = this.connection.prepareStatement("SELECT Sum(amount) AS somma FROM movement WHERE (movement.item_id) =?   ")) {
			statement.setInt(1, id_item);
			int result = 0;
			try (ResultSet rs = statement.executeQuery()) {
				while (rs.next()) {
					result = rs.getInt("somma");
				}
			}
			return result;
		}
	}

	public int inventoryIDDate(final int id_item, final Timestamp date) throws SQLException {
		try (PreparedStatement statement = this.connection.prepareStatement("SELECT Sum(amount) AS somma FROM movement WHERE (movement.item_id =?  and movement.date<=?)")) {
			statement.setInt(1, id_item);
			statement.setTimestamp(2, date);
			int result = 0;
			try (ResultSet rs = statement.executeQuery()) {
				while (rs.next()) {
					result = rs.getInt("somma");
				}
			}
			return result;
		}
	}

	public List<Movement> searchAllMovements() throws SQLException {
		try (PreparedStatement statement = this.connection.prepareStatement("select * from movement")) {
			final List<Movement> result = new ArrayList<>();
			try (ResultSet rs = statement.executeQuery()) {
				while (rs.next()) {
					result.add(new Movement(rs.getInt("id"), rs.getInt("item_id"), rs.getInt("amount"), rs.getTimestamp("date")));
				}
			}
			return result;
		}
	}

	public List<Item> itemsWhitoutMovements() throws SQLException {
		try (PreparedStatement statement = this.connection.prepareStatement("SELECT item.ID,code, description FROM item LEFT JOIN movement ON (item.id = item_id) WHERE date is null order by item.id")) {
			final List<Item> result = new ArrayList<>();
			try (ResultSet rs = statement.executeQuery()) {
				while (rs.next()) {
					result.add(new Item(rs.getInt("id"), rs.getString("code"), rs.getString("description")));
				}
			}
			return result;
		}
	}

	public int giacenzaID(final int id_item) throws SQLException {
		try (PreparedStatement statement = this.connection.prepareStatement("SELECT Sum(amount) AS somma FROM movement WHERE (movement.item_id) =?   ")) {
			statement.setInt(1, id_item);
			int result = 0;
			try (ResultSet rs = statement.executeQuery()) {
				while (rs.next()) {
					result = rs.findColumn("somma");
				}
			}
			return result;
		}
	}

	public List<Item> inventoryLess10() throws SQLException {
		try (PreparedStatement statement = this.connection.prepareStatement("SELECT item.code, item.id, item.description FROM item LEFT JOIN movement ON item.id = movement.item_id GROUP BY item.code, item.id, item.description HAVING sum(amount)<=10;")) {
			final List<Item> result = new ArrayList<>();
			try (ResultSet rs = statement.executeQuery()) {
				while (rs.next()) {
					result.add(new Item(rs.getInt("id"), rs.getString("code"), rs.getString("description")));
				}
			}
			return result;
		}
	}

	public List<Item> inventoryItemLastMounth(final Timestamp MounthNow, final Timestamp MounthLess) throws SQLException {
		try (PreparedStatement statement = this.connection.prepareStatement("SELECT item.id,code,description FROM item INNER JOIN movement ON item.id =  item_id WHERE date BETWEEN ? and ?")) {
			statement.setTimestamp(1, MounthNow);
			statement.setTimestamp(2, MounthLess);
			final List<Item> result = new ArrayList<>();
			try (ResultSet rs = statement.executeQuery()) {
				while (rs.next()) {
					result.add(new Item(rs.getInt("id"), rs.getString("code"), rs.getString("description")));
				}
			}
			return result;
		}

	}

	public List<ItemMovement> inventoryMovementLastMounth(final Timestamp MounthNow, final Timestamp MounthLess) throws SQLException {
		try (PreparedStatement statement = this.connection.prepareStatement("SELECT item.id,description,date, amount  FROM item INNER JOIN movement ON item.id =  item_id WHERE date BETWEEN ? and ? GROUP BY item.id,description,date, amount")) {
			statement.setTimestamp(1, MounthNow);
			statement.setTimestamp(2, MounthLess);
			final List<ItemMovement> result = new ArrayList<>();
			try (ResultSet rs = statement.executeQuery()) {
				while (rs.next()) {
					result.add(new ItemMovement(rs.getInt("id"), rs.getString("description"), rs.getTimestamp("date"), rs.getInt("amount")));
				}
			}
			return result;
		}

	}
}
