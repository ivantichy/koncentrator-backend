package cz.ivantichy.supersimple.restapi.handlers;

import java.io.IOException;

import org.json.JSONObject;
import org.testng.annotations.Test;

import cz.ivantichy.supersimple.restapi.server.PUTRequest;

public class CreateCaAdapterTest {

	@Test()
	public static void main(String[] args) throws IOException {

		String name = "tun-basic-12345";
		String type = "tun-basic";
		String domain = name;
		int days = 3650;

		JSONObject json = new JSONObject();

		json.put("subvpn_name", name);
		json.put("subvpn_type", type);
		json.put("domain", domain);
		json.put("ca_valid_days", days);

		CreateCaAdapter cca = new CreateCaAdapter();

		System.out.println(cca.handlePUT(new PUTRequest(null, null, null, json.toString(), null)));
		

	}

}