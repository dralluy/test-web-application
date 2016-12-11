package com.test.webapp.core.processor;

/**
 * Core controller functionality.
 * Gets a request context, executes de business logic and returns a response context.
 * <T> response data type
 * 
 * @author david.ralluy
 *
 */
@FunctionalInterface
public interface ContextProcessor<T> {
	/**
	 * @param request
	 * @return
	 */
	ResponseContext<T> processContext(RequestContext request);
}
