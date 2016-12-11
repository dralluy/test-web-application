package com.test.webapp.session;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

/**
 * Session for an user. It has a default session timeout and holds a list of
 * releated cookies.
 * 
 * @author david
 *
 */
public final class Session {

	public static final int SESSION_TIMEOUT = 5;

	private String id;

	private long createdTime;

	private long lastAccesedTime;

	private boolean expired;

	private String username;

	private int timeout = SESSION_TIMEOUT;

	private List<Cookie> cookies = new ArrayList<>();

	public Session() {
		this.id = UUID.randomUUID().toString();
		this.createdTime = System.currentTimeMillis();
		this.setLastAccesedTime(createdTime);
		this.setExpired(false);
	}

	public long getCreatedTime() {
		return createdTime;
	}

	public void setLastAccesedTime(long lastAccesedTime) {
		this.lastAccesedTime = lastAccesedTime;
	}

	public long getLastAccesedTime() {
		return lastAccesedTime;
	}

	public boolean isExpired() {
		return expired;
	}

	public void setExpired(boolean expired) {
		this.expired = expired;
		this.cookies = new ArrayList<>();
	}

	public String getId() {
		return id;
	}

	public int getTimeout() {
		return timeout;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public void addCookie(Cookie cookie) {
		this.cookies.add(cookie);
	}

	public Cookie getCookie(String name) {
		Cookie result = null;
		Optional<Cookie> cookie = this.cookies.stream().filter(c -> c.getName().equals(name)).findFirst();
		if (cookie.isPresent()) {
			result = cookie.get();
		}
		return result;
	}

	public void removeCookie(String name) {
		Cookie cookie = getCookie(name);
		if (cookie != null) {
			cookies.remove(cookie);
		}
	}
}
