package com.test.webapp.core.handler;

import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.sun.net.httpserver.HttpExchange;
import com.test.webapp.core.HttpStatus;
import com.test.webapp.core.IPageRenderer;
import com.test.webapp.core.processor.ResponseContext;
import com.test.webapp.server.HttpResponseFactory;
import com.test.webapp.session.SessionManager;
import com.test.webapp.view.HandlebarsPageRenderer;


/**
 * Handle requests that needs render html pages.
 * @author david.ralluy
 *
 */
@SuppressWarnings("restriction")
public class MvcHttpHandler extends AbstractHttpHandler {

	private static final Logger LOGGER = LoggerFactory.getLogger(MvcHttpHandler.class);

	private IPageRenderer<String> pageRenderer = new HandlebarsPageRenderer();

	@Override
	protected void doPostProcessing(ResponseContext<?> response, HttpExchange t) {

		//Remove cookie session if needed.
		SessionManager.getInstance().removeCookieSession(t);

		try {

			String pageContent = null;

			if (response.getStatus().equals(HttpStatus.OK)) {
				pageContent = pageRenderer.resolvePageContent(response.getPageName(),
						response.getData() != null ? response.getData().toString() : null);
			}

			// Add content negotiation
			t.getResponseHeaders().add("Content-Type", "text/html");
			HttpResponseFactory.buildResponse(t, response, pageContent);

		} catch (IOException e) {
			LOGGER.error("Error rendering page {}", response.getPageName(), e);
		}
	}

}
