package com.test.webapp.session;

import java.util.Optional;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import rx.Observable;

@RunWith(JUnit4.class)
public class SessionManagerTest {

	@Test
	public void shouldCreateNewSession() {
		// Given

		// When
		String sessionId = SessionManager.getInstance().createSession("user1");

		// Then
		Assert.assertNotNull(sessionId);
	}

	@Test
	public void shouldExpireNewSession() {
		// Given
		String sessionId = SessionManager.getInstance().createSession("user1");
		
		// When	
		Optional<Session> session = SessionManager.getInstance().getSession(sessionId);
		if (session.isPresent()) {
			//Add 6 minutes
			session.get().setLastAccesedTime(System.currentTimeMillis() - (1000 * 60 * 6));
		}
		
		//Expire session
		Observable.just(System.currentTimeMillis())
				.subscribe(time -> SessionManager.getInstance().checkSessionsTimeout(time));

		// Then
		Assert.assertTrue(SessionManager.getInstance().isSessionExpired(sessionId));
	}
}
