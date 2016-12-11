package com.test.webapp.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public final class H2Database {
	private static final Logger LOGGER = LoggerFactory.getLogger(H2Database.class);
	
	private static final String DB_DRIVER = "org.h2.Driver";
	private static final String DB_CONNECTION = "jdbc:h2:mem:test;DB_CLOSE_DELAY=-1";
	private static final String DB_USER = "";
	private static final String DB_ACCESS = "";

	private H2Database() {}
	
	public static Connection getDBConnection() {
		Connection dbConnection = null;
		try {
			Class.forName(DB_DRIVER);
		} catch (ClassNotFoundException e) {
			LOGGER.error("Database error ", e);
		}
		try {
			dbConnection = DriverManager.getConnection(DB_CONNECTION, DB_USER, DB_ACCESS);
			return dbConnection;
		} catch (SQLException e) {
			LOGGER.error("Database error ", e);
		}
		return dbConnection;
	}

	public static void stopDatabase() throws SQLException {
		try (Connection connection = getDBConnection(); Statement shutdownStatement = connection.createStatement()) {
			shutdownStatement.executeUpdate("SHUTDOWN");
		}
	}

	public static void initDatabase() throws SQLException {

		PreparedStatement createPreparedStatement = null;
		Statement insertPreparedStatement = null;

		String createQueryUser = "CREATE TABLE USERS(username varchar(255) primary key, password varchar(255), roles varchar(255))";

		try (Connection connection = getDBConnection()) {
			connection.setAutoCommit(false);

			createPreparedStatement = connection.prepareStatement(createQueryUser);
			createPreparedStatement.executeUpdate();
			createPreparedStatement.close();

			// USuarios
			insertPreparedStatement = connection.createStatement();
			insertPreparedStatement.execute(
					"INSERT INTO USERS(username, password, roles) VALUES('user1', 'password1', 'PAGE_1,ADMIN')");
			insertPreparedStatement
					.execute("INSERT INTO USERS(username, password, roles) VALUES('user2', 'password2', 'PAGE_2')");
			insertPreparedStatement
					.execute("INSERT INTO USERS(username, password, roles) VALUES('user3', 'password3', 'PAGE_3')");

			insertPreparedStatement.close();
			connection.commit();
		}

	}
}
