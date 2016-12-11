package com.test.webapp.session;

import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.sun.net.httpserver.Headers;
import com.sun.net.httpserver.HttpExchange;
import com.test.webapp.server.HttpRequestHelper;

/**
 * Session Manager. Keeps a session list for users authenticated. The current
 * session gets update with every request, and expired when the idle time
 * finishes.
 * 
 * @author david
 *
 */
@SuppressWarnings("restriction")
public final class SessionManager {

	public static final String SESSION_COOKIE = "JSESSIONID";

	public static final Logger LOGGER = LoggerFactory.getLogger(SessionManager.class);

	/**
	 * Sessions map.
	 */
	private Map<String, Session> sessions = new ConcurrentHashMap<>();

	private SessionManager() {
	}

	public static SessionManager getInstance() {
		return SessionManagerHolder.instance;
	}

	private static class SessionManagerHolder {
		public static final SessionManager instance = new SessionManager();
	}

	public synchronized String createSession(String username) {
		Session newSession = new Session();
		newSession.setUsername(username);
		sessions.put(newSession.getId(), newSession);
		Cookie cookie = new Cookie(SESSION_COOKIE, newSession.getId());
		newSession.addCookie(cookie);
		LOGGER.info("Session created: " + newSession.getId());
		return newSession.getId();
	}
	
	public void closeSession(String username) {
		Optional<Session> session = SessionManager.getInstance().getSessionUser(username);
		if (session.isPresent()) {
			session.get().setExpired(true);
		}
	}

	public boolean isSessionExpired(String id) throws SessionNotFoundException {
		Session session = sessions.get(id);
		if (session == null) {
			throw new SessionNotFoundException(id);
		}
		return session.isExpired();
	}

	public boolean isUserInSession(String username) {
		Optional<Session> session = sessions.values().stream().filter(s -> s.getUsername().equals(username))
				.findFirst();
		if (session.isPresent() && !session.get().isExpired()) {
			return true;
		}
		return false;
	}

	public Optional<Session> getSession(String sessionId) {
		return Optional.ofNullable(sessions.get(sessionId));
	}

	public Optional<Session> getSessionUser(String userName) {
		return sessions.values().stream().filter(s -> s.getUsername().equals(userName)).findFirst();
	}

	public boolean checkSessionNeeded(HttpExchange httpExchange) {
		boolean tmpResult = true;
		// Get JSESSIONID from cookie
		Optional<String> sessionCookie = HttpRequestHelper.extractCookieValue(httpExchange, SESSION_COOKIE);
		if (sessionCookie.isPresent()) {
			tmpResult = false;
			try {
				if (isSessionExpired(sessionCookie.get())) {
					tmpResult = true;
				}
			} catch (SessionNotFoundException snfe) {
				LOGGER.error("Session not found: " + snfe);
				tmpResult = true;
			}
		}
		return tmpResult;
	}

	public void removeCookieSession(HttpExchange httpExchange) {
		Optional<String> value = HttpRequestHelper.extractCookieValue(httpExchange, SESSION_COOKIE);
		if (value.isPresent() && isSessionExpired(value.get())) {
			Headers headers = httpExchange.getResponseHeaders();
			headers.add("Set-Cookie",
					SessionManager.SESSION_COOKIE + "=-; path=/; httponly; expires=Thu, 01 Jan 1970 00:00:00 GMT");
		}
	}

	public void updateCookieValue(HttpExchange httpExchange, String cookieName) {
		Optional<String> cookie = HttpRequestHelper.extractCookieValue(httpExchange, cookieName);
		if (cookie.isPresent()) {
			Optional<String> sessionCookie = HttpRequestHelper.extractCookieValue(httpExchange, SESSION_COOKIE);
			if (sessionCookie.isPresent()) {
				Session session = sessions.get(sessionCookie.get());
				if (session != null && !session.isExpired()) {
					if (session.getCookie(cookieName) != null) {
						session.removeCookie(cookieName);
					}
					Cookie newCookie = new Cookie(cookieName, cookie.get());
					session.addCookie(newCookie);
				}

			}
		}
	}

	/**
	 * Gets called from an observable every 30 seconds and expires the sessions
	 * with idle time longer that the session timeout.
	 * 
	 * @param currenttime
	 */
	public void checkSessionsTimeout(Long currenttime) {
		for (Map.Entry<String, Session> sessionEntry : sessions.entrySet()) {
			Session session = sessionEntry.getValue();
			if (!session.isExpired()
					&& currenttime - session.getLastAccesedTime() > 1000 * 60 * Session.SESSION_TIMEOUT) {
				session.setExpired(true);
				LOGGER.info("Session expired: " + session.getId());
			}
		}
	}

	/**
	 * Update last accesed time when there is a request for a session.
	 * 
	 * @param id
	 */
	public void updateIdleTime(Optional<String> id) {
		if (id.isPresent()) {
			Optional<Session> session = getSession(id.get());
			if (session.isPresent() && !session.get().isExpired()) {
				session.get().setLastAccesedTime(System.currentTimeMillis());
				LOGGER.info("Session updated: " + session.get().getId());
			}
		}
	}

}
