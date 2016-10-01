package cz.ivantichy.supersimple.restapi.handlers;

import java.io.IOException;
import java.util.HashMap;

import org.testng.annotations.Test;

import cz.ivantichy.fileutils.FileWork;
import cz.ivantichy.supersimple.restapi.server.GETRequest;

public class GenerateServerAdapterTest {

	@Test()
	public static void main(String[] args) throws IOException {

		GenerateServerAdapter gsa = new GenerateServerAdapter();

		HashMap<String, String> getparams = new HashMap<String, String>();

		getparams.put("common_name", "test");
		getparams.put("domain", "test");
		getparams.put("server_valid_days", "3650");
		getparams.put("subvpn_name", "tun-basic-12345");
		getparams.put("subvpn_type", "tun-basic");

		if (args.length > 0) {
			
			getparams.put("common_name", args[0]);
			getparams.put("domain", args[1]);
			getparams.put("subvpn_name", args[1]);
			getparams.put("subvpn_type", args[2]);

		}

		FileWork.saveFile("server.json",
				gsa.handleGET(new GETRequest(getparams, null, null, null))
						.toString());

	}
}
