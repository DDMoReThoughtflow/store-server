/**
 * 
 */
package com.mango.prov.store.rest;

import static org.junit.Assert.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.Scanner;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import com.mango.prov.store.Application;
import com.mango.prov.store.service.RdfThoughtflowQueryService;

/**
 * @author jchard
 *
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(Application.class)
@WebAppConfiguration
public class ThoughtflowQueryControllerTest {

	@Autowired
	private WebApplicationContext webApplicationContext;

	private MockMvc mockMvc;

	@Before
	public void setup() {
		this.mockMvc = MockMvcBuilders.webAppContextSetup(this.webApplicationContext).build();
	}


	/**
	 * @throws java.lang.Exception
	 */
	@After
	public void tearDown() throws Exception {
	}

	/**
	 * Test method for {@link com.mango.prov.store.rest.ThoughtflowQueryController#getModelTree(java.util.Map)}.
	 */
	@Test
	public void testGetModelTree() throws Exception {
		String message = loadMessage("/test-getmodeltree.json");
		//Send the request to the controller.
		mockMvc.perform(post("/thoughtflow/api/v0/modelTree/")
					.contentType(MediaType.APPLICATION_JSON_UTF8) // The content type of the request.
					.content(message) // The content of the request
					.accept(MediaType.APPLICATION_JSON_UTF8) // The content type that we want back. 
				)
			// Test the results
			// Check the HTTP status code
			.andExpect(status().isOk())
			// Check the content
			.andExpect(content().string(RdfThoughtflowQueryService.EMPTY_RESULT));
	}

	/**
	 * Test method for {@link com.mango.prov.store.rest.ThoughtflowQueryController#getEntity(java.util.Map)}.
	 */
	@Test
	public void testGetEntity() throws Exception {
		String message = loadMessage("/test-getentity.json");
		//Send the request to the controller.
		mockMvc.perform(post("/thoughtflow/api/v0/entity/")
					.contentType(MediaType.APPLICATION_JSON_UTF8) // The content type of the request.
					.content(message) // The content of the request
					.accept(MediaType.APPLICATION_JSON_UTF8) // The content type that we want back. 
				)
			// Test the results
			// Check the HTTP status code
			.andExpect(status().isOk())
			// Check the content
			.andExpect(content().string(RdfThoughtflowQueryService.EMPTY_RESULT));
	}

	/**
	 * Test method for {@link com.mango.prov.store.rest.ThoughtflowQueryController#getEntity(java.util.Map)}.
	 */
	@Test
	public void testGetActivity() throws Exception {
		String message = loadMessage("/test-getactivity.json");
		//Send the request to the controller.
		mockMvc.perform(post("/thoughtflow/api/v0/activity/")
					.contentType(MediaType.APPLICATION_JSON_UTF8) // The content type of the request.
					.content(message) // The content of the request
					.accept(MediaType.APPLICATION_JSON_UTF8) // The content type that we want back. 
				)
			// Test the results
			// Check the HTTP status code
			.andExpect(status().isOk())
			// Check the content
			.andExpect(content().string(RdfThoughtflowQueryService.EMPTY_RESULT));
	}

	/**
	 * Load the message from the classpath
	 * 
	 * @param testDataResource
	 *            the resource to load
	 * @return the contents of the resource.
	 */
	@SuppressWarnings("resource")
	private String loadMessage(String testDataResource) {
		return new Scanner(ThoughtflowQueryControllerTest.class.getResourceAsStream(testDataResource), "UTF-8")
				.useDelimiter("\\A").next();
	}
}
