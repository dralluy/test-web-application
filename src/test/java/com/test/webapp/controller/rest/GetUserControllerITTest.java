package com.test.webapp.controller.rest;

import java.io.IOException;

import org.junit.Assert;
import org.junit.Test;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.test.webapp.AbstractIntegrationTest;
import com.test.webapp.core.HttpMethod;
import com.test.webapp.dao.impl.UserDAO;
import com.test.webapp.model.User;
import com.test.webapp.service.IUserService;
import com.test.webapp.service.impl.UserService;

public class GetUserControllerITTest extends AbstractIntegrationTest {

	private IUserService userService = new UserService(new UserDAO());
	
	@Test
	public void shouldReturnUser1Test() throws IOException {
		
		//Given
		User expectedUser = userService.getUser("user1");
		initConnection("http://localhost:8000/api/users/user/user1", HttpMethod.GET, "user1:password1", true);
		
		//When
		String data = extractDataFromRequest();
		
		//Then
		ObjectMapper mapper = new ObjectMapper();
		User user = mapper.readValue(data, User.class);
		Assert.assertEquals(expectedUser, user);
	}
}
