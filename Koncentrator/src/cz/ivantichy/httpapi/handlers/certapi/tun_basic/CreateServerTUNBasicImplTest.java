package cz.ivantichy.httpapi.handlers.certapi.tun_basic;

import java.io.IOException;

import org.json.JSONObject;
import org.testng.annotations.Test;

public class CreateServerTUNBasicImplTest {
	@Test
	public static void main(String[] args) throws IOException {

		CreateServerTUNBasicImpl c = new CreateServerTUNBasicImpl();

		JSONObject json = new JSONObject();

		json.put("subvpn_name", "test123");
		json.put("subvpn_type", "tun-basic");

		c.create(json);

	}

}
