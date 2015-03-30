package cz.ivantichy.supersimple.restapi.handlers;

import java.io.IOException;
import java.util.HashMap;

import org.testng.annotations.Test;

import cz.ivantichy.supersimple.restapi.server.GETRequest;

public class GenerateProfileAdapterTest {
	@Test()
	public static void main(String[] args) throws IOException {
		
		GenerateProfileAdapter gpa = new GenerateProfileAdapter();
		
		HashMap<String, String> getparams = new HashMap<String, String>();

		getparams.put("common_name", "test");
		getparams.put("domain", "test");
		getparams.put("profile_valid_days", "365");
		getparams.put("subvpn_name", "tun-basic-12345");
		getparams.put("subvpn_type", "tun-basic");
		
		System.out.println(gpa.handleGET(new GETRequest(getparams, null, null, null)));
		
	}

}
