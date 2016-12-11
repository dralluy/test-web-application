package com.test.webapp.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Users of web application. One user can have one or more roles.
 * 
 * @author david
 *
 */
@JsonIgnoreProperties({ "password" })
public class User {

	@JsonProperty
	private String username;

	private String password;

	@JsonProperty
	private String roles;

	public User() {
		this("", "");
	}

	@JsonCreator
	public User(@JsonProperty("username") String username, @JsonProperty("password") String password) {
		this.username = username;
		this.password = password;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getRoles() {
		return roles;
	}

	public void setRoles(String roles) {
		this.roles = roles;
	}

	public String getPassword() {
		return this.password;
	}

	/**
	 * Password is write only. 
	 * @param password
	 * @return
	 */
	public boolean isEqualsPassword(String password) {
		return this.password.equals(password);
	}

	@Override
	public String toString() {
		return "User [username=" + username + ", roles=" + roles + "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((username == null) ? 0 : username.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		User other = (User) obj;
		if (username == null) {
			if (other.username != null)
				return false;
		} else if (!username.equals(other.username))
			return false;
		return true;
	}

}
