package com.test.webapp;

import java.sql.SQLException;
import java.util.Arrays;
import java.util.concurrent.TimeUnit;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.test.webapp.core.handler.MvcHttpHandler;
import com.test.webapp.core.handler.RestHttpHandler;
import com.test.webapp.database.H2Database;
import com.test.webapp.security.AuthenticationManager;
import com.test.webapp.server.WebHttpServer;
import com.test.webapp.server.filters.AuthenticationFilter;
import com.test.webapp.server.filters.ResourceFilter;
import com.test.webapp.server.filters.SessionFilter;
import com.test.webapp.session.SessionManager;

import rx.Observable;

/**
 * Application inicialization. Creates the http server, H2 and all the
 * configuration needed.
 * There is one handler for API Rest controller and another for mvc controllers.
 * 
 * @author david
 *
 */
public final class WebAppStart {
	private static final Logger LOGGER = LoggerFactory.getLogger(WebAppStart.class);
	
	private static final String MAIN_CONTEXT = "/app";
	private static final String REST_CONTEXT = "/api/users";

	private static final int PORT = 8000;

	private static WebHttpServer simpleHttpServer = null;

	private WebAppStart() {}
	
	public static void initInfraestructure() {
		// Create a new SimpleHttpServer
		simpleHttpServer = new WebHttpServer(PORT);

		ResourceFilter mappingFilter = new ResourceFilter();

		AuthenticationManager authManager = new AuthenticationManager();

		simpleHttpServer.addHttpContext(REST_CONTEXT, new RestHttpHandler(),
				Arrays.asList(mappingFilter, new AuthenticationFilter()), authManager);
		simpleHttpServer.addHttpContext(MAIN_CONTEXT, new MvcHttpHandler(),
				Arrays.asList(mappingFilter, new SessionFilter()), authManager);
		// Start the server
		simpleHttpServer.start();
		LOGGER.info("Server is started and listening on port {}" , PORT);

		// Init session timeout controller
		Observable.interval(0, 30, TimeUnit.SECONDS).map(tick -> System.currentTimeMillis())
				.subscribe(time -> SessionManager.getInstance().checkSessionsTimeout(time));

		// Database
		try {
			H2Database.initDatabase();
		} catch (SQLException e) {
			LOGGER.error("Database init error ", e);
		}
	}

	public static void stopInfraestructure() throws SQLException {
		simpleHttpServer.stop();
		H2Database.stopDatabase();
	}

	public static void main(String[] args) throws Exception {
		initInfraestructure();
	}
}
