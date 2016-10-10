package cz.koncentrator_v2.api.cert.CreateCa.test;



import org.json.JSONObject;
import org.testng.Assert;
import org.testng.annotations.Test;

import cz.ivantichy.fileutils.FileWork;
import cz.koncentrator_v2.api.cert.CreateCa.Impls.CreateCaCERT_TUN_basicImpl;

public class CreateCaCERT_TUN_basicImplTest {

	@Test
	public static void testName() throws Exception {

		CreateCaCERT_TUN_basicImpl c = new CreateCaCERT_TUN_basicImpl();

		JSONObject json = new JSONObject();

		json.put("subvpn_name", "test123");
		json.put("subvpn_type", "tun-basic");
		json.put("ca_name", "testca123");
		json.put("ca_valid_days", "1");
		json.put("domain", "test.domain.koncentrator.cz");
		

		c.create(json);
		Assert.assertTrue(FileWork
				.folderExists("/etc/openvpn/easy-rsa/2.0/instances/tun-basic/testca123/"));

	}
}
