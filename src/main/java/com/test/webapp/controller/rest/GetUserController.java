package com.test.webapp.controller.rest;

import com.test.webapp.core.HttpMethod;
import com.test.webapp.core.HttpStatus;
import com.test.webapp.core.MethodMapping;
import com.test.webapp.core.processor.ContextProcessor;
import com.test.webapp.core.processor.RequestContext;
import com.test.webapp.core.processor.ResponseContext;
import com.test.webapp.dao.impl.UserDAO;
import com.test.webapp.model.User;
import com.test.webapp.service.IUserService;
import com.test.webapp.service.impl.UserService;

/**
 * Rest controller for getting users.
 * 
 * @author david
 *
 */
@MethodMapping(method = HttpMethod.GET)
public class GetUserController implements ContextProcessor<User> {

	private IUserService userService = new UserService(new UserDAO());

	@Override
	public ResponseContext<User> processContext(RequestContext request) {
		String usercode = getUserFromURL(request);

		User user = userService.getUser(usercode);
		if (user != null) {
			return new ResponseContext<>(user, HttpStatus.OK);
		}
		return new ResponseContext<>(new User(), HttpStatus.NOT_FOUND);
	}

	String getUserFromURL(RequestContext request) {
		String path = request.getUri().getPath();
		return path.substring(path.lastIndexOf('/') + 1);
	}
}
