package cz.ivantichy.httpapi.handlers.vpnapi;

import java.io.IOException;

import cz.ivantichy.supersimple.restapi.handlers.CreateCaAdapter;
import cz.ivantichy.supersimple.restapi.handlers.DeleteCaAdapter;
import cz.ivantichy.supersimple.restapi.handlers.GenerateProfileAdapter;
import cz.ivantichy.supersimple.restapi.handlers.GenerateServerAdapter;
import cz.ivantichy.supersimple.restapi.server.Server;

public class RunnerCERT {
	
	public static void main(String[] args) throws IOException {
		
	
	Server s = new Server();

	s.registerGETHandler("/generateserver/", new GenerateServerAdapter());
	s.registerPUTHandler("/createca/", new CreateCaAdapter());
	s.registerDELETEHandler("/deleteca/", new DeleteCaAdapter());
	s.registerGETHandler("/generateprofile/", new GenerateProfileAdapter());
	s.listen(10001);
	
	

}

}
