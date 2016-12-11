package com.test.webapp.controller.rest;

import java.io.IOException;

import org.junit.Assert;
import org.junit.Test;

import com.test.webapp.AbstractIntegrationTest;
import com.test.webapp.core.HttpMethod;
import com.test.webapp.core.HttpStatus;

public class UpdateUserControllerITTest extends AbstractIntegrationTest {
	
	@Test
	public void shouldCreateUser10Test() throws IOException {
		
		//Given
		initConnection("http://localhost:8000/api/users/user/user1", HttpMethod.PUT, "user1:password1", true);
		fillDataRequest();
		
		//When
		extractDataFromRequest();
		
		//Then
		Assert.assertEquals(HttpStatus.OK.getCode(), httpConnection.getResponseCode());
	}
	
	@Test
	public void onlyAdminRoleCanUpdateUsersTest() throws IOException {
		
		//Given
		//User2 hasn´t got ADMIN role.
		initConnection("http://localhost:8000/api/users/user/user1", HttpMethod.PUT, "user2:password2", true);
		fillDataRequest();		
		
		//When
		extractDataFromRequest();
		
		//Then
		//Assert.assertTrue("User deleted".equals(data));
		Assert.assertEquals(HttpStatus.FORBIDDEN.getCode(), httpConnection.getResponseCode());
	}
	
	private void fillDataRequest() {
		String userdata = "{\"username\":\"user10\", \"password\":\"password10\",\"roles\":\"PAGE_1, PAGE_2\" }";
		putDataForRequest(userdata);
	}
}
