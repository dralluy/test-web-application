package com.test.webapp.core.handler;

import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import com.test.webapp.core.HttpStatus;
import com.test.webapp.core.processor.ContextProcessor;
import com.test.webapp.core.processor.MappingsProcessorResolver;
import com.test.webapp.core.processor.RequestContext;
import com.test.webapp.core.processor.ResponseContext;
import com.test.webapp.security.AuthorizationManager;
import com.test.webapp.security.IAuthorization;
import com.test.webapp.server.HttpRequestHelper;
import com.test.webapp.server.HttpResponseFactory;

/**
 * Main application handler. Works as a generic dispatcher with the following steps:
 * - Extract request data
 * - Extract username
 * - Resolve controller from mappings
 * - Checks user authorization
 * - Execute controller
 * - Do post processing (render pages, write responses, etc.). It depends on specific handlers.
 * 
 * @author david.ralluy
 *
 */
@SuppressWarnings("restriction")
public abstract class AbstractHttpHandler implements HttpHandler {

	private static final Logger LOGGER = LoggerFactory.getLogger(AbstractHttpHandler.class);
	
	private IAuthorization authorizationManager = AuthorizationManager.getInstance();

	@Override
	public void handle(HttpExchange t) throws IOException {

		try {
			doProcessing(t);
		} catch (Exception e) {
			HttpResponseFactory.buildErrorResponse(t, HttpStatus.INTERNAL_SERVER, "Internal servel error");
			LOGGER.error("Internal server error", e);
		}
	}

	protected void doProcessing(HttpExchange httpExchange) throws IOException {

		// Extract data from request
		String requestData = HttpRequestHelper.extractRequestData(httpExchange);
		String username = httpExchange.getPrincipal().getUsername();

		// Resolve mappings to find controller
		ContextProcessor<?> controller = MappingsProcessorResolver.getInstance()
				.createControllerFromMapping(httpExchange.getRequestURI().getPath(), httpExchange.getRequestMethod());

		// Check authorization
		if (authorizationManager.isUserAuthorized(username, controller)) {
			// Create request context
			RequestContext request = new RequestContext.RequestContextBuilder(httpExchange.getRequestURI())
					.data(requestData).username(username).build();

			// Execute controller
			ResponseContext<?> response = controller.processContext(request);

			// Post processing
			doPostProcessing(response, httpExchange);
			
			LOGGER.info("Executing request {} with response {} ", request, response);
		} else {
			LOGGER.error("Resource access unauthorized ");
			HttpResponseFactory.buildErrorResponse(httpExchange, HttpStatus.FORBIDDEN, "Resource access denied.");
		}
	}

	/**
	 * Execute actions post controllers.
	 * 
	 * @param response
	 * @param t
	 */
	protected abstract void doPostProcessing(ResponseContext<?> response, HttpExchange t);
}
