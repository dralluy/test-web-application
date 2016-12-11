package com.test.webapp.core.processor;

import java.net.URI;

/**
 * Wraps a web request for context processing. 
 * @author david
 *
 */
public final class RequestContext {

	private URI uri;

	private String username;

	private String data;

	RequestContext(URI uri, String data, String username) {
		this.uri = uri;
		this.data = data;
		this.username = username;
	}

	public URI getUri() {
		return uri;
	}

	public String getUser() {
		return username;
	}

	public String getData() {
		return data;
	}

	/**
	 * Request builder.
	 * 
	 * @author david
	 *
	 */
	public static class RequestContextBuilder {
		private final URI uri;

		private String username;

		private String data;

		public RequestContextBuilder(URI uri) {
			this.uri = uri;
		}

		public RequestContextBuilder username(String username) {
			this.username = username;
			return this;
		}

		public RequestContextBuilder data(String data) {
			this.data = data;
			return this;
		}

		public RequestContext build() {
			return new RequestContext(uri, data, username);
		}
	}

	@Override
	public String toString() {
		return "RequestContext [uri=" + uri + ", username=" + username + ", data=" + data + "]";
	}

}
