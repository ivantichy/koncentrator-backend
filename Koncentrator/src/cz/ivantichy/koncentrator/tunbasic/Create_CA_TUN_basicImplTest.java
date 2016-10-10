package cz.ivantichy.koncentrator.tunbasic;

import java.io.IOException;
import java.util.HashMap;

import org.json.JSONObject;
import org.testng.annotations.Test;

import cz.ivantichy.supersimple.restapi.handlers.GenerateServerAdapter;
import cz.ivantichy.supersimple.restapi.server.GETRequest;
import cz.koncentrator_v2.api.cert.common.CERTFactory;

public class Create_CA_TUN_basicImplTest {

	@Test(priority = 2)
	public static void test() throws IOException {

		Create_CA_TUN_basicImpl create = new Create_CA_TUN_basicImpl();

		JSONObject json = new JSONObject();

		json.put("subvpn_name", "test123");
		json.put("subvpn_type", "tun-basic");
		json.put("server_name", "test-server-123");
		json.put("ca", "dummyca");
		json.put("ta", "dummyta");
		json.put("dh", "dummydh");
		json.put("cert", "dummycert");
		json.put("key", "dummykey");
		json.put("proto", "udp");
		json.put("port", "1194");
		json.put("dev", "tun0");
		json.put("mng_port", "12345");

		create.create(json);
	}

	@Test(priority = 3)
	public static void testWithRealCerts() throws Exception {

		// CreateCa cc = new CreateCa();

		GenerateServerAdapter gsa = new GenerateServerAdapter();

		HashMap<String, String> getparams = new HashMap<String, String>();

		JSONObject json = new JSONObject();

		json.put("subvpn_name", "test123");
		json.put("subvpn_type", "tun-basic");
		json.put("server_name", "test-server-123");
		json.put("common_name", "test-server-123");
		json.put("domain", "test-server-123.tun-basic.koncentrator.cz");
		json.put("ca", "dummyca");
		json.put("ta", "dummyta");
		json.put("dh", "dummydh");
		json.put("cert", "dummycert");
		json.put("key", "dummykey");
		json.put("proto", "udp");
		json.put("port", "1194");
		json.put("dev", "tun0");
		json.put("mng_port", "12345");
		json.put("override", "y"); // ca
		json.put("server_valid_days", "90");
		json.put("ca_valid_days", "90"); // ca

		JSONObject cajson = new JSONObject(CERTFactory.getInstanceForCreateCa(
				json).create(json));

		json.merge(cajson);

		// TODO kde se meni common_name?
		json.put("subvpn_name", "test123");
		json.put("subvpn_type", "tun-basic");
		json.put("server_name", "test-server-123");
		json.put("common_name", "test-server-123");

		GETRequest req = new GETRequest(getparams, null, null, null);

		String[] names = JSONObject.getNames(json);
		for (int i = 0; i < names.length; i++) {

			req.getparams.put(names[i], json.getString(names[i]));

		}

		JSONObject serverjson = new JSONObject(gsa.handleGET(
				new GETRequest(getparams, null, null, null)).toString());

		json.merge(serverjson);

		Create_CA_TUN_basicImpl create = new Create_CA_TUN_basicImpl();

		create.create(serverjson);

	}

}
