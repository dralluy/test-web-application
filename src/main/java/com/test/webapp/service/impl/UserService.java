package com.test.webapp.service.impl;

import java.util.List;

import com.test.webapp.dao.IUserDAO;
import com.test.webapp.model.User;
import com.test.webapp.service.IUserService;

/**
 * User service implementation.
 * @author david
 *
 */
public class UserService implements IUserService {

	private IUserDAO userDAO;

	public UserService(IUserDAO userDAO) {
		this.userDAO = userDAO;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.sbol.webapp.service.IUserService#getUsers()
	 */
	@Override
	public List<User> getUsers() {
		return userDAO.getUsers();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.sbol.webapp.service.IUserService#createUser(java.lang.String,
	 * java.lang.String)
	 */
	@Override
	public User createUser(String username, String password, String roles) {
		return userDAO.createUser(username, password, roles);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.sbol.webapp.service.IUserService#deleteUser(java.lang.String)
	 */
	@Override
	public void deleteUser(String username) {
		userDAO.deleteUser(username);
	}

	@Override
	public User getUser(String username) {
		return userDAO.getUser(username);
	}


	@Override
	public User updateUser(String username, String password, String roles) {
		return userDAO.updateUser(username, password, roles);
	}
}
