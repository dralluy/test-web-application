package com.test.webapp.security;

import com.test.webapp.core.processor.ContextProcessor;

/**
 * Authorization element. Checks if an user is authorized to execute a
 * controller.
 * 
 * @author david
 *
 */
public interface IAuthorization {

	boolean isUserAuthorized(String user, ContextProcessor<?> controller);

}