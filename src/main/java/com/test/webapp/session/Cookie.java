package com.test.webapp.session;

/**
 * @author david.ralluy
 *
 */
public final class Cookie {

	private final String name;
	private final String value;
	private String domain;
	private String path;
	private long creationTime;
	private boolean httpOnly;

	public Cookie(String name, String value) {
		this(name, value, System.currentTimeMillis());
	}

	public Cookie(String name, String value, long creationTime) {
		this(name, value, creationTime, true);
	}

	public Cookie(String name, String value, long creationTime, boolean httpOnly) {
		this(name, value, creationTime, httpOnly, "/");
	}

	public Cookie(String name, String value, long creationTime, boolean httpOnly, String path) {
		this.name = name;
		this.value = value;
		this.creationTime = creationTime;
		this.httpOnly = httpOnly;
		this.path = path;
	}

	public String getName() {
		return name;
	}

	public String getValue() {
		return value;
	}

	public String getDomain() {
		return domain;
	}

	public String getPath() {
		return path;
	}

	public boolean isHttpOnly() {
		return httpOnly;
	}

	public long getCreationTime() {
		return creationTime;
	}

}
