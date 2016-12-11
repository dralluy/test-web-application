package com.test.webapp.security;

import java.io.UnsupportedEncodingException;
import java.util.Base64;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.sun.net.httpserver.Authenticator;
import com.sun.net.httpserver.Headers;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpPrincipal;
import com.test.webapp.core.HttpStatus;
import com.test.webapp.dao.impl.UserDAO;
import com.test.webapp.model.User;
import com.test.webapp.server.HttpRequestHelper;
import com.test.webapp.service.IUserService;
import com.test.webapp.service.impl.UserService;
import com.test.webapp.session.SessionManager;

/**
 * Checks user using basic authentication. Uses user service to find user in
 * database and checks password validity.
 * 
 * @author david
 *
 */
@SuppressWarnings("restriction")
public class AuthenticationManager extends Authenticator {

	private static final Object AUTHORIZATION_HEADER = "Authorization";
	private IUserService userService = new UserService(new UserDAO());

	private static final Logger LOGGER = LoggerFactory.getLogger(AuthenticationManager.class);

	@Override
	public Result authenticate(HttpExchange httpExchange) {
		Result result = new Failure(HttpStatus.UNAUTHORIZED.getCode());
		String[] data;
		try {
			data = extractAuthorizationHeader(httpExchange);
			User user = userService.getUser(data[0]);
			if (user != null && user.getPassword().equals(data[1])) {
				// Create session if needed
				if (SessionManager.getInstance().checkSessionNeeded(httpExchange)) {
					String sessionId = SessionManager.getInstance().createSession(user.getUsername());
					HttpRequestHelper.putCookieSession(httpExchange, sessionId);
				}
				HttpPrincipal principal = new HttpPrincipal(data[0], data[1]);
				result = new Success(principal);
			}
		} catch (Exception e) {
			// Error
			LOGGER.error("Authentication error ", e);
		}

		return result;
	}

	public String[] extractAuthorizationHeader(HttpExchange t) throws UnsupportedEncodingException {
		String[] requestData = null;
		// Look for authorization header
		Headers headers = t.getRequestHeaders();
		List<String> values = headers.get(AUTHORIZATION_HEADER);
		if (values != null) {
			if (values.get(0).contains("Basic")) {
				String encodedData = values.get(0).replace("Basic", "").trim();
				byte[] byteData = Base64.getDecoder().decode(encodedData);
				String decodedData = new String(byteData, "UTF-8");
				requestData = decodedData.split(":");
			}
		}
		return requestData;
	}

}
