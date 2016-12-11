package com.test.webapp.service;

import java.util.List;

import com.test.webapp.model.User;

/**
 * User service contract.
 * 
 * @author david
 *
 */
public interface IUserService {

	User getUser(String username);

	List<User> getUsers();

	User createUser(String username, String password, String roles);

	void deleteUser(String username);
	
	User updateUser(String username, String password, String roles);

}