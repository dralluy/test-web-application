package com.test.webapp.controller.rest;

import java.io.IOException;

import org.junit.Assert;
import org.junit.Test;

import com.test.webapp.AbstractIntegrationTest;
import com.test.webapp.core.HttpMethod;
import com.test.webapp.core.HttpStatus;

public class CreateUserControllerITTest extends AbstractIntegrationTest {

	@Test
	public void shouldCreateUser10Test() throws IOException {
		
		//Given
		initConnection("http://localhost:8000/api/users", HttpMethod.POST, "user1:password1", true);
		fillDataRequest();
		
		//When
		extractDataFromRequest();
		
		//Then
		Assert.assertEquals(HttpStatus.CREATED.getCode(), httpConnection.getResponseCode());
	}
	
	@Test
	public void onlyAdminRoleCanCreateUsersTest() throws IOException {
		
		//Given
		//User2 hasn´t got ADMIN role.
		initConnection("http://localhost:8000/api/users", HttpMethod.POST, "user2:password2", true);
		fillDataRequest();		
		
		//When
		extractDataFromRequest();
		
		//Then
		//Assert.assertTrue("User deleted".equals(data));
		Assert.assertEquals(HttpStatus.FORBIDDEN.getCode(), httpConnection.getResponseCode());
	}
	

	private void fillDataRequest() {
		String userdata = "{\"username\":\"user10\", \"password\":\"password10\",\"roles\":\"PAGE_1\" }";
		putDataForRequest(userdata);
	}
}
