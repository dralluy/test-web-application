package com.test.webapp.service;

import java.util.List;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import com.test.webapp.model.User;
import com.test.webapp.service.impl.UserService;

@RunWith(JUnit4.class)
public class UserServiceTest {

	private IUserService userServie = new UserService(new UserDAOMock());
	
	@Test
	public void usernameShouldBeUser1Test() {
		//Given
		String username = "user1";
		
		//When
		User user = userServie.getUser(username);
		
		//Then
		Assert.assertEquals(username, user.getUsername());
	}
	
	@Test
	public void shouldBeTwoUsersTest() {
		//Given
		int usersNumber = 2;
		
		//When
		List<User> users = userServie.getUsers();
		
		//Then
		Assert.assertTrue(users.size() == usersNumber);
	}
	
}
