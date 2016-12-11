package com.test.webapp.controller.rest;

import java.util.List;

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
 * Rest controller for getting list users.
 * @author david
 *
 */
@MethodMapping(method = HttpMethod.GET)
public class GetUserListController implements ContextProcessor<List<User>> {

	private IUserService userService = new UserService(new UserDAO());

	@Override
	public ResponseContext<List<User>> processContext(RequestContext request) {

		List<User> users = userService.getUsers();
		return new ResponseContext<>(users, HttpStatus.OK);

	}
}
