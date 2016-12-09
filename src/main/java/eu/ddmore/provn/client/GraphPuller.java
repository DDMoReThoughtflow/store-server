package eu.ddmore.provn.client;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintStream;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

import org.apache.log4j.Level;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.openprovenance.prov.interop.InteropFramework;
import org.openprovenance.prov.interop.InteropFramework.ProvFormat;
import org.openprovenance.prov.json.Converter;
import org.openprovenance.prov.model.Document;
import org.openprovenance.prov.xml.ProvFactory;

import com.hp.hpl.jena.query.QueryExecutionFactory;
import com.hp.hpl.jena.query.ResultSet;
import com.hp.hpl.jena.query.ResultSetFormatter;
import com.hp.hpl.jena.rdf.model.Model;
import com.hp.hpl.jena.sparql.engine.http.QueryEngineHTTP;
import com.hp.hpl.jena.update.UpdateExecutionFactory;
import com.hp.hpl.jena.update.UpdateFactory;
import com.hp.hpl.jena.update.UpdateProcessor;
import com.hp.hpl.jena.query.ParameterizedSparqlString;
import com.hp.hpl.jena.query.Query;
import com.hp.hpl.jena.query.QueryExecution;
import com.hp.hpl.jena.query.ResultSetFormatter;

/**
 * Save a Provenance graph with JENA.
 */
public class GraphPuller {
	public static ProvFactory pFactory = ProvFactory.getFactory();

	// Bodge code to switch LOG4J logging messages.
	static {
		@SuppressWarnings("unchecked")
		List<Logger> loggers = Collections.<Logger>list(LogManager.getCurrentLoggers());
		loggers.add(LogManager.getRootLogger());
		for ( Logger logger : loggers ) logger.setLevel(Level.OFF);
	}
	private String queryEndpoint = "http://localhost:8081/fuseki/ds3/query";
	private String queryOutput = "json";
	private InteropFramework intF = null;
	private ByteArrayOutputStream buffer = null;
	private Converter c = null;
	private boolean echo_statement = false;
	QueryExecution qexec = null;
	ResultSet rs = null;
	HashMap<String,String> linktoSparql =  new HashMap<String,String>();
	/**
	 * Constructor
	 */
	public GraphPuller() {
		//intF = new InteropFramework();
		//buffer = new ByteArrayOutputStream(10240 * 4);
		//c = new Converter(pFactory);
		linktoSparql.put("USED", "prov:used");
		linktoSparql.put("GENERATED", "prov:wasGeneratedBy");
		

	}


	/**
	 * Pulls a JSON provenance graph to the triple store.
	 * @param json_file Path to the JSON file
	 * @throws Exception
	 */
	public String pullModeltoJson(String repoid) throws Exception {
		QueryExecution qexec = null;
		ResultSet rs = null;

		String json  = null;
		String queryFile = "src/main/resources/sqarql/model.rq";
		String jennaquery = null;
		ParameterizedSparqlString pss = null;
		try {
			jennaquery = loadQuery(queryFile);
			
			pss = new ParameterizedSparqlString();
			pss.setNsPrefix("repo", repoid  + "#");
			pss.setCommandText(jennaquery);
			pss.setLiteral(0, repoid);
			pss.setLiteral(1, repoid);
			pss.setLiteral(2, repoid);
			//pss.setLiteral("user_Id", userId);
			System.out.println(pss.toString());
			pss.asQuery();
			if (echo_statement) {
				System.out.println(pss.toString());

			}
			qexec = QueryExecutionFactory.sparqlService(formQueryEndpoint(), pss.toString());
			((QueryEngineHTTP)qexec).addParam("timeout", "10000") ;
			 Model m = qexec.execConstruct() ;

			ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
			SparQLtoPROVO converter = new SparQLtoPROVO();
			converter.convert(m);
			converter.toJSON(outputStream);
			json = new String(outputStream.toByteArray());
		}
		catch (IOException e)
		{
			throw new Exception("Could load local Spaql query " + queryFile , e);

		} finally {
			if (qexec != null) qexec.close();
		}
		return json;
	}


	/**
	 * Pulls a JSON provenance graph to the triple store.
	 * @param json_file Path to the JSON file
	 * @throws Exception
	 */
	public String pullAll() throws Exception {
		QueryExecution qexec = null;
		ResultSet rs = null;

		String json  = null;
		String queryFile = "src/main/resources/sqarql/all_triples.rq";
		String jennaquery = null;
		ParameterizedSparqlString pss = null;
	
		try {
			jennaquery = loadQuery(queryFile);
			
			pss = new ParameterizedSparqlString();
			pss.setCommandText(jennaquery);

			//pss.setLiteral("user_Id", userId);
			// System.out.println(pss.toString());
			pss.asQuery();
			if (echo_statement) {
				System.out.println(pss.toString());

			}
			qexec = QueryExecutionFactory.sparqlService(formQueryEndpoint(), pss.toString());
			((QueryEngineHTTP)qexec).addParam("timeout", "10000") ;
			 Model m = qexec.execConstruct() ;

			ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
			SparQLtoPROVO converter = new SparQLtoPROVO();
			converter.convert(m);
			converter.toJSON(outputStream);
			json = new String(outputStream.toByteArray());
		}
		catch (IOException e)
		{
			throw new Exception("Could load local Spaql query " + queryFile , e);

		} finally {
			if (qexec != null) qexec.close();
		}
		return json;
	}



	/**
	 * Echo the persistence SparQL command to an output stream.
	 * @param out Output Stream 
	 * @param json_file JSON Input File
	 * @throws IOException 
	 */


	/**
	 * THe URI of the JENA query end-point.
	 * @param uri Web Address of the end-point
	 */
	public void setQueryEndpoint(String uri) { queryEndpoint = uri; }

	protected String formQueryEndpoint()	{
		return queryEndpoint + "?output=" + queryOutput;
	}
	/**
	 * Echo SparQL statement to STDOUT
	 * @param decision Decision
	 */
	public void setEchoStatement(boolean decision) { echo_statement = decision; }

	public static String loadQuery(String filepath) throws IOException {
		BufferedReader br = new BufferedReader(new FileReader(filepath));

		StringBuilder sb = new StringBuilder();
		String line = br.readLine();

		while (line != null) {
			sb.append(line);
			sb.append(System.lineSeparator());
			line = br.readLine();
		}
		br.close();

		return sb.toString();
	}


	public Object pullEntitytoJson(String repo, String entity_id, Long depth, List<String> relationships) throws Exception {
		QueryExecution qexec = null;
		Model m = null;

		String json  = null;
		String queryFile = "src/main/resources/sqarql/entity.rq";
		String jennaquery = null;
		ParameterizedSparqlString pss = null;
		
		String reformedid = "" + repo + entity_id.replace("repo:","") + "";
		String filter = "";
		if (relationships.size() >0){
			filter = "FILTER(?pred in (";
			boolean first = true;
			for (String r:relationships){
				if (first){
					filter = filter + linktoSparql.get(r);
					first = false;
				} else {
					filter = filter + "," + linktoSparql.get(r);
				}
					
				
			}
			
			filter = filter + "))";
		}
		
		try {
			jennaquery = loadQuery(queryFile);

			jennaquery = jennaquery.replaceAll("replacep1repo", reformedid);
			jennaquery = jennaquery.replaceAll("relationshipfilter", filter);
			pss = new ParameterizedSparqlString();
			pss.setCommandText(jennaquery);
			pss.setNsPrefix("repo", repo  );

			System.out.println(pss.toString());
			pss.asQuery();
			if (echo_statement) {
				System.out.println(pss.toString());

			}
			qexec = QueryExecutionFactory.sparqlService(formQueryEndpoint(), pss.toString());
			((QueryEngineHTTP)qexec).addParam("timeout", "10000") ;
			m = qexec.execConstruct() ;

			ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
			SparQLtoPROVO converter = new SparQLtoPROVO();
			converter.convert(m);
			converter.toJSON(outputStream);
			json = new String(outputStream.toByteArray());
		}
		catch (IOException e)
		{
			throw new Exception("Could load local Spaql query " + queryFile , e);

		} finally {
			if (qexec != null) qexec.close();
		}
		return json;
	}


	public Object pullActivitytoJson(String repo_id, Long depth, List<String> relationships, String entity_id,
			String activity_id) throws IOException {
		String json;
		Model m;
		ParameterizedSparqlString pss;
		String queryFile = "src/main/resources/sqarql/activity.rq";
		
		String jennaquery;
		jennaquery = loadQuery(queryFile);

		jennaquery = jennaquery.replaceAll("actvitystring", activity_id);
		
		pss = new ParameterizedSparqlString();
		pss.setCommandText(jennaquery);
		

		System.out.println(pss.toString());
		pss.asQuery();
		if (echo_statement) {
			System.out.println(pss.toString());

		}
		qexec = QueryExecutionFactory.sparqlService(formQueryEndpoint(), pss.toString());
		((QueryEngineHTTP)qexec).addParam("timeout", "10000") ;
		
		m = qexec.execConstruct() ;

		ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
		SparQLtoPROVO converter = new SparQLtoPROVO();
		converter.convert(m);
		converter.toJSON(outputStream);
		json = new String(outputStream.toByteArray());
		return json;
		
	}
}
