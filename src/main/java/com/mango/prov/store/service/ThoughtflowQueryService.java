/*
* Copyright 2016, Mango Solutions Ltd - All rights reserved.
*
* SPDX-License-Identifier:   AGPL-3.0
*/
package com.mango.prov.store.service;

/**
 * The interface for the provenance store. 
 */
public interface ThoughtflowQueryService {

	/**
	 * Retrieve the model tree from the Thoughtflow store
	 * @param Map the JSON query message 
	 * 
	 * @return The model tree
	 */
	Object getModelTree(String message);
	
	
	/**
	 * Retrieve details about an entity from the Thoughtflow store
	 * @param Map the JSON query message
	 * 
	 * @return PROV-JOSN information about the entity
	 */
	Object getEntity(String message);

	/**
	 * Retrieve details about an activity from the Thoughtflow store.
	 * 
	 * Supported parameters in JSON Message:
	 * 	repo_id : restrict results to a specific repository (optional)
	 *  depth : number of nodes to follow from the matched activities
	 *  relationships : list of relationships to follow from the item being looked up
	 *  
	 * One of
	 *  entity_id : The id of the entity to start from. Locate activities that have the provided 
	 *  	relationship with this entity. For example entity_id : 1, relationships: ["used", "generated"] would
	 *  	find activities that either used or generated this entity 
	 *  activity_id : The id of the activity to start from. Focus on this activity and follow all the provided
	 *      relationships away from it. Used to find more information about a specific activity
	 * 
	 * @param Map the JSON query message
	 * 
	 * @return Prov-JSON object about the activity
	 */
	Object getActivity(String message);
	
}
