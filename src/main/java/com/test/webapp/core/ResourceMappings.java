package com.test.webapp.core;

import java.net.URI;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Class utility as Enum. Works as a singleton class.
 * @author david
 *
 */
public enum ResourceMappings {
	; //Needed
	public static final String APP_MAPPING = "\\/app/page[1|2|3]";

	public static final String APP_API_MAPPING = "\\/api/users.*";

	public static final boolean isApiMapping(URI uri) {
		Pattern pattern = Pattern.compile(ResourceMappings.APP_API_MAPPING);
		Matcher matcher = pattern.matcher(uri.getPath());
		return matcher.matches();
	}

	public static final boolean isNavigationMapping(URI uri) {
		Pattern pattern = Pattern.compile(ResourceMappings.APP_MAPPING);
		Matcher matcher = pattern.matcher(uri.getPath());
		return matcher.matches();
	}

	public static final boolean isMappingAllowed(URI uri) {
		return isApiMapping(uri) || isNavigationMapping(uri);
	}
}
