package com.test.webapp.core.handler;

import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sun.net.httpserver.HttpExchange;
import com.test.webapp.core.processor.ResponseContext;
import com.test.webapp.server.HttpResponseFactory;

/**
 * Handle request JSON related. The rest API uses this handler.
 * @author david.ralluy
 *
 */
@SuppressWarnings("restriction")
public class RestHttpHandler extends AbstractHttpHandler {

	private static final Logger LOGGER = LoggerFactory.getLogger(RestHttpHandler.class);

	@Override
	protected void doPostProcessing(ResponseContext<?> response, HttpExchange t) {
		ObjectMapper mapper = new ObjectMapper();
		String responseData;
		try {
			responseData = mapper.writeValueAsString(response.getData());
			// Add content negotiation
			t.getResponseHeaders().add("Content-Type", "application/json; charset=UTF-8");
			HttpResponseFactory.buildResponse(t, response, responseData);
		} catch (JsonProcessingException e) {
			LOGGER.error("Error json parsing ", e);
		} catch (IOException e) {
			LOGGER.error("Error IO ", e);
		}

	}

}
