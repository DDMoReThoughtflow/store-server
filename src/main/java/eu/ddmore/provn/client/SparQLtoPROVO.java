package eu.ddmore.provn.client;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;

import org.openprovenance.prov.interop.InteropFramework;
import org.openprovenance.prov.interop.InteropFramework.ProvFormat;
import org.openprovenance.prov.model.Document;
import org.openprovenance.prov.model.ProvFactory;
import org.openprovenance.prov.rdf.Ontology;
import org.openprovenance.prov.rdf.Utility;
import org.openrdf.rio.RDFFormat;
import org.openrdf.rio.RDFHandlerException;
import org.openrdf.rio.RDFParseException;

import com.hp.hpl.jena.rdf.model.Model;
import com.hp.hpl.jena.sparql.function.library.uuid;

public class SparQLtoPROVO {

	private Document doc = null;
	InteropFramework intF = null;

	public void toJSON(OutputStream output)	{
		
		intF.writeDocument(output, ProvFormat.JSON, doc);
	}



	public SparQLtoPROVO()	{
		intF=new InteropFramework();
	}

	public Document convert(Model m) {
		
		//m.removeNsPrefix("ddmore");
		m.write(System.out,"TURTLE" );
		ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
		m.write(outputStream,"TURTLE" );
		 
		final ProvFactory pFactory=new org.openprovenance.prov.xml.ProvFactory();
		final Ontology onto=new Ontology(pFactory);  
		final Utility u = new Utility(pFactory,onto);
		ByteArrayInputStream  instr = new ByteArrayInputStream(outputStream.toByteArray());
	
		 
		
		
		try {
			
			doc =  u.parseRDF(instr,RDFFormat.TURTLE,"");
		
			
		} catch (RDFParseException | RDFHandlerException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	//	doc.getNamespace().register("ddmore", "http://www.ddmore.eu/#");
		//doc.
		intF.writeDocument(System.out, ProvFormat.JSON, doc);


		return doc;
	}








}