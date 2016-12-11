package com.test.webapp.view;

import java.io.IOException;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.jknack.handlebars.Context;
import com.github.jknack.handlebars.Handlebars;
import com.github.jknack.handlebars.JsonNodeValueResolver;
import com.github.jknack.handlebars.Template;
import com.github.jknack.handlebars.context.FieldValueResolver;
import com.github.jknack.handlebars.context.JavaBeanValueResolver;
import com.github.jknack.handlebars.context.MapValueResolver;
import com.github.jknack.handlebars.context.MethodValueResolver;
import com.github.jknack.handlebars.io.ClassPathTemplateLoader;
import com.github.jknack.handlebars.io.TemplateLoader;

/**
 * Html handlebars based renderer.
 * 
 * @author david.ralluy
 *
 */
public class HandlebarsPageRenderer extends AbstractPageRenderer<String> {

	private TemplateLoader loader = new ClassPathTemplateLoader();

	private Handlebars handlebars = null;

	/**
	 * 
	 */
	public HandlebarsPageRenderer() {
		loader.setPrefix("/templates");
		loader.setSuffix(".html");
		handlebars = new Handlebars(loader);
	}

	@Override
	public String resolvePageContent(String page, String contextData) throws IOException {

		Template template = handlebars.compile(page);

		if (contextData != null && !contextData.isEmpty()) {
			JsonNode jsonNode = new ObjectMapper().readValue(contextData, JsonNode.class);
			return template.apply(getContext(jsonNode));
		}
		return template.text();

	}

	private Context getContext(JsonNode model) {

		Context context = Context.newBuilder(model)
				.resolver(JsonNodeValueResolver.INSTANCE, JavaBeanValueResolver.INSTANCE, FieldValueResolver.INSTANCE,
						MapValueResolver.INSTANCE, MethodValueResolver.INSTANCE)
				.build();
		return context;
	}
}
