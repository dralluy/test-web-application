package com.test.webapp.controller.rest;

import com.test.webapp.core.HttpMethod;
import com.test.webapp.core.HttpStatus;
import com.test.webapp.core.MethodMapping;
import com.test.webapp.core.processor.ContextProcessor;
import com.test.webapp.core.processor.RequestContext;
import com.test.webapp.core.processor.ResponseContext;
import com.test.webapp.dao.impl.UserDAO;
import com.test.webapp.model.User;
import com.test.webapp.security.Roles;
import com.test.webapp.service.IUserService;
import com.test.webapp.service.impl.UserService;

/**
 * Rest controller for deleting users.
 * @author david
 *
 */
@Roles(name = "ADMIN")
@MethodMapping(method = HttpMethod.DELETE)
public class DeleteUserController implements ContextProcessor<String> {

	private IUserService userService = new UserService(new UserDAO());

	@Override
	public ResponseContext<String> processContext(RequestContext request) {
		String username = getUserFromURL(request);
		User user = userService.getUser(username);
		userService.deleteUser(user.getUsername());
		return new ResponseContext<>("User deleted", HttpStatus.OK);
	}

	String getUserFromURL(RequestContext request) {
		String path = request.getUri().getPath();
		return path.substring(path.lastIndexOf('/') + 1);
	}
}
