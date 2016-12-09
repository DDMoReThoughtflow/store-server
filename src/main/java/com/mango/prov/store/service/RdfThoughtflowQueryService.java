/*
* Copyright 2016, Mango Solutions Ltd - All rights reserved.
*
* SPDX-License-Identifier:   AGPL-3.0
*/
package com.mango.prov.store.service;

import java.io.IOException;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Map;

import org.apache.log4j.Logger;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.stereotype.Service;

import eu.ddmore.provn.client.GraphPuller;

/**
 * Implementation of the Query Service that works on the RDF triple store
 */
@Service
public class RdfThoughtflowQueryService implements ThoughtflowQueryService {

	Logger LOGGER = Logger.getLogger(RdfThoughtflowQueryService.class);
	GraphPuller gp = null;

	public RdfThoughtflowQueryService() {
		gp = new GraphPuller();
		String queryEndpoint = "http://localhost:8081/fuseki/ds3/query";
		gp.setQueryEndpoint(queryEndpoint);
		gp.setEchoStatement(false);

	}

	public static String EMPTY_RESULT = "{ \"prefix\": {" + "\"ddmore\": \"http://www.ddmore.eu/#\","
			+ "\"xsd\": \"http://www.w3.org/2001/XMLSchema#\"," + "\"default\": \"http://www.ddmore.eu/#\","
			+ "\"prov\": \"http://www.w3.org/ns/prov#\"," + "\"vcs\": \"https://github.com/#\"" + "} } ";

	/**
	 * Retrieve the model tree from the Thoughtflow store
	 * 
	 * @param String
	 *            the JSON query message
	 * 
	 * @return The model tree
	 */
	@Override
	public Object getModelTree(String message) {

		String repoId = null;
		String userId = null;
		JSONParser parser = new JSONParser();
		try {
			JSONObject json;
			json = (JSONObject) parser.parse(message);
			repoId = (String) json.get("repo_id");
			userId = (String) json.get("user_id");
			// TODO userid not implented yet
			LOGGER.debug("Repo_id = " + repoId);
			return gp.pullModeltoJson(repoId);
		} catch (ParseException e) {
			throw new IllegalArgumentException("Could not parse message " + message, e);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if (repoId == null) {
			throw new IllegalArgumentException("repo_id not specified");
		}

		return EMPTY_RESULT;
	}

	/**
	 * Retrieve details about an entity from the Thoughtflow store
	 * 
	 * @param String
	 *            the JSON query message
	 * 
	 * @return Provenance information about the entity
	 */
	@Override
	public Object getEntity(String message) {
		String repo = null;
		String entity_id = null;
		Long depth = Long.valueOf(1);
		//String relationships = null;

		JSONParser parser = new JSONParser();
		try {
			JSONObject json;
			json = (JSONObject) parser.parse(message);
			repo = (String) json.get("repo");
			entity_id = (String) json.get("entity_id");
			depth = (Long) json.get("depth");
		//	relationships = (String) json.get("relationships");

			ArrayList<String> relationships = new ArrayList<String>(5);
			relationships.add("GENERATED");
			relationships.add("USED");
			
			
			// TODO userid not implented yet
			LOGGER.debug("Repo_id = " + repo);
			return gp.pullEntitytoJson(repo, entity_id, depth, relationships);
		} catch (ParseException e) {
			throw new IllegalArgumentException("Could not parse message " + message, e);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if (repo == null) {
			throw new IllegalArgumentException("repo_id not specified");
		}
		if (entity_id == null) {
			throw new IllegalArgumentException("entity_id not specified");
		}

		return EMPTY_RESULT;
	}

	/**
	 * Retrieve details about an activity from the Thoughtflow store
	 * 
	 * @param String
	 *            the JSON query message
	 * 
	 * @return Provenance information about the activity
	 */
	@Override
	public Object getActivity(String message) {
		try {
			JSONParser parser = new JSONParser();
			JSONObject json;

			json = (JSONObject) parser.parse(message);
			
			String repo_id = (String) json.get("repo_id");
			Long depth = (Long) json.get("depth");
			//String relationships = (String) json.get("relationships");
			String entity_id = (String) json.get("entity_id");
			String activity_id = (String) json.get("activity_id");
			ArrayList<String> relationships = new ArrayList<String>(5);
		
			
			if (activity_id == null) {
				throw new IllegalArgumentException("activity_id not specified");
			}
			
			
			return gp.pullActivitytoJson(repo_id, depth, relationships, entity_id,activity_id);
			
		

		} catch (ParseException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return EMPTY_RESULT;
	}

}