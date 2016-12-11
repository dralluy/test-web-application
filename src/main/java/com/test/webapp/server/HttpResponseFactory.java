package com.test.webapp.server;

import java.io.IOException;
import java.io.OutputStream;

import com.sun.net.httpserver.Headers;
import com.sun.net.httpserver.HttpExchange;
import com.test.webapp.core.HttpStatus;
import com.test.webapp.core.processor.ResponseContext;

/**
 * Builds different kind of responses.
 * 
 * @author david.ralluy
 *
 */
@SuppressWarnings("restriction")
public final class HttpResponseFactory {

	public static final String SET_COOKIE = "Set-Cookie";

	private HttpResponseFactory() {}
	
	/**
	 * @param httpExchange
	 * @param response
	 * @param pageContent
	 * @throws IOException
	 */
	public static void buildResponse(HttpExchange httpExchange, ResponseContext<?> response, String pageContent)
			throws IOException {
		switch (response.getStatus()) {
		case OK:
		case CREATED:
			httpExchange.sendResponseHeaders(response.getStatus().getCode(), pageContent.getBytes().length);
			writeResponse(httpExchange, pageContent);
			break;
		case REDIRECT:
			buildRedirectResponse(httpExchange, response);
			break;
		case UNAUTHORIZED:
			buildAhutorizationResponse(httpExchange, response);
			break;
		case FORBIDDEN:
		case NOT_FOUND:
		case INTERNAL_SERVER:
			buildErrorResponse(httpExchange, response.getStatus(), response.getData().toString());
			break;
		default:
			break;
		}

	}

	/**
	 * @param httpExchange
	 * @param status
	 * @param error
	 * @throws IOException
	 */
	public static void buildErrorResponse(HttpExchange httpExchange, HttpStatus status, String error)
			throws IOException {
		httpExchange.sendResponseHeaders(status.getCode(), error.getBytes().length);
		writeResponse(httpExchange, error);
	}
	
	/**
	 * @param httpExchange
	 * @param response
	 * @throws IOException
	 */
	public static void buildRedirectResponse(HttpExchange httpExchange, ResponseContext<?> response)
			throws IOException {

		// Login redirect
		String redirect = "redirecting to " + response.getPageName();
		// Set the response header status and length
		Headers headers = httpExchange.getResponseHeaders();
		headers.add("Location", "/app/" + response.getPageName());
		httpExchange.sendResponseHeaders(HttpStatus.REDIRECT.getCode(), redirect.getBytes().length);
		writeResponse(httpExchange, redirect);
	}

	public static void buildAhutorizationResponse(HttpExchange httpExchange, ResponseContext<?> response)
			throws IOException {
		// Set the response header status and length
		Headers headers = httpExchange.getResponseHeaders();
		headers.add("WWW-Authenticate", "Basic realm=\"webapp\"");
		headers.add(SET_COOKIE, "JSESSIONID=-; path=/; httponly; expires=Thu, 01 Jan 1970 00:00:00 GMT");
		httpExchange.sendResponseHeaders(HttpStatus.UNAUTHORIZED.getCode(), 0);
		// Write the response string
		writeResponse(httpExchange, "");
	}

	private static void writeResponse(HttpExchange httpExchange, String response) throws IOException {
		try (OutputStream os = httpExchange.getResponseBody()) {
			os.write(response.getBytes());
		}
	}
}
