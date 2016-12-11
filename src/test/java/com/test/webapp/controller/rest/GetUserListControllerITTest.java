package com.test.webapp.controller.rest;

import java.io.IOException;
import java.util.List;

import org.junit.Assert;
import org.junit.Test;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.test.webapp.AbstractIntegrationTest;
import com.test.webapp.core.HttpMethod;
import com.test.webapp.model.User;

public class GetUserListControllerITTest extends AbstractIntegrationTest {

	
	
	@Test
	public void shouldReturnAllUsersTest() throws IOException {
		
		//Given
		initConnection("http://localhost:8000/api/users", HttpMethod.GET, "user1:password1", true);
		
		//When
		String data = extractDataFromRequest();
		
		//Then
		ObjectMapper mapper = new ObjectMapper();
		List<User> users = mapper.readValue(data, new TypeReference<List<User>>(){});
		Assert.assertEquals(3, users.size());
	}
}
