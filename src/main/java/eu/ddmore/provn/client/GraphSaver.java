/* ----------------------------------------------------------------------------
 * This file is part of ThoughtFlow Provenance Server.  
 * 
 * Copyright (C) 2016 jointly by the following organisation:- 
 * 1. DDMoRe Foundation
 * 2. Cyprotex Discovery Ltd, Macclesfield, England, UK
 *
 * This library is free software; you can redistribute it and/or modify it
 * under the terms of the GNU General Public License as published by
 * the Free Software Foundation. A copy of the license agreement is provided
 * in the file named "LICENSE.txt" included with this software distribution.
 * ----------------------------------------------------------------------------
 */

package eu.ddmore.provn.client;

import static eu.ddmore.provn.client.Utils.createInsertGraphSparQL;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.openprovenance.prov.interop.InteropFramework;
import org.openprovenance.prov.interop.InteropFramework.ProvFormat;
import org.openprovenance.prov.json.Converter;
import org.openprovenance.prov.model.Bundle;
import org.openprovenance.prov.model.Document;
import org.openprovenance.prov.model.MentionOf;
import org.openprovenance.prov.model.Statement;
import org.openprovenance.prov.model.StatementOrBundle;
import org.openprovenance.prov.model.Used;
import org.openprovenance.prov.xml.ProvFactory;

import com.hp.hpl.jena.update.UpdateExecutionFactory;
import com.hp.hpl.jena.update.UpdateFactory;
import com.hp.hpl.jena.update.UpdateProcessor;

/**
 * Save a Provenance graph with JENA.
 */
public class GraphSaver {
	public static ProvFactory pFactory = ProvFactory.getFactory();
	private ByteArrayOutputStream buffer = null;
	private Converter c = null;
	private boolean echo_statement = false;
	private InteropFramework intF = null;
	private String updateEndpoint = "http://localhost:8081/fuseki/ds3/update";
	
	/**
	 * Constructor
	 */
	public GraphSaver() {
		intF = new InteropFramework();
		buffer = new ByteArrayOutputStream(10240 * 4);
		c = new Converter(pFactory);
	}
	
	/**
	 * Echo the persistence SparQL command to an output stream.
	 * @param out Output Stream 
	 * @param json_file JSON Input File
	 * @throws IOException 
	 */
	public void echo(PrintStream out, String json_file) throws IOException {
		buffer.flush();
		buffer.reset();
		
		Document doc = c.readDocument(json_file);
        intF.writeDocument(buffer, ProvFormat.TURTLE, doc);
        buffer.flush();
        String [] lines = buffer.toString().split("\n");
        
        String stmt = createInsertGraphSparQL(lines);
        out.println(stmt);
	}
	
	/**
	 * Save a JSON provenance graph to the triple store.
	 * @param out Path to the JSON file
	 * @throws Exception
	 */
	public void save(InputStream fin) throws Exception {
		buffer.flush();
		buffer.reset();
		
		Document doc = c.readDocument(fin);
//		
//		HashMap<String, String> mentions = new HashMap<String, String>() ;
//		List<MentionOf> mentionsToRemove = new ArrayList<MentionOf>(1);
//		List<StatementOrBundle> sab = doc.getStatementOrBundle();
//		List<Bundle> bundlesList = new ArrayList<Bundle>(1);
//			for (StatementOrBundle s:sab)
//			{
//				switch (s.getKind())
//				{
//				case PROV_BUNDLE:
//					Bundle bun = (Bundle) s;
//					bundlesList.add(bun);
//					bun.getStatement();
//					for (Statement b:bun.getStatement())
//					{
//						switch (b.getKind())
//						{
//						case PROV_MENTION:
//							MentionOf men = (MentionOf) b;
//							//DDmore Check
//							int check1 = men.getSpecificEntity().getNamespaceURI().compareTo("http://www.ddmore.eu/#");
//							boolean check2 =men.getSpecificEntity().getLocalPart().startsWith("mentionOf_");
//							if (check1 == 0 & check2 )
//							{
//								mentions.put(men.getSpecificEntity().toString(), men.getGeneralEntity().toString());
//								mentionsToRemove.add(men);
//							}
//							
//						}
//					}
//				}
//			}
//		
//			//remove statements 
//			for (Bundle b:bundlesList){
//				for (MentionOf m:mentionsToRemove) {
//					b.getStatement().remove(m);
//				}
//			}
//			
//			replaceMentionOf(mentions,sab);
			
			
		
			
        intF.writeDocument(buffer, ProvFormat.TURTLE, doc);
        buffer.flush();
        
        String [] lines = buffer.toString().split("\n");
        
        String stmt = createInsertGraphSparQL(lines);
        if (echo_statement) {
        	System.out.println(stmt);
        	return;
        }
       
        UpdateProcessor updater = UpdateExecutionFactory.createRemote(UpdateFactory.create(stmt), updateEndpoint);
        updater.execute();
	}
	
	private void replaceMentionOf( HashMap<String, String> mentions,List<StatementOrBundle> sab) {
		for (StatementOrBundle s:sab)
		{
			switch (s.getKind())
			{
				case PROV_BUNDLE:
					Bundle b = (Bundle) s;
					replaceMentionOf(b.getStatement(),mentions);
					default:
						replaceMentionOf(mentions,(Statement) s);
					
			}
		}
		
	}
	
	
	private void replaceMentionOf(HashMap<String, String> mentions, Statement s) {
		switch (s.getKind())
		{
		case PROV_USAGE:
			Used u = (Used) s;
			String ref = mentions.get(u.getEntity().toString());
			if (ref != null)
			{
				
			}
		}
		
	}

	private void replaceMentionOf(List<Statement> sab,HashMap<String, String> mentions ) {
		for (StatementOrBundle s:sab)
		{
			
		}
		
	}

	/**
	 * Save a JSON provenance graph to the triple store.
	 * @param json_file Path to the JSON file
	 * @throws Exception
	 */
	public void save(String json_file) throws Exception {
		buffer.flush();
		buffer.reset();
		
		Document doc = c.readDocument(json_file);
		
		
		
        intF.writeDocument(buffer, ProvFormat.TURTLE, doc);
        buffer.flush();
        String [] lines = buffer.toString().split("\n");
        
        String stmt = createInsertGraphSparQL(lines);
        if (echo_statement) {
        	System.out.println(stmt);
        	return;
        }
       
        UpdateProcessor updater = UpdateExecutionFactory.createRemote(UpdateFactory.create(stmt), updateEndpoint);
        updater.execute();
	}
	
	/**
	 * Save a concatonated raw JSON String
	 * @param json Raw JSON String
	 * @throws Exception Throw if things go wonky
	 */
	public void saveJsonString(String json) throws Exception {
		byte [] bytes = json.toString().getBytes();
		ByteArrayInputStream bin = new ByteArrayInputStream(bytes);
		save(bin);
		bin.close();
	}
	
	/**
	 * Echo SparQL statement to STDOUT
	 * @param decision Decision
	 */
	public void setEchoStatement(boolean decision) { echo_statement = decision; }
	
	/**
	 * THe URI of the JENA end-point.
	 * @param uri Web Address of the end-point
	 */
	public void setUpdateEndpoint(String uri) { updateEndpoint = uri; }
}
