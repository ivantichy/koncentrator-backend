package cz.ivantichy.httpapi.handlers.certapi.tun_basic;

import static org.testng.AssertJUnit.*;
import java.io.IOException;

import org.json.JSONObject;
import org.testng.annotations.Test;

import cz.ivantichy.fileutils.FileWork;

public class CreateServerTUNBasicImplTest {
	
	@Test(priority=1)
	public static void test() {

		CreateServerTUNBasicImpl c = new CreateServerTUNBasicImpl();

		JSONObject json = new JSONObject();

		json.put("subvpn_name", "test123");
		json.put("subvpn_type", "tun-basicxx");
		json.put("server_name", "test-server-123");

		boolean failed = false;
		try {
			c.create(json);
		} catch (IOException e) {

			// must fail
			failed = true;

		}

		assertTrue(failed);

		assertFalse(FileWork
				.folderExists("/etc/openvpn/instances/tun-basic/test-server-123/"));

		assertFalse(FileWork

				.fileExists("/etc/openvpn/instances/tun-basic/test-server-123/test-server-123.json"));

	}
	@Test(priority=2)
	public static void test2() throws IOException {

		CreateServerTUNBasicImpl c = new CreateServerTUNBasicImpl();

		JSONObject json = new JSONObject();

		json.put("subvpn_name", "test123");
		json.put("subvpn_type", "tun-basic");
		json.put("server_name", "test-server-123");

		c.create(json);

		assertTrue(FileWork
				.folderExists("/etc/openvpn/instances/tun-basic/test-server-123/"));

		assertTrue(FileWork

				.fileExists("/etc/openvpn/instances/tun-basic/test-server-123/test-server-123.json"));

	}



}
