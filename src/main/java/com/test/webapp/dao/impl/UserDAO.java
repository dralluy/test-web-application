package com.test.webapp.dao.impl;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.test.webapp.dao.IUserDAO;
import com.test.webapp.database.H2Database;
import com.test.webapp.model.User;

/**
 * User DAO implementation.
 * 
 * @author david
 *
 */
public class UserDAO implements IUserDAO {

	private static final Logger LOGGER = LoggerFactory.getLogger(UserDAO.class);
	/*
	 * (non-Javadoc)
	 * 
	 * @see com.sbol.webapp.dao.IUserDAO#getUsers()
	 */
	@Override
	public List<User> getUsers() {
		List<User> users = new ArrayList<>();

		String usersQuery = "select * from users";

		try (Connection connection = H2Database.getDBConnection();
				Statement searchUsers = connection.createStatement();
				ResultSet set = searchUsers.executeQuery(usersQuery)) {

			while (set.next()) {
				User user = new User(set.getString(1), set.getString(2));
				user.setRoles(set.getString(3));
				users.add(user);
			}
			set.close();
			return users;
		} catch (SQLException e) {
			LOGGER.error("Database error ", e);
		} 
		return users;

	}

	@Override
	public User getUser(String username) {
		String usersQuery = "select * from users u where username='" + username + "'";
		try (Connection connection = H2Database.getDBConnection();
				Statement searchUser = connection.createStatement();
				ResultSet set = searchUser.executeQuery(usersQuery)) {

			while (set.next()) {
				User user = new User(set.getString(1), set.getString(2));
				user.setRoles(set.getString(3));
				return user;
			}
		} catch (SQLException e) {
			LOGGER.error("Database error ", e);
		}
		return null;
	}

	@Override
	public User createUser(String username, String password, String roles) {
		User user = new User(username, password);
		user.setRoles(roles);
		return user;
	}

	@Override
	public void deleteUser(String username) {
		// TODO Auto-generated method stub

	}

	@Override
	public User updateUser(String username, String password, String roles) {
		User user = getUser(username);
		user.setRoles(roles);
		return user;
	}
}
