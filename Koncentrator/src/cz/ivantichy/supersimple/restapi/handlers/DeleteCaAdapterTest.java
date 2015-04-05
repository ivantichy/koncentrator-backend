package cz.ivantichy.supersimple.restapi.handlers;

import java.io.IOException;
import java.util.HashMap;

import org.testng.annotations.Test;

import cz.ivantichy.supersimple.restapi.server.DELETERequest;

public class DeleteCaAdapterTest {
	@Test()
	public static void main(String[] args) throws IOException {

		DeleteCaAdapter dca = new DeleteCaAdapter();
		
		HashMap<String, String> getparams = new HashMap<String, String>();

		getparams.put("subvpn_name", "tun-basic-12345");
		getparams.put("subvpn_type", "tun-basic");
		
		dca.handleDELETE(new DELETERequest(getparams, null, null, null));
		
	}
}
