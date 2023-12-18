package com.azserve.academy.java.jdbc.ddl;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

public class Tables {

	private final Connection connection;

	public Tables(final Connection connection) {
		this.connection = connection;
	}

	public void create() throws SQLException {
		try (Statement statement = this.connection.createStatement()) {
			statement.executeUpdate("create table item (id SERIAL primary key not null, code varchar(10) not null, description varchar(255) not null)");
			statement.executeUpdate("create table movement (id SERIAL primary key not null, item_id int not null, amount int not null, date timestamp)");
			statement.executeUpdate("alter table movement add CONSTRAINT FK_MOVEMENT_ITEM FOREIGN KEY (item_id) REFERENCES item (id)");
			statement.executeUpdate("alter table item add CONSTRAINT UX_ITEM_CODE UNIQUE (code)");
		}
	}
}
