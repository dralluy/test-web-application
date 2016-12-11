package com.test.webapp.controller.rest;

import java.io.IOException;

import org.junit.Assert;
import org.junit.Test;

import com.test.webapp.AbstractIntegrationTest;
import com.test.webapp.core.HttpMethod;
import com.test.webapp.core.HttpStatus;

public class DeleteUserControllerITTest extends AbstractIntegrationTest {

	@Test
	public void shouldReturnUser1Test() throws IOException {
		
		//Given
		//User1 has got ADMIN role.
		initConnection("http://localhost:8000/api/users/user/user1", HttpMethod.DELETE, "user1:password1", true);
		
		
		//When
		extractDataFromRequest();
		
		//Then
		Assert.assertEquals(HttpStatus.OK.getCode(), httpConnection.getResponseCode());
	}
	
	@Test
	public void onlyAdminRoleCanDeleteUsersTest() throws IOException {
		
		//Given
		//User2 hasn´t got ADMIN role.
		initConnection("http://localhost:8000/api/users/user/user1", HttpMethod.DELETE, "user2:password2", true);
		
		
		//When
		extractDataFromRequest();
		
		//Then
		//Assert.assertTrue("User deleted".equals(data));
		Assert.assertEquals(HttpStatus.FORBIDDEN.getCode(), httpConnection.getResponseCode());
	}
}
