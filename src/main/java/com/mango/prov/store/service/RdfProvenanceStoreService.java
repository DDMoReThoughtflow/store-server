/*
* Copyright 2016, Mango Solutions Ltd - All rights reserved.
*
* SPDX-License-Identifier:   AGPL-3.0
*/
package com.mango.prov.store.service;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import eu.ddmore.provn.client.GraphSaver;


/**
 * Implementation of a Provenance Store Service backed by an RDF triple store.
 *
 */
@Service
public class RdfProvenanceStoreService implements ProvenanceStoreService {

	Logger LOGGER = Logger.getLogger(RdfProvenanceStoreService.class)
			;
	@Override
	public long createDocument(Object doc) {
		String json = (String) doc;
		try {
			GraphSaver gs = new GraphSaver();
			gs.saveJsonString(json);
		} catch (Exception e) {
			LOGGER.error("Could not save JSON " + json, e);
			return -1;
		}

		return 0;
	}

}
