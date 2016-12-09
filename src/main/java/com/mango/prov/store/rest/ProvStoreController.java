/*
* Copyright 2016, Mango Solutions Ltd - All rights reserved.
*
* SPDX-License-Identifier:   AGPL-3.0
*/
package com.mango.prov.store.rest;

import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.mango.prov.store.service.ProvenanceStoreService;

/**
 * Implementation of the ProvStore REST API.
 * https://provenance.ecs.soton.ac.uk/store/help/api/
 * 
 */
@RestController
@RequestMapping("/store/api/v0")
public class ProvStoreController {
	@SuppressWarnings("unused")
	private Logger LOGGER = LoggerFactory.getLogger(ProvStoreController.class);

	
	@Autowired
	private ProvenanceStoreService provStoreService;
	
	/**
	 * https://provenance.ecs.soton.ac.uk/store/help/api/#documents-save
	 * @param message the JSON document as a String 
	 * @return
	 */
	@RequestMapping(path = "/documents/", method = RequestMethod.POST)
	public ResponseEntity<String> createDocument(@RequestBody String message) {
		/*
		 * TODO Gareth: complete the implementation here to match the ProvStore API.  
		 */
		long identifier = provStoreService.createDocument(message);
		
		/*
		 * TODO Gareth: The response is created, however this does not match the ProvStore API and is only an example
		 * of how to return some content.
		 */
		ResponseEntity<String> entity = new ResponseEntity<String>(Long.toString(identifier), HttpStatus.CREATED);
		
		return entity;
	}

	/**
	 * https://provenance.ecs.soton.ac.uk/store/help/api/#documents-bundles
	 * 
	 * @param documentId the id of the document to append the provided bundle on to.
	 * @param parameters the parameters specified on the URL.
	 * @param message the JSON document as a String
	 * @return
	 */
	@RequestMapping(path = "/documents/{documentId}/bundles/", method = RequestMethod.POST)
	public ResponseEntity<?> createDocumentBundle(@PathVariable String documentId,
			@RequestParam() Map<String, String> parameters, @RequestBody String message) {
		/*
		 * TODO Gareth: complete the implementation here to match the ProvStore API.  
		 */
		ResponseEntity<?> entity = new ResponseEntity<>(HttpStatus.ACCEPTED);
		return entity;
	}

	/**
	 * https://provenance.ecs.soton.ac.uk/store/help/api/#documents-list
	 * 
	 * @param parameters the request parameters from the URL.
	 * @return
	 */
	@RequestMapping(path = "/documents/", method = RequestMethod.GET)
	public ResponseEntity<?> getDocuments(@RequestParam() Map<String, String> parameters) {
		/*
		 * TODO Gareth: complete the implementation here to match the ProvStore API.  
		 */
		ResponseEntity<?> entity = new ResponseEntity<>(HttpStatus.OK);
		return entity;
	}

	/**
	 * https://provenance.ecs.soton.ac.uk/store/help/api/#documents-detail
	 * 
	 * @param documentId the provenance document id.
	 * @return
	 */
	@RequestMapping(path = "/documents/{documentId}/", method = RequestMethod.GET)
	public ResponseEntity<?> getDocumentInfoWithId(@PathVariable String documentId) {
		/*
		 * TODO Gareth: complete the implementation here to match the ProvStore API.  
		 */
		ResponseEntity<?> entity = new ResponseEntity<>(HttpStatus.OK);
		return entity;
	}

	/**
	 * https://provenance.ecs.soton.ac.uk/store/help/api/#documents-raw
	 * 
	 * @param documentId
	 * @param format
	 * @return
	 */
	@RequestMapping(path = "/documents/{documentId}.{format}", method = RequestMethod.GET)
	public ResponseEntity<?> getDocumentWithIdInFormat(@PathVariable String documentId, @PathVariable String format) {
		/*
		 * TODO Gareth: complete the implementation here to match the ProvStore API.  
		 */
		ResponseEntity<?> entity = new ResponseEntity<>(HttpStatus.OK);
		return entity;
	}

	/**
	 * https://provenance.ecs.soton.ac.uk/store/help/api/#documents-bundles
	 * 
	 * @param documentId
	 * @return
	 */
	@RequestMapping(path = "/documents/{documentId}/bundles/", method = RequestMethod.GET)
	public ResponseEntity<?> getDocumentBundles(@PathVariable String documentId) {
		/*
		 * TODO Gareth: complete the implementation here to match the ProvStore API.  
		 */
		ResponseEntity<?> entity = new ResponseEntity<>(HttpStatus.OK);
		return entity;
	}

	/**
	 * https://provenance.ecs.soton.ac.uk/store/help/api/#documents-bundle
	 * 
	 * Note that the behaviour is different depending on whether the bundleId
	 * has a file format extension on or not.
	 * 
	 * @param documentId
	 * @param bundleId
	 * @return
	 */
	@RequestMapping(path = "/documents/{documentId}/bundles/{bundleId}", method = RequestMethod.GET)
	public ResponseEntity<?> getDocumentBundleById(@PathVariable String documentId, @PathVariable String bundleId) {
		/*
		 * TODO Gareth: complete the implementation here to match the ProvStore API.  
		 */
		ResponseEntity<?> entity = new ResponseEntity<>(HttpStatus.OK);
		return entity;
	}

	/**
	 * https://provenance.ecs.soton.ac.uk/store/help/api/#documents-delete
	 * 
	 * @param documentId
	 * @return
	 */
	@RequestMapping(path = "/documents/{documentId}", method = RequestMethod.DELETE)
	public ResponseEntity<?> deleteDocumentWithId(@PathVariable String documentId) {
		/*
		 * TODO Gareth: complete the implementation here to match the ProvStore API.  
		 */
		ResponseEntity<?> entity = new ResponseEntity<>(HttpStatus.NO_CONTENT);
		return entity;
	}

	public ProvenanceStoreService getProvStoreService() {
		return provStoreService;
	}

	public void setProvStoreService(ProvenanceStoreService provStoreService) {
		this.provStoreService = provStoreService;
	}

}
