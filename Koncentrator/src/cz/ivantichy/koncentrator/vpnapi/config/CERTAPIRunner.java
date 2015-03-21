package cz.ivantichy.koncentrator.vpnapi.config;

import java.io.IOException;

import cz.ivantichy.supersimple.restapi.handlers.CreateCaAdapter;
import cz.ivantichy.supersimple.restapi.handlers.DeleteCaAdapter;
import cz.ivantichy.supersimple.restapi.handlers.GenerateProfileAdapter;
import cz.ivantichy.supersimple.restapi.handlers.GenerateServerAdapter;
import cz.ivantichy.supersimple.restapi.server.Server;

public class CERTAPIRunner {

	public static void main(String[] args) throws IOException {

		Server s = new Server();

		s.registerGETHandler("/generateserver/", new GenerateServerAdapter());
		s.registerPUTHandler("/createca/", new CreateCaAdapter());
		s.registerDELETEHandler("/deleteca/", new DeleteCaAdapter());
		s.registerGETHandler("/generateprofile/", new GenerateProfileAdapter());

		int port;

		if (args.length > 0) {
			port = Integer.parseInt(args[0]);

		} else {
			port = 8081;
		}
		s.listen(port);

	}
}
