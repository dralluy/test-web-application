package com.test.webapp.server.filters;

import java.io.IOException;
import java.net.URI;

import com.sun.net.httpserver.Filter;
import com.sun.net.httpserver.HttpExchange;
import com.test.webapp.core.HttpStatus;
import com.test.webapp.core.ResourceMappings;
import com.test.webapp.server.HttpResponseFactory;

/**
 * Filter to allow access to defined mappings. 
 * @author david
 *
 */
@SuppressWarnings("restriction")
public class ResourceFilter extends Filter {

	private static final String FILTER_DESC = "Resource filter";

	@Override
	public String description() {
		return FILTER_DESC;
	}

	@Override
	public void doFilter(HttpExchange httpExchange, Chain filterChain) throws IOException {

		// Get request URI
		URI uri = httpExchange.getRequestURI();

		if (ResourceMappings.isMappingAllowed(uri)) {
			filterChain.doFilter(httpExchange);
		} else {
			// Mapping not found. Error.
			HttpResponseFactory.buildErrorResponse(httpExchange, HttpStatus.NOT_FOUND, "Error: Resource not found");
		}
	}

}
