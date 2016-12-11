package com.test.webapp.core;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import com.test.webapp.controller.Page1Controller;
import com.test.webapp.core.processor.ContextProcessor;
import com.test.webapp.core.processor.MappingsProcessorResolver;

@RunWith(JUnit4.class)
public class MappingsProcessorResolverTest {

	@Test
	public void shouldGetPage1ControllerTest() {
		//Given
		String path = "/app/page1";
		String method = "GET";
		
		//When
		ContextProcessor<?> controller = MappingsProcessorResolver.getInstance().createControllerFromMapping(path, method);
		
		//Then
		Assert.assertTrue(controller instanceof Page1Controller);
	}
	
	@Test
	public void shouldNotGetAnyControllerTest() {
		//Given
		String path = "/app/page25";
		String method = "POST";
		
		//When
		ContextProcessor<?> controller = MappingsProcessorResolver.getInstance().createControllerFromMapping(path, method);
		
		//Then
		Assert.assertNull(controller);
	}
}
