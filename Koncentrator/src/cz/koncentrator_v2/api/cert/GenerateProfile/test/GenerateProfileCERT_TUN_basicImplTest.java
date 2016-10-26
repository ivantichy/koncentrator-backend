package cz.koncentrator_v2.api.cert.GenerateProfile.test;

import org.json.JSONObject;
import org.testng.Assert;
import org.testng.annotations.Test;

import cz.ivantichy.fileutils.FileWork;
import cz.koncentrator_v2.api.cert.CreateCa.Impls.CreateCaCERT_TUN_basicImpl;
import cz.koncentrator_v2.api.cert.GenerateProfile.impls.GenerateProfileCERT_TUN_basicImpl;
import cz.koncentrator_v2.api.cert.GenerateServer.Impls.GenerateServerCERT_TUN_basicImpl;

public class GenerateProfileCERT_TUN_basicImplTest {

	@Test(priority = 4)
	public static void test() throws Exception {

		GenerateProfileCERT_TUN_basicImpl gp = new GenerateProfileCERT_TUN_basicImpl();

		JSONObject json = new JSONObject();

		//TODO zkusit ubrat parametry
		json.put("subvpn_name", "test123");
		json.put("common_name", "cn123");
		json.put("ca_name", "testca123");
		json.put("subvpn_type", "tun-basic");
		json.put("profile_valid_days", "1");
		json.put("domain", "test.domain.koncentrator.cz");

		gp.generate(json);
		Assert.assertTrue(FileWork
				.fileExists("/etc/openvpn/easy-rsa/2.0/instances/tun-basic/testca123/cn123.json"));

	}

}
