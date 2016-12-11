package com.test.webapp.server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.sun.net.httpserver.Headers;
import com.sun.net.httpserver.HttpExchange;
import com.test.webapp.session.Session;
import com.test.webapp.session.SessionManager;

/**
 * Helper for extracting data from request.
 * 
 * @author david.ralluy
 *
 */
@SuppressWarnings("restriction")
public final class HttpRequestHelper {

	private static final Logger LOGGER = LoggerFactory.getLogger(HttpRequestHelper.class);
	
	public static final String COOKIE = "Cookie";
	public static final String SET_COOKIE = "Set-Cookie";
	
	private HttpRequestHelper() {
	}

	public static String extractUserFromSession(HttpExchange httpExchange) {
		String username = null;
		Optional<String> cookieSession = extractCookieValue(httpExchange,
				SessionManager.SESSION_COOKIE);
		if (cookieSession.isPresent()) {
			Optional<Session> session = SessionManager.getInstance().getSession(cookieSession.get());
			if (session.isPresent()) {
				username = session.get().getUsername();
			}
		}
		return username;
	}

	public static String extractRequestData(HttpExchange httpExchange) {
		String requestData = "";
		// Look for request body data
		if (httpExchange.getRequestBody() != null) {
			try (InputStreamReader isr = new InputStreamReader(httpExchange.getRequestBody(), "utf-8");
					BufferedReader br = new BufferedReader(isr)) {
				requestData = br.readLine();
				String tmpRequestData = "";
				while ((tmpRequestData = br.readLine()) != null && (requestData.length() > 0)) {
					requestData += tmpRequestData;
				}
			} catch (IOException e) {
				LOGGER.error("Error extracting data ", e);
			}
		}
		return requestData;
	}
	
	public static Optional<String> extractCookieValue(HttpExchange httpExchange, String cookieName) {
		Headers headers = httpExchange.getRequestHeaders();
		for (Map.Entry<String, List<String>> header : headers.entrySet()) {
			if (header.getKey().equals(COOKIE)) {
				for (String cookieValue : header.getValue()) {
					if (cookieValue.contains(cookieName)) {
						String[] cookieValues = cookieValue.split(";");
						for (int i = 0; i < cookieValues.length; i++) {
							if (cookieValues[i].contains(cookieName)) {
								String[] cookieValueSplit = cookieValues[i].split("=");
								return Optional.of(cookieValueSplit[1]);
							}
						}
					}
				}
			}
		}
		return Optional.empty();
	}
	
	public static void putCookieSession(HttpExchange httpExchange, String cookieSession) {
		Headers headers = httpExchange.getResponseHeaders();
		headers.add(SET_COOKIE, SessionManager.SESSION_COOKIE + "=" + cookieSession + "; path=/; httponly;");
	}
}