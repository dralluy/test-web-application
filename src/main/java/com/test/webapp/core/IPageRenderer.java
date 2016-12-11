package com.test.webapp.core;

import java.io.IOException;

/**
 * Page renderer. Resolves page content and renders page as response.
 * 
 * @author david.ralluy
 * @param <T>
 *            Data type rendered
 *
 */
public interface IPageRenderer<T> {

	/**
	 * @param page
	 * @param contextData
	 * @return T rendered type
	 * @throws IOException
	 */
	T resolvePageContent(String page, String contextData) throws IOException;
}
