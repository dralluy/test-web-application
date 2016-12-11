package com.test.webapp.session;

/**
 * Throw this exception when the session is not found.
 * 
 * @author david
 *
 */
public class SessionNotFoundException extends RuntimeException {

	/**
	 * Serial id.
	 */
	private static final long serialVersionUID = 1L;

	public SessionNotFoundException(String id) {
		super(" Session not found. ID: " + id);
	}
}
