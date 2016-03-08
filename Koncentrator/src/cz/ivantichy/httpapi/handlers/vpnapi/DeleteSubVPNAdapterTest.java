package cz.ivantichy.httpapi.handlers.vpnapi;

import java.io.IOException;
import java.util.HashMap;

import org.testng.annotations.Test;

import cz.ivantichy.supersimple.restapi.server.DELETERequest;

public class DeleteSubVPNAdapterTest {

	@Test()
	public static void main(String[] args) throws IOException {

		DeleteSubVPNAdapter dsa = new DeleteSubVPNAdapter();

		HashMap<String, String> getparams = new HashMap<String, String>();

		getparams.put("subvpn_name", args[0]);
		getparams.put("subvpn_type", args[1]);

		dsa.handleDELETE(new DELETERequest(getparams, null, null, null));

	}
}
