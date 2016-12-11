package com.test.webapp.authentication;

import java.io.IOException;

import org.junit.Assert;
import org.junit.Test;

import com.test.webapp.AbstractIntegrationTest;
import com.test.webapp.core.HttpMethod;
import com.test.webapp.core.HttpStatus;


public class UserAutheticationTest extends AbstractIntegrationTest {

	@Test
	public void userShouldBeAutheticatedTest() throws IOException {
		//Given
		initConnection("http://localhost:8000/api/users/user/user1", HttpMethod.GET, "user1:password1", true);
		
		//Then
		Assert.assertEquals(HttpStatus.OK.getCode(), httpConnection.getResponseCode());
	}
	
	@Test
	public void userShouldNotBeAutheticatedTest() throws IOException {
		//Given
		initConnection("http://localhost:8000/api/users/user/user1", HttpMethod.GET, "baduser:badpassword", true);
		
		//Then
		Assert.assertEquals(HttpStatus.UNAUTHORIZED.getCode(), httpConnection.getResponseCode());
	}
}
