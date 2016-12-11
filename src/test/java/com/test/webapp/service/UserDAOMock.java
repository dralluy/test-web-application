package com.test.webapp.service;

import java.util.Arrays;
import java.util.List;

import com.test.webapp.dao.IUserDAO;
import com.test.webapp.model.User;

public class UserDAOMock implements IUserDAO {

	@Override
	public List<User> getUsers() {
		User user1 = new User("user1", "password1");
		
		User user2 = new User("user2", "password2");
		
		return Arrays.asList(user1, user2);
	}

	@Override
	public User getUser(String username) {
		return new User("user1", "password1");
	}

	@Override
	public User createUser(String username, String password, String roles) {
		User user =  new User(username, password);
		user.setRoles(roles);
		return user;
	}

	@Override
	public void deleteUser(String username) {
		// TODO 
		
	}

	@Override
	public User updateUser(String username, String password, String roles) {
		User user =  new User(username, password);
		user.setRoles(roles);
		return user;
	}

}
