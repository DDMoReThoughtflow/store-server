package eu.ddmore.provn.client;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PipedInputStream;
import java.io.PipedOutputStream;
import java.io.PrintStream;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import org.openprovenance.prov.interop.InteropFramework;
import org.openprovenance.prov.interop.InteropFramework.ProvFormat;
import org.openprovenance.prov.model.Document;
import org.openprovenance.prov.model.Namespace;
import org.openprovenance.prov.model.ProvFactory;
import org.openprovenance.prov.model.QualifiedName;
import org.openprovenance.prov.rdf.Ontology;
import org.openprovenance.prov.rdf.RdfConstructor;
import org.openprovenance.prov.rdf.SesameGraphBuilder;
import org.openprovenance.prov.rdf.Utility;
import org.openprovenance.prov.rdf.collector.QualifiedCollector;
import org.openprovenance.prov.rdf.collector.RdfCollector;
import org.openrdf.model.impl.LiteralImpl;
import org.openrdf.model.impl.URIImpl;
import org.openrdf.repository.Repository;
import org.openrdf.repository.RepositoryException;
import org.openrdf.repository.contextaware.ContextAwareRepository;
import org.openrdf.repository.sail.SailRepository;
import org.openrdf.rio.RDFFormat;
import org.openrdf.rio.RDFHandlerException;
import org.openrdf.rio.RDFParseException;
import org.openrdf.sail.memory.MemoryStore;

import com.hp.hpl.jena.query.QuerySolution;
import com.hp.hpl.jena.query.ResultSet;

import com.hp.hpl.jena.rdf.model.*;

public class SparQLtoPROVOManual {
	private Namespace ns;
	private Namespace nsnotreplacing;
	private Document doc = null;

	public void toJSON(OutputStream output)
	{
		InteropFramework intF=new InteropFramework();
		intF.writeDocument(output, ProvFormat.JSON, doc);
	}
	public void registarNoReplacingNS(String prefix,String namespace){
		nsnotreplacing.register(prefix, namespace);;
	}


	public SparQLtoPROVOManual()
	{
		ns = new Namespace();

		ns.register("xsd", "http://www.w3.org/2001/XMLSchema");
		ns.register("ddmore", "http://www.ddmore.eu/");
		ns.register("prov", "http://www.w3.org/ns/prov");
		ns.registerDefault("http://www.ddmore.eu/");
		ns.register("rdf","http://www.w3.org/1999/02/22-rdf-syntax-ns");
		ns.register("rdfs", "http://www.w3.org/2000/01/rdf-schema");
		nsnotreplacing = new Namespace();
		//ns.register("repo", "https://github.com/halfmanhalfgeek/MDLProject");
		//Not really support to be here
		//ns.register("vcs", "https://github.com/");
	}

	public Document convert(ResultSet rs) {
		//Out for the temp RDF TURTLE
		ByteArrayOutputStream   outstr = new ByteArrayOutputStream();
		PrintStream ps = new PrintStream(outstr);
		try {

			int line = 1;

			Map<String, String> namespaces = ns.getPrefixes();
			Set<String> prefixes = namespaces.keySet();
			for (String p:prefixes){
				//Debug statements
				System.out.print("@prefix ");
				System.out.print(p + ": ");
				System.out.print("<");
				System.out.print(namespaces.get(p));
				System.out.println("#> .");


				ps.print("@prefix ");
				ps.print(p + ": ");
				ps.print("<");
				ps.print(namespaces.get(p));
				ps.println("#> .");
				line++;
			}


			Map<String, String> namespacesnr = nsnotreplacing.getPrefixes();
			Set<String> prefixesnr = namespacesnr.keySet();
			for (String p:prefixesnr){
				//Debug statements
				System.out.print("@prefix ");
				System.out.print(p + ": ");
				System.out.print("<");
				System.out.print(namespacesnr.get(p));
				System.out.println("#> .");


				ps.print("@prefix ");
				ps.print(p + ": ");
				ps.print("<");
				ps.print(namespacesnr.get(p));
				ps.println("#> .");
				line++;
			}
			ps.println("");
			//ps.println( "@prefix repo: <https://github.com/halfmanhalfgeek/MDLProject#> . ");
			System.out.println();


		
			while (rs.hasNext()) {

				QuerySolution qs = rs.next();
				System.out.print(line);
				
				RDFNode subjectnode = qs.get("s");
				if(subjectnode.isLiteral()) {
					System.out.print("" + makeLiteral(subjectnode.toString()) + "");
					ps.print("" + makeLiteral(subjectnode.toString()) + "");
				} else
				{
					System.out.print(" " + repoReplace(subjectnode.toString()) + "");
					ps.print("" + repoReplace(subjectnode.toString()) + "");
				}
				
				
				//System.out.print("" + repoReplace(subjectnode.toString())+ "");
				
				
				//ps.println("" + repoReplace(subjectnode.toString())+ "");
				RDFNode prednode = qs.get("p");
				System.out.print(" " + repoReplace(prednode.toString()));
				ps.print(" " + repoReplace(prednode.toString()));
				RDFNode objectnode = qs.get("o");
			
				
				if(objectnode.isLiteral()) {
					System.out.println(" " + makeLiteral(objectnode.toString()) + " .");
					ps.println(" " + makeLiteral(objectnode.toString()) + " .");
				} else
				{
					
					System.out.println(" " + repoReplace(objectnode.toString()) + " .");
					ps.println(" " + repoReplace(objectnode.toString()) + " .");
					System.out.println(objectnode);
				}
				line++;
			}



			ByteArrayInputStream  instr = new ByteArrayInputStream(outstr.toByteArray());
			final ProvFactory pFactory=new org.openprovenance.prov.xml.ProvFactory();
			final Ontology onto=new Ontology(pFactory);    	
			final Utility u = new Utility(pFactory,onto);

			doc =  u.parseRDF(instr,RDFFormat.TURTLE,"");
			InteropFramework intF=new InteropFramework();
			//intF.writeDocument("test.prov", doc);     
			intF.writeDocument(System.out, ProvFormat.PROVN, doc);
			intF.writeDocument(System.out, ProvFormat.JSON, doc);
		} catch (RDFParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (RDFHandlerException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			ps.close();
		}


		return null;
	}



	protected  String makeLiteral(String substr) {


		int index = substr.indexOf("^^");
		String returnValue = null;
		if (index != -1) {
			String label = substr.substring(0, index);

			String datatype = substr.substring(index + 2, substr.length());
			//URIImpl url = new URIImpl(datatype);
			returnValue = "\"" + label + "\"" + "^^" + repoReplaceLit(datatype);

		}


		return returnValue;
	}

	protected  String repoReplaceLit(String str) {
		int index = str.indexOf('#');
		String returnValue = null;
		if (index != -1) {
			String names = str.substring(0, index);
			String prefix = ns.getNamespaces().get(names);
			String local = str.substring(index + 1, str.length());
			if (prefix == null)
			{
				returnValue = "<" + names + "#" + "\"" + local + "\">";
			}
			else {
				//System.out.println("pre" + prefix);




				returnValue = "" + prefix + ":" +  "" + local + "";;
			}
		}
		return returnValue;

	}
	protected String repoReplace(String str) {


		int index = str.indexOf('#');
		String returnValue = null;
		if (index != -1) {


			String names = str.substring(0, index);
			String prefix = ns.getNamespaces().get(names);
			String local = str.substring(index + 1, str.length());
			if (prefix == null)
			{
				returnValue = "<" + names + "#" + "" + local + ">";
			}
			else {
				//System.out.println("pre" + prefix);

				returnValue = "" + prefix + ":" +  "" + local + "";;
			}
		}
		else
		{
			// If think we have a this is a reference 
			//System.out.println(  str);
			//TODO do i need to check it is reallt blank
			returnValue = "_:"+ str;

		}





		return returnValue;
	}
}