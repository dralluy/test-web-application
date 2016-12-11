package com.test.webapp.service;

import java.util.Arrays;
import java.util.List;

import com.test.webapp.model.User;

public class UserServiceMock implements IUserService {

	@Override
	public User getUser(String username) {
		return new User(username, "password");
	}

	@Override
	public List<User> getUsers() {
		User user1 = new User("user1", "password1");
		
		User user2 = new User("user2", "password2");
		
		return Arrays.asList(user1, user2);
	}

	@Override
	public User createUser(String username, String password, String roles) {
		User user1 = new User(username, password);
		user1.setRoles(roles);
		return user1 ;
	}

	@Override
	public void deleteUser(String username) {
		
	}

	@Override
	public User updateUser(String username, String password, String roles) {
		User user1 = new User(username, password);
		user1.setRoles(roles);
		return user1 ;
	}

}
