package com.azserve.academy.java.jdbc;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.List;
import java.util.Random;
import java.util.Scanner;

import com.azserve.academy.java.jdbc.ddl.Tables;
import com.azserve.academy.java.jdbc.model.Item;
import com.azserve.academy.java.jdbc.model.ItemService;

public class Database {

	/*
	 * postgres=# create role test with login password 'test';
	 * CREATE ROLE
	 * postgres=# create database test with owner test;
	 * CREATE DATABASE
	 *
	 */

	public static void main(final String[] args) throws SQLException {
		final String host = "localhost";
		final String database = "test";
		final String username = "test";
		final String password = "test";

		try (Connection connection = DriverManager.getConnection("jdbc:postgresql://" + host + "/" + database, username, password);
				Scanner scanner = new Scanner(System.in)) {
			int command;
			while ((command = readCommand(scanner)) != 0) {
				switch (command) {
					case 1:
						createTable(connection);
						break;
					case 2:
						insertRandomItem(connection, 10);
						break;
					case 3:
						findItem(connection, scanner);
						break;
					case 4:
						searchAllItems(connection);
						break;
					case 5:
						searchItems(connection, scanner);
						break;
					default:
						System.out.println("Invalid command: " + command);
				}
			}
			System.out.println("Bye bye");

		}
	}

	private static int readCommand(final Scanner scanner) {

		System.out.println("""
				1 - create table
				2 - fill with random items
				3 - find item with id
				4 - search all items
				5 - search items by description
				0 - quit
				""");

		return scanner.nextInt();
	}

	private static void createTable(final Connection connection) throws SQLException {
		new Tables(connection).create();
	}

	private static void insertRandomItem(final Connection connection, final int count) throws SQLException {
		final Random r = new Random();
		final ItemService itemService = new ItemService(connection);
		for (int i = 0; i < count; i++) {
			final String code = Utils.padLeft(Math.abs(r.nextInt()) + "", 10, '0');
			itemService.addItem(code, "item with code " + code);
		}
	}

	private static void findItem(final Connection connection, final Scanner scanner) throws SQLException {
		System.out.println("id:");
		final int id = scanner.nextInt();
		final ItemService itemService = new ItemService(connection);
		final Item item = itemService.getItem(id);
		if (item == null) {
			System.out.println("Item with id " + id + " not found");
		} else {
			System.out.println(item);
		}
	}

	private static void searchAllItems(final Connection connection) throws SQLException {
		final ItemService itemService = new ItemService(connection);
		final List<Item> item = itemService.searchItem("%");
		item.stream().forEach(System.out::println);
	}

	private static void searchItems(final Connection connection, final Scanner scanner) throws SQLException {
		System.out.println("description:");
		final String description = scanner.next();
		final ItemService itemService = new ItemService(connection);
		final List<Item> item = itemService.searchItem(description);
		item.stream().forEach(System.out::println);
	}
}
