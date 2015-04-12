package cz.ivantichy.httpapi.handlers.vpnapi;

import java.io.IOException;

import cz.ivantichy.httpapi.handlers.vpnapi.alliance.BlockAllianceAdapter;
import cz.ivantichy.httpapi.handlers.vpnapi.alliance.CreateAllianceAdapter;
import cz.ivantichy.httpapi.handlers.vpnapi.alliance.DeleteAllianceAdapter;
import cz.ivantichy.supersimple.restapi.server.Server;

public class RunnerVPN {
	
	public static void main(String[] args) throws IOException {
		
	
	Server s = new Server();

	s.registerPUTHandler("/createsubvpn/", new CreateSubVPNAdapter());
	s.registerPOSTHandler("/blocksubvpn/", new BlockSubVPNAdapter());
	s.registerDELETEHandler("/deletesubvpn/", new DeleteSubVPNAdapter());

	
	s.registerPUTHandler("/createserver/", new  CreateServerAdapter());
	
	s.registerPUTHandler("/createprofile/", new CreateProfileAdapter());
	s.registerPOSTHandler("/blockprofile/", new BlockProfileAdapter());
	s.registerDELETEHandler("/deleteprofile/", new DeleteProfileAdapter());

	s.registerPUTHandler("/createalliance/", new CreateAllianceAdapter());
	s.registerPOSTHandler("/blockalliance/", new BlockAllianceAdapter());
	s.registerDELETEHandler("/deletealliance/", new DeleteAllianceAdapter());
	
	s.listen(10002);
	

	}

}
