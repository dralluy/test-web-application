package com.test.webapp.server.filters;

import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.sun.net.httpserver.Filter;
import com.sun.net.httpserver.HttpExchange;
import com.test.webapp.security.AuthenticationManager;
import com.test.webapp.server.HttpRequestHelper;
import com.test.webapp.server.HttpResponseFactory;
import com.test.webapp.session.SessionManager;

import rx.Observable;

/**
 * Checks if session is needed in mvc handler.
 
 * @author david
 *
 */
@SuppressWarnings("restriction")
public class SessionFilter extends Filter {

	private static final Logger LOGGER = LoggerFactory.getLogger(SessionFilter.class);
	private static final String FILTER_DESC = "Session filter management";

	@Override
	public String description() {
		return FILTER_DESC;
	}

	@Override
	public void doFilter(HttpExchange httpExchange, Chain filterChain) throws IOException {

		try {
			if (SessionManager.getInstance().checkSessionNeeded(httpExchange)
					&& ((AuthenticationManager) httpExchange.getHttpContext().getAuthenticator())
							.extractAuthorizationHeader(httpExchange) == null) {
				HttpResponseFactory.buildAhutorizationResponse(httpExchange, null);
			} else {

				// Update session idle time
				Observable
						.just(HttpRequestHelper.extractCookieValue(httpExchange,
								SessionManager.SESSION_COOKIE))
						.subscribe(id -> SessionManager.getInstance().updateIdleTime(id));

				filterChain.doFilter(httpExchange);
			}
		} catch (Exception e) {
			LOGGER.error("Unexpected error", e);
		}
	}
}
