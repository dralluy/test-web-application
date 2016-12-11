package com.test.webapp.server;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.sun.net.httpserver.Filter;
import com.sun.net.httpserver.HttpContext;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpServer;
import com.test.webapp.security.AuthenticationManager;

/**
 * Server application. Adds filters and authentication manager.
 * @author david
 *
 */
@SuppressWarnings("restriction")
public class WebHttpServer {

	private static final Logger LOGGER = LoggerFactory.getLogger(WebHttpServer.class);
	
	private HttpServer httpServer;

	/**
	 * Instantiates a new simple http server.
	 *
	 * @param port
	 *            the port
	 * @param context
	 *            the context
	 * @param handler
	 *            the handler
	 */
	public WebHttpServer(int port) {
		try {
			// Create HttpServer which is listening on the given port
			httpServer = HttpServer.create(new InetSocketAddress(port), 0);
			// Create a default executor
			httpServer.setExecutor(null);
		} catch (IOException e) {
			LOGGER.error("Error init server ", e);
		}

	}

	public void addHttpContext(String context, HttpHandler handler, List<Filter> filters,
			AuthenticationManager authManager) {
		// Create a new context for the given context and handler
		final HttpContext mainContext = httpServer.createContext(context, handler);
		mainContext.setAuthenticator(authManager);
		// Create the filters
		filters.stream().forEach(f -> mainContext.getFilters().add(f));
	}

	/**
	 * Start.
	 */
	public void start() {
		this.httpServer.start();
	}

	/**
	 * Stop server.
	 */
	public void stop() {
		this.httpServer.stop(0);
	}
}
