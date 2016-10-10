package cz.koncentrator_v2.api.cert.GenerateServer.test;

import org.json.JSONObject;
import org.testng.Assert;
import org.testng.annotations.Test;

import cz.ivantichy.fileutils.FileWork;
import cz.koncentrator_v2.api.cert.CreateCa.Impls.CreateCaCERT_TUN_basicImpl;
import cz.koncentrator_v2.api.cert.GenerateServer.Impls.GenerateServerCERT_TUN_basicImpl;

public class GenerateServerCERT_TUN_basicImplTest {

	@Test(priority=2)
	public static void test() throws Exception {

		GenerateServerCERT_TUN_basicImpl gs = new GenerateServerCERT_TUN_basicImpl();

		JSONObject json = new JSONObject();

		json.put("subvpn_name", "test123");
		json.put("ca_name", "testca123");
		json.put("server_name", "server123");
		json.put("subvpn_type", "tun-basic");
		json.put("server_valid_days", "1");
		json.put("domain", "test.domain.koncentrator.cz");

		gs.generate(json);
		Assert.assertTrue(FileWork
				.fileExists("/etc/openvpn/easy-rsa/2.0/instances/tun-basic/testca123/server123.json"));

	}

}
