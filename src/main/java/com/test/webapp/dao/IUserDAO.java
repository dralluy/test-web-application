package com.test.webapp.dao;

import java.util.List;

import com.test.webapp.model.User;

/**
 * User DAO.
 * @author david
 *
 */
public interface IUserDAO {

	List<User> getUsers();

	User getUser(String username);
	
	User createUser(String username, String password, String roles);
	
	void deleteUser(String username);
	
	User updateUser(String username, String password, String roles);
}