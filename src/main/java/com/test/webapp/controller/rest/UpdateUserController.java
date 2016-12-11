package com.test.webapp.controller.rest;

import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.databind.ObjectMapper;
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
 * Rest controller for updating users.
 * @author david
 *
 */
@Roles(name = "ADMIN")
@MethodMapping(method = HttpMethod.PUT)
public class UpdateUserController implements ContextProcessor<String> {

	private static final Logger LOGGER = LoggerFactory.getLogger(CreateUserController.class);
	
	private IUserService userService = new UserService(new UserDAO());

	@Override
	public ResponseContext<String> processContext(RequestContext request) {
		String username = getUserFromURL(request);
		User user = getUserFromRequest(request);
		if (user != null) {
			userService.updateUser(username, user.getPassword(), user.getRoles());
			return new ResponseContext<>("User updated", HttpStatus.OK);
		}
		return new ResponseContext<>("Internal error", HttpStatus.INTERNAL_SERVER);
	}

	String getUserFromURL(RequestContext request) {
		String path = request.getUri().getPath();
		return path.substring(path.lastIndexOf('/') + 1);
	}

	User getUserFromRequest(RequestContext request) {
		User user = null;
		if (request.getData() != null && !request.getData().isEmpty()) {
			String userData = request.getData();
			ObjectMapper mapper = new ObjectMapper();
			try {
				user = mapper.readValue(userData, User.class);
			} catch (IOException e) {
				LOGGER.error("Error reading user data", e);
			}
		}
		return user;
	}

}
