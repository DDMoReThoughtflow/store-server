/*
* Copyright 2016, Mango Solutions Ltd - All rights reserved.
*
* SPDX-License-Identifier:   AGPL-3.0
*/
package com.mango.prov.store.rest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.mango.prov.store.service.ThoughtflowQueryService;

/**
 * Implementation of the Thoughtflow Query Service.
 * http://www.ddmore.eu/thoughtflow/api
 * 
 */
@RestController
@RequestMapping("/thoughtflow/api/v0")
public class ThoughtflowQueryController {
	@SuppressWarnings("unused")
	private Logger LOGGER = LoggerFactory.getLogger(ThoughtflowQueryController.class);

	
	@Autowired
	private ThoughtflowQueryService thoughtflowQueryService;
	
	/**
	 * http://www.ddmore.eu/thoughtflow/api/v0/modelTree
	 * @param parameters the query parameters
	 * @return A PROV-JSON result
	 */
	@RequestMapping(path = "/modelTree/", method = RequestMethod.POST)
	public ResponseEntity<?> getModelTree(@RequestBody String message) {

		Object result = thoughtflowQueryService.getModelTree(message);
		
		// Convert object result to JSON string
		String json = (String)result; //???
		
		ResponseEntity<?> entity = new ResponseEntity<String>(json, HttpStatus.OK);
		
		
		return entity;
	}


	/**
	 * http://www.ddmore.eu/thoughtflow/api/v0/entity
	 * 
	 * @param parameters the parameters specified on the URL.
	 * @param message the JSON document as a String
	 * @return
	 */
	@RequestMapping(path = "/entity/", method = RequestMethod.POST)
	public ResponseEntity<?> getEntity(@RequestBody String message) {

		Object result = thoughtflowQueryService.getEntity(message);
		
		// Convert object result to JSON string
		String json = (String)result; //???
		
		ResponseEntity<?> entity = new ResponseEntity<String>(json, HttpStatus.OK);
		
		return entity;
	}

	/**
	 * http://www.ddmore.eu/thoughtflow/api/v0/activity
	 * 
	 * @param parameters the parameters specified on the URL.
	 * @param message the JSON document as a String
	 * @return
	 */
	@RequestMapping(path = "/activity/", method = RequestMethod.POST)
	public ResponseEntity<?> getActivity(@RequestBody String message) {

		Object result = thoughtflowQueryService.getActivity(message);
		
		// Convert object result to JSON string
		String json = (String)result; //???
		
		ResponseEntity<?> entity = new ResponseEntity<String>(json, HttpStatus.OK);
		
		return entity;
	}

	public ThoughtflowQueryService getThoughtflowQueryService() {
		return thoughtflowQueryService;
	}

	public void setThoughtflowQueryService(ThoughtflowQueryService thoughtflowQueryService) {
		this.thoughtflowQueryService = thoughtflowQueryService;
	}

}
