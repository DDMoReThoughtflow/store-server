package eu.ddmore.provn.client;

import static org.junit.Assert.*;

import java.util.ArrayList;

import org.junit.Test;

public class Puller {
	static String  queryEndpoint = "http://localhost:8081/fuseki/ds3/query";
	@Test
	public void getall() {
	
		GraphPuller gs = null;

		gs = new GraphPuller();
		gs.setQueryEndpoint(queryEndpoint);
		gs.setEchoStatement(false);

		try {
			System.out.println(gs.pullAll());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	
	
	@Test
	public void getmodel() {
	
		GraphPuller gs = null;

		gs = new GraphPuller();
		gs.setQueryEndpoint(queryEndpoint);
		gs.setEchoStatement(false);

		try {
			System.out.println(gs.pullModeltoJson("https://github.com/halfmanhalfgeek/MDLProject"));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}


	@Test
	public void getentity1() {
		
		GraphPuller gs = null;

		gs = new GraphPuller();
		gs.setQueryEndpoint(queryEndpoint);
		gs.setEchoStatement(false);
		ArrayList<String> relationships = new ArrayList<String>(5);
		relationships.add("GENERATED");
		relationships.add("USED");
		try {
			System.out.println(gs.pullEntitytoJson("https://github.com/halfmanhalfgeek/MDLProject#",  "repo:3103d73a24a57321080d46ef04fd7c016b514000/models/Step1.mdl", Long.valueOf(1), relationships));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	@Test
	public void getactivity1() {
		
		GraphPuller gs = null;

		gs = new GraphPuller();
		gs.setQueryEndpoint(queryEndpoint);
		gs.setEchoStatement(false);
		ArrayList<String> relationships = new ArrayList<String>(5);
		relationships.add("GENERATED");
		relationships.add("USED");

		try {
			System.out.println(gs.pullActivitytoJson("", (long) 1, relationships, "", "https://github.com/halfmanhalfgeek/MDLProject#9"));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
		@Test
		public void getentity2() {
		
			GraphPuller gs = null;

			gs = new GraphPuller();
			gs.setQueryEndpoint(queryEndpoint);
			gs.setEchoStatement(false);
			ArrayList<String> relationships = new ArrayList<String>(5);
			relationships.add("GENERATED");
			relationships.add("USED");

			try {
				System.out.println(gs.pullEntitytoJson("https://github.com/halfmanhalfgeek/MDLProject#",  "repo:911493aa4eb180787e86b19d4275071565a8df46/models/warfarin_conc.csv", Long.valueOf(1), relationships));
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}






		}

	}
