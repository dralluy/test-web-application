package com.test.webapp.core.processor;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.regex.Pattern;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.test.webapp.core.MethodMapping;

/**
 * Get controller from request.
 * 
 * @author david
 *
 */
public final class MappingsProcessorResolver {

	private static final Logger LOGGER = LoggerFactory.getLogger(MappingsProcessorResolver.class);
	
	private Properties mappings = new Properties();

	private MappingsProcessorResolver() {
		String filename = "mappings.properties";
		try (InputStream input = Thread.currentThread().getContextClassLoader().getResourceAsStream(filename)) {
			mappings.load(input);
		} catch (IOException e) {
			LOGGER.error("Mappings error " , e);
		}
	}
	
	public static MappingsProcessorResolver getInstance() {
		return MappingsResolverHolder.instance;
	}

	private static class MappingsResolverHolder {
		public static final MappingsProcessorResolver instance = new MappingsProcessorResolver();
	}


	public ContextProcessor<?> createControllerFromMapping(String path, String method) {
		String controllerName = null;
		for (Object key : mappings.keySet()) {
			Pattern pattern = Pattern.compile(key.toString());
			if (pattern.matcher(path).matches()) {
				controllerName = mappings.getProperty(key.toString());
			}
		}
		if (controllerName != null) {
			Class<?> controllerClass;
			try {
				String[] controllers = controllerName.split(",");
				if (controllers.length == 1) {
					controllerClass = Class.forName(controllerName.trim());
					return (ContextProcessor<?>) controllerClass.newInstance();
				} else {
					return findExactMatch(controllers, method);
				}
			} catch (ClassNotFoundException | InstantiationException | IllegalAccessException e) {
				LOGGER.error("Error " , e);
			}

		}
		return null;
	}

	private ContextProcessor<?> findExactMatch(String[] controllerName, String method) {
		List<ContextProcessor<?>> controllers = new ArrayList<>();
		try {
			for (String controller : controllerName) {
				controllers.add((ContextProcessor<?>) Class.forName(controller.trim()).newInstance());
			}
		} catch (InstantiationException | IllegalAccessException | ClassNotFoundException e) {
			LOGGER.error("Mappings error " , e);
		}

		for (ContextProcessor<?> contextProcessor : controllers) {
			MethodMapping mapping = contextProcessor.getClass().getAnnotation(MethodMapping.class);
			if (mapping.method().toString().equals(method)) {
				return contextProcessor;
			}
		}

		return null;
	}
}
