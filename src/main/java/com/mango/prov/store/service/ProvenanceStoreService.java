/*
* Copyright 2016, Mango Solutions Ltd - All rights reserved.
*
* SPDX-License-Identifier:   AGPL-3.0
*/
package com.mango.prov.store.service;

/**
 * The interface for the provenance store. 
 * TODO Gareth add in whatever methods are needed for an interface over the RDF triple store.
 */
public interface ProvenanceStoreService {

	/**
	 * Creates the provenance document in the store
	 * @param provenanceDocument the provenance document to store.
	 * 
	 * @return the identifier of the stored document.
	 */
	long createDocument(Object provenanceDocument);
	
}
