/*
* Copyright 2016, Mango Solutions Ltd - All rights reserved.
*
* SPDX-License-Identifier:   AGPL-3.0
*/
package com.mango.prov.store.rest;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.util.Scanner;

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

/**
 * Unit test for the ProvStoreController
 *
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(Application.class)
@WebAppConfiguration
public class ProvStoreControllerTest {

	@Autowired
	private WebApplicationContext webApplicationContext;

	private MockMvc mockMvc;

	@Before
	public void setup() {
		this.mockMvc = MockMvcBuilders.webAppContextSetup(this.webApplicationContext).build();
	}

	/**
	 * Example simple test case testing the document creation endpoint with assertions on the 
	 * HTTP status and the content of the response.
	 * @throws Exception
	 */
	@Test
	public void testCreateDocument6() throws Exception {
		// load the message from the classpath
		String message = loadMessage("/6.Ran Step 1.json");
		//Send the request to the controller.
		mockMvc.perform(post("/store/api/v0/documents/")
					.contentType(MediaType.APPLICATION_JSON_UTF8) // The content type of the request.
					.content(message) // The content of the request
					.accept(MediaType.APPLICATION_JSON_UTF8) // The content type that we want back. 
				)
			// Test the results
			// Check the HTTP status code
			.andExpect(status().isCreated())
			// Check the content
			.andExpect(content().string("0"));

	}
	@Test
	public void testCreateDocument1() throws Exception {
		// load the message from the classpath
		String message = loadMessage("/1.Intialised project data.json");
		//Send the request to the controller.
		mockMvc.perform(post("/store/api/v0/documents/")
					.contentType(MediaType.APPLICATION_JSON_UTF8) // The content type of the request.
					.content(message) // The content of the request
					.accept(MediaType.APPLICATION_JSON_UTF8) // The content type that we want back. 
				)
			// Test the results
			// Check the HTTP status code
			.andExpect(status().isCreated())
			// Check the content
			.andExpect(content().string("0"));

	}
	@Test
	public void testCreateDocument2() throws Exception {
		// load the message from the classpath
		String message = loadMessage("/2.Imported base model.json");
		//Send the request to the controller.
		mockMvc.perform(post("/store/api/v0/documents/")
					.contentType(MediaType.APPLICATION_JSON_UTF8) // The content type of the request.
					.content(message) // The content of the request
					.accept(MediaType.APPLICATION_JSON_UTF8) // The content type that we want back. 
				)
			// Test the results
			// Check the HTTP status code
			.andExpect(status().isCreated())
			// Check the content
			.andExpect(content().string("0"));

	}
	@Test
	public void testCreateDocument3() throws Exception {
		// load the message from the classpath
		String message = loadMessage("/3.Created EDA Script.json");
		//Send the request to the controller.
		mockMvc.perform(post("/store/api/v0/documents/")
					.contentType(MediaType.APPLICATION_JSON_UTF8) // The content type of the request.
					.content(message) // The content of the request
					.accept(MediaType.APPLICATION_JSON_UTF8) // The content type that we want back. 
				)
			// Test the results
			// Check the HTTP status code
			.andExpect(status().isCreated())
			// Check the content
			.andExpect(content().string("0"));

	}
	@Test
	public void testCreateDocument4() throws Exception {
		// load the message from the classpath
		String message = loadMessage("/4.Ran EDA script.json");
		//Send the request to the controller.
		mockMvc.perform(post("/store/api/v0/documents/")
					.contentType(MediaType.APPLICATION_JSON_UTF8) // The content type of the request.
					.content(message) // The content of the request
					.accept(MediaType.APPLICATION_JSON_UTF8) // The content type that we want back. 
				)
			// Test the results
			// Check the HTTP status code
			.andExpect(status().isCreated())
			// Check the content
			.andExpect(content().string("0"));

	}
	@Test
	public void testCreateDocument5() throws Exception {
		// load the message from the classpath
		String message = loadMessage("/5.Wrote runner script for step 1.json");
		//Send the request to the controller.
		mockMvc.perform(post("/store/api/v0/documents/")
					.contentType(MediaType.APPLICATION_JSON_UTF8) // The content type of the request.
					.content(message) // The content of the request
					.accept(MediaType.APPLICATION_JSON_UTF8) // The content type that we want back. 
				)
			// Test the results
			// Check the HTTP status code
			.andExpect(status().isCreated())
			// Check the content
			.andExpect(content().string("0"));

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
		return new Scanner(ProvStoreControllerTest.class.getResourceAsStream(testDataResource), "UTF-8")
				.useDelimiter("\\A").next();
	}
}
