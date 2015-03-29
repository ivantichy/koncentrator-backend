package cz.ivantichy.koncentrator.vpnapi.config;

import cz.ivantichy.supersimple.restapi.config.UnixConfigReader;
import cz.ivantichy.supersimple.restapi.config.VPNAPIConfigReader;
import cz.ivantichy.supersimple.restapi.server.Server;

public class VPNAPIRunner extends UnixConfigReader {

	public static void main(String[] args) throws Exception {

		UnixConfigReader reader = new VPNAPIConfigReader();
		// running server returned;
		@SuppressWarnings("unused")
		Server s = reader.readConfigAndListen(args);

	}
}