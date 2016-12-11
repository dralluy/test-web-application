package com.test.webapp.core.processor;

import com.test.webapp.core.HttpStatus;

/**
 * Wraps a web response in the processor context.
 * The data response can be of any type.
 * @author david.ralluy
 *
 */
public final class ResponseContext<T> {

	private T data;

	private HttpStatus status;

	private String pageName;

	private boolean redirect;

	private String sessionId;

	public ResponseContext(T data, HttpStatus status) {
		this(data, status, null);
	}

	public ResponseContext(T data, HttpStatus status, String pageName) {
		this.data = data;
		this.status = status;
		this.pageName = pageName;
		this.redirect = false;
	}

	public ResponseContext(T data, HttpStatus status, String pageName, boolean redirect) {
		this.data = data;
		this.status = status;
		this.pageName = pageName;
		this.redirect = redirect;
	}

	public T getData() {
		return data;
	}

	public HttpStatus getStatus() {
		return status;
	}

	public String getPageName() {
		return pageName;
	}

	public boolean isRedirect() {
		return this.redirect;
	}

	public String getSessionId() {
		return sessionId;
	}

	public void setSessionId(String sessionId) {
		this.sessionId = sessionId;
	}

	@Override
	public String toString() {
		return "ResponseContext [data=" + data + ", status=" + status + ", sessionId=" + sessionId + "]";
	}

}
