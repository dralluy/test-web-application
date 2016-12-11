package com.test.webapp.core;

/**
 * Http status codes.
 * @author david
 *
 */
public enum HttpStatus {

	OK(200), CREATED(201), REDIRECT(302), UNAUTHORIZED(401), FORBIDDEN(403), NOT_FOUND(404), INTERNAL_SERVER(500);

	int code;

	HttpStatus(int code) {
		this.code = code;
	}

	public int getCode() {
		return this.code;
	}
}
