package cz.ivantichy.koncentrator.simple.certgen;

import org.json.JSONObject;

public class CreateCaTest {

	public static void main(String[] args) {

		String name = "tun-basic-12345";
		String type = "tun-basic";
		String domain = name;
		int days = 3650;

		JSONObject json = new JSONObject();

		json.put("subvpn_name", name);
		json.put("subvpn_type", type);
		json.put("domain", domain);
		json.put("valid_days", days);

	}

}
