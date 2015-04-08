package cz.ivantichy.httpapi.handlers.vpnapi;

import java.io.IOException;

import cz.ivantichy.supersimple.restapi.server.Server;

public class RunnerVPN {
	
	public static void main(String[] args) throws IOException {
		
	
	Server s = new Server();

	s.registerPUTHandler("/createsubvpn/", new CreateSubVPNAdapter());
	s.registerPUTHandler("/createserver/", new  CreateServerAdapter());
	s.registerPUTHandler("/createprofile/", new CreateProfileAdapter());
	s.registerPOSTHandler("/blockprofile/", new BlockProfileAdapter());
	s.registerDELETEHandler("/deleteprofile/", new DeleteProfileAdapter());
	
	s.listen(10002);
	
	

}

}
