package cz.ivantichy.httpapi.handlers.vpnapi;

import java.io.IOException;
import java.util.HashMap;

import org.testng.annotations.Test;

import cz.ivantichy.supersimple.restapi.server.DELETERequest;

public class DeleteProfileAdapterTest {

	@Test()
	public static void main(String[] args) throws IOException {

		DeleteProfileAdapter dpa = new DeleteProfileAdapter();

		HashMap<String, String> getparams = new HashMap<String, String>();

		getparams.put("subvpn_name", args[0]);
		getparams.put("subvpn_type", args[1]);
		getparams.put("common_name", args[2]);

		dpa.handleDELETE(new DELETERequest(getparams, null, null, null));

	}
}
