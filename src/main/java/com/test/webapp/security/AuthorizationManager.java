package com.test.webapp.security;

import com.test.webapp.core.processor.ContextProcessor;
import com.test.webapp.dao.impl.UserDAO;
import com.test.webapp.service.IUserService;
import com.test.webapp.service.impl.UserService;

/**
 * Implementation of authorization, checking user roles validity for executing
 * controllers. Authorization is defined through {@see Roles} annotation.
 * @author david
 *
 */
public class AuthorizationManager implements IAuthorization {

	private IUserService userService = new UserService(new UserDAO());

	private AuthorizationManager() {
	}

	public static IAuthorization getInstance() {
		return AuthorizationManagerHolder.instance;
	}

	private static class AuthorizationManagerHolder {
		public static final IAuthorization instance = new AuthorizationManager();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.sbol.webapp.security.IAuthorization#isUserAUthorized(java.lang.
	 * String, com.sbol.webapp.core.ContextProcessor)
	 */
	@Override
	public boolean isUserAuthorized(String user, ContextProcessor<?> controller) {
		Roles annotation = controller.getClass().getAnnotation(Roles.class);
		boolean tmpResult = false;
		if (annotation != null) {
			String roles = userService.getUser(user).getRoles();
			if (!roles.isEmpty()) {
				String[] rolenames = roles.split(",");
				for (String rolename : rolenames) {
					if (rolename.equals(annotation.name())) {
						tmpResult = true;
						break;
					}
				}
			}
		}
		if (annotation == null) {
			tmpResult = true;
		}
		return tmpResult;
	}
}
