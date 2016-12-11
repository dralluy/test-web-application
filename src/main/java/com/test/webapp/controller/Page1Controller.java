package com.test.webapp.controller;

import com.test.webapp.core.HttpStatus;
import com.test.webapp.core.processor.ContextProcessor;
import com.test.webapp.core.processor.RequestContext;
import com.test.webapp.core.processor.ResponseContext;
import com.test.webapp.security.Roles;
import com.test.webapp.session.SessionManager;

@Roles(name = "PAGE_1")
public class Page1Controller implements ContextProcessor<String> {

	@Override
	public ResponseContext<String> processContext(RequestContext request) {
		if (request.getData() != null && request.getData().contains("Close")) {
			SessionManager.getInstance().closeSession(request.getUser());
		} else {
			return new ResponseContext<>("{\"username\":\"" + request.getUser() + "\"}", HttpStatus.OK, "page1");
		}
		return new ResponseContext<>("{\"username\":\"" + request.getUser() + "\"}", HttpStatus.UNAUTHORIZED, null);
	}

}
