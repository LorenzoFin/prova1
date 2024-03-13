package com.azserve.academy.java.jdbc;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Random;
import java.util.Scanner;
import java.util.TimeZone;

import com.azserve.academy.java.jdbc.ddl.Tables;
import com.azserve.academy.java.jdbc.model.Item;
import com.azserve.academy.java.jdbc.model.ItemMovement;
import com.azserve.academy.java.jdbc.model.ItemService;
import com.azserve.academy.java.jdbc.model.Movement;
import com.azserve.academy.java.jdbc.model.MovementService;

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

					case 10:
						searchAllMovement(connection);
						break;
					case 11:
						es1(connection);
						break;
					case 12:
						es2(connection, scanner);
						break;
					case 13:
						es3(connection, scanner);
						break;
					case 14:
						es4(connection);
						break;
					case 15:
						es5(connection, scanner);
					case 16:
						es6(connection, scanner);
					case 17:
						es7(connection, scanner);
						break;
					case 18:
						es8(connection);
						break;
					default:
						System.out.println("Invalid command: " + command);
				}
			}
			System.out.println("Bye bye");

		}
	}

	private static void es8(final Connection connection) throws SQLException {
		final Scanner scanner = new Scanner(System.in);
		final ItemService itemService = new ItemService(connection);
		final MovementService addMovement = new MovementService(connection);

		final int item_id = readIntIDItem(scanner, connection);

		final int result = addMovement.inventoryID(item_id);

		final int amount = readIntAmount(scanner);

		if (amount + result < 0) {
			System.out.println("the movement leads to negative inventory");
		}
		System.out.println("Are you sure? Yes[0] / No[1]  ");
		final int risp = readInt(scanner);
		if (risp == 0) {
			final Timestamp date = readData(scanner);
			addMovement.addMovement(item_id, amount, date);

		} else {
			System.out.println("process interrupted ");
		}
	}

	private static Timestamp readData(final Scanner scanner) {
		String s;
		final Date d = null;
		Timestamp ts = null;
		SimpleDateFormat dateFormat = new SimpleDateFormat();
		System.out.println("Inserisci la data [yyyy-MM-dd HH:mm:ss]: ");
		final Scanner in = new Scanner(System.in);
		s = in.nextLine();

		try {
			dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			final Date date = dateFormat.parse(s);
			final long time = date.getTime();
			ts = new Timestamp(time);

		} catch (final ParseException e) {
			System.out.println("Formato data non valido.");
		}

		return ts;
	}

	private static int readList(final Scanner scanner) {
		return scanner.nextInt();
	}

	private static int readInt(final Scanner scanner) {
		return scanner.nextInt();
	}

	private static int readIntIDItem(final Scanner scanner, final Connection connection) throws SQLException {
		System.out.println("insert id_item ");
		int result = 0;
		final ItemService itemService = new ItemService(connection);
		final List<Integer> id = itemService.searchAllItemId();
		final boolean exist = false;
		result = scanner.nextInt();
		while (!id.contains(new Integer(result))) {
			System.out.println("wrong  id_item. Try again ");
			result = scanner.nextInt();
		}
		return result;

	}

	private static int readIntAmount(final Scanner scanner) {
		System.out.println("insert amount ");
		return scanner.nextInt();
	}

	private static void es7(final Connection connection, final Scanner scanner) throws SQLException {
		final Date date = new Date();
		final Calendar c = Calendar.getInstance(TimeZone.getTimeZone("Europe/Rome"), Locale.ITALY);
		c.setTime(date);
		c.add(Calendar.MONTH, -1);
		final Timestamp MounthLess = new Timestamp(c.getTimeInMillis());
		final long time = date.getTime();
		final Timestamp MounthNow = new Timestamp(time);

		final MovementService movementService = new MovementService(connection);
		final List<ItemMovement> item = movementService.inventoryMovementLastMounth(MounthLess, MounthNow);
		item.stream().forEach(System.out::println);
		System.out.println("\n");

	}

	private static void es6(final Connection connection, final Scanner scanner) throws SQLException {
		final Date date = new Date();
		// DateFormat formatoData = DateFormat.getDateInstance(DateFormat.SHORT, Locale.ITALY);
		final Calendar c = Calendar.getInstance(TimeZone.getTimeZone("Europe/Rome"), Locale.ITALY);
		c.setTime(date);
		c.add(Calendar.MONTH, -1);
		final Timestamp MounthLess = new Timestamp(c.getTimeInMillis());
		final long time = date.getTime();
		final Timestamp MounthNow = new Timestamp(time);

		final MovementService movementService = new MovementService(connection);
		final List<Item> item = movementService.inventoryItemLastMounth(MounthLess, MounthNow);
		item.stream().forEach(System.out::println);
		System.out.println("\n");
	}

	private static void es5(final Connection connection, final Scanner scanner) throws SQLException {
		final MovementService movementService = new MovementService(connection);
		final List<Item> item = movementService.inventoryLess10();
		item.stream().forEach(System.out::println);
		System.out.println("\n");

	}

	private static void es4(final Connection connection) throws SQLException {
		final MovementService movementService = new MovementService(connection);
		final List<Item> item = movementService.itemsWhitoutMovements();
		item.stream().forEach(System.out::println);
		System.out.println("\n");
	}

	private static void es3(final Connection connection, final Scanner scanner) throws SQLException {

		final int id_item = readIntIDItem(scanner, connection);

		final Timestamp date = readData(scanner);
		final MovementService movementService = new MovementService(connection);
		final int result = movementService.inventoryIDDate(id_item, date);
		System.out.println("Total inventory to the item with ID:[" + id_item + "] until [" + date + "] is:" + result + "/n");

	}

	private static void es2(final Connection connection, final Scanner scanner) throws SQLException {
		final int code = readIntIDItem(scanner, connection);
		final MovementService addMovement = new MovementService(connection);
		final int result = addMovement.inventoryID(code);
		System.out.println("Total inventory to the item with ID:[" + code + "] is:" + result + "/n");
	}

	private static void es1(final Connection connection) throws SQLException {
		final Scanner scanner = new Scanner(System.in);
		final ItemService itemService = new ItemService(connection);
		final int item_id = readIntIDItem(scanner, connection);
		final int amount = readIntAmount(scanner);
		final Timestamp date = readData(scanner);
		final MovementService addMovement = new MovementService(connection);
		addMovement.addMovement(item_id, amount, date);
	}

	private static void searchAllMovement(final Connection connection) throws SQLException {
		final MovementService movementService = new MovementService(connection);
		final List<Movement> item = movementService.searchAllMovements();
		item.stream().forEach(System.out::println);
	}

	private static int readCommand(final Scanner scanner) {
		System.out.println("""
				0 - quit
				1 - create table
				2 - fill with random items
				3 - find item with id
				4 - search all items
				5 - search items by description
				6 - search all movements
				-----------------------------------
				11 - q1
				12 - q2
				13 - q3
				14 - q4
				15 - q5
				16 - q6
				17 - q7
				18 - q1 EXTRA
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
