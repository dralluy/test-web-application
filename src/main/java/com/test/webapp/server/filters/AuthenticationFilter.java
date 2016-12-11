package com.test.webapp.server.filters;

import java.io.IOException;

import com.sun.net.httpserver.Filter;
import com.sun.net.httpserver.HttpExchange;
import com.test.webapp.security.AuthenticationManager;
import com.test.webapp.server.HttpResponseFactory;

/**
 * Authentication filter used for api rest handler.
 * @author david
 *
 */
@SuppressWarnings("restriction")
public class AuthenticationFilter extends Filter {

	private static final String FILTER_DESC = "Authentication filter management";

	@Override
	public String description() {
		return FILTER_DESC;
	}

	/**
	 * If authorization is needed, redirect to request authentication.
	 */
	@Override
	public void doFilter(HttpExchange httpExchange, Chain filterChain) throws IOException {

		if (((AuthenticationManager) httpExchange.getHttpContext().getAuthenticator())
				.extractAuthorizationHeader(httpExchange) == null) {
			HttpResponseFactory.buildAhutorizationResponse(httpExchange, null);
		} else {
			filterChain.doFilter(httpExchange);
		}
	}
}
